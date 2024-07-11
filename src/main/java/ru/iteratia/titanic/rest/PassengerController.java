package ru.iteratia.titanic.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.iteratia.titanic.model.Gender;
import ru.iteratia.titanic.report.PassengersInfoPage;
import ru.iteratia.titanic.request.PassengerListParameters;
import ru.iteratia.titanic.request.PaginationRequest;
import ru.iteratia.titanic.service.PassengerService;

import java.util.Map;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
@RequestMapping(path = "/passengers")
public class PassengerController {
    private final PassengerService passengerService;

    /**
     * Основной метод контроллера для вывода списка пассажиров по заданным параметрам
     * @param model - объект для передачи папаметров в шаблон отображения .html
     * @param page - номер старницы
     * @param size - размер страницы (количество пассажиров на странице)
     * @param name - параметр поиска - имя пассажира
     * @param survived - параметр отображения выживших пассажиров
     * @param minAge - мимнимальный возраст пассажиров для отображения
     * @param gender - пол пассажира
     * @param hasRelatives - наличие родственников пассажира на борту
     * @param sortField - наименование параметра для сортировки
     * @param sortDirection - тип сортировки (по возрастанию/убыванию)
     * @return - возвращает html страницу со списком пассажиров, отобранных согласно параметрам
     */
    @GetMapping
    public String listPassengers(Model model,
                                 @RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "50") int size,
                                 @RequestParam(required = false, defaultValue = "") String name,
                                 @RequestParam(required = false) Boolean survived,
                                 @RequestParam(required = false) Integer minAge,
                                 @RequestParam(required = false, defaultValue = "") String gender,
                                 @RequestParam(required = false) Boolean hasRelatives,
                                 @RequestParam(defaultValue = "name") String sortField,
                                 @RequestParam(defaultValue = "asc") String sortDirection) {


        Gender wrapGender = (Objects.equals(gender, ""))
                ? null
                : Gender.valueOf(gender.toUpperCase());

        PassengersInfoPage passengersInfo = passengerService
                .getPassengersInfo(
                        new PassengerListParameters(name.trim(), survived, minAge, wrapGender, hasRelatives),
                        new PaginationRequest(page, size, sortField, sortDirection)
                );

        model.addAttribute("passengerPage", passengersInfo.passengerPage());
        model.addAttribute("statistics", passengersInfo.statistics());
        model.addAttribute("param", Map.of(
                "page", page,
                "size", size,
                "name", name,
                "survived", survived == null ? "" : survived,
                "minAge", minAge == null ? "" : minAge,
                "gender", gender,
                "hasRelatives", hasRelatives == null ? "" : hasRelatives
        ));
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDirection", sortDirection);

        return "passengers";
    }

}
