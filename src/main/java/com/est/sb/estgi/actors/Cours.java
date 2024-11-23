package com.est.sb.estgi.actors;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Cours {
    private final IntegerProperty id;
    private final StringProperty title;
    private final StringProperty description;
    private StringProperty teacherName;
    private Teacher teacher;

    public Cours(int id, String title, String description, Teacher teacher) {
        this.id = new SimpleIntegerProperty(id);
        this.title = new SimpleStringProperty(title);
        this.description = new SimpleStringProperty(description);
        this.teacher = teacher;
        this.teacherName = new SimpleStringProperty(teacher.getName());
    }


    public Teacher getTeacher() {
        return teacher;
    }

    public int getId() {
        return id.get();
    }

    public String getTitle() {
        return title.get();
    }

    public String getDescription() {
        return description.get();
    }

    public String getTeacherName() {
        return teacherName.get();
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
        this.teacherName = new SimpleStringProperty(teacher.getName());
    }
    public IntegerProperty idProperty() {
        return id;
    }
    public StringProperty teacherNameProperty() {
        return teacherName;
    }
    public StringProperty titleProperty() {
        return title;
    }
    public StringProperty descriptionProperty() {
        return description;
    }
}

