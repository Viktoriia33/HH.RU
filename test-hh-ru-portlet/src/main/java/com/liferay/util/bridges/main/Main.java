package com.liferay.util.bridges.main;

import com.liferay.util.bridges.data_base.DataBaseConnection;
import com.liferay.util.bridges.data_base.DataBaseOperation;

public class Main {
    public static void main(String[] args) {

//        JsonParser jsonParser = new JsonParser();
//        DataBaseConnection dataBaseConnection = DataBaseConnection.getInstance();
//        dataBaseConnection.start();
//        DataBaseOperation dataBaseOperation = new DataBaseOperation(jsonParser);
//        dataBaseOperation.deleteTable();
//        dataBaseOperation.createTable();
//        dataBaseOperation.insertDataIntoDB();
//        dataBaseConnection.stop();

        DataBaseConnection dataBaseConnection = DataBaseConnection.getInstance();
        dataBaseConnection.start();
        DataBaseOperation dataBaseOperation = new DataBaseOperation();

        dataBaseConnection.stop();
    }
}
