package com.hainguyen.carrental.controller;

import com.hainguyen.carrental.dto.CarDTO;
import com.hainguyen.carrental.exception.ObjectNotFound;
import com.hainguyen.carrental.service.IBookingService;
import com.hainguyen.carrental.service.ICarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private IBookingService iBookingService;
    @Autowired
    private ICarService iCarService;

    @GetMapping("")
    public String goListCar(Model model, @PageableDefault(value = 20) Pageable pageable) {
        Page<CarDTO> carDTOPage = iCarService.findAllCarPage(pageable);
        model.addAttribute("carDTOPage", carDTOPage);
        return "admin/home";
    }

    @GetMapping("/create")
    public String goCreateCar(Model model) {
        model.addAttribute("carDTO", new CarDTO());
        return "admin/create";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute CarDTO carDTO,
                       BindingResult bindingResult,
                       Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("carDTO", carDTO);
        } else {
            iCarService.addCar(carDTO);
            model.addAttribute("carDTO", new CarDTO());
            model.addAttribute("success", "Create car successfully!");
        }
        return "admin/create";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute CarDTO carDTO,
                         BindingResult bindingResult,
                         Model model,
                         RedirectAttributes redirect) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("carDTO", carDTO);
            return "admin/home";
        } else {
            iCarService.addCar(carDTO);
            redirect.addFlashAttribute("success", "Update car successfully!");
            return "redirect:/admin/";
        }
    }

    @ExceptionHandler(ObjectNotFound.class)
    public ModelAndView showNotFoundPage() {
        return new ModelAndView("notFound");
    }
}
