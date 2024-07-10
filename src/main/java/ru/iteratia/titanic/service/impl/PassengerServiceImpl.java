package ru.iteratia.titanic.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.iteratia.titanic.exception.PassengerNotFoundException;
import ru.iteratia.titanic.model.Gender;
import ru.iteratia.titanic.model.Passenger;
import ru.iteratia.titanic.repository.PassengerRepository;
import ru.iteratia.titanic.request.PassengersInfoPage;
import ru.iteratia.titanic.request.Statistics;
import ru.iteratia.titanic.service.PassengerService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PassengerServiceImpl implements PassengerService {
    private final PassengerRepository passengerRepository;

    @Override
    @Cacheable("passengersInfo")
    public PassengersInfoPage getPassengersInfo(
            Pageable pageable, String name, Boolean survived, Integer minAge, Gender gender, Boolean hasRelatives
    ) {
        name = name.trim();
        List<Passenger> passengers = passengerRepository.findFilteredPassengers(name, survived, minAge, gender, hasRelatives);

        if (passengers.isEmpty()) {
            throw new PassengerNotFoundException("Пассажиры не найдены");
        }

        return new PassengersInfoPage(
                getPassengers(passengers, pageable),
                getStatistics(passengers)
        );
    }

    public Page<Passenger> getPassengers(List<Passenger> passengers, Pageable pageable) {
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), passengers.size());
        List<Passenger> page = passengers.subList(start, end);

        return new PageImpl<>(page, pageable, passengers.size());
    }

    public Statistics getStatistics(List<Passenger> passengers) {
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
