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
        public String password;
        public Integer Accepts_Credit_Cards;
        public Integer Good_for_Groups;
        public Integer Has_TV;
        public Integer Happy_Hour;
        public Integer Good_For_Dancing;
        public Integer Good_for_Kids;
        public Integer Alcohol;
        public Integer Smoking;

        // MULTI VALUED
        public Noise Noise_Level;

        //public Music_ Music;

        public static class Noise {
            public  int id;
            public Integer Quiet;
            public Integer Average;
            public Integer Loud;
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

    }
    public login_response(){

    }
}
