package com.ExceptionHandled.Alerts;


import javax.swing.*;

public class AlertFactory {
    private static AlertFactory instance = new AlertFactory();

    private AlertFactory(){
    }

    public static AlertFactory getInstance(){
        return instance;
    }

    public void displayCommonAlert(String reason){
        JOptionPane.showMessageDialog(null, reason);
    }

    public boolean displayConfirmationAlert(String reason){
        return JOptionPane.showConfirmDialog(null, reason, "Confirm Account Deletion", JOptionPane.YES_NO_OPTION) == 0;
    }
}
