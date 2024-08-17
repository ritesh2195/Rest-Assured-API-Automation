package com.jira.utils;

import java.sql.*;

public class DBUtil {

    private final Statement statement;
    private final Connection connection;

    public DBUtil() throws ClassNotFoundException, SQLException {

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
        DBUtil connector = new DBUtil();

        String value = connector.getQuery("Select * from issue");

        System.out.println(value);
    }
}
