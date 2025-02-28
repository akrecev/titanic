package ru.iteratia.titanic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.iteratia.titanic.model.Gender;
import ru.iteratia.titanic.model.Passenger;

import java.util.List;

public interface PassengerRepository extends JpaRepository<Passenger, Long> {
    @Query("""
            SELECT p FROM Passenger p
            WHERE (UPPER(p.name) LIKE UPPER(CONCAT('%', :name, '%')))
            AND (:survived IS NULL OR p.survived = :survived)
            AND (:minAge IS NULL OR p.age > :minAge)
            AND (:gender IS NULL OR p.gender = :gender)
            AND (:hasRelatives IS NULL
                OR (:hasRelatives = FALSE AND (p.parentsChildrenAboard = 0 AND p.siblingsSpousesAboard = 0))
                OR (:hasRelatives = TRUE AND (p.parentsChildrenAboard > 0 OR p.siblingsSpousesAboard > 0)))
            """)
    List<Passenger> findFilteredPassengers(String name, Boolean survived, Integer minAge, Gender gender, Boolean hasRelatives);
}