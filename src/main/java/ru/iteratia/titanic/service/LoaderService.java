package ru.iteratia.titanic.service;

import java.util.List;

public interface LoaderService {
    String loadData();
    List<?> parseData();
    void saveData();

}
