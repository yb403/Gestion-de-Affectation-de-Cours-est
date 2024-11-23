package com.est.sb.estgi.actors;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

abstract class User {
    private final IntegerProperty id;
    private final StringProperty Fname;
    private final StringProperty Lname;
    private final StringProperty email;
    private final StringProperty password;
    public User(int id,String Fname,String Lname,String email,String password) {
        this.id = new SimpleIntegerProperty(id);
        this.Fname = new SimpleStringProperty(Fname);
        this.Lname = new SimpleStringProperty(Lname);
        this.email = new SimpleStringProperty("dev@dev.com");
        this.password = new SimpleStringProperty(password);

    }
    public String getLname() {
        return Lname.get();
    }

    public StringProperty lnameProperty() {
        return Lname;
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public String getFname() {
        return Fname.get();
    }

    public StringProperty fnameProperty() {
        return Fname;
    }

    public String getEmail() {
        return email.get();
    }

    public StringProperty emailProperty() {
        return email;
    }

    public String getPassword() {
        return password.get();
    }

    public StringProperty passwordProperty() {
        return password;
    }


}

