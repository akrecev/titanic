package ru.iteratia.titanic.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.iteratia.titanic.model.Passenger;

import java.util.List;

public interface PassengerService {
    Page<Passenger> getPassengers(Pageable pageable);

    List<Passenger> getFilteredPassengers(String name, Boolean survived, Integer minAge, String gender, Boolean hasRelatives);
}
