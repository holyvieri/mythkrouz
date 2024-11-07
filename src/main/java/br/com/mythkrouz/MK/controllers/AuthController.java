package br.com.mythkrouz.MK.controllers;

import br.com.mythkrouz.MK.entities.User;
import br.com.mythkrouz.MK.exceptions.EntityAlreadyExistsException;
import br.com.mythkrouz.MK.security.TokenService;
import br.com.mythkrouz.MK.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("api/auth")
public class AuthController {
    @Autowired
    UserService userService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    TokenService tokenService;


    @PostMapping("/register")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        try {
            User createdUser = userService.createUser(user);
            return ResponseEntity.ok(createdUser);
        }catch (EntityAlreadyExistsException e){
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody Map<String, String> authRequest) {
        String email = authRequest.get("email");
        String password = authRequest.get("password");

        var usernamePassword = new UsernamePasswordAuthenticationToken(email, password);

        var auth = this.authenticationManager.authenticate(usernamePassword);

        String token = tokenService.generateToken((User)auth.getPrincipal());

        return ResponseEntity.ok().body(token);
    }

}
