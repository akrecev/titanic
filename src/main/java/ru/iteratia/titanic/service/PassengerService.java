package ru.iteratia.titanic.service;

import ru.iteratia.titanic.report.PassengersInfoPage;
import ru.iteratia.titanic.request.PassengerListParameters;
import ru.iteratia.titanic.request.SortType;

public interface PassengerService {
    PassengersInfoPage getPassengersInfo(PassengerListParameters parameters, SortType sortType);

}
