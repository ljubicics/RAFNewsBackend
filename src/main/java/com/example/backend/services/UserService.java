package com.example.backend.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.example.backend.entities.User;
import com.example.backend.repositories.user.UserRepository;
import org.apache.commons.codec.digest.DigestUtils;


import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class UserService {
    @Inject
    private UserRepository userRepository;

    public String login(String email, String password)
    {
        String hashedPassword = DigestUtils.sha256Hex(password);
        System.out.println(hashedPassword);
        User user = this.userRepository.findUser(email);

        if (user == null || !user.getUser_password().equals(hashedPassword) || user.isUser_status() == false) {
            return null;
        }
        Date issuedAt = new Date();
        Date expiresAt = new Date(issuedAt.getTime() + 24*60*60*1000);
        Algorithm algorithm = Algorithm.HMAC256("secret");
        return JWT.create()
                .withIssuedAt(issuedAt)
                .withExpiresAt(expiresAt)
                .withSubject(email)
                .withClaim("type", user.getUser_type())
                .withClaim("id", user.getUser_id())
                .sign(algorithm);
    }

    public boolean isAuthorized(String token, ContainerRequestContext containerRequestContext) {
        Algorithm algorithm = Algorithm.HMAC256("secret");
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT jwt = verifier.verify(token);
        String email = jwt.getSubject();
        User user = this.userRepository.findUser(email);
        String type = jwt.getClaim("type").asString();
        if (user == null) {
            return false;
        }

        if(containerRequestContext.getUriInfo().getPath().contains("update")) {
            return true;
        }
        if(containerRequestContext.getUriInfo().getPath().contains("users") && (type == null || type.equals("CONTENT_CREATOR"))) {
            return false;
        }
        return true;
    }

    public boolean isAuthorizedUser(String token) {
        Algorithm algorithm = Algorithm.HMAC256("secret");
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT jwt = verifier.verify(token);
        Map<String, Claim> claimMap = jwt.getClaims();
        Claim claim = claimMap.get("type");
        String type = claim.asString();
        if (type.equals("NORMAL_USER")) {
            return false;
        }
        return true;
    }

    public List<User> allUsers() {
        return this.userRepository.allUsers();
    }

    public User addUser(User user){
        return this.userRepository.addUser(user);
    }

    public User updateUser(User newUser, String email) {
        return this.userRepository.updateUser(newUser, email);
    }

    public void deleteUser(String email) {
        this.userRepository.deleteUser(email);
    }

    public User findUser(String email) {
        return this.userRepository.findUser(email);
    }

    public void changeStatus(String email) {this.userRepository.userStatus(email);}

}
