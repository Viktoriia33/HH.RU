package com.liferay.util.bridges.ui;

import com.liferay.util.bridges.entity.Vacancy;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

public class ListPanel extends JPanel {

    private JList<String> jList;
    private JScrollPane jScrollPane;

    private DefaultListModel model;

    private String text;
    private String[] mas;

    public ListPanel(LinkedList<Vacancy> listVacancy) {
        setBackground(Color.GRAY);
        setLayout(null);

        setData(listVacancy);

        model = new DefaultListModel();
        for (int i = 0; i < mas.length; i++) {
            model.addElement(mas[i]);
        }
        jList = new JList();
        jList.setModel(model);
        jList.setFont(new Font("Serif", Font.PLAIN, 14));

        jScrollPane = new JScrollPane(jList);
        jScrollPane.setBounds(400, 0, 500, 600);

        add(jScrollPane);
    }

    public String[] setData(LinkedList<Vacancy> list) {
        mas = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            text = "<html>" + list.get(i).getName() + "<br>" +
                    list.get(i).getNameOrganization() + "<br>" +
                    list.get(i).getSalary() + "<br>" +
                    list.get(i).getArea() + "<br>" +
                    "" + list.get(i).getDate() + "<br>" +
                    list.get(i).getProfArea() + "<br>" +
                    "________________________________________________________________</html>";
            mas[i] = text;
        }
        return mas;
    }

    public DefaultListModel getModel() {
        return model;
    }

    public JScrollPane getJScrollPane() {
        return jScrollPane;
    }
}
