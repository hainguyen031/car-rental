package com.hainguyen.carrental.dto;

import lombok.Data;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.text.NumberFormat;
import java.util.Locale;

@Data
public class CarDTO {
    private Long id;
    private String brand;
    private String model;
    private int seat;
    private int rentPrice;
    private String description;
    private String url;
    private String url2;
    private String url3;
    private String url4;
    private String carLocation;
    private boolean status;
    public String formatRentPrice() {
        return NumberFormat.getNumberInstance(Locale.US).format(rentPrice);
    }
}
