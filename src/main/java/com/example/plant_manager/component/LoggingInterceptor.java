package com.example.plant_manager.component;

import com.example.plant_manager.plant.entity.Plant;
import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import jakarta.security.enterprise.SecurityContext;
import lombok.extern.java.Log;

import java.io.Serializable;
import java.util.UUID;
import java.util.logging.Level;

@Log
@Interceptor
@LogOperation
@Priority(Interceptor.Priority.APPLICATION)
public class LoggingInterceptor implements Serializable {

    @Inject
    private SecurityContext securityContext;

    @AroundInvoke
    public Object logMethodEntry(InvocationContext context) throws Exception {
        String user = "anonymous";
        if (securityContext != null && securityContext.getCallerPrincipal() != null) {
            user = securityContext.getCallerPrincipal().getName();
        }

        String operation = context.getMethod().getName();

        String resourceId = "unknown";
        Object[] parameters = context.getParameters();

        if (parameters.length > 0) {
            for (Object param : parameters) {
                if (param instanceof UUID) {
                    resourceId = param.toString();
                    break;
                }
                else if (param instanceof Plant) {
                    resourceId = ((Plant) param).getId().toString();
                    break;
                }
            }
        }

        log.log(Level.INFO, "User: {0}, Operation: {1}, Resource ID: {2}", new Object[]{user, operation, resourceId});

        return context.proceed();
    }
}