package com.ExceptionHandled.Alerts;

import com.ExceptionHandled.Interfaces.Alert;

public class AlertFactory {
    public AlertFactory(String reason){
        if (reason.equals("NonUniqueUsername")){

        }
        else if (reason.equals("IncorrectUsername")){

        }
        else if (reason.equals("IncorrectPassword")){

        }
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
