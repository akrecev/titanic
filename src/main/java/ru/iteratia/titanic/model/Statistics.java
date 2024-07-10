package ru.iteratia.titanic.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Statistics {
    private final double totalFare;
    private final long passengersWithRelatives;
    private final long survivedPassengers;

}
