package ru.iteratia.titanic.passenger.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.iteratia.titanic.passenger.model.Passenger;

public interface PassengerRepository extends JpaRepository<Passenger, Long> {
}
