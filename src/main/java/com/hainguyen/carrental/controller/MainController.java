package com.hainguyen.carrental.controller;

import com.hainguyen.carrental.dto.LoginDTO;
import com.hainguyen.carrental.dto.UserDTO;
import com.hainguyen.carrental.exception.ObjectNotFound;
import com.hainguyen.carrental.model.User;
import com.hainguyen.carrental.repository.IUserRepository;
import com.hainguyen.carrental.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collection;

@Controller
public class MainController {
    @Autowired
    private IUserService iUserService;
    @Autowired
    private IUserRepository iUserRepository;

    @GetMapping("/")
    public String goHome() {
        return "/index";
    }

    @GetMapping("/login")
    public String goLogin(Model model) {
        LoginDTO loginDTO = new LoginDTO();
        model.addAttribute("loginDTO", loginDTO);
        return "/login";
    }

    @GetMapping("/register")
    public String goResgister(Model model) {
        UserDTO userDTO = new UserDTO();
        model.addAttribute("userDTO", userDTO);
        return "/register";
    }

    @GetMapping("/403")
    public String go403() {
        return "/403Page";
    }

    @PostMapping("/doRegister")
    public String doRegister(@ModelAttribute UserDTO userDTO,
                             BindingResult bindingResult,
                             Model model) {
        iUserService.usernameExists(userDTO, bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute("userDTO", userDTO);
            return "/register";
        } else {
            iUserService.registerNewUser(userDTO);
            return "/login";
        }
    }

//    @PostMapping("/doLogin")
//    public String loginSuccessRedirect(@ModelAttribute LoginDTO loginDTO,
//                                       RedirectAttributes redirect) {
//        User user = iUserRepository.findByUsername(loginDTO.getUsername());
//        if (user == null) {
//            System.out.println("User not found! " + loginDTO.getUsername());
//            throw new UsernameNotFoundException("User " + loginDTO.getUsername() + " was not found in the database");
//        }
//        boolean checkLoginSuccess = iUserService.checkLogin(user, loginDTO);
//        if (checkLoginSuccess) {
//            for (GrantedAuthority authority : user.getAuthorities()) {
//                if (authority.getAuthority().equals("ROLE_USER")) {
//                    return "redirect:/customer"; // Điều hướng người dùng ROLE_USER tới "/customer"
//                } else if (authority.getAuthority().equals("ROLE_ADMIN")) {
//                    return "redirect:/admin"; // Điều hướng người dùng ROLE_ADMIN tới "/admin"
//                }
//            }
//        }
//        return "redirect:/403"; // Điều hướng mặc định (không có quyền cụ thể)
//    }

//    @PostMapping("/login")
//    public String loginSuccessRedirect(Authentication authentication) {
//        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
//
//        if (authorities.contains(new SimpleGrantedAuthority("ROLE_USER"))) {
//            return "redirect:/customer"; // Điều hướng người dùng ROLE_USER tới "/user-page"
//        } else if (authorities.contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
//            return "redirect:/admin"; // Điều hướng người dùng ROLE_ADMIN tới "/admin-page"
//        }
//
//        return "redirect:/403"; // Điều hướng mặc định (không có quyền cụ thể)
//    }

    @ExceptionHandler(ObjectNotFound.class)
    public ModelAndView showNotFoundPage() {
        return new ModelAndView("notFound");
    }
}
