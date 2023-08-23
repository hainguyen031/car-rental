package com.hainguyen.carrental.controller;

import com.hainguyen.carrental.dto.*;
import com.hainguyen.carrental.exception.ObjectNotFound;
import com.hainguyen.carrental.model.User;
import com.hainguyen.carrental.service.IBookingService;
import com.hainguyen.carrental.service.ICarService;
import com.hainguyen.carrental.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    private ICarService iCarService;
    @Autowired
    private IBookingService iBookingService;
    @Autowired
    private IUserService iUserService;

    @GetMapping("")
    public String goHomePage(Model model, @PageableDefault(value = 20) Pageable pageable) {
        Page<CarDTO> carDTOPage = iCarService.findAllCarPage(pageable);
        model.addAttribute("carDTOPage", carDTOPage);
        return "/customer/home";
    }

//    @PostMapping("/search-car")
//    public String searchCar(@RequestParam("carType") Integer seat,
//                            @RequestParam("carLocation") String carLocation,
//                            @RequestParam("pick-up-date") String startDateStr,
//                            @RequestParam("return-date") String endDateStr,
//                            @RequestParam(defaultValue = "0") int page,
//                            Model model, RedirectAttributes redirect) {
//
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
//        LocalDate startDate = LocalDate.parse(startDateStr, formatter);
//        LocalDate endDate = LocalDate.parse(endDateStr, formatter);
//
//        Page<CarDTO> carDTOPage = iCarService.searchCarPage(seat, carLocation, startDate, endDate, PageRequest.of(page, 10));
//        model.addAttribute("carDTOPage",carDTOPage);
//        return "/customer/home";
//    }

    @PostMapping("/booking")
    public String doBooking(@ModelAttribute BookingDTORequest bookingDTORequest,
                            Model model) {
        boolean success = iBookingService.saveBooking(bookingDTORequest);
        if (success) {
            model.addAttribute("bookingDTORequest", new BookingDTORequest());
            model.addAttribute("success", "Booking car successfully!");
        } else {
            model.addAttribute("success", "Booking failed !!!!");
        }
            return "/customer/home";
    }

    @GetMapping("/myBooking")
    public String goMyBooking(Model model,
                              @PageableDefault(value = 10) Pageable pageable,
                              @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails != null) {
            String username = userDetails.getUsername();
            User user = iUserService.findUserByUsername(username);
            Page<BookingDTOResponse> bookingDTOPage = iBookingService.findAllBookingByUser(user, pageable);
            model.addAttribute("bookingDTOPage", bookingDTOPage);

        }
        return "/customer/booking";
    }

    @ExceptionHandler(ObjectNotFound.class)
    public ModelAndView showNotFoundPage() {
        return new ModelAndView("notFound");
    }
}
