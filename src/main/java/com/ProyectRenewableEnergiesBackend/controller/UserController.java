package com.ProyectRenewableEnergiesBackend.controller;

import com.ProyectRenewableEnergiesBackend.DTO.UserResponse;
import com.ProyectRenewableEnergiesBackend.model.User;
import com.ProyectRenewableEnergiesBackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200/")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<UserResponse> add(@RequestBody User user) {
        User newUser = userService.add(user);
        UserResponse userResponse = userService.createUserResponse(newUser);
        return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAll() {
        List<UserResponse> userList = userService.getAll();
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable("id") String username) {
        Optional<User> userResponse = userService.getById(username);
        return userResponse.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/validate/{id}")
    public ResponseEntity<?> validateUser(@PathVariable("id") String username, @RequestBody String password){
        boolean isValid = userService.validateUser(username,password);
        return ResponseEntity.ok().body("{\"valid\": " + isValid + "}");
    }

    @GetMapping("/profile/{id}")
    public ResponseEntity<Optional<User>> getUserProfile(@PathVariable("id") String username){
        Optional<User> user = userService.getById(username);
        if(user.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }
/*
    @GetMapping("/login/{id}")
    public ResponseEntity<UserResponse> getByIdPass(@PathVariable("id") String username, @RequestBody String password){
        Optional<UserResponse> userResponse = userService.getByIdPass(username, password);
        return userResponse.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
*/
    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteById(@PathVariable("id") String username) {
        userService.deleteById(username);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") String username, @RequestBody User user){
        User partialUpdateUser = new User();
        if(user.getPassword()!=null){
            partialUpdateUser.setPassword(user.getPassword());
        }
        if(user.getName()!=null){
            partialUpdateUser.setName(user.getName());
        }
        if(user.getLastName()!=null){
            partialUpdateUser.setLastName(user.getLastName());
        }
        if(user.getEmail()!=null){
            partialUpdateUser.setEmail(user.getEmail());
        }
        User updatedUser = userService.updateUserById(username,partialUpdateUser);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }
}
