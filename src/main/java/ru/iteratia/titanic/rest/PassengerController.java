package ru.iteratia.titanic.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.iteratia.titanic.model.Passenger;
import ru.iteratia.titanic.service.PassengerService;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/passengers")
public class PassengerController {
    private final PassengerService passengerService;

    @GetMapping
    public String listPassengers(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "50") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Passenger> passengerPage = passengerService.getPassengers(pageable);

        return passengerPage.stream().map(passenger -> passenger.toString() + "\n").toList().toString();
    }
}
