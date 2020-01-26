package com.fundamentals.controller;

import java.util.List;
import java.util.Optional;
import org.apache.log4j.Logger;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fundamentals.repositories.UserRepository;
import com.fundamentals.models.User;

@Controller
public class UserController {

    private static final Logger LOG = Logger.getLogger(UserController.class);
    private static final String USER_NOT_EXIST = "User %s does not exist";
    private static final String ADD_PAGE = "add";
    private static final String LIST_ALL_USERS_PAGE = "list";
    private static final String DELETE_PAGE = "delete";
    private static final String FIND_USER_PAGE = "users";
    private static final String HOME_PAGE = "index";
    private static final String ERROR_ATTRIBUTE = "error";
    private static final String USER_NAME_ATTRIBUTE = "userName";
    private static final String USER_ID_ATTRIBUTE = "userId";
    private static final String USER_PASS_ATTRIBUTE = "userPassword";

    @Autowired
    private UserRepository userRepository;

    @GetMapping(value = { "/", "/home" })
    public String welcome() {
        LOG.info("Home page loaded.");
        return HOME_PAGE;
    }

    @GetMapping(value = "/list")
    public String getAllUsers(HttpServletRequest request, HttpServletResponse response) {
        List<User> users = userRepository.findAll();
        LOG.info("Get all users result: " + users);
        List<String> names =
                users.stream().map(User::getName).collect(Collectors.toList());
        request.setAttribute("userNames", names);
        return LIST_ALL_USERS_PAGE;
    }

    @GetMapping(value = "/add")
    public String getAddUserPage() {
        LOG.info("Add page loaded.");
        return ADD_PAGE;
    }

    @PostMapping(value = "/add")
    public String addUser(HttpServletRequest request, HttpServletResponse response) {
        validateRequestParameters(request);
        if(request.getParameter(ERROR_ATTRIBUTE)!=null){
            return ADD_PAGE;
        }
        String name = request.getParameter(USER_NAME_ATTRIBUTE);
        String password = request.getParameter(USER_PASS_ATTRIBUTE);
        if (userRepository.findByName(name) != null) {
            request.setAttribute(ERROR_ATTRIBUTE, String.format("User %s already exist", name));
            LOG.warn(String.format("User %s already exist", name));
            return ADD_PAGE;
        }
        User user = new User().setName(name).setPassword(password);
        userRepository.save(user);
        request.setAttribute(USER_NAME_ATTRIBUTE, name);
        return ADD_PAGE;
    }

    @GetMapping(value = "/delete")
    public String getDeleteUserPage() {
        LOG.info("Deleted page loaded.");
        return DELETE_PAGE;
    }

    @PostMapping(value = "/delete")
    public String deleteUser(HttpServletRequest request, HttpServletResponse response) {
        String name = request.getParameter(USER_NAME_ATTRIBUTE);
        User user = userRepository.findByName(name);
        if (user == null) {
            request.setAttribute(ERROR_ATTRIBUTE, String.format(USER_NOT_EXIST, name));
            LOG.warn(String.format(USER_NOT_EXIST, name));
            return DELETE_PAGE;
        }
        userRepository.delete(user);
        LOG.info("User was deleted. " + user);
        request.setAttribute(USER_NAME_ATTRIBUTE, name);
        return DELETE_PAGE;
    }

    @GetMapping(value = "/users")
    public String getUser(@RequestParam(name = "name", required = false) String name, HttpServletRequest request,
            HttpServletResponse response) {
        if (name == null) {
            LOG.warn("Passed name was null");
            return FIND_USER_PAGE;
        }
        User user = userRepository.findByName(name);
        if (user == null) {
            request.setAttribute(ERROR_ATTRIBUTE, String.format(USER_NOT_EXIST, name));
            LOG.warn(String.format(USER_NOT_EXIST, name));
            return FIND_USER_PAGE;
        }
        request.setAttribute(USER_NAME_ATTRIBUTE, user.getName());
        request.setAttribute(USER_ID_ATTRIBUTE, user.getId());
        request.setAttribute(USER_PASS_ATTRIBUTE, user.getPassword());
        LOG.info("User was found. " + user);
        return FIND_USER_PAGE;
    }

    @PostMapping(value = "/users")
    public String updateUser(HttpServletRequest request, HttpServletResponse response) {
        validateRequestParameters(request);
        if(request.getParameter(ERROR_ATTRIBUTE)!=null){
            return FIND_USER_PAGE;
        }
        Optional<User> user = userRepository.findById(Integer.valueOf(request.getParameter(USER_ID_ATTRIBUTE)));
        User foundUser;
        if(user.isPresent()){
            foundUser = user.get();
        }
        else {
            request.setAttribute(ERROR_ATTRIBUTE, String.format("User with ID %s does not exist", request.getParameter(USER_ID_ATTRIBUTE)));
            LOG.warn(String.format("User with ID %s does not exist", request.getParameter(USER_ID_ATTRIBUTE)));
            return FIND_USER_PAGE;
        }
        foundUser.setPassword(request.getParameter(USER_PASS_ATTRIBUTE));
        foundUser.setName(request.getParameter(USER_NAME_ATTRIBUTE));
        userRepository.save(foundUser);
        request.setAttribute("status", foundUser.getName());
        return FIND_USER_PAGE;
    }

    private void validateRequestParameters(HttpServletRequest request){
        String name = request.getParameter(USER_NAME_ATTRIBUTE);
        String password = request.getParameter(USER_PASS_ATTRIBUTE);
        if(name.isEmpty()|| password.isEmpty()){
            request.setAttribute(ERROR_ATTRIBUTE, String.format("Fields can not be empty. Passed values: Name [%s]. Password [%s]", name,
                    password));
            LOG.warn(String.format("Fields can not be empty. Passed values: Name [%s]. Password [%s]", name,password));
        }
    }
}
