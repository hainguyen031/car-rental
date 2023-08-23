package com.hainguyen.carrental.converter;

import com.hainguyen.carrental.dto.BookingDTO;
import com.hainguyen.carrental.dto.BookingDTOResponse;
import com.hainguyen.carrental.model.Booking;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Component
@AllArgsConstructor
public class ListBookingConverter implements Function<List<Booking>, List<BookingDTOResponse>> {

    public List<BookingDTOResponse> apply(List<Booking> bookingList) {
        List<BookingDTOResponse> bookingDTOList = new ArrayList<>();
        for (Booking booking : bookingList) {
            BookingDTOResponse bookingDTO = new BookingDTOResponse();
            BeanUtils.copyProperties(booking, bookingDTO);
            bookingDTOList.add(bookingDTO);
        }
        return bookingDTOList;
    }
}
