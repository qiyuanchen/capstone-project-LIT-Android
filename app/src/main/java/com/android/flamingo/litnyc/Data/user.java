package com.android.flamingo.litnyc.Data;

import com.j256.ormlite.field.DatabaseField;

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
    public Integer Accepts_Credit_Cards;
    @DatabaseField
    public Integer Good_for_Groups;
    @DatabaseField
    public Integer Has_TV;
    @DatabaseField
    public Integer Happy_Hour;
    @DatabaseField
    public Integer Good_For_Dancing;
    @DatabaseField
    public Integer Good_for_Kids;
    @DatabaseField
    public Integer Alcohol;
    @DatabaseField
    public Integer Smoking;

    // MULTI VALUED
    @DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true)
    public Noise Noise_Level;

    //public Music_ Music;

    public static class Noise {
        @DatabaseField(generatedId=true)
        public  int id;
        @DatabaseField
        public Integer Quiet;
        @DatabaseField
        public Integer Average;
        @DatabaseField
        public Integer Loud;
        @DatabaseField
        public Integer Very_Loud;

        public Noise(){

        }
    }

    public static class Music_ {
        public Integer Juke_Box;
        public Integer DJ;
        public Integer Karaoke;
        public Integer Live;
        public Integer Background;
        public Integer Music_Videos;
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
        Accepts_Credit_Cards = 0;
        Good_for_Groups = 0;
        Has_TV = 0;
        Happy_Hour = 0;
        Good_For_Dancing = 0;
        Good_for_Kids = 0;
        Alcohol = 0;
        Smoking = 0;
        new_user = true;
        Noise_Level.Average=0;
        Noise_Level.Loud=0;
        Noise_Level.Quiet=0;
        Noise_Level.Very_Loud=0;
        //Music
		/*
		Music.Background=0;
		Music.DJ=0;
		Music.Juke_Box=0;
		Music.Karaoke=0;
		Music.Live=0;
		Music.Music_Videos=0;*/

    }

}
