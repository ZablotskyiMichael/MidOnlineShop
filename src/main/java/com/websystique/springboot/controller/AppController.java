package com.websystique.springboot.controller;

import com.websystique.springboot.model.User;
import com.websystique.springboot.service.UserDto;
import com.websystique.springboot.service.UserService;
import org.h2.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.*;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

@Controller
public class AppController {
    private static String UPLOADED_FOLDER = "D:/Новая папка/webapp13ver/src/main/resources/static/images/";
	@Autowired
    UserService userService;

	@RequestMapping("/")
	String home(ModelMap modal) {
		modal.addAttribute("title","CRUD Example");
		return "index";
	}

	@RequestMapping("/login")
	String login (ModelMap modal) {
		modal.addAttribute("title","Login");
		return "login";
	}

	@RequestMapping("/partials/{page}")
	String partialHandler(@PathVariable("page") final String page,Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String name = auth.getName(); //get logged in username

		model.addAttribute("user", userService.findByName(name));
		return page;
	}
	@RequestMapping("/productadmin")
	String adminHandler() {
		return "productadmin";
	}

	@RequestMapping("/administrator/{page}")
	String adminHandler(@PathVariable("page") final String page) {

		return page;
	}

    @RequestMapping("/images/jpg/{name}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable("name") String name) throws IOException {
		try{
        String filename = UPLOADED_FOLDER + name + ".jpg";
        InputStream inputImage = new FileInputStream(filename);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[512];
        int l = inputImage.read(buffer);
        while(l >= 0) {
            outputStream.write(buffer, 0, l);
            l = inputImage.read(buffer);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "image/jpeg");
        headers.set("Content-Disposition", "attachment; filename=\"" + name + ".jpg\"");
        return new ResponseEntity<byte[]>(outputStream.toByteArray(), headers, HttpStatus.OK);
		}catch (FileNotFoundException e){
			System.out.println("File not found: " + name + ".jpg");
		}
		return null;
    }

}
