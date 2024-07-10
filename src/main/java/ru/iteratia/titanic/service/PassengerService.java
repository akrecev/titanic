package ru.iteratia.titanic.service;

import org.springframework.data.domain.Pageable;
import ru.iteratia.titanic.model.Gender;
import ru.iteratia.titanic.request.PassengersInfoPage;

public interface PassengerService {
    PassengersInfoPage getPassengersInfo(
            Pageable pageable, String name, Boolean survived, Integer minAge, Gender gender, Boolean hasRelatives);

}
