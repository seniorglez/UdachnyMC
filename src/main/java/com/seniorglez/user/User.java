package com.seniorglez.user;

/**
 * A model class which represents a user of the service.
 */
public class User {

    private String username;
    private String password;

    public User( String name, String password ) {
        this.username = name;
        this.password = password;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

}
