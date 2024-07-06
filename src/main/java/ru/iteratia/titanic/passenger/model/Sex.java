package ru.iteratia.titanic.passenger.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Sex {
    MALE("male"),
    FEMALE("female");

    private final String sex;
}
