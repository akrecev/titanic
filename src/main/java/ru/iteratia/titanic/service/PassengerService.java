package ru.iteratia.titanic.service;

import ru.iteratia.titanic.request.PassengersInfoPage;
import ru.iteratia.titanic.responce.PassengerListParameters;
import ru.iteratia.titanic.responce.SortType;

public interface PassengerService {
    PassengersInfoPage getPassengersInfo(PassengerListParameters parameters, SortType sortType);

}
