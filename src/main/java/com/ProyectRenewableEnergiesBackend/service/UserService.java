package com.ProyectRenewableEnergiesBackend.service;

import com.ProyectRenewableEnergiesBackend.DTO.UserResponse;
import com.ProyectRenewableEnergiesBackend.model.User;
import com.ProyectRenewableEnergiesBackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User add(User user) {
        return userRepository.save(user);
    }

    public List<UserResponse> getAll() {
        List<User> userList = userRepository.findAll();
        return userList.stream()
                .map(this::createUserResponse)  //.map( oldUser -> createUserResponse(oldUser))
                .collect(Collectors.toList());
    }

    public Optional<User> getById(String username) {
        Optional<User> user = userRepository.findById(username);
        UserResponse userResponse = new UserResponse();
        userResponse.setUserName(user.get().getUserName());
        userResponse.setName(user.get().getName());
        userResponse.setLastName(user.get().getLastName());
        userResponse.setEmail(user.get().getEmail());
        userResponse.setPermissions(user.get().getPermissions());
        return user; // TODO controlar cuando el id es nulo
    }

    public boolean validateUser(String id, String password){
        Optional<User> user = userRepository.findById(id);
        boolean response = false;
        if(user.isPresent()){
            response = user.get().getPassword().equals(password);
        }
        return response;
    }
/*
    public Optional<UserResponse> getByIdPass(String username, String password){
        Optional<User> user = userRepository.findById(username);
        UserResponse userResponse = new UserResponse();
        if(user.isPresent() && user.get().getPassword().equals(password)){
            userResponse.setUserName(user.get().getUserName());
            userResponse.setName(user.get().getName());
            userResponse.setLastName(user.get().getLastName());
            userResponse.setEmail(user.get().getEmail());
            userResponse.setPermissions(user.get().getPermissions());
        }
        return Optional.of(userResponse);
    }
*/
    public void deleteById(String usernane) {
        userRepository.deleteById(usernane);
    }

    public boolean existByUsername(String username) {
        return userRepository.existsById(username);
    }

    public boolean existByEmail(String email) {
        return userRepository.existsByEmail(email);
    }


    public UserResponse createUserResponse(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setUserName(user.getUserName());
        userResponse.setName(user.getName());
        userResponse.setLastName(user.getLastName());
        userResponse.setEmail(user.getEmail());
        userResponse.setPermissions(user.getPermissions());

        return userResponse;
    }

    public User updateUserById(String id, User updatedUser){
        return userRepository.findById(id).map(existingUser->{
            if(updatedUser.getPassword()!=null){
                existingUser.setPassword(updatedUser.getPassword());
            }
            if(updatedUser.getName()!=null){
                existingUser.setName(updatedUser.getName());
            }
            if(updatedUser.getLastName()!=null){
                existingUser.setLastName(updatedUser.getLastName());
            }
            if(updatedUser.getEmail()!=null){
                existingUser.setEmail(updatedUser.getEmail());
            }
            return userRepository.save(existingUser);
        }).orElseThrow(()->new RuntimeException("User with ID "+id+" not found"));
    }
}
