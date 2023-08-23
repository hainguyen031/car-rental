package com.hainguyen.carrental.converter;

import com.hainguyen.carrental.dto.BookingDTO;
import com.hainguyen.carrental.dto.BookingDTOResponse;
import com.hainguyen.carrental.dto.CarDTO;
import com.hainguyen.carrental.model.Booking;
import com.hainguyen.carrental.model.Car;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@AllArgsConstructor
public class PageBookingConverter implements Function<Page<Booking>, Page<BookingDTOResponse>> {
    public Page<BookingDTOResponse> apply(Page<Booking> bookingPage) {
        return bookingPage.map(booking -> {
            BookingDTOResponse bookingDTO = new BookingDTOResponse();
            BeanUtils.copyProperties(booking, bookingDTO);
            return bookingDTO;
        });
    }
}
