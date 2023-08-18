package com.hainguyen.carrental.controller.api;

import com.hainguyen.carrental.dto.BookingDTO;
import com.hainguyen.carrental.dto.CarDTO;
import com.hainguyen.carrental.dto.SearchCarDTO;
import com.hainguyen.carrental.exception.ObjectNotFound;
import com.hainguyen.carrental.service.IBookingService;
import com.hainguyen.carrental.service.ICarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/customerRes")
public class RestCustomerController {

    @Autowired
    private ICarService iCarService;
    @Autowired
    private IBookingService iBookingService;

//    @GetMapping("/all-car")
//    public ResponseEntity<List<CarDTO>> getAllCar(){
//        List<CarDTO> carDTOList = iCarService.findAll();
//        return new ResponseEntity<>(carDTOList, HttpStatus.OK);
//    }

    @GetMapping("")
    public ResponseEntity<Page<CarDTO>> getCarPage(@PageableDefault(value = 20) Pageable pageable) {
        Page<CarDTO> carDTOPage = iCarService.findAllCarPage(pageable);
        return new ResponseEntity<>(carDTOPage, HttpStatus.OK);
    }

    @GetMapping("/sort-asc")
    public ResponseEntity<List<CarDTO>> sortCarByPriceAsc() {
        List<CarDTO> carDTOList = iCarService.findAllByOrderByRentPriceAsc();
        return new ResponseEntity<>(carDTOList, HttpStatus.OK);
    }

    @GetMapping("/sort-desc")
    public ResponseEntity<List<CarDTO>> sortCarByPriceDesc() {
        List<CarDTO> carDTOList = iCarService.findAllByOrderByRentPriceDesc();
        return new ResponseEntity<>(carDTOList, HttpStatus.OK);
    }

    // lỗi 400
//    @PostMapping("/search-car")
//    public ResponseEntity<List<CarDTO>> searchCar(@RequestParam(name = "seat", required = false) Integer seat,
//                                       @RequestParam(name = "carLocation", required = false) String carLocation,
//                                       @RequestParam(name = "startDate", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate rentalDate,
//                                       @RequestParam(name = "endDate", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate returnDate) {
//        List<CarDTO> carDTOList = iCarService.searchCarByForm(seat, carLocation, rentalDate, returnDate);
//        return new ResponseEntity<>(carDTOList, HttpStatus.OK);
//    }

    @PostMapping("/search-car")
        public ResponseEntity<List<CarDTO>> searchCar(@RequestBody SearchCarDTO searchCarDTO) {
        System.out.println(searchCarDTO);
        System.out.println(searchCarDTO);
        List<CarDTO> carDTOList = iCarService.searchCarByForm(searchCarDTO);
        return new ResponseEntity<>(carDTOList, HttpStatus.OK);
    }

//    @PostMapping("/rent-car")
//    public ResponseEntity<?> rentCar(@RequestParam Long userID,
//                                     @RequestParam Long carID,
//                                     @RequestParam String cccd,
//                                     @RequestParam String gplx,
//                                     @RequestParam String pickupLocation,
//                                     @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate rentalDate,
//                                     @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate returnDate) {
//        boolean success = iCarService.rentCar(userID, carID, cccd, gplx, pickupLocation, rentalDate, returnDate);
//        if (success) {
//            return new ResponseEntity<>("Car rented successfully.", HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>("Car rental failed.", HttpStatus.BAD_REQUEST);
//        }
//    }

    @PostMapping("/rent-car")
    public ResponseEntity<?> rentCar(@RequestBody BookingDTO bookingDTO) {
        boolean success = iCarService.rentCar(bookingDTO.getCustomerId(), bookingDTO.getCarId(), bookingDTO.getCccd(), bookingDTO.getGplx(), bookingDTO.getPickupLocation(), bookingDTO.getStartDate(), bookingDTO.getEndDate());
        if (success) {
            return new ResponseEntity<>("Car rented successfully.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Car rental failed.", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/history-booking")
    public ResponseEntity<?> getBookingHistory(@RequestParam Long customerId) {
        List<BookingDTO> bookingList = iBookingService.getBookingHistory(customerId);
        return new ResponseEntity<>(bookingList, HttpStatus.OK);
    }

    @ExceptionHandler(ObjectNotFound.class)
    public ModelAndView showNotFoundPage() {
        return new ModelAndView("notFound");
    }
}
