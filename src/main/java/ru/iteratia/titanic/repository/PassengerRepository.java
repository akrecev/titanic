package ru.iteratia.titanic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.iteratia.titanic.model.Passenger;

public interface PassengerRepository extends JpaRepository<Passenger, Long> {
}
