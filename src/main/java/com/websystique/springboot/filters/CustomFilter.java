package com.websystique.springboot.filters;

import com.websystique.springboot.model.HistoryOfVisits;
import com.websystique.springboot.service.HistoryService;
import com.websystique.springboot.service.ProductService;
import com.websystique.springboot.service.UserService;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomFilter extends GenericFilterBean {

    HistoryService historyService;
    ProductService productService;
    UserService userService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        final HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        final HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        System.out.println("request is " + httpRequest.getClass());
        System.out.println("request URL : " + httpRequest.getRequestURL());
        System.out.println("response is " + httpResponse.getClass());
        ServletContext servletContext = servletRequest.getServletContext();
        WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
        userService = webApplicationContext.getBean(UserService.class);
        productService = webApplicationContext.getBean(ProductService.class);
        historyService = webApplicationContext.getBean(HistoryService.class);
        if(httpRequest.getRequestURL().length()>35 && httpRequest.getRequestURL().substring(0,35).equals("http://localhost:8080/user/product/")){
          // createHistory(httpRequest);
            System.out.println("yeeeees");
            StringBuffer url = httpRequest.getRequestURL();
            System.out.println(url.substring(url.length()-1,url.length()));
            //historyService.createHistory(Long.valueOf(url.substring(url.length()-1,url.length())));
            createHistory(httpRequest);
        }else{
            System.out.println("nooooo");
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    private void createHistory(HttpServletRequest httpRequest){
        HistoryOfVisits historyOfVisits = new HistoryOfVisits();
        historyOfVisits.setUserId(userService.getCurrentUser());
        historyOfVisits.setProduct(productService.findById(Long.valueOf(httpRequest.getRequestURL().substring(35,36))));
        historyOfVisits.setTime(getCurrentTime());
        if (historyService.isNewVisit(getCurrentTime()))
         historyService.saveHistory(historyOfVisits);
    }
    private String getCurrentTime(){
        java.util.Date javaDate = new java.util.Date();
        long javaTime = javaDate.getTime();

       return  new java.sql.Date(javaTime).toString() +" " +  new java.sql.Time(javaTime).toString();
    }
}

