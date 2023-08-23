package com.hainguyen.carrental.service.impl;

import com.hainguyen.carrental.constraint.ERole;
import com.hainguyen.carrental.dto.LoginDTO;
import com.hainguyen.carrental.dto.UserDTO;
import com.hainguyen.carrental.model.User;
import com.hainguyen.carrental.repository.IUserRepository;
import com.hainguyen.carrental.service.IUserService;
import com.hainguyen.carrental.util.EncrytedPasswordUtils;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService, IUserService {
    @Autowired
    private IUserRepository iUserRepository;
    private Function<UserDTO, User> userDtoConverter;
    private PasswordEncoder passwordEncoder;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = iUserRepository.findByUsername(username);

        if (user == null) {
            System.out.println("User not found! " + username);
            throw new UsernameNotFoundException("User " + username + " was not found in the database");
        }
        System.out.println("Found User: " + user);

        List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
        GrantedAuthority authority = new SimpleGrantedAuthority(user.getRoles());
        grantList.add(authority);

        UserDetails userDetails = (UserDetails) new org.springframework.security.core.userdetails.User(user.getUsername(), //
                user.getPassword(), grantList);

        return userDetails;
    }

    @Override
    public void usernameExists(UserDTO userDTO, BindingResult bindingResult) {
        User user = userDtoConverter.apply(userDTO);
        if (!"".equals(user.getUsername()) ) {
            if (iUserRepository.findByUsername(user.getUsername()) != null) {
                bindingResult.rejectValue("username", "username.exists");
            }
        }
    }

    @Override
    public void registerNewUser(UserDTO userDTO) {
        User user = userDtoConverter.apply(userDTO);
        String encrytePassword = EncrytedPasswordUtils.encrytePassword(user.getPassword());
        user.setPassword(encrytePassword);
        user.setRoles("ROLE_".concat(ERole.USER.toString()));
        iUserRepository.save(user);
    }

    @Override
    public boolean checkLogin(User user, LoginDTO loginDTO) {
        return passwordEncoder.matches(loginDTO.getPassword(), user.getPassword());
    }

    @Override
    public User findUserByUsername(String username) {
        User user = iUserRepository.findByUsername(username);
        return user;
    }
}
