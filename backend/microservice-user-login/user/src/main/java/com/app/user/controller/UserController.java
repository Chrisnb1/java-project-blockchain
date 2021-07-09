package com.app.user.controller;

import com.app.user.dto.UserDTO;
import com.app.user.dto.UserLogin;
import com.app.user.model.User;
import com.app.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@Validated
public class UserController {

    private final IUserService userService;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public Object getListAllUsers() {
        try {
            List<User> users = userService.getAllUser();
            return ResponseEntity.accepted().body(users);
        } catch (Exception e) {
            System.out.println("Error: " + e);
            return e.getCause();

        }
    }

    @GetMapping("/login")
    public Object loginUser(@Valid @RequestBody UserLogin login){
        try{
            boolean user = userService.getLogin(login);
            return ResponseEntity.accepted().body(user);
        } catch (Exception e){
            System.out.println("Error: " + e);
            return e.getCause();
        }
    }

    @PostMapping("/registration")
    public Object createNewUser(@Valid @RequestBody UserDTO request){
        try{
            System.out.println("request: " + request);
            User newUser = userService.createUser(request);
            System.out.println("nuevo usuario desde controler: " + newUser);
            if(newUser == null){
                return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
            }
            return ResponseEntity.accepted().body(newUser);
        } catch (Exception e){
            System.out.println("Error: " + e);
            return e.getCause();
        }
    }
}
