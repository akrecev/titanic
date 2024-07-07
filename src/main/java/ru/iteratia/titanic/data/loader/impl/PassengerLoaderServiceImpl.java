package ru.iteratia.titanic.data.loader.impl;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.iteratia.titanic.data.loader.LoaderService;

@Service
public class PassengerLoaderServiceImpl implements LoaderService {

    /**
     * url адрес для загрузки данных, указан в src/main/resources/application.yml
     */
    @Value("${dataUrl}")
    private String url;

    /**
     * метод для загрузки данных о пассажирах Титаника
     */
    @Override
    @PostConstruct
    public String loadData() {
        return new RestTemplate().getForObject(url, String.class);
    }
}
