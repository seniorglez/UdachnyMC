package com.seniorglez.domain.model;

/**
 * A model class which represents a user of the service.
 */
public class User {

    /**
     * A string which represent the the user's username.
     */
    private String username;
    /**
     * A string which represent the the user's password.
     */
    private String password;

    /**
     * Constructs a new User with the given parameters.
     * @param name A string which represent the username of the user.
     * @param password A string which represent the username of the user.
     */
    public User( String name, String password ) {
        this.username = name;
        this.password = password;
    }

    /**
     * Return a string which represent the user's username.
     * @return a string which represent the user's username.
     */
    public String getUsername() {
        return this.username;
    }

    /**
     *  Return a string which represent the user's password.
     * @return a string which represent the user's password.
     */
    public String getPassword() {
        return this.password;
    }

}
