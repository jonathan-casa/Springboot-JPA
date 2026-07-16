package com.jonathan.springboot.jpa.springboot_jpa.dto;

public class PersonDTO {

    private String name;
    private String lastname;
    
    
    
    public PersonDTO(String name, String lastname) {
        this.name = name;
        this.lastname = lastname;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getLastname() {
        return lastname;
    }
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Override
    public String toString() {
        return "PersonDTO [name=" + name + ", lastname=" + lastname + "]";
    }

    

    
}
