package com.android.flamingo.litnyc.Utilities;

import android.app.Application;
import android.content.Context;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Qiyuan on 5/19/2016.
 */
public class SyncApplication extends Application {
    private static SyncApplication mInstance;
    private static final int SYNC_TIMER_CYCLE = 10000;
    private static final int SERVER_SYNC_CYCLE=300000;
    Timer timer = new Timer(true);
    SyncServer syncServer=new SyncServer();

    public SyncApplication() {
    }

    public static synchronized SyncApplication getInstance() {
        return mInstance;
    }
    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;



        /*restore checkin request list from database*/

        /*start the schedule task*/
        timer.schedule(syncServer,SERVER_SYNC_CYCLE,SERVER_SYNC_CYCLE);
    }
    private class SyncServer extends TimerTask {
        @Override
        public void run() {
            /*sync the local data to the server*/



        }
    }
}
