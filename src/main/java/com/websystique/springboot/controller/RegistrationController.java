package com.websystique.springboot.controller;

import com.websystique.springboot.model.User;
import com.websystique.springboot.service.DictionaryService;
import com.websystique.springboot.service.UserDto;
import com.websystique.springboot.service.UserService;
import com.websystique.springboot.util.CustomErrorType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class RegistrationController {

    @Autowired
    UserService userService;

    @Autowired
    DictionaryService dictionaryService;

    @RequestMapping(value = "/user/registration", method = RequestMethod.GET)
    public String showRegistrationForm(WebRequest request, Model model) {
        UserDto userDto = new UserDto();
        model.addAttribute("user", userDto);
        model.addAttribute("listOfUsers" , userService.findAllUsers());
        return "registration";
    }
    @RequestMapping(value = "/user/registration", method = RequestMethod.POST)
    public ModelAndView registerUserAccount(
            @ModelAttribute("user")  UserDto accountDto,
            BindingResult result,
            WebRequest request,
            Errors errors) {
        if (accountDto.getName()==null || accountDto.getPassword()==null){
            result.addError(new ObjectError("name","Name or password is empty !"));
            return new ModelAndView("registration", "error", new CustomErrorType("Name or password is empty !"));}
        User registered = new User();
        if (!result.hasErrors()) {
            registered = createUserAccount(accountDto, result);
        }
        if (registered == null) {
            result.rejectValue("name", "message.regError");
        }
        if (result.hasErrors()) {
            return new ModelAndView("registration", "user", accountDto ).addObject("listOfUsers",userService.findAllUsers());
        }
        else {
            return new ModelAndView("login");
        }
    }
    private User createUserAccount(UserDto accountDto, BindingResult result) {
        User registered = null;
        registered = userService.registerNewUserAccount(accountDto);
        return registered;
    }
    @RequestMapping(value = "/user/account", method = RequestMethod.GET)
    public String showUserAccount(WebRequest request, Model model) {

        User current = getCurrentUser();
        UserDto userDto = getData(new UserDto(),current);
        model.addAttribute("user", userDto);
        return "account";
    }
    @RequestMapping(value = "/user/account", method = RequestMethod.POST)
    public ModelAndView updateUserAccount(
            @ModelAttribute("user")  UserDto user,
            BindingResult result,
            WebRequest request,
            Errors errors) {
        User updateUser = new User();

        User current = getCurrentUser();
        if (!result.hasErrors()) {
            updateUser.setId(current.getId());
            updateUser.setEnabled(current.getEnabled());
            updateUser.setName(user.getName());
            updateUser.setRole(current.getRole());
            updateUser.setPassword(user.getPassword());
            userService.updateUser(updateUser);
        }

        if (result.hasErrors()) {
            return new ModelAndView("account");
        }
        else {

            return new ModelAndView("account", "user", getData(new UserDto(),current));
        }
    }

    private User getCurrentUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        return userService.findByName(name);
    }
    private UserDto getData (UserDto userDto , User current){
        userDto.setId(current.getId());
        userDto.setName(current.getName());
        userDto.setPassword(current.getPassword());
        userDto.setRoleTitle(current.getRole().getName());
        return userDto;
    }
}
