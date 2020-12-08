package com.seniorglez.user;

/**
 * Allows to check if a user is on the database.
 */
public class UserController {

    /**
     * Returns true if there is a user with the given credentials registered on the application.
     * @param username A string which represent the the user's username.
     * @param password A string which represent the the user's password.
     * @return true if there is a user with the same username and password.
     */
    public static boolean authenticate( String username, String password ) {
        if( username ==null || password == null ) return false;
        if ( username.isEmpty() || password.isEmpty() ) {
            return false;
        }
        User user = UserDAO.getInstance().getUserByUsername( username );
        if ( user == null ) {
            return false;
        }
        return  user.getPassword().equals(password);
    }
}
