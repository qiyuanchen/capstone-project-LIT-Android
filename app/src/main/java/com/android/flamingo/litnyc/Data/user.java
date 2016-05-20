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
    public String pw;
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
        this.pw = last;
        newuser();

    }

    public user() {

    }

    @Override
    public String toString() {
        return String.format("User[id=%s, userName='%s', password='%s']", id, userName, pw);
    }

    public String getUserName() {
        return userName;
    }

    public String getpw() {
        return pw;
    }

    public String getID() {
        return id;
    }

    public void newuser() {
        Accepts_Credit_Cards = 1.0;
        Good_for_Groups = 1.0;
        Has_TV = 1.0;
        Happy_Hour = 1.0;
        Good_For_Dancing = 1.0;
        Good_for_Kids = 1.0;
        Alcohol = 1.0;
        Smoking = 1.0;
        new_user = true;
        Noise_Level.Average=1.0;
        Noise_Level.Loud=1.0;
        Noise_Level.Quiet=1.0;
        Noise_Level.Very_Loud=1.0;

        //Music
		/*
		Music.Background=1.0;
		Music.DJ=1.0;
		Music.Juke_Box=1.0;
		Music.Karaoke=1.0;
		Music.Live=1.0;
		Music.Music_Videos=1.0;*/

    }
    public void incrementAtt(String att,double delta){
        switch (att){
            case "Accepts Credit Cards":
                this.Accepts_Credit_Cards+=delta;
                break;
            case "Good for Groups":
                this.Good_for_Groups+=delta;
                break;
            case"Has TV":
                this.Has_TV+=delta;
                break;
            case"Happy Hour":
                this.Happy_Hour+=delta;
                break;
            case"Good for Dancing":
                this.Good_For_Dancing+=delta;
                break;
            case"Alcohol":
                this.Alcohol+=delta;
                break;
            case"Good for Kids":
                this.Good_for_Kids+=delta;
                break;
            case"Smoking":
                this.Smoking+=delta;
                break;
            case"Average":
                this.Noise_Level.Average+=delta;
                break;
            case"Loud":
                this.Noise_Level.Loud+=delta;
                break;
            case"Quiet":
                this.Noise_Level.Quiet+=delta;
                break;
            case"Very Loud":
                this.Noise_Level.Very_Loud+=delta;
                break;
            default:
                break;

        }
    }

}
