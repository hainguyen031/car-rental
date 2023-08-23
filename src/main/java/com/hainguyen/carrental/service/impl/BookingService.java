package com.hainguyen.carrental.service.impl;

import com.hainguyen.carrental.dto.BookingDTO;
import com.hainguyen.carrental.dto.BookingDTORequest;
import com.hainguyen.carrental.dto.BookingDTOResponse;
import com.hainguyen.carrental.model.Booking;
import com.hainguyen.carrental.model.Car;
import com.hainguyen.carrental.model.User;
import com.hainguyen.carrental.repository.IBookingRepository;
import com.hainguyen.carrental.repository.ICarRepository;
import com.hainguyen.carrental.repository.IUserRepository;
import com.hainguyen.carrental.service.IBookingService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Function;

@Service
@AllArgsConstructor
public class BookingService implements IBookingService {
    @Autowired
    private IUserRepository iUserRepository;
    @Autowired
    private IBookingRepository iBookingRepository;
    @Autowired
    private ICarRepository iCarRepository;

    private Function<List<Booking>, List<BookingDTOResponse>> listBookingConverter;
    private Function<Page<Booking>, Page<BookingDTOResponse>> pageBookingConverter;

    @Override
    public List<BookingDTOResponse> getBookingHistory(Long customerId) {
        User user = iUserRepository.findById(customerId).orElseThrow(() -> new NoSuchElementException("Không tìm thấy người dùng"));
        List<Booking> bookingList = iBookingRepository.findByUser(user);
        return listBookingConverter.apply(bookingList);
    }

    @Override
    public List<BookingDTOResponse> getBookingHistoryForCar(Long carId) {
        Car car = iCarRepository.findById(carId).orElseThrow(() -> new NoSuchElementException("Không tìm thấy xe"));
        List<Booking> bookingList = iBookingRepository.findByCar(car);
        return listBookingConverter.apply(bookingList);
    }

    @Override
    public Page<BookingDTOResponse> getBookingHistoryForCarPaging(Long id, Pageable pageable) {
        Car car = iCarRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Không tìm thấy xe"));
        Page<Booking> bookingPage = iBookingRepository.findByCar(car, pageable);
        return pageBookingConverter.apply(bookingPage);
    }

    @Override
    public List<BookingDTOResponse> findAll() {
        List<Booking> bookingList = iBookingRepository.findAll();
        return listBookingConverter.apply(bookingList);
    }

    @Override
    public boolean saveBooking(BookingDTORequest bookingDTORequest) {
        Optional<Car> carOptional = iCarRepository.findById(bookingDTORequest.getCarId());
        User userOptional = iUserRepository.findByUsername(bookingDTORequest.getCusName());
        if (carOptional.isPresent() & userOptional != null) {
            Car car = carOptional.get();
            Long isAvailable = iBookingRepository.checkCarAvailability(car.getId(), bookingDTORequest.getStartDate(), bookingDTORequest.getEndDate());
            if (isAvailable == 0) {
                Booking booking = new Booking();
                booking.setCar(car);
                booking.setStartDate(bookingDTORequest.getStartDate());
                booking.setEndDate(bookingDTORequest.getEndDate());
                booking.setUser(userOptional);
                booking.setCccd(bookingDTORequest.getCccd());
                booking.setGplx(bookingDTORequest.getGplx());
                booking.setPickupLocation(bookingDTORequest.getPickupLocation());
                iBookingRepository.save(booking);
                return true;
            }
        }
        return false;
    }

    @Override
    public Page<BookingDTOResponse> findAllBookingByUser(User user, Pageable pageable) {
        Page<Booking> bookingPage = iBookingRepository.findByUser(user, pageable);
        return pageBookingConverter.apply(bookingPage);
    }


}
