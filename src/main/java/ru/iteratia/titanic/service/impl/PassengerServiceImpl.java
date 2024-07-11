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
import ru.iteratia.titanic.report.PassengersInfoPage;
import ru.iteratia.titanic.report.Statistics;
import ru.iteratia.titanic.request.PassengerListParameters;
import ru.iteratia.titanic.request.PaginationRequest;
import ru.iteratia.titanic.service.PassengerService;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PassengerServiceImpl implements PassengerService {
    private final PassengerRepository passengerRepository;

    /**
     * Основной метод получения данных о пассажирах
     * @param parameters - параметры сортировки и поиска: name, survived, minAge, gender, hasRelatives
     * @param paginationRequest - параметры пагинации и сортировки
     * @return - возвращает страницу с перечнем пассажиров, удовлетворяющих условиям поиска и сортировки
     */
    @Override
    @Cacheable("passengersInfo")
    public PassengersInfoPage getPassengersInfo(PassengerListParameters parameters, PaginationRequest paginationRequest) {
        Pageable pageable = PageRequest.of(paginationRequest.page(), paginationRequest.size());
        List<Passenger> passengers = passengerRepository
                .findFilteredPassengers(parameters.name(), parameters.survived(),
                        parameters.minAge(), parameters.gender(), parameters.hasRelatives());

        if (passengers.isEmpty()) {
            throw new PassengerNotFoundException("Пассажиры не найдены");
        }

        List<Passenger> sortedList = sortList(passengers, paginationRequest);
        Page<Passenger> passengerPage = getPassengersPage(sortedList, pageable);
        Statistics statistics = getStatistics(sortedList);

        return new PassengersInfoPage(passengerPage, statistics);
    }

    /**
     * Метод для сортировки списка пассажиров согласно входных параметров
     * @param passengerList - сортируемый список пассажиров
     * @param paginationRequest - параметры пагинации и сортировки
     * @return - спиок пассажиров, отсортированный согласно входных параметров
     */
    private List<Passenger> sortList(List<Passenger> passengerList, PaginationRequest paginationRequest) {
        switch (paginationRequest.sortField()) {
            case "name" -> passengerList.sort(Comparator.comparing(Passenger::getName));
            case "age" -> passengerList.sort(Comparator.comparing(Passenger::getAge));
            case "fare" -> passengerList.sort(Comparator.comparing(Passenger::getFare));
        }
        if (!Objects.equals(paginationRequest.sortDirection(), "asc")) {
            Collections.reverse(passengerList);
        }

        return passengerList;
    }


    /**
     * Метод для формирования страницы из выбранного списка пассажиров
     * @param passengers - исходный список пассажиров
     * @param pageable - параметры пагинации
     * @return - возвращает сформированную страницу
     */
    private Page<Passenger> getPassengersPage(List<Passenger> passengers, Pageable pageable) {
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), passengers.size());
        List<Passenger> page = passengers.subList(start, end);

        return new PageImpl<>(page, pageable, passengers.size());
    }

    /**
     * Метод для получения статистики исходя из отобранного списка пассажиров
     * @param passengers - исходный список для сбора статистических данных
     * @return - объект, содержащий статистические данные (общая сумма оплаты за билеты,
     *           количество пассажиров с родственниками, количество выживших пассажиров)
     */
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
