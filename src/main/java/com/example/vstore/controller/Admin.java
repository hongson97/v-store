package com.example.vstore.controller;

import com.example.vstore.bind.User;
import com.example.vstore.bind.UserServer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;


import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class Admin {
    @Autowired
    UserServer userServer;

    @GetMapping(value = {"","/listUser"})
    public String listUser(ModelMap mode, final HttpSession session) throws IOException, InterruptedException {
        List<User> listUser = userServer.findAll();
        mode.addAttribute("listUsers", listUser);
        return "showAllUser";
    }
}
