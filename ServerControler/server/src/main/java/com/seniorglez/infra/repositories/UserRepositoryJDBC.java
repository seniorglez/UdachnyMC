package com.seniorglez.infra.repositories;

import java.sql.*;
import com.seniorglez.domain.UserRepository;
import com.seniorglez.domain.model.User;
import com.seniorglez.domain.model.UserErrors;

import com.seniorglez.functionalJava.monads.Result;

public class UserRepositoryJDBC implements UserRepository {

    @Override
    public Result< User, UserErrors > getUserByUsername( String username ) {
        String url = "jdbc:mysql://db/myschema";
        String query = "SELECT * FROM users WHERE username=?";
        ResultSet resultSet = null;
        try (Connection connection = DriverManager.getConnection( url, "root", "P@ssw0rd" );
            PreparedStatement statement = connection.prepareStatement( query )) {
            statement.setString( 1, username );
            resultSet = statement.executeQuery();
            return ( resultSet.next() ) ? new Result.Success< User, UserErrors >(new User( resultSet.getString( "username" ),resultSet.getString( "password" ))) : new Result.Failure< User , UserErrors >( UserErrors.USER_DOES_NOT_EXIST );
        } catch ( SQLTimeoutException e ) {
            e.printStackTrace();
            return new Result.Failure< User, UserErrors >( UserErrors.TIMEOUT ) ;
        } catch ( SQLException e ) {
            e.printStackTrace();
            return new Result.Failure<User, UserErrors>( UserErrors.SERVER_ERROR );
        } finally {
            if ( resultSet!=null ) try {
                 resultSet.close(); 
                } catch( Exception e ) {
                    e.printStackTrace();
                    return new Result.Failure< User, UserErrors >( UserErrors.SERVER_ERROR );
            }; //beautiful <3
        }
    }

    public Result<User, UserErrors> getUser(String username, String password) {
        return this.getUserByUsername(username);//REFACTOR ME PLZ
    }
}
