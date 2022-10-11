package com.GroupOfMe.TestApp.Model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.LocalDateTime;
import java.util.Date;

public class Attendant {

    public String name;
    @JsonSerialize(using = DateSerializer.class)
    @JsonDeserialize(using = DateDeserializer.class)
    private Date time;
    //private LocalDateTime timeObject;
    //----constructor
    /*
    public Attendant(String name, Date time) {
        this.name = name;
        this.time = time;
    }*/
    // ----setters
    @JsonSetter
    public String name() {
        return name;
    }
    @JsonSetter
    public Date time() {
        return time;
    }

    //--getters
    @JsonGetter
    public void setName(String name) {
        this.name = name;
    }
    @JsonGetter
    public void setTime(Date time) {
        this.time = time;
    }
}
