package com.liferay.util.bridges.data_base;

import com.liferay.util.bridges.entity.Vacancy;
import com.liferay.util.bridges.json.JsonParser;

import java.util.*;

public class DataBaseOperation {

    private DataBaseHelper dataBaseHelper;
    private LinkedList<Vacancy> listVacancies;


    public DataBaseOperation(JsonParser jsonParser) {
        dataBaseHelper = new DataBaseHelper();
        listVacancies = jsonParser.getListVacancies();
    }

    public DataBaseOperation() {
        dataBaseHelper = new DataBaseHelper();
    }

    public void insertDataIntoDB() {
        for (int i = 0; i < listVacancies.size(); i++) {
            dataBaseHelper.insert(listVacancies.get(i).getName(), listVacancies.get(i).getNameOrganization(),
                    listVacancies.get(i).getSalary(), listVacancies.get(i).getSalaryInt(), listVacancies.get(i).getDate(),
                    listVacancies.get(i).getArea(), listVacancies.get(i).getProfArea());
        }
    }

    public void deleteTable() {
        dataBaseHelper.deleteTable();
    }

    public void createTable() {
        dataBaseHelper.createTable();
    }

    public LinkedList<Vacancy> selectData(int offset, String sql) {
        LinkedList<Vacancy> linkedList = dataBaseHelper.select(offset, sql);
        return linkedList;
    }

    public LinkedList<Vacancy> selectDataWhereName(int offset, String name) {
        LinkedList<Vacancy> linkedList = dataBaseHelper.selectWhereName("%" + name, offset);
        return linkedList;
    }
}
