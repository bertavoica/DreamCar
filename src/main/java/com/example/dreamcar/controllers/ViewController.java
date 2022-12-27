package com.example.dreamcar.controllers;

import com.example.dreamcar.repositories.UserRepository;
import org.jsondoc.core.annotation.Api;
import org.jsondoc.core.pojo.ApiStage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;

@Controller
@Api(name = "View Controller", description = "Provides a list of methods which return the credentials needed by html pages", stage = ApiStage.RC)
public class ViewController implements WebMvcConfigurer {
    private UserRepository userRepository;

    @Autowired
    ViewController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping(value = {"/login"}, method = RequestMethod.GET)
    public String login(Model model) {
        return "login";
    }

    @RequestMapping(value = "/logout")
    public String logout(HttpServletRequest request) {
        HttpSession httpSession = request.getSession();
        httpSession.invalidate();

        return "redirect:login";
    }

    @RequestMapping(value = "/component")
    public String index(Model model, Principal principal) {

        if (principal == null) {
            return "redirect:login";
        }

        model.addAttribute("email", principal.getName());
        return "car-component";
    }
}
