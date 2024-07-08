package ru.iteratia.titanic.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.iteratia.titanic.model.Passenger;
import ru.iteratia.titanic.repository.PassengerRepository;
import ru.iteratia.titanic.service.PassengerService;

@Service
@RequiredArgsConstructor
public class PassengerServiceImpl implements PassengerService {
    private final PassengerRepository passengerRepository;

    @Override
    public Page<Passenger> getPassengers(Pageable pageable) {
        return passengerRepository.findAll(pageable);
    }
}
