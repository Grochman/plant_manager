package com.example.plant_manager.configuration;

import com.example.plant_manager.user.repository.UserRepository;
import com.example.plant_manager.user.service.UserService;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class CreateServices implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent event) {
        UserRepository userRepository = (UserRepository) event.getServletContext().getAttribute("userRepository");

        event.getServletContext().setAttribute("userService", new UserService(userRepository));
    }
}
