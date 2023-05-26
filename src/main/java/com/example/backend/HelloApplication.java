package com.example.backend;

import com.example.backend.repositories.news.MySqlNewsRepository;
import com.example.backend.repositories.news.NewsRepository;
import com.example.backend.repositories.user.MySqlUserRepository;
import com.example.backend.repositories.user.UserRepository;
import com.example.backend.services.NewsService;
import com.example.backend.services.UserService;
import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;

import javax.inject.Singleton;
import javax.ws.rs.ApplicationPath;

@ApplicationPath("/api")
public class HelloApplication extends ResourceConfig {
    public HelloApplication() {
        property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);

        AbstractBinder binder = new AbstractBinder() {
            @Override
            protected void configure() {
                this.bind(MySqlUserRepository.class).to(UserRepository.class).in(Singleton.class);
                this.bind(MySqlNewsRepository.class).to(NewsRepository.class).in(Singleton.class);

                this.bindAsContract(NewsService.class);
                this.bindAsContract(UserService.class);
            }
        };
        register(binder);

        // Ucitavamo resurse
        packages("com.example.backend");
    }
}