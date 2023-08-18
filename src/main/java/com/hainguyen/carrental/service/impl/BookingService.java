package com.hainguyen.carrental.service.impl;

import com.hainguyen.carrental.dto.BookingDTO;
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

    private Function<List<Booking>, List<BookingDTO>> listBookingConverter;
    private Function<Page<Booking>, Page<BookingDTO>> pageBookingConverter;

    @Override
    public List<BookingDTO> getBookingHistory(Long customerId) {
        User user = iUserRepository.findById(customerId).orElseThrow(() -> new NoSuchElementException("Không tìm thấy người dùng"));
        List<Booking> bookingList = iBookingRepository.findByUser(user);
        return listBookingConverter.apply(bookingList);
    }

    @Override
    public List<BookingDTO> getBookingHistoryForCar(Long carId) {
        Car car = iCarRepository.findById(carId).orElseThrow(() -> new NoSuchElementException("Không tìm thấy xe"));
        List<Booking> bookingList = iBookingRepository.findByCar(car);
        return listBookingConverter.apply(bookingList);
    }

    @Override
    public Page<BookingDTO> getBookingHistoryForCarPaging(Long id, Pageable pageable) {
        Car car = iCarRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Không tìm thấy xe"));
        Page<Booking> bookingPage = iBookingRepository.findByCar(car, pageable);
        return pageBookingConverter.apply(bookingPage);
    }
}
