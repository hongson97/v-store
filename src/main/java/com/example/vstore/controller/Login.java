package com.example.vstore.controller;

import com.example.vstore.bind.User;
import com.example.vstore.bind.UserServer;
import com.example.vstore.bind.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class Login {
    public static Logger logger = LoggerFactory.getLogger(Login.class);
    @Autowired
    UserServer userServer;

    @Autowired
    UserService userService;

/*
    @PostMapping("/home")
    //@ResponseBody
    public String login(ModelMap mode,HttpSession session, @RequestParam(value = "userName") String userName,
                        @RequestParam(value = "password") String password) throws IOException, InterruptedException {

        User user = userServer.findByUser_nameAndPassword(userName, password);
        if (user == null) {
            mode.addAttribute("msg","Đăng nhập thất bại, hãy đăng nhập lại!");
            return "login_from";
        }
        session.setAttribute("user", user);
        return "home";
    }
*/
    @GetMapping(value = {"/home", "/"})
    public String home(ModelMap mode, HttpSession session){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //authentication.getPrincipal()
        if(authentication != null) {
            session.setAttribute("user", (User) authentication.getPrincipal());
        }
        else {
            return "login";
        }
        return "home";
    }

    @GetMapping(value = "/login")
    public String loginFrom(ModelMap mode, HttpSession session, @RequestParam(value = "error", required = false) String error) {
        if(session.getAttribute("user") != null) {
            return "home";
        }
        mode.addAttribute("msg", error);
        return "login_form";
    }
/*
    @GetMapping("/logout")
    public String logoutForm(ModelMap mode, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
            session.removeAttribute("user");
            mode.addAttribute("msg","Đăng xuất thành công!");
        }
        return "login_form";
    }
    */

    @GetMapping("/informationUser")
    public String informationUser() {
        return "information_user";
    }


}
