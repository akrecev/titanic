package ru.iteratia.titanic.service;

import ru.iteratia.titanic.report.PassengersInfoPage;
import ru.iteratia.titanic.request.PassengerListParameters;
import ru.iteratia.titanic.request.PaginationRequest;

public interface PassengerService {
    PassengersInfoPage getPassengersInfo(PassengerListParameters parameters, PaginationRequest paginationRequest);

}
