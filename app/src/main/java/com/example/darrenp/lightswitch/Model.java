package com.example.darrenp.lightswitch;

public class Model{
    String name;
    String value;

    Model(String name, String value){
        this.name = name;
        this.value = value;
    }
    public String getName(){
        return this.name;
    }
    public String getValue(){
        return this.value;
    }

}