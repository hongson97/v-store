package com.example.vstore.controller;

import com.example.vstore.bind.User;
import com.example.vstore.bind.UserServer;
import com.example.vstore.bind.UserService;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;


import java.io.IOException;
import java.net.Authenticator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class SignUp {
    @Autowired
    UserServer userServer;
    @Autowired
    UserService userService;

    @GetMapping("/sign-up")
    public String signupFrom(ModelMap mode, @RequestParam(value = "error", required = false) String error){
        mode.addAttribute("msg",error);
        return "sign-up";
    }

    @PostMapping(value="/sign-up", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String signUp(ModelMap mode,@RequestBody User user){
        User c = userServer.findByUser_name1(user.getUser_name());
        if (c != null) {
            return "/sign-up?error=User name da ton tai!";
        }
        if (user.getPassword() == null) {
            return "/sign-up?error=Hay nhap password!";
        }
        user.setRole("ROLE_USER");
        user.setWallet(10000L);
        userService.signUpUser(user);
        return "/login?error=Dang ky thanh cong, hay dang nhap!";
    }
}
