package com.seniorglez.infra;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.seniorglez.domain.Users;
import com.seniorglez.domain.model.User;

public class UserJDBC  implements Users {


    public User getUserByUsername(String username) {
        String url = "jdbc:mysql://db/myschema"; //Seriously, I don't know why I have to explicitly define the scheme 
        String query = "SELECT * FROM users WHERE username=?";
        ResultSet resultSet = null;
        try (Connection connection = DriverManager.getConnection(url, "root", "P@ssw0rd");
            PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            resultSet = statement.executeQuery();
            return (resultSet.next()) ? new User(resultSet.getString("username"),resultSet.getString("password")) : null;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (resultSet!=null) try{ resultSet.close(); } catch(Exception e){e.printStackTrace();}; //beautiful line <3
        }
        return null;
    }
}
