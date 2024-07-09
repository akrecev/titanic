package ru.iteratia.titanic.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.iteratia.titanic.model.Passenger;
import ru.iteratia.titanic.service.PassengerService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/passengers")
public class PassengerController {
    private final PassengerService passengerService;

    @GetMapping
    public String listPassengers(Model model,
                                 @RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "50") int size,
                                 @RequestParam(required = false, defaultValue = "") String name,
                                 @RequestParam(required = false) Boolean survived,
                                 @RequestParam(required = false) Integer minAge,
                                 @RequestParam(required = false) String gender,
                                 @RequestParam(required = false) Boolean hasRelatives) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Passenger> passengerPage = passengerService.getPassengers(pageable);
        List<Passenger> passengers = passengerService.getFilteredPassengers(name, survived, minAge, gender, hasRelatives);
        model.addAttribute("passengerPage", passengerPage);
        model.addAttribute("passengers", passengers);
        return "templates/index";
    }
}
