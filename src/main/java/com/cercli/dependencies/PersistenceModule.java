package com.cercli.dependencies;

import com.google.inject.AbstractModule;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.validation.Validation;
import jakarta.validation.ValidatorFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Guice module for setting up persistence and dependency injection.
 */
public class PersistenceModule extends AbstractModule {
    @Override
    protected void configure() {
        // Read environment variables
        String dbUser = System.getenv("DB_USERNAME");
        String dbPassword = System.getenv("DB_PASSWORD");

        Map<String, Object> properties = new HashMap<>();
        if (dbUser != null) {
            properties.put("jakarta.persistence.jdbc.user", dbUser);
        }
        if (dbPassword != null) {
            properties.put("jakarta.persistence.jdbc.password", dbPassword);
        }

        // Build the ValidatorFactory and include it in the properties
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        properties.put("jakarta.persistence.validation.factory", validatorFactory);

        // Ensure the event listeners are auto-registered
        properties.put("hibernate.validator.autoregister_listeners", "true");

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("EmployeePU", properties);
        bind(EntityManagerFactory.class).toInstance(emf);
        bind(EntityManager.class).toProvider(emf::createEntityManager);
    }
}
