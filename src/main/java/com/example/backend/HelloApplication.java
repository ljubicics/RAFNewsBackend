package com.example.backend;

import com.example.backend.repositories.category.CategoryRepository;
import com.example.backend.repositories.category.MySqlCategoryRepository;
import com.example.backend.repositories.comment.CommentRepository;
import com.example.backend.repositories.comment.MySqlCommentRepository;
import com.example.backend.repositories.news.MySqlNewsRepository;
import com.example.backend.repositories.news.NewsRepository;
import com.example.backend.repositories.user.MySqlUserRepository;
import com.example.backend.repositories.user.UserRepository;
import com.example.backend.services.CategoryService;
import com.example.backend.services.CommentService;
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
                this.bind(MySqlCategoryRepository.class).to(CategoryRepository.class).in(Singleton.class);
                this.bind(MySqlCommentRepository.class).to(CommentRepository.class).in(Singleton.class);

                this.bindAsContract(CommentService.class);
                this.bindAsContract(CategoryService.class);
                this.bindAsContract(NewsService.class);
                this.bindAsContract(UserService.class);
            }
        };
        register(binder);

        // Ucitavamo resurse
        packages("com.example.backend");
    }
}