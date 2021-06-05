package com.seniorglez.infra;

import java.sql.*;


import com.seniorglez.domain.Users;
import com.seniorglez.domain.model.User;
import com.seniorglez.domain.model.UserErrors;

import com.seniorglez.functionalJava.monads.Result;

public class UserJDBC  implements Users {


    public Result<User, UserErrors> getUserByUsername(String username) {
        String url = "jdbc:mysql://db/myschema"; //Seriously, I don't know why I have to explicitly define the scheme 
        String query = "SELECT * FROM users WHERE username=?";
        ResultSet resultSet = null;
        try (Connection connection = DriverManager.getConnection(url, "root", "P@ssw0rd");
            PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            resultSet = statement.executeQuery();
            return (resultSet.next()) ? new Result.Success<User, UserErrors>(new User(resultSet.getString("username"),resultSet.getString("password"))) : new Result.Failure<User, UserErrors>(UserErrors.USER_DOES_NOT_EXIST);
        } catch (SQLTimeoutException e) {
            e.printStackTrace();
            return new Result.Failure<User, UserErrors>(UserErrors.TIMEOUT);
        } catch (SQLException e) {
            e.printStackTrace();
            return new Result.Failure<User, UserErrors>(UserErrors.SERVER_ERROR);
        }finally {
            if (resultSet!=null) try {
                 resultSet.close(); 
                } catch(Exception e) {
                    e.printStackTrace();
                    return new Result.Failure<User, UserErrors>(UserErrors.SERVER_ERROR);
            }; //beautiful <3
        }
    }
}
