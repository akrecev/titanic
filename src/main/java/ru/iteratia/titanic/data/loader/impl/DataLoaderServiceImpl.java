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

    @Value("${data}")
    private String url;

    @Override
    @PostConstruct
    public String loadData() {
        return restTemplate.getForObject(url, String.class);
    }
}
