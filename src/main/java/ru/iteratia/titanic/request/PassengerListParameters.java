package ru.iteratia.titanic.request;

import ru.iteratia.titanic.model.Gender;

public record PassengerListParameters(
        String name, Boolean survived, Integer minAge, Gender gender, Boolean hasRelatives) {
}
