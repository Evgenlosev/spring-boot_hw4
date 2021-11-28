package com.geekbrains.spring.web.repositories;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SessionFactoryUtils {
    private SessionFactory factory;

    @Autowired
    public void init() {
        factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();
    }

    public Session getSession() {
        return factory.getCurrentSession();
    }

    public void shutdown() {
        if (factory != null) {
            factory.close();
        }
    }
}
