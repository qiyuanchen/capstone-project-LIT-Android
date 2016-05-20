package com.android.flamingo.litnyc.Network;

import com.android.flamingo.litnyc.Data.user;

/**
 * Created by Qiyuan on 5/2/2016.
 */
public class login_response {

    public network_user result;


    public static class network_user{
        public String id;
        // check if new user
        public boolean new_user;

        // BINARY VALUES
        public String userName;
        public String pw;
        public Double Accepts_Credit_Cards;
        public Double Good_for_Groups;
        public Double Has_TV;
        public Double Happy_Hour;
        public Double Good_For_Dancing;
        public Double Good_for_Kids;
        public Double Alcohol;
        public Double Smoking;

        // MULTI VALUED
        public Noise Noise_Level;

        //public Music_ Music;

        public static class Noise {
            public  int id;
            public Double Quiet;
            public Double Average;
            public Double Loud;
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

    }
    public login_response(){

    }
}
