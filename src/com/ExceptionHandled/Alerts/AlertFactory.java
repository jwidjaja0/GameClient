package com.ExceptionHandled.Alerts;

import com.ExceptionHandled.Interfaces.Alert;

import javax.swing.*;

public class AlertFactory {
    private String alert;
    public AlertFactory(String reason){
        if (reason.equals("NonUniqueUsername")){
            alert = (new SignUpFailUsername()).alertMessage();
        }
        else if (reason.equals("IncorrectUsername")){
            alert = (new LoginFailUsername()).alertMessage();
        }
        else if (reason.equals("IncorrectPassword")){
            alert = (new LoginFailPassword()).alertMessage();
        }

        JOptionPane.showMessageDialog(null, alert);
    }
}

class SignUpFailUsername implements Alert{

    @Override
    public String alertMessage() {
        return "Your Username has already been taken.";
    }
}

class LoginFailUsername implements Alert{

    @Override
    public String alertMessage() {
        return "The username you have entered is not found in our system.";
    }
}

class LoginFailPassword implements Alert{

    @Override
    public String alertMessage() {
        return "The password you have entered does not match the one found in our system.";
    }
}

//TODO: Add more alerts as necessary
