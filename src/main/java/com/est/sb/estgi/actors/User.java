package com.est.sb.estgi.actors;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public abstract class User {
    private final IntegerProperty id;
    private final StringProperty Fname;
    private final StringProperty Lname;
    private final StringProperty email;
    private final StringProperty password;
    private final StringProperty role;
    public User(int id,String Fname,String Lname,String email,String password, Role role) {
        this.id = new SimpleIntegerProperty(id);
        this.Fname = new SimpleStringProperty(Fname);
        this.Lname = new SimpleStringProperty(Lname);
        this.email = new SimpleStringProperty(email);
        this.password = new SimpleStringProperty(password);
        this.role = new SimpleStringProperty(role.name());

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


    public String getRole() {
        return role.get();
    }

    public StringProperty roleProperty() {
        return role;
    }
    public String getName(){

        return this.getFname() + " " +this.getLname();

    }
}

