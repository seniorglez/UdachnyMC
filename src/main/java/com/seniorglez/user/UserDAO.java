package com.seniorglez.user;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class UserDAO {

    private final List<User> users = Arrays.asList(
            new User("Marco", "Passw0rd"),
            new User("ElPanaMiguel", "Passw0rd"),
            new User("Diego", "Passw0rd")
    );
    private static UserDAO instance = new UserDAO();

    private UserDAO(){};

    public static UserDAO getInstance() {
        return instance;
    }

    public User getUserByUsername(String username) {
        return users.stream().filter(b -> b.getUsername().equals(username)).findFirst().orElse(null);
    }

    public Iterable<String> getAllUserNames() {
        return users.stream().map(User::getUsername).collect(Collectors.toList());
    }
}
