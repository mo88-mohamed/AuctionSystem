package Database;


import java.sql.*;

public class Database {
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=AuctionSystem;encrypt=true;trustServerCertificate=true;user=user;password=123456789";
    private static Connection connection;
    private static Statement statement;
    private static ResultSet resultSet;
    private static Database database;

    private Database() {}
    //singleton pattern
    public static Database getInstance() throws SQLException {
        if (database==null) {
            database = new Database();
            connection = DriverManager.getConnection(URL);
            statement = connection.createStatement();
        }
        return database;
    }

    public boolean update(String query) throws SQLException {
        try {
            return statement.executeUpdate(query)==1;
        } catch (Exception exception) {
            return false;
        }
    }

    public ResultSet getRecords(String query) throws SQLException {
        resultSet = statement.executeQuery(query);
        return resultSet;
    }
}
