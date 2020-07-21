package com.demo.entity;

public class Comsu {
    private String id;
    private String name;

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

    public Comsu(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Com{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
