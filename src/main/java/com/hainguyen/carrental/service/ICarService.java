package com.hainguyen.carrental.service;

import com.hainguyen.carrental.dto.CarDTO;
import com.hainguyen.carrental.dto.SearchCarDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface ICarService {
    List<CarDTO> findAll();

    List<CarDTO> findAllByOrderByRentPriceAsc();

    List<CarDTO> findAllByOrderByRentPriceDesc();

    List<CarDTO> searchCarByForm(SearchCarDTO searchCarDTO);

    boolean rentCar(Long id, Long carID, String cccd, String gplx, String pickupLocation, LocalDate rentalDate, LocalDate returnDate);

    CarDTO addCar(CarDTO carDTO);

    CarDTO getCarById(Long id);

    CarDTO updateCar(Long id, CarDTO carDTO);

    void deleteCar(Long id);

    Page<CarDTO> findAllCarPage(Pageable pageable);

    Page<CarDTO> searchCarPage(Integer seat, String carLocation, LocalDate startDate, LocalDate endDate, Pageable pageable);
}

