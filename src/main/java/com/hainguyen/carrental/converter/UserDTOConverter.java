package com.hainguyen.carrental.converter;

import com.hainguyen.carrental.dto.CarDTO;
import com.hainguyen.carrental.dto.UserDTO;
import com.hainguyen.carrental.model.Car;
import com.hainguyen.carrental.model.User;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.function.Function;
@Component
@AllArgsConstructor
public class UserDTOConverter implements Function<UserDTO, User> {
    public User apply(UserDTO userDTO) {
        User user = new User();
        BeanUtils.copyProperties(userDTO, user);
        return user;
    }
}
