package com.example.backend.filter;

import com.example.backend.resources.NewsResource;
import com.example.backend.resources.UserResource;
import com.example.backend.services.UserService;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.util.List;

@Provider
public class AuthFilter implements ContainerRequestFilter {
    @Inject
    private UserService userService;

    @Override
    public void filter(ContainerRequestContext containerRequestContext) {
        if (!this.isAuthRequired(containerRequestContext)) {
            return;
        }

        try {
            String token = containerRequestContext.getHeaderString("Authorization");
            if (token != null && token.startsWith("Bearer ")) {
                token = token.replace("Bearer ", "");
            }

            if (!this.userService.isAuthorized(token)) {
                containerRequestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
            }
        } catch (Exception exception) {
            containerRequestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }
    }

    private boolean isAuthRequired(ContainerRequestContext req) {
        if (req.getUriInfo().getPath().contains("login")) {
            return false;
        }
        List<Object> matchedResources = req.getUriInfo().getMatchedResources();
        for (Object matchedResource : matchedResources) {
            if (matchedResource instanceof NewsResource || matchedResource instanceof UserResource) {
                return true;
            }
        }
        return false;
    }
}
