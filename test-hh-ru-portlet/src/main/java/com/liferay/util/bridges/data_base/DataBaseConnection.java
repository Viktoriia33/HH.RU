package com.liferay.util.bridges.data_base;

import java.sql.*;

public class DataBaseConnection {

    private static DataBaseConnection instance;

    private final String connectionString = "jdbc:mysql://localhost:3306/lportal?useLegacyDatetimeCode=" +
            "false&serverTimezone=UTC";
    private final String login = "root";
    private final String password = "root";
    private Connection connection;

    public static final DataBaseConnection getInstance() {
        if (instance == null) instance = new DataBaseConnection();
        return instance;
    }

    public void start() {
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
        } catch (SQLException e) {
            System.out.println("Can't register Driver.");
            e.printStackTrace();
            return;
        }

        try {
            connection = DriverManager.getConnection(connectionString, login, password);
            System.out.println("Connection successfully!");
            System.out.println("Заполнение базы данных...");
        } catch (SQLException e) {
            System.out.println("Can't get connection. Incorrect URL");
            e.printStackTrace();
            return;
        }
    }

    public void stop() {
        try {
            connection.close();
            System.out.println("Connection close successfully.");
        } catch (SQLException e) {
            System.out.println("Can't close connection");
            e.printStackTrace();
            return;
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
