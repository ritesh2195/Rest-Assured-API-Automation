package Utility;

import java.sql.*;

public class DBConnector {

    private final Statement statement;
    private final Connection connection;

    public DBConnector() throws ClassNotFoundException, SQLException {

        Class.forName("com.mysql.cj.jdbc.Driver");

        connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/JIRA","user","password");

        statement = connection.createStatement();

    }

    public String getQuery(String query){

        try {
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()){

                return resultSet.getString("commentText");
            }

            resultSet.close();

            statement.close();

            connection.close();

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return "";
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        DBConnector connector = new DBConnector();

        String value = connector.getQuery("Select * from issue");

        System.out.println(value);
    }
}
