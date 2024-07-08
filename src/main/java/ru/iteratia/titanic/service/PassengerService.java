package ru.iteratia.titanic.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.iteratia.titanic.model.Passenger;

public interface PassengerService {
    Page<Passenger> getPassengers(Pageable pageable);
}
