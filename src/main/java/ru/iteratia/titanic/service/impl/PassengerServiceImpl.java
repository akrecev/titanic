package ru.iteratia.titanic.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.iteratia.titanic.exception.PassengerNotFoundException;
import ru.iteratia.titanic.model.Passenger;
import ru.iteratia.titanic.repository.PassengerRepository;
import ru.iteratia.titanic.request.PassengersInfoPage;
import ru.iteratia.titanic.request.Statistics;
import ru.iteratia.titanic.responce.PassengerListParameters;
import ru.iteratia.titanic.responce.SortType;
import ru.iteratia.titanic.service.PassengerService;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PassengerServiceImpl implements PassengerService {
    private final PassengerRepository passengerRepository;

    @Override
    @Cacheable("passengersInfo")
    public PassengersInfoPage getPassengersInfo(PassengerListParameters parameters, SortType sortType) {
        Pageable pageable = PageRequest.of(sortType.page(), sortType.size());
        List<Passenger> passengers = passengerRepository
                .findFilteredPassengers(parameters.name(), parameters.survived(),
                        parameters.minAge(), parameters.gender(), parameters.hasRelatives());

        if (passengers.isEmpty()) {
            throw new PassengerNotFoundException("Пассажиры не найдены");
        }

        List<Passenger> sortedList = sortList(passengers, sortType);
        Page<Passenger> passengerPage = getPassengersPage(sortedList, pageable);
        Statistics statistics = getStatistics(sortedList);

        return new PassengersInfoPage(passengerPage, statistics);
    }

    private List<Passenger> sortList(List<Passenger> passengerList, SortType sortType) {
        switch (sortType.sortField()) {
            case "name" -> passengerList.sort(Comparator.comparing(Passenger::getName));
            case "age" -> passengerList.sort(Comparator.comparing(Passenger::getAge));
            case "fare" -> passengerList.sort(Comparator.comparing(Passenger::getFare));
        }
        if (!Objects.equals(sortType.sortDirection(), "asc")) {
            Collections.reverse(passengerList);
        }

        return passengerList;
    }

    private Page<Passenger> getPassengersPage(List<Passenger> passengers, Pageable pageable) {
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), passengers.size());
        List<Passenger> page = passengers.subList(start, end);

        return new PageImpl<>(page, pageable, passengers.size());
    }

    private Statistics getStatistics(List<Passenger> passengers) {
        double totalFare = passengers.stream()
                .mapToDouble(Passenger::getFare)
                .sum();
        long passengersWithRelatives = passengers.stream()
                .filter(p -> p.getSiblingsSpousesAboard() > 0 || p.getParentsChildrenAboard() > 0)
                .count();
        long survivedPassengers = passengers.stream()
                .filter(Passenger::getSurvived)
                .count();

        return new Statistics(totalFare, passengersWithRelatives, survivedPassengers);
    }
}
