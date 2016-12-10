package com.example.babatundeanafi.mfinder.Model.model;

/**
 * Created by babatundeanafi on 20/10/16.
 */





public class Mosque {

    //private variables
    private int id;
    private String name;
    private String address;
    private String phone;
    private String email;
    private String language;



    // Empty constructor
    public Mosque(){

    }
    // constructor
    public Mosque(int id, String name, String address, String phone, String email, String language){
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.language = language;
    }


    // constructor
    public Mosque(String name, String address, String phone, String email, String language){
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.language = language;
    }
    // constructor
    public Mosque(int id, String name, String address, String phone, String language){
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.language = language;

    }

    // constructor
    public Mosque(int id, String name, String address, String language){
        this.id = id;
        this.name = name;
        this.address = address;
        this.language = language;

    }
    // getting ID
    public int getID(){
        return this.id;
    }

    // setting id
    public void setID(int id){
        this.id = id;
    }

    // getting name
    public String getName(){
        return this.name;
    }

    // setting name
    public void setName(String name){
        this.name = name;
    }

    // getting address
    public String getAddress(){
        return this.address;
    }

    // setting address
    public void setAddress(String address){
        this.address = address;
    }

    // getting phone number
    public String getPhone(){
        return this.phone;
    }

    // setting phone number
    public void setPhone(String phone){
        this.phone = phone;
    }

    // getting email
    public String getEmail(){
        return this.email;
    }

    // setting email
    public void setEmail(String email){
        this.email = email;
    }

    // getting language
    public String getLanguage(){
        return this.language;
    }

    //setting language
    public void setLanguage(String language){
        this.language = language;
    }
}
