package com.est.sb.estgi.actors;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Admin extends User {

    public Admin(int id,String Fname,String Lname,String email,String password) {
        super(id,Fname,Lname,email,password,Role.ADMIN);
    }



}

