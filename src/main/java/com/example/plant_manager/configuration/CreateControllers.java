package com.example.plant_manager.configuration;

import com.example.plant_manager.component.DtoFunctionFactory;
import com.example.plant_manager.user.controller.UserController;
import com.example.plant_manager.user.service.UserService;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class CreateControllers implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent event) {
        UserService userService = (UserService) event.getServletContext().getAttribute("userService");
        String avatarDir = event.getServletContext().getInitParameter("avatarDir");
        event.getServletContext().setAttribute("userController", new UserController(
                userService,
                new DtoFunctionFactory(),
                avatarDir
        ));
    }
}
