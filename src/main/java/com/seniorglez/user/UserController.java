package com.seniorglez.user;

public class UserController {

    public static boolean authenticate(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            return false;
        }
        User user = UserDAO.getInstance().getUserByUsername(username);
        if (user == null) {
            return false;
        }
        return (user.getPassword().equals(password));
    }
}
