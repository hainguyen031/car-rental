package com.hainguyen.carrental.controller;

import com.hainguyen.carrental.dto.CarDTO;
import com.hainguyen.carrental.dto.SearchCarDTO;
import com.hainguyen.carrental.exception.ObjectNotFound;
import com.hainguyen.carrental.service.IBookingService;
import com.hainguyen.carrental.service.ICarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    private ICarService iCarService;
    @Autowired
    private IBookingService iBookingService;

    @GetMapping("")
    public String goHomePage(Model model, @PageableDefault(value = 20) Pageable pageable) {
        Page<CarDTO> carDTOPage = iCarService.findAllCarPage(pageable);
        model.addAttribute("carDTOPage", carDTOPage);
        return "customer/home";
    }

    @PostMapping("/search-car")
    public String searchCar(@RequestParam("carType") Integer seat, @RequestParam("carLocation") String carLocation,
                            @RequestParam("pick-up-date") LocalDate startDate, @RequestParam("return-date") LocalDate endDate,
                            @RequestParam(defaultValue = "0") int page,
                            Model model, RedirectAttributes redirect) {
        Page<CarDTO> carDTOPage = iCarService.searchCarPage(seat, carLocation, startDate, endDate, PageRequest.of(page, 20));
//        List<CarDTO> carDTOList = iCarService.searchCarByForm(searchCarDTO);
        model.addAttribute("carDTOPage",carDTOPage);
        return "customer/home";
    }

    @ExceptionHandler(ObjectNotFound.class)
    public ModelAndView showNotFoundPage() {
        return new ModelAndView("notFound");
    }
}
