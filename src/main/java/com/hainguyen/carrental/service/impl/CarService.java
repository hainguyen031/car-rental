package com.hainguyen.carrental.service.impl;

import com.hainguyen.carrental.dto.CarDTO;
import com.hainguyen.carrental.dto.SearchCarDTO;
import com.hainguyen.carrental.model.Booking;
import com.hainguyen.carrental.model.Car;
import com.hainguyen.carrental.repository.IBookingRepository;
import com.hainguyen.carrental.repository.ICarRepository;
import com.hainguyen.carrental.service.ICarService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Function;

@Service
@AllArgsConstructor
public class CarService implements ICarService {
    @Autowired
    private ICarRepository iCarRepository;
    @Autowired
    private IBookingRepository iBookingRepository;

    private Function<List<Car>, List<CarDTO>> listCarConverter;
    private Function<Car, CarDTO> carConverter;
    private Function<CarDTO, Car> carDtoConverter;
    private Function<Page<Car>, Page<CarDTO>> pageCarConverter;

    @Override
    public List<CarDTO> findAll() {
        List<Car> carList = iCarRepository.findAllByStatus(true);
        return listCarConverter.apply(carList);
    }

    @Override
    public List<CarDTO> findAllByOrderByRentPriceAsc() {
        List<Car> carList = iCarRepository.findAllByStatusOrderByRentPriceAsc(true);
        return listCarConverter.apply(carList);
    }

    @Override
    public List<CarDTO> findAllByOrderByRentPriceDesc() {
        List<Car> carList = iCarRepository.findAllByStatusOrderByRentPriceDesc(true);
        return listCarConverter.apply(carList);
    }

    @Override
    public List<CarDTO> searchCarByForm(SearchCarDTO searchCarDTO) {
        int seat = searchCarDTO.getSeat();
        String carLocation = searchCarDTO.getCarLocation();
        LocalDate rentalDate = searchCarDTO.getStartDate();
        LocalDate returnDate = searchCarDTO.getEndDate();
        List<Car> carList = iCarRepository.findAllBySeatAndCarLocationAndRentalDateAndReturnDate(seat, carLocation, rentalDate, returnDate);
        return listCarConverter.apply(carList);
    }

    @Override
    public boolean rentCar(Long id, Long carID, String cccd, String gplx, String pickupLocation, LocalDate rentalDate, LocalDate returnDate) {
        Optional<Car> carOptional = iCarRepository.findById(carID);
        if (carOptional.isPresent()) {
            Car car = carOptional.get();
            boolean isAvailable = iBookingRepository.checkCarAvailability(car.getId(), rentalDate, returnDate);
            if (isAvailable) {
                Booking booking = new Booking();
                booking.setCar(car);
                booking.setStartDate(rentalDate);
                booking.setEndDate(returnDate);
                iBookingRepository.save(booking);
                return true;
            }
        }
        return false;
    }

    @Override
    public CarDTO addCar(CarDTO carDTO) {
        Car car = carDtoConverter.apply(carDTO);
        car.setStatus(true);
        iCarRepository.save(car);
        return carConverter.apply(car);
    }

    @Override
    public CarDTO getCarById(Long id) {
        Car car = iCarRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Không tìm thấy xe"));
        return carConverter.apply(car);
    }

    @Override
    public CarDTO updateCar(Long id, CarDTO carDTO) {
        Car car = iCarRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Không tìm thấy xe"));
        Car carRequest = carDtoConverter.apply(carDTO);
        car = iCarRepository.save(carRequest);
        return carConverter.apply(car);
    }

    @Override
    public void deleteCar(Long id) {
        Car car = iCarRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Không tìm thấy xe"));
        car.setStatus(false);
        iCarRepository.save(car);
    }

    @Override
    public Page<CarDTO> findAllCarPage(Pageable pageable) {
        Page<Car> cars = iCarRepository.findAllByStatus(true, pageable);
//        int totalPage = cars.getTotalPages();
//        long totalRecord = cars.getTotalElements();
//        int currentPage = cars.getNumber();
//        int size = cars.getSize();
        return pageCarConverter.apply(cars);
    }

    @Override
    public Page<CarDTO> searchCarPage(Integer seat, String carLocation, LocalDate startDate, LocalDate endDate, Pageable pageable) {
//        int seat = searchCarDTO.getSeat();
//        String carLocation = searchCarDTO.getCarLocation();
//        LocalDate rentalDate = searchCarDTO.getStartDate();
//        LocalDate returnDate = searchCarDTO.getEndDate();
        Page<Car> cars = iCarRepository.findAllByForm(seat, carLocation, startDate, endDate, pageable);
        return pageCarConverter.apply(cars);
    }


}
