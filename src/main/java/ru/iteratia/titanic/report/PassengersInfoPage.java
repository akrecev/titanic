package ru.iteratia.titanic.report;

import org.springframework.data.domain.Page;
import ru.iteratia.titanic.model.Passenger;

public record PassengersInfoPage(Page<Passenger> passengerPage, Statistics statistics) {
}
