package com.example.backend.resources;

import com.example.backend.entities.User;
import com.example.backend.request.LoginRequest;
import com.example.backend.services.UserService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/users")
public class UserResource {
    @Inject
    private UserService userService;

    @POST
    @Path("/login")
    @Produces({MediaType.APPLICATION_JSON})
    public Response login(@Valid LoginRequest loginRequest)
    {
        Map<String, String> response = new HashMap<>();
        String jwt = this.userService.login(loginRequest.getEmail(), loginRequest.getPassword());
        if (jwt == null) {
            response.put("message", "These credentials do not match our records");
            return Response.status(422, "Unprocessable Entity").entity(response).build();
        }

        response.put("jwt",jwt);
        return Response.ok(response).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> all()
    {
        return this.userService.allUsers();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public User addUser(@Valid User user) {
        return this.userService.addUser(user);
    }

    @GET
    @Path("/{email}")
    @Produces(MediaType.APPLICATION_JSON)
    public User findUser(@PathParam("email") String email) {
        return this.userService.findUser(email);
    }

    @PATCH
    @Path("/{email}")
    @Produces(MediaType.APPLICATION_JSON)
    public User updateUser(@Valid User newUser, @PathParam("email") String email) {
        return this.userService.updateUser(newUser, email);
    }

    @DELETE
    @Path("/delete/{email}")
    @Produces(MediaType.APPLICATION_JSON)
    public void delete(@PathParam("email") String email) {
        this.userService.deleteUser(email);
    }

    @GET
    @Path("/updateStatus/{email}")
    public void changeStatus(@PathParam("email") String email) {this.userService.changeStatus(email);}

}
