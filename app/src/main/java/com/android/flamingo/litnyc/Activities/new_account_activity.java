package com.android.flamingo.litnyc.Activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.flamingo.litnyc.R;

public class new_account_activity extends AppCompatActivity {

    public static void callMe(Activity activity) {
        Intent intent = new Intent(activity, new_account_activity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_account_activity);
    }
}
