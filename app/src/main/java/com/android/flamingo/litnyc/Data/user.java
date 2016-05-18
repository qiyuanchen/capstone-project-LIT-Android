package com.android.flamingo.litnyc.Data;

import com.j256.ormlite.field.DatabaseField;

import java.util.HashMap;

/**
 * Created by qc on 3/29/2016.
 */
public class user {

    @DatabaseField(id=true)
    public String id;
    public user(String id){
        this.id=id;
    }
    // check if new user
    @DatabaseField
    public boolean new_user;

    // BINARY VALUES
    @DatabaseField
    public String userName;
    @DatabaseField
    public String password;
    @DatabaseField
    public Double Accepts_Credit_Cards;
    @DatabaseField
    public Double Good_for_Groups;
    @DatabaseField
    public Double Has_TV;
    @DatabaseField
    public Double Happy_Hour;
    @DatabaseField
    public Double Good_For_Dancing;
    @DatabaseField
    public Double Good_for_Kids;
    @DatabaseField
    public Double Alcohol;
    @DatabaseField
    public Double Smoking;

    private HashMap<String,Double> map;

    // MULTI VALUED
    @DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true)
    public Noise Noise_Level;

    //public Music_ Music;

    public static class Noise {
        @DatabaseField(generatedId=true)
        public  int id;
        @DatabaseField
        public Double Quiet;
        @DatabaseField
        public Double Average;
        @DatabaseField
        public Double Loud;
        @DatabaseField
        public Double Very_Loud;

        public Noise(){

        }
    }

    public static class Music_ {
        public Double Juke_Box;
        public Double DJ;
        public Double Karaoke;
        public Double Live;
        public Double Background;
        public Double Music_Videos;
    }

    public user(String first, String last) {
        this.userName = first;
        this.password = last;
        newuser();

    }

    public user() {

    }

    @Override
    public String toString() {
        return String.format("User[id=%s, userName='%s', password='%s']", id, userName, password);
    }

    public String getUserName() {
        return userName;
    }

    public String getpw() {
        return password;
    }

    public String getID() {
        return id;
    }

    public void newuser() {
        Accepts_Credit_Cards = 0.0;
        Good_for_Groups = 0.0;
        Has_TV = 0.0;
        Happy_Hour = 0.0;
        Good_For_Dancing = 0.0;
        Good_for_Kids = 0.0;
        Alcohol = 0.0;
        Smoking = 0.0;
        new_user = true;
        Noise_Level.Average=0.0;
        Noise_Level.Loud=0.0;
        Noise_Level.Quiet=0.0;
        Noise_Level.Very_Loud=0.0;
        map=new HashMap<>();
        map.put("Accepts_Credit_Cards",Accepts_Credit_Cards);
        map.put("Good_for_Groups",Good_for_Groups);
        map.put("Has_TV",Has_TV);
        map.put("Happy_Hour",Happy_Hour);
        map.put("Good_For_Dancing",Good_For_Dancing);
        map.put("Good_for_Kids",Good_for_Kids);
        map.put("Alcohol",Alcohol);
        map.put("Smoking",Smoking);
        map.put("Average",Noise_Level.Average);
        map.put("Loud",Noise_Level.Loud);
        map.put("Quiet",Noise_Level.Quiet);
        map.put("Very_Loud",Noise_Level.Very_Loud);
        //Music
		/*
		Music.Background=0.0;
		Music.DJ=0.0;
		Music.Juke_Box=0.0;
		Music.Karaoke=0.0;
		Music.Live=0.0;
		Music.Music_Videos=0.0;*/

    }
    public void incrementAtt(String att){
        switch (att){
            case "Accepts_Credit_cards":
                this.Accepts_Credit_Cards++;
                break;
            case "Good_for_Groups":
                this.Good_for_Groups++;
        }
    }

}
