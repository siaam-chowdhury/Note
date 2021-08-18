package com.example.notes.Models;

public class HeadingAndDescriptionModel {

    int id;
    String heading, description;

    public HeadingAndDescriptionModel(String heading, String description) {
        this.heading = heading;
        this.description = description;
    }

    public HeadingAndDescriptionModel(int id, String heading, String description) {
        this.id = id;
        this.heading = heading;
        this.description = description;
    }




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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
