package com.example.vstore.controller;

import com.example.vstore.bind.*;
import org.springframework.http.MediaType;
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
    @Autowired
    UserService userService;
    @Autowired
    ProductsServer productsServer;
    @Autowired
    BillServer billServer;

    @GetMapping(value = {"","/user"})
    public String listUser(ModelMap mode, final HttpSession session) throws IOException, InterruptedException {
        List<User> listUser = userServer.findAll();
        mode.addAttribute("listUsers", listUser);
        return "list_user";
    }
    @GetMapping("/editUser")
    public String editUser(ModelMap mode, @RequestParam("id") Long idUser, @RequestParam(value = "error", required = false) String error) {
        User user = userServer.findById_user(idUser);
        if (user == null) {
            mode.addAttribute("status","ERROR");
            mode.addAttribute("title","Not found");
            mode.addAttribute("msg","Not found user!");
            return "status";
        }
        mode.addAttribute("user", user);
        mode.addAttribute("msg", error);
        return "editUser_form";
    }

    @ResponseBody
    @PostMapping(value="/editUser", produces = MediaType.APPLICATION_JSON_VALUE)
    public String updateUser(@RequestBody User user) {
        User u = userServer.findByUser_name1(user.getUser_name());
        if (u == null) {
            return "/admin/editUser?id="+user.getId_user()+"&error=Khong tim thay user name!";
        }
        else if (user.getWallet() < 0) {
            return "/admin/editUser?id="+u.getId_user()+"&error=Error: Wallet >= 0";
        }
        else if (!user.getRole().equals("ROLE_USER") && !user.getRole().equals("ROLE_ADMIN")) {
            return "/admin/editUser?id="+u.getId_user()+"&error=ROLE khong ton tai!";
        }

        if (user.getEnabled() == null)
            user.setEnabled(Boolean.TRUE);

        user.setId_user(u.getId_user());
        userService.updateUser(user);
        return "/admin/editUser?id="+user.getId_user()+"&error=Update thanh cong!";


    }

    @PostMapping("/deleteUser")
    public String deleteUser(ModelMap mode, @RequestParam(value="id") Long id) {
        User user = userServer.findById_user(id);
        if (user == null) {
            mode.addAttribute("status","ERROR");
            mode.addAttribute("title","Not found");
            mode.addAttribute("msg","Not found user!");
            return "status";
        }
        //delete all bill of user
        List<Bill> bills = billServer.findById_user(user.getId_user());
        for (Bill b: bills
             ) {
            billServer.deleteById(b.getId_bill());
        }
        //delete user
        userServer.deleteById(id);
        return "redirect:/admin/user";
    }

    @GetMapping("/product")
    public String listProduct(ModelMap mode, final HttpSession session) throws IOException, InterruptedException {
        List<Products> listProducts = productsServer.findAll();
        mode.addAttribute("listProducts", listProducts);
        return "list_products_ad";
    }

    @GetMapping("/editProduct")
    public String editProduct(ModelMap mode, @RequestParam(value = "id", required = false) Long id, @RequestParam(value = "error", required = false) String error) {
        if (id == null) //add product
        {
            mode.addAttribute("title", "ADD Product");
            mode.addAttribute("product", new Products());
        }
        else        // edit product
        {
            mode.addAttribute("title", "Edit Product");
            Products product = productsServer.findByID(id);
            if (product == null) {
                mode.addAttribute("status","ERROR");
                mode.addAttribute("title","Not found");
                mode.addAttribute("msg","Not found product!");
                return "status";
            }
            mode.addAttribute("product", product);

        }

        mode.addAttribute("msg", error);
        return "editProduct_form";
    }

    @ResponseBody
    @PostMapping(value = "/editProduct", produces = MediaType.APPLICATION_JSON_VALUE)
    public String updateProduct(@RequestBody Products product) {
        Products p = productsServer.findByID(product.getId_product());
        //check id product
        if (p == null) {        //id product khong ton tai trong database -> id = null -> add product
            product.setId_product(null);
        }
        if (product.getPrice() < 0)
            return "/admin/editProduct?error=Price khong hop le";
        productsServer.save(product);
        return "/admin/product";



    }

    @PostMapping("/deleteProduct")
    public String deleteProduct(ModelMap mode, @RequestParam(value = "id") Long id) {
        Products p = productsServer.findByID(id);
        if (p == null)
        {
            mode.addAttribute("status","ERROR");
            mode.addAttribute("title","Not found");
            mode.addAttribute("msg","Not found product!");
            return "status";
        }
        productsServer.deleteById(id);
        return "redirect:/admin/product";
    }

}
