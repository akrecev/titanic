package ru.iteratia.titanic.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.iteratia.titanic.model.Passenger;
import ru.iteratia.titanic.model.Sex;
import ru.iteratia.titanic.repository.PassengerRepository;
import ru.iteratia.titanic.service.PassengerService;

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
        name = (name == null)
                ? ""
                : name;
        Sex sex = (gender == null)
                ? null
                : Sex.valueOf(gender.toUpperCase());

        return passengerRepository.findFilteredPassengers(name, survived, minAge, sex, hasRelatives);
    }
}
