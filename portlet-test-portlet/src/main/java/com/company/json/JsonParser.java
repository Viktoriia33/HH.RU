package com.company.json;

import com.company.entity.Vacancy;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Date;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class JsonParser {

    private final String urlVacancies = "https://api.hh.ru/vacancies?specialization=1&area=4&page=";
    private final String urlSpecializations = "https://api.hh.ru/specializations";

    private final String salaryIsNotIndicated = "з/п не указана";

    private int amountPages;

    private String resultJson;
    private String profArea;

    private Vacancy vacancy;
    private LinkedList<Vacancy> listVacancies;

    public JsonParser() {

        listVacancies = new LinkedList();

        connectUrl(urlSpecializations);
        parserSpecializations(resultJson);

        connectUrl(urlVacancies + 0);
        parserAmount(resultJson);
        parserVacancies(resultJson);

        if (amountPages > 100) {
            amountPages = 100;
        }

        for (int i = 1; i < amountPages; i++) {
            connectUrl(urlVacancies + i);                  //добавляем в linkedList вакансии со всех страниц
            parserVacancies(resultJson);
        }
    }

    public void connectUrl(String stringUrl) {
        try {
            URL url = new URL(stringUrl);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }

            resultJson = buffer.toString();

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void parserSpecializations(String resultJson) throws JSONException {
        JSONArray jArray = new JSONArray(resultJson);
        profArea = jArray.getJSONObject(0).getString("name");
    }

    public void parserAmount(String resultJson) throws JSONException {
        JSONObject jsonObject = new JSONObject(resultJson);
        amountPages = jsonObject.getInt("pages");
    }

    public void parserVacancies(String resultJson) throws JSONException {
        JSONObject jsonObject = new JSONObject(resultJson);

        JSONArray jArray = jsonObject.getJSONArray("items");

        for (int i = 0; i < jArray.length(); i++) {
            JSONObject json = jArray.getJSONObject(i);

            String area = json.getJSONObject("area").getString("name");
            Date dateTime = retrieveDateTime(json);
            String name = retrieveName(json);
            String nameOrganization = retrieveNameOrganization(json);

            String salary;
            Object object = retrieveSalary(json);
            if (!object.equals(null)) {
                salary = (String) object;
            } else salary = salaryIsNotIndicated;

            int salaryInt = retrieveSalaryInt(salary);

            vacancy = new Vacancy(name, nameOrganization, dateTime, salary, salaryInt, area, profArea);
            listVacancies.add(vacancy);
        }
    }

    public int retrieveSalaryInt(String salary) {
        int salaryInt;
        if (salary.equals("з/п не указана")) {
            salaryInt = 0;
        } else if (salary.substring(0, 2).equals("До") || salary.substring(0, 2).equals("От")) {
            StringTokenizer tokenizer = new StringTokenizer(salary, " ");
            tokenizer.nextToken();
            int s = Integer.valueOf(tokenizer.nextToken());
            salaryInt = s;
        } else {
            StringTokenizer tokenizer = new StringTokenizer(salary, " -");
            int s = Integer.valueOf(tokenizer.nextToken());
            salaryInt = s;
        }
        return salaryInt;
    }

    public Date retrieveDateTime(JSONObject json) {
        String s = json.getString("published_at");
        String date = s.substring(0, 10);
        Date dateTime = Date.valueOf(date);
        return dateTime;
    }

    public Object retrieveSalary(JSONObject json) {
        String salary;
        try {
            Object s1 = json.getJSONObject("salary").get("from");
            Object s2 = json.getJSONObject("salary").get("to");
            if (s1.equals(null) && !s2.equals(null)) {
                salary = "До " + s2;
            } else if (s2.equals(null) && !s1.equals(null)) {
                salary = "От " + s1;
            } else {
                salary = "" + s1 + "-" + s2;
            }
            String s3 = json.getJSONObject("salary").getString("currency");
            salary = salary + " " + s3;
        } catch (JSONException e) {
            Object salary_0 = json.get("salary");
            return salary_0;
        }
        return salary;
    }

    public String retrieveName(JSONObject json) {
        String name = json.getString("name");
        return name;
    }

    public String retrieveNameOrganization(JSONObject json) {
        String nameOrganization = json.getJSONObject("employer").getString("name");
        return nameOrganization;
    }

    public LinkedList<Vacancy> getListVacancies() {
        return listVacancies;
    }

    public int getAmountPages() {
        return amountPages;
    }

    public void setAmountPages(int amountPages) {
        this.amountPages = amountPages;
    }
}
