package com.hainguyen.carrental.controller.api;

import com.hainguyen.carrental.dto.BookingDTO;
import com.hainguyen.carrental.dto.CarDTO;
import com.hainguyen.carrental.exception.ObjectNotFound;
import com.hainguyen.carrental.service.IBookingService;
import com.hainguyen.carrental.service.ICarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("/adminRes")
public class RestAdminController {
    @Autowired
    private IBookingService iBookingService;
    @Autowired
    private ICarService iCarService;

    @GetMapping("/booking-history/{id}")
    public ResponseEntity<List<BookingDTO>> getBookingHistoryForCar(@PathVariable Long id) {
        List<BookingDTO> bookingList = iBookingService.getBookingHistoryForCar(id);
        return new ResponseEntity<>(bookingList, HttpStatus.OK);
    }

    @GetMapping("/booking-history-paging/{id}")
    public ResponseEntity<Page<BookingDTO>> getBookingPaging(@PathVariable Long id, @PageableDefault(value = 5) Pageable pageable) {
        Page<BookingDTO> bookingDTOPage = iBookingService.getBookingHistoryForCarPaging(id, pageable);
        return new ResponseEntity<>(bookingDTOPage, HttpStatus.OK);
    }

    @PostMapping("/car")
    public ResponseEntity<CarDTO> addCar(@RequestBody CarDTO carDTO) {
        CarDTO saveCarDTO = iCarService.addCar(carDTO);
        return new ResponseEntity<>(saveCarDTO, HttpStatus.OK);
    }

    @GetMapping("/car/{id}")
    public ResponseEntity<CarDTO> getCarById(@PathVariable Long id) {
        CarDTO carDTO = iCarService.getCarById(id);
        return new ResponseEntity<>(carDTO, HttpStatus.OK);
    }

    @PutMapping("/car/{id}")
    public ResponseEntity<CarDTO> updateCarById(@PathVariable Long id, @RequestBody CarDTO carDTO) {
        CarDTO updateCarDTO = iCarService.updateCar(id, carDTO);
        return new ResponseEntity<>(updateCarDTO, HttpStatus.OK);
    }

    @DeleteMapping("/car/{id}")
    public ResponseEntity<Void> deleteCarById(@PathVariable Long id) {
        iCarService.deleteCar(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/car")
    public ResponseEntity<List<CarDTO>> getAllCar() {
        List<CarDTO> carDTOList = iCarService.findAll();
        return new ResponseEntity<>(carDTOList, HttpStatus.OK);
    }

    @GetMapping("/car-paging")
    public ResponseEntity<Page<CarDTO>> getAllCarPaging(@PageableDefault(value = 5) Pageable pageable) {
        Page<CarDTO> carDTOPage = iCarService.findAllCarPage(pageable);
        return new ResponseEntity<>(carDTOPage, HttpStatus.OK);
    }

    @ExceptionHandler(ObjectNotFound.class)
    public ModelAndView showNotFoundPage() {
        return new ModelAndView("notFound");
    }
}
