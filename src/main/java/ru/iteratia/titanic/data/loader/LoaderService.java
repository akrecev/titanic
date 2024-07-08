package ru.iteratia.titanic.data.loader;

import java.util.List;

public interface LoaderService {
    String loadData();
    List<?> parseData();
    void saveData();

}
