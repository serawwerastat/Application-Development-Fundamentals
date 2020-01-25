package com.api_dev_fundamentals.APIDevFundamentals.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.api_dev_fundamentals.APIDevFundamentals.models.User;
import com.api_dev_fundamentals.APIDevFundamentals.repositories.UserRepository;

@Controller
public class testController {

    @Autowired
    public UserRepository userRepository;

    private int idCount = 10;

    @GetMapping(value= {"/", "/hello"})
    public String welcome() {
        return "index";
    }

    @GetMapping(value = "/list")
    public String getAllUsers(HttpServletRequest request, HttpServletResponse response){
        List<String> names = userRepository.findAll().stream().map(User::getName).collect(Collectors.toList());
        request.setAttribute("userNames", names);
        return "list";
    }

    @GetMapping(value = "/add")
    public String addUser(){
        return "add";
    }

    @PostMapping(value = "/add")
    public String addUser2(HttpServletRequest request, HttpServletResponse response){
        String name = request.getParameter("name");
        String password = request.getParameter("pass");
        User user = new User(name, password).setId(idCount++);
        userRepository.save(user);

        request.setAttribute("userName", name);
        return "add";
    }

    @GetMapping(value = "/delete")
    public String deleteUser(){
        return "delete";
    }

    @PostMapping(value = "/delete")
    public String deleteUser2(HttpServletRequest request, HttpServletResponse response){
        String name = request.getParameter("name");
        String password = request.getParameter("pass");
        User user = userRepository.findByName(name);
        userRepository.delete(user);

        request.setAttribute("userName", name);
        return "delete";
    }
}
