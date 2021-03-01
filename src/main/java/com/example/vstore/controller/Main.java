package com.example.vstore.controller;

import com.example.vstore.mode.FileUploadUtil;
import com.example.vstore.mode.User;
import com.example.vstore.DAO.UserServer;
import com.example.vstore.DAO.UserService;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;


import javax.activation.MimetypesFileTypeMap;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.support.ServletContextResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class Main {
    @Autowired
    UserServer userServer;

    @Autowired
    UserService userService;

    @Autowired
    ResourceLoader resourceLoader;

    @Autowired
    private ServletContext servletContext;


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

    @GetMapping("/informationUser")
    public String informationUser(ModelMap mode, HttpSession session) {
        User user = (User) session.getAttribute("user");
        mode.addAttribute("user", user);
        return "information_user";
    }

    @GetMapping("/infoUserVuln")
    public String infoUserVuln(ModelMap mode, @RequestParam("id") Long id) {
        User user = userServer.findById_user(id);
        mode.addAttribute("user", user);
        return "information_user";
    }

    @GetMapping("/informationUser/edit")
    public String editInfo(ModelMap mode, HttpSession session) {
        User user = (User) session.getAttribute("user");
        mode.addAttribute("user", user);
        return "editInfo";
    }

    @ResponseBody
    @PostMapping(value = "/informationUser/edit", produces = MediaType.APPLICATION_JSON_VALUE)
    public String updateInfo(HttpSession session, @RequestBody User user) {
        User u = (User) session.getAttribute("user");
        if (user.getName() != "")
            u.setName(user.getName());
        if (user.getEmail() != "")
            u.setEmail(user.getEmail());
        if (user.getAge() > 0)
            u.setAge(user.getAge());
        if (user.getPassword() != "")
            u.setPassword(user.getPassword());

        userService.updateUser(u);
        return "/informationUser";

    }

    @GetMapping("/upAVT")
    public String upAVTForm(HttpSession session, ModelMap mode) {
        User user = (User) session.getAttribute("user");

        if (user.getAvt() != null) {
            mode.addAttribute("avt", user.getAvt());
        }
        else
            mode.addAttribute("avt", "default.jpg");
        return "uploadAVT";
    }

    @PostMapping(value = "/upAVT",  produces = MediaType.APPLICATION_JSON_VALUE)
    public String upAVT(HttpSession session, Model mode, @RequestParam("image") MultipartFile multipartFile) throws IOException {
        User user = (User) session.getAttribute("user");
        if(multipartFile.isEmpty()) {
            mode.addAttribute("avt",user.getAvt());
            mode.addAttribute("msg", "Please select a file to upload");
            return "uploadAVT";
        }

        try {
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            if(fileName.startsWith("../")) {
                mode.addAttribute("avt",user.getAvt());
                mode.addAttribute("msg","file name khong hop le");
                return "uploadAVT";
            }

            String uploadDir = "src\\main\\resources\\static\\avt";
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
            user.setAvt(fileName);
            userServer.save(user);
            mode.addAttribute("msg", "Success");
        } catch (Exception e) {
            mode.addAttribute("msg", e.toString());
        }
        mode.addAttribute("avt",user.getAvt());
        return "uploadAVT";
    }
    @PostMapping(value = "/upAVTVuln",  produces = MediaType.APPLICATION_JSON_VALUE)
    public String upAVTVuln(HttpSession session, Model mode, @RequestParam("image") MultipartFile multipartFile) throws IOException {
        User user = (User) session.getAttribute("user");
        if(multipartFile.isEmpty()) {
            mode.addAttribute("avt",user.getAvt());
            mode.addAttribute("msg", "Please select a file to upload");
            return "uploadAVT";
        }
        try {
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            String uploadDir = "src\\main\\resources\\static\\avt";
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
            user.setAvt(fileName);
            userServer.save(user);
            mode.addAttribute("msg", "Success");
        } catch (Exception e) {
            mode.addAttribute("msg", e.toString());
        }
        mode.addAttribute("avt",user.getAvt());
        return "uploadAVT";
    }

    @GetMapping(value = "/avt/{filename}")
    public void serveFile(@PathVariable String filename, HttpServletResponse response, HttpServletRequest request) throws IOException  {
        String fileDir = "src\\main\\resources\\static\\avt";
        Path file = Paths.get(fileDir, filename);
        if (Files.exists(file)) {
            String mimeType = URLConnection.guessContentTypeFromName(filename);


            response.setContentType(mimeType);
            response.addHeader("Content-Disposition","attachment; filename="+filename);
            try {
                Files.copy(file,response.getOutputStream());
                response.getOutputStream().flush();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }





}
