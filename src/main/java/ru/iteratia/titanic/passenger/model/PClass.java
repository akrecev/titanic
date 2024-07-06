package ru.iteratia.titanic.passenger.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PClass {
    FIRST(1),
    SECOND(2),
    THIRD(3);

    private final Integer PClass;
}
