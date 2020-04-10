package com.ExceptionHandled.Alerts;

import com.ExceptionHandled.Interfaces.Alert;

import javax.swing.*;

public class AlertFactory {
    private String alert;
    public AlertFactory(String reason){
        alert = reason;
    }

    public void displayAlert(){
        JOptionPane.showMessageDialog(null, alert);
    }
}
