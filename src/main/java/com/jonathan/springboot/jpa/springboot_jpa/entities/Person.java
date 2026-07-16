package com.jonathan.springboot.jpa.springboot_jpa.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity // indica que es una clase persistente
@Table(name="persons")
public class Person {

    @Id // Indica la columna que sera la clave de la tabla 
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;

    private String name;
    private String lastname;

    @Column(name = "programming_language") //renombrar columna programmingLanguage
    private String programmingLanguage;
    
    //Constructor vacio que  va a utilizar JPA para crear la instancia.
    public Person() {
    }

    // Contructor con todos sus atributos
    public Person(Long id, String name, String lastname, String programmingLanguage) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.programmingLanguage = programmingLanguage;
    }


    //constructor creado para el ejemplo findAllObjectPersonPersonalizada();
    public Person(String name, String lastname) {
        this.name = name;
        this.lastname = lastname;
    }

    //Getters y Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
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
    public String getProgramingLanguage() {
        return programmingLanguage;
    }
    public void setProgramingLanguage(String programmingLanguage) {
        this.programmingLanguage = programmingLanguage;
    }


    @Override  // Generar ToString con Accion de Fuente para imprimir los datos
    public String toString() { 
        return "[id=" + id + ", name=" + name + ", lastname=" + lastname + ", programmingLanguage="
                + programmingLanguage + "]";
    }

    
}


