package com.controller;

import com.common.User;
import com.model.DBConnection;
import com.model.UserDAO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
//@RequestMapping("/user")
public class UserController {
    
    @Autowired
    private UserDAO userDAO;
    
    @Autowired
    private LocationController locationController;

    @RequestMapping(value = "/index.html")
    public String index(HttpServletRequest request) {  // http://ip:port/appname/index.html
        HttpSession session = request.getSession();
        if (session.getAttribute("user") == null) {
            return "forward:/login.html";
        }
        return "index";
    }

    @RequestMapping(value = "/login.html", method = RequestMethod.GET)
    public String login(HttpServletRequest request) {  // http://ip:port/appname/login.html
        DBConnection.fillDBInfo("db");
        HttpSession session = request.getSession();
        if (session.getAttribute("user") != null) {
            return "redirect:/";
        }
        return "login";
    }

    @RequestMapping(value = "/register.html", method = RequestMethod.GET)
    public String register(HttpServletRequest request, ModelMap map) {  // http://ip:port/appname/register.html
        DBConnection.fillDBInfo("db");
        HttpSession session = request.getSession();
        if (session.getAttribute("user") != null) {
            return "redirect:/";
        }
        map.addAttribute("countryList", locationController.countryList(false));
        return "register";
    }

    @RequestMapping(value = "/login.html", method = RequestMethod.POST)
    public String login(HttpServletRequest request, User user, ModelMap map) {  // http://ip:port/appname/login.html
        if (userDAO.validateUser(user)) {
            request.getSession().setAttribute("user", user);
            return "redirect:/";
        }
        map.addAttribute("error", "invalid username and/or password");
        return "login";
    }

    @RequestMapping(value = "/register.html", method = RequestMethod.POST)
    public String register(HttpServletRequest request, User user, ModelMap map) {  // http://ip:port/appname/register.html
        int flag  = userDAO.registerUser(user);
        if (flag == 1) {
            request.getSession().setAttribute("user", user);
            return "redirect:/";
        } else if(flag == -1)
            map.addAttribute("error", "username is not available");
        else
            map.addAttribute("error", "Operation failed...");
        map.addAttribute("countryList", locationController.countryList(false));
        return "register";
    }
    
    @RequestMapping(value="/username.html", method = RequestMethod.POST)
    @ResponseBody
    public String checkUseranme(String username) {
        boolean flag = userDAO.usernameExisted(username);
        if(flag)
            return "username is not available";
        return "username is available";
    }
}
