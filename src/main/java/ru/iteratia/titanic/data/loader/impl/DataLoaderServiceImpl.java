package ru.iteratia.titanic.data.loader.impl;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.iteratia.titanic.data.loader.LoaderService;

@Service
@RequiredArgsConstructor
public class DataLoaderServiceImpl implements LoaderService {
    private final RestTemplate restTemplate;

    /**
     * url адрес для загрузки данных указан в src/main/resources/application.yml
     */
    @Value("${dataUrl}")
    private String url;

    /**
     * метод загрузки данных о пассажирах Титаника
     */
    @Override
    @PostConstruct
    public String loadData() {
        return restTemplate.getForObject(url, String.class);
    }
}
