package de.evoila.elasticsearch;

public class Author {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Author (String name){
        setName(name);
    }
}
