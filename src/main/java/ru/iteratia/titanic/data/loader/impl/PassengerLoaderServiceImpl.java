package ru.iteratia.titanic.data.loader.impl;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.iteratia.titanic.data.loader.LoaderService;
import ru.iteratia.titanic.passenger.model.PClass;
import ru.iteratia.titanic.passenger.model.Passenger;
import ru.iteratia.titanic.passenger.model.Sex;
import ru.iteratia.titanic.passenger.repository.PassengerRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PassengerLoaderServiceImpl implements LoaderService {
    private final PassengerRepository passengerRepository;

    /**
     * Url адрес для загрузки данных, указан в src/main/resources/application.yml
     */
    @Value("${dataUrl}")
    private String url;

    /**
     * Метод для загрузки данных о пассажирах Титаника
     */
    @Override
    public String loadData() {
        return new RestTemplate().getForObject(url, String.class);
    }

    /**
     * Метод для преобразования текстовых данных о пассажирах Титаника в список объектов Passenger
     */
    @Override
    public List<Passenger> parseData() {
        List<Passenger> passengers = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new StringReader(loadData()))) {
            String line;
            boolean firstLine = true;
            while ((line = reader.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue;
                }
                String[] fields = line.split(",");
                Passenger passenger = new Passenger();
                passenger.setSurvived(fields[0].equals("1"))
                        .setPClass(PClass.values()[Integer.parseInt(fields[1]) - 1])
                        .setName(fields[2])
                        .setSex(Sex.valueOf(fields[3].toUpperCase()))
                        .setAge((int) Math.floor(Double.parseDouble(fields[4])))
                        .setSiblingsSpousesAboard(Integer.parseInt(fields[5]))
                        .setParentsChildrenAboard(Integer.parseInt(fields[6]))
                        .setFare(Double.parseDouble(fields[7]));
                passengers.add(passenger);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return passengers;
    }

    /**
     * Метод для сохранения информации о пассажирах в базу данных,
     * обеспечивает автоматическую загрузку данных при инициализации приложения
     */
    @Override
    @PostConstruct
    public void saveData() {
        if (passengerRepository.count() == 0) {
            passengerRepository.saveAll(parseData());
        }
    }
}
