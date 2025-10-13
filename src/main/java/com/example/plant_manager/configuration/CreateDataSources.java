package com.example.plant_manager.configuration;

import com.example.plant_manager.serialization.CloningUtility;
import com.example.plant_manager.user.repository.UserRepository;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class CreateDataSources implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent event) {
        event.getServletContext().setAttribute("userRepository", new UserRepository(new CloningUtility()));
    }
}
