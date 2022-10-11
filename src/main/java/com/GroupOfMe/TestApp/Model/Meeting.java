package com.GroupOfMe.TestApp.Model;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

//@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
//@Getter
public class Meeting {
    private ArrayList<Attendant> listOfAttendants = new ArrayList<>();
    private String responsiblePerson;
    private String name;
    private String description;
    public enum Category {
        CodeMonkey("CodeMonkey") , Hub("Hub") , Short("Short") , TeamBuilding("TeamBuilding");
        public final String label;
         Category (String label) {
            this.label= label;
        }
    }
    private Category category;
    public enum Type { Live("Live") , InPerson("InPerson");
        public final String label;
         Type (String label) {
            this.label= label;
        }
    }
    private Type type;
    @JsonSerialize(using=DateSerializer.class)
    @JsonDeserialize(using = DateDeserializer.class)
    private Date startDate;

    @JsonSerialize(using=DateSerializer.class)
    @JsonDeserialize(using = DateDeserializer.class)
    private Date endDate;

/*
    public Meeting(
            String responsiblePerson,
            String name,
            String description,
            String startDate,
            String endDate,
            String scategory,
            String stype) {

        this.responsiblePerson = responsiblePerson;
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        //startDateObject = LocalDateTime.parse(startDate, formatter);
        //endDateObject = LocalDateTime.parse(endDate, formatter);


        switch(scategory) {
            case "CodeMonkey":
                category=Category.CodeMonkey;
                break;
            case "Hub":
                category=Category.Hub;
                break;
            case "Short":
                category=Category.Short;
                break;
            case "TeamBuilding":
                category=Category.TeamBuilding;
                break;
            default:
                throw new IllegalStateException("Expected values for category: CodeMonkey , Hub , Short , TeamBuilding " + scategory);
        }
        switch(stype) {
            case "Live":
                type=Type.Live;
                break;
            case "InPerson":
                type=Type.InPerson;
                break;
            default:
                throw new IllegalStateException("Expected values for Type:  Live , InPerson " + stype);
        }
    }
*/

    // ----methods
    public int addAttendantToList(Attendant attendant){
        listOfAttendants.add(attendant);
        return 1;
    }
    public int removeAttendantToList(String attendant) throws Exception {
        Optional<Attendant> optionalAttendant = listOfAttendants.stream()
                .filter(attendant1 -> attendant1.name().equals(attendant))
                .findAny();
        if (optionalAttendant.isEmpty()) {throw new Exception("There is no attendant with given name");}
        else { listOfAttendants.remove(optionalAttendant.get());
            listOfAttendants.forEach(attend->System.out.println(attend.name()));
            return 1;}

    }

    // ----getters
    @JsonGetter
    public ArrayList<Attendant> listOfAttendants() {
        return listOfAttendants;
    }
    @JsonGetter
    public String responsiblePerson() {
        return responsiblePerson;
    }
    @JsonGetter
    public String name() {
        return name;
    }
    @JsonGetter
    public String description() {
        return description;
    }
    @JsonGetter("category")
    public Category category() {
        return category;
    }
    @JsonGetter("type")
    public Type type() {
        return type;
    }
    @JsonGetter
    public Date startDate() {
        return startDate;
    }
    @JsonGetter
    public Date endDate() {
        return endDate;
    }


    // ---- setters
    @JsonSetter
    public void setListOfAttendants(ArrayList<Attendant> listOfAttendants) {
        this.listOfAttendants = listOfAttendants;
    }
    @JsonSetter
    public void setResponsiblePerson(String responsiblePerson) {
        this.responsiblePerson = responsiblePerson;
    }
    @JsonSetter
    public void setName(String name) {
        this.name = name;
    }
    @JsonSetter
    public void setDescription(String description) {
        this.description = description;
    }
    @JsonSetter("category")
    public void setCategory(Category category) {
        this.category = category;
    }
    @JsonSetter("type")
    public void setType(Type type) {
        this.type = type;
    }
    @JsonSetter
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    @JsonSetter
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
