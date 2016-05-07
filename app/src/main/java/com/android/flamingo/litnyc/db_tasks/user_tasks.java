package com.android.flamingo.litnyc.db_tasks;

import android.content.Context;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

import com.android.flamingo.litnyc.Data.DatabaseHelper;
import com.android.flamingo.litnyc.Data.user;

/**
 * Created by Qiyuan on 5/5/2016.
 */
public  class user_tasks {

    public static boolean updateDB(Context context, user u){
        try {
            Dao<user, String> user = DatabaseHelper.getInstance(context).getUserDao();
            user.createOrUpdate(u);
        }catch(SQLException e){
            e.printStackTrace(System.out);
            return false;
        }
        return true;

    }
    public static user queryDB(Context context, String userID){
        user current;
        try {
            Dao<user, String> user = DatabaseHelper.getInstance(context).getUserDao();
            current=user.queryForId(userID);
        }catch(SQLException e){
            e.printStackTrace(System.out);
            return null;
        }
        return current;

    }
}
