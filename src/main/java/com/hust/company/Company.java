package com.hust.company;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity

public class Company {
    @Id
    String id;
    String name;
    String phone;
    String description;
    String foundedYear;
    String idLocation; 

    public Company (){

    }
    public Company(String id, String name, String phone, String description, String foundedYear, String idLocation){
        super();
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.description = description;
        this.foundedYear = foundedYear;
        this.idLocation = idLocation;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFoundedYear() {
        return foundedYear;
    }

    public void setFoundedYear(String foundedYear) {
        this.foundedYear = foundedYear;
    }

    public String getIdLocation() {
        return idLocation;
    }

    public void setIdLocation(String idLocation) {
        this.idLocation = idLocation;
    }

    
}