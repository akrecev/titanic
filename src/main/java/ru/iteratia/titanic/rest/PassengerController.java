package ru.iteratia.titanic.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.iteratia.titanic.model.Passenger;
import ru.iteratia.titanic.service.PassengerService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping(path = "/passengers")
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
        List<Passenger> passengers = passengerService.getFilteredPassengers(name, survived, minAge, gender, hasRelatives);
        Page<Passenger> passengerPage = passengerService.getPassengers(pageable);
        model.addAttribute("passengerPage", passengerPage);
        model.addAttribute("statistics", passengerService.getStatistics());
        return "passengers";
    }

}
