package ru.iteratia.titanic.data.parser;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.iteratia.titanic.data.loader.LoaderService;
import ru.iteratia.titanic.passenger.model.PClass;
import ru.iteratia.titanic.passenger.model.Passenger;
import ru.iteratia.titanic.passenger.model.Sex;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PassengerParserServiceImpl implements ParserService {
    private final LoaderService loaderService;

    /**
     * метод для преобразования текстовых данных о пассажирах Титаника в список объектов Passenger
     */
    @Override
    @PostConstruct
    public List<Passenger> parseData() {
        List<Passenger> passengers = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new StringReader(loaderService.loadData()))) {
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
                        .setPclass(PClass.values()[Integer.parseInt(fields[1]) - 1])
                        .setName(fields[2])
                        .setSex(Sex.valueOf(fields[3].toUpperCase()))
                        .setAge((int)Math.floor(Double.parseDouble(fields[4])))
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
}
