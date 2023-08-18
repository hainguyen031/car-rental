package com.hainguyen.carrental.util;

import org.springframework.validation.Errors;

import java.time.LocalDate;

public class Validation {
    private static final String CCCD_REGEX = "^[1-9]\\d{12}$";
    private static final String GPLX_REGEX = "^[1-9]\\d{12}$";
    public static void checkStartDate(String fieldName, LocalDate startDate1, Errors errors) {
        LocalDate now =LocalDate.now();
        if (startDate1 == null) {
            errors.rejectValue(fieldName, "startDate.empty");
        } else if (startDate1.compareTo(now) < 0) {
            errors.rejectValue(fieldName, "startDate.past");
        }
    }

    public static void checkEndDate(String fieldName, LocalDate startDate, LocalDate endDate, Errors errors) {
        if (endDate == null) {
            errors.rejectValue(fieldName, "endDate.empty");
        } else if (endDate.compareTo(startDate) < 0) {
            errors.rejectValue(fieldName, "endDate.past");
        }
    }

    public static void checkCccd(String fieldName, String cccd, Errors errors) {
        if ("".equals(cccd)) {
            errors.rejectValue(fieldName, "CCCD.empty");
        } else if (!cccd.matches(CCCD_REGEX)) {
            errors.rejectValue(fieldName, "CCCD.invalid");
        }
    }

    public static void checkGplx(String fieldName, String gplx, Errors errors) {
        if ("".equals(gplx)) {
            errors.rejectValue(fieldName, "GPLX.empty");
        } else if (!gplx.matches(GPLX_REGEX)) {
            errors.rejectValue(fieldName, "GPLX.invalid");
        }
    }
}
