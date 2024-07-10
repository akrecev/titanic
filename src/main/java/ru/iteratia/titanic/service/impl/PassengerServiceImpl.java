package ru.iteratia.titanic.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.iteratia.titanic.model.Gender;
import ru.iteratia.titanic.model.Passenger;
import ru.iteratia.titanic.repository.PassengerRepository;
import ru.iteratia.titanic.service.PassengerService;
import ru.iteratia.titanic.model.Statistics;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PassengerServiceImpl implements PassengerService {
    private final PassengerRepository passengerRepository;

    @Override
    public Page<Passenger> getPassengers(Pageable pageable) {
        return passengerRepository.findAll(pageable);
    }

    @Override
    public List<Passenger> getFilteredPassengers(
            String name, Boolean survived, Integer minAge, String gender, Boolean hasRelatives
    ) {
        Gender sex = (gender == null)
                ? null
                : Gender.valueOf(gender.toUpperCase());

        return passengerRepository.findFilteredPassengers(name, survived, minAge, sex, hasRelatives);
    }

    @Override
    public Statistics getStatistics() {
        List<Passenger> passengers = passengerRepository.findAll();
        double totalFare = passengers.stream().mapToDouble(Passenger::getFare).sum();
        long passengersWithRelatives = passengers.stream().filter(p -> p.getSiblingsSpousesAboard() > 0 || p.getParentsChildrenAboard() > 0).count();
        long survivedPassengers = passengers.stream().filter(Passenger::getSurvived).count();
        return new Statistics(totalFare, passengersWithRelatives, survivedPassengers);
    }
}
