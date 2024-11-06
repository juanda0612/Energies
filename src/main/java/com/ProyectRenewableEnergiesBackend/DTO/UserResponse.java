package com.ProyectRenewableEnergiesBackend.DTO;

import com.ProyectRenewableEnergiesBackend.model.Permissions;

import java.util.List;

public class UserResponse {
    private String userName;
    private String name;
    private String lastName;
    private String email;
    private List<Permissions> permissions;

    public UserResponse() {
    }

    public UserResponse(String userName, String name, String lastName, String email, List<Permissions> permissions) {
        this.userName = userName;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.permissions = permissions;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Permissions> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permissions> permissions) {
        this.permissions = permissions;
    }
}
