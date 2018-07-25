package com.company.ui;

import javax.swing.*;

public class DialogInfo extends JFrame {

    public DialogInfo() {
        dialog();
    }

    public void dialog() {
        JOptionPane.showMessageDialog(this, "Подождите. Загружаются данные. Для продолжения " +
                        "закройте окно.",
                "", JOptionPane.INFORMATION_MESSAGE);
    }
}
