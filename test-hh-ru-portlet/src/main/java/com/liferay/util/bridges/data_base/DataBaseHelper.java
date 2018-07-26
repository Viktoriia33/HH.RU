package com.liferay.util.bridges.data_base;

import com.liferay.util.bridges.entity.Vacancy;

import java.sql.*;
import java.util.LinkedList;

public class DataBaseHelper {

    public static final String TABLE_NAME = "vacancies";

    public static final String ID = "id";

    public static final String NAME = "name_v";
    public static final String NAME_ORGANIZATION = "name_organization";
    public static final String SALARY = "salary";
    public static final String SALARY_INT = "salary_int";
    public static final String DATE = "date_p";
    public static final String AREA = "area";
    public static final String PROF_AREA = "prof_area";

    public static final int LIMIT = 20;

    public static final String SQL_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
            " (" + ID + " INTEGER AUTO_INCREMENT, " + NAME + "  VARCHAR(500), " + NAME_ORGANIZATION + " VARCHAR(500), "
            + SALARY + " VARCHAR(30), " + SALARY_INT + " INT, " + DATE + " DATE, " + AREA + " VARCHAR(30), " +
            PROF_AREA + " VARCHAR(100), PRIMARY KEY(id));";

    public static final String SQL_DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";

    public static final String SQL_SELECT_WHERE_NAME = "SELECT * FROM " + TABLE_NAME + " WHERE " +
            NAME + " LIKE ? " + " LIMIT " + LIMIT + " OFFSET " + "?;";

    public static final String SQL_SELECT = "SELECT * FROM " + TABLE_NAME + " LIMIT " + LIMIT + " OFFSET " + "?;";

    public static final String SQL_SELECT_SORT_BY_SALARY = "SELECT * FROM " + TABLE_NAME + " ORDER BY "
            + SALARY_INT + " DESC LIMIT " + LIMIT + " OFFSET " + "?;";

    public static final String SQL_SELECT_SORT_BY_DATE = "SELECT * FROM " + TABLE_NAME + " ORDER BY " +
            "UNIX_TIMESTAMP(STR_TO_DATE(" + DATE + ", '%Y-%m-%d')) DESC" + " LIMIT " + LIMIT + " OFFSET " + "?;";

    public static final String SQL_INSERT = "INSERT INTO " + TABLE_NAME + "(" + NAME + ", " + NAME_ORGANIZATION + ", "
            + SALARY + ", " + SALARY_INT + ", " + DATE + ", " + AREA + ", " + PROF_AREA +
            ") VALUES (?, ?, ?, ?, ?, ?, ?);";

    private DataBaseConnection instance;
    private Connection connection;

    public DataBaseHelper() {
        instance = DataBaseConnection.getInstance();
        connection = instance.getConnection();
    }

    public void insert(String name, String nameOrganization, String salary, int salaryInt, Date dateTime, String area,
                       String profArea) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, nameOrganization);
            preparedStatement.setString(3, salary);
            preparedStatement.setInt(4, salaryInt);
            preparedStatement.setDate(5, dateTime);
            preparedStatement.setString(6, area);
            preparedStatement.setString(7, profArea);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteTable() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(SQL_DELETE_TABLE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createTable() {
        try (Statement statement = connection.createStatement()) {
            statement.execute(SQL_CREATE_TABLE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public LinkedList<Vacancy> select(int offset, String sql) {
        LinkedList<Vacancy> listVacancies = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, offset);
            ResultSet resultSet = preparedStatement.executeQuery();
            listVacancies = resultList(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listVacancies;
    }

    public LinkedList<Vacancy> selectWhereName(String name, int offset) {
        LinkedList<Vacancy> listVacancies = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_WHERE_NAME)) {
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, offset);
            ResultSet resultSet = preparedStatement.executeQuery();
            listVacancies = resultList(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listVacancies;
    }

    private LinkedList<Vacancy> resultList(ResultSet resultSet) throws SQLException {
        LinkedList<Vacancy> listVacancies = new LinkedList();
        Vacancy vacancy;
        while (resultSet.next()) {
            vacancy = new Vacancy();
            vacancy.setArea(resultSet.getString("area"));
            vacancy.setDate(resultSet.getDate("date_p"));
            vacancy.setName("Должность: " + resultSet.getString("name_v"));
            vacancy.setNameOrganization("Организация: " + resultSet.getString("name_organization"));
            vacancy.setSalary("Зарплата: " + resultSet.getString("salary"));
            vacancy.setProfArea(resultSet.getString("prof_area"));
            listVacancies.add(vacancy);
        }
        return listVacancies;
    }
}
