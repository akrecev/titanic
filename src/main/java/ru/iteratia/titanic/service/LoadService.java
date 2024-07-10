package ru.iteratia.titanic.service;

import java.util.List;

public interface LoadService {
    String loadData();

    List<?> parseData();

    void saveData();

}
