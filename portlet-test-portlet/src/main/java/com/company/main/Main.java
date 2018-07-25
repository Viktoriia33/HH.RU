package com.company.main;

import com.company.data_base.DataBaseConnection;
import com.company.data_base.DataBaseOperation;
import com.company.json.JsonParser;
import com.company.ui.DialogInfo;
import com.company.ui.UserInterface;

public class Main {
    public static void main(String[] args) {

        new DialogInfo();

        JsonParser jsonParser = new JsonParser();

        DataBaseConnection dataBaseConnection = DataBaseConnection.getInstance();
        dataBaseConnection.start();

        DataBaseOperation dataBaseOperation = new DataBaseOperation(jsonParser);

        dataBaseOperation.deleteTable();
        dataBaseOperation.createTable();
        dataBaseOperation.insertDataIntoDB();

        dataBaseConnection.stop();
        new UserInterface(jsonParser.getAmountPages());

    }
}
