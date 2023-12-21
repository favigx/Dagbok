package com.dagbok.dagbok;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class DiaryPosts {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String heading;
    private String mainText;
    private LocalDate date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getMainText() {
        return mainText;
    }

    public void setMainText(String mainText) {
        this.mainText = mainText;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}