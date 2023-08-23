package com.hainguyen.carrental.service;

import com.hainguyen.carrental.dto.LoginDTO;
import com.hainguyen.carrental.dto.UserDTO;
import com.hainguyen.carrental.model.User;
import org.springframework.validation.BindingResult;

public interface IUserService {
    void usernameExists(UserDTO userDTO, BindingResult bindingResult);

    void registerNewUser(UserDTO userDTO);

    boolean checkLogin(User user, LoginDTO loginDTO);

    User findUserByUsername(String username);
}
