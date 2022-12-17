package ClientSide;

import Database.Database;
import Models.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Authentication {
    private ResultSet resultSet;
    private String query;
    public Authentication() {
    }
    public boolean isUserExist(String email) throws SQLException {
        query = "select count(1) as exist from USERS where email= '"+ email +"'";
        resultSet = Database.getInstance().getRecords(query);
        resultSet.next();
        return resultSet.getBoolean("exist");
    }

    public boolean createNewUser(User user) throws SQLException {
        query = "INSERT INTO USERS(EMAIL, FULL_NAME, PASSWORD) values('" + user.getEmail() + "','" + user.getFullName() + "','" + user.getPassword() + "')";
        return Database.getInstance().update(query);
    }

    public boolean logIn(User user) throws SQLException {
        query = "select count(1) as exist from USERS where EMAIL='" + user.getEmail() + "' and PASSWORD='" + user.getPassword() + "'";
        resultSet = Database.getInstance().getRecords(query);
        resultSet.next();
        return resultSet.getBoolean("exist");
    }
}
