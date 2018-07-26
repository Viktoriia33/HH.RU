package com.liferay.util.bridges.ui;

import com.liferay.util.bridges.data_base.DataBaseConnection;
import com.liferay.util.bridges.data_base.DataBaseHelper;
import com.liferay.util.bridges.data_base.DataBaseOperation;
import com.liferay.util.bridges.entity.Vacancy;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;

public class UserInterface extends JFrame implements ActionListener, ItemListener {

    private int pages;

    private LinkedList<Vacancy> listVacancy;

    private DataBaseOperation dataBaseOperation;
    private DataBaseConnection dataBaseConnection;

    private JTabbedPane jTabbedPane;

    private JPanel tabbedPanel;
    private JPanel comboPanel;

    private JComboBox<String> jComboBox;
    private JTextField jTextField;
    private JLabel jLabelName;

    private LinkedList<ListPanel> listStore;

    public UserInterface(int pages) {

        this.pages = pages;
        connectDataBase();

        listStore = new LinkedList();

        setTitle("HH.RU");
        setSize(1370, 700);
        setResizable(true);
        setLocation(0, 5);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        tabbedPanel = new JPanel();
        tabbedPanel.setLayout(new BorderLayout());

        comboPanelSet();
        tabbedPaneSet();

        tabbedPanel.add(comboPanel, BorderLayout.NORTH);

        add(tabbedPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    public void connectDataBase() {
        dataBaseConnection = DataBaseConnection.getInstance();
        dataBaseConnection.start();

        dataBaseOperation = new DataBaseOperation();
    }

    public void tabbedPaneSet() {
        jTabbedPane = new JTabbedPane(JTabbedPane.BOTTOM, JTabbedPane.SCROLL_TAB_LAYOUT);
        ListPanel listPanel;

        for (int i = 0; i < pages; i++) {
            listVacancy = dataBaseOperation.selectData(i * DataBaseHelper.LIMIT, DataBaseHelper.SQL_SELECT);
            listPanel = new ListPanel(listVacancy);
            listStore.add(listPanel);
            jTabbedPane.add("" + (i + 1), listPanel);
        }

        tabbedPanel.add(jTabbedPane);
    }

    public void comboPanelSet() {
        comboPanel = new JPanel();
        comboPanel.setLayout(new FlowLayout());

        jComboBox = new JComboBox();
        jComboBox.setSize(30, 10);
        jComboBox.addItem("Сортировать");
        jComboBox.addItem("По дате");
        jComboBox.addItem("По убыванию зарплаты");
        jComboBox.addItemListener(this);

        jLabelName = new JLabel("                           Поиск по названию должности: ");

        jTextField = new JTextField(70);
        jTextField.addActionListener(this::actionPerformed);

        comboPanel.add(jComboBox);
        comboPanel.add(jLabelName);
        comboPanel.add(jTextField);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String name = jTextField.getText();
        if (!name.equals("")) {
            refreshListByName(name);
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        String selected = e.getItem().toString();
        if (selected == "По дате") {
            refreshList(DataBaseHelper.SQL_SELECT_SORT_BY_DATE);
        } else if (selected == "По убыванию зарплаты") {
            refreshList(DataBaseHelper.SQL_SELECT_SORT_BY_SALARY);
        }
    }

    public void refreshList(String sql) {
        clearList();
        LinkedList<Vacancy> linkedList;
        for (int i = 0; i < listStore.size(); i++) {
            ListPanel listPanel = listStore.get(i);
            DefaultListModel model = listPanel.getModel();
            linkedList = dataBaseOperation.selectData(i * DataBaseHelper.LIMIT, sql);
            String[] mas = listPanel.setData(linkedList);
            addElementToModel(model, mas);
            goToStart(listPanel);
        }
    }

    public void refreshListByName(String name) {
        clearList();
        LinkedList<Vacancy> linkedList;
        for (int i = 0; i < listStore.size(); i++) {
            ListPanel listPanel = listStore.get(i);
            DefaultListModel model = listPanel.getModel();
            linkedList = dataBaseOperation.selectDataWhereName(i * DataBaseHelper.LIMIT, name);
            String[] mas = listPanel.setData(linkedList);
            addElementToModel(model, mas);
            goToStart(listPanel);
        }
    }

    public void goToStart(ListPanel listPanel) {
        listPanel.getJScrollPane().getVerticalScrollBar().setValue(0);
        jTabbedPane.setSelectedIndex(0);
    }

    public void addElementToModel(DefaultListModel model, String[] mas) {
        for (int i = 0; i < mas.length; i++) {
            model.addElement(mas[i]);
        }
    }

    public void clearList() {
        for (int i = 0; i < listStore.size(); i++) {
            ListPanel listPanel = listStore.get(i);
            DefaultListModel model = listPanel.getModel();
            model.clear();
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        dataBaseConnection.stop();
    }
}

