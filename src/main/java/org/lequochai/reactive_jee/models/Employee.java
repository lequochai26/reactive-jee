package org.lequochai.reactive_jee.models;

public class Employee {
    // Fields:
    private int id;
    private String name;

    // Constructors: 
    public Employee() {
    }

    public Employee(int id, String name) {
        this.id = id;
        this.name = name;
    }

    // Methods:
    @Override
    public String toString() {
        return "Employee [id=" + id + ", name=" + name + "]";
    }

    // Getters / setters:
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
