package com.android.flamingo.litnyc.Activities;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.android.flamingo.litnyc.Data.user;
import com.android.flamingo.litnyc.Network.EndPoints;
import com.android.flamingo.litnyc.Network.NetworkHelper;
import com.android.flamingo.litnyc.Network.result_response;
import com.android.flamingo.litnyc.Network.updateresponse;
import com.android.flamingo.litnyc.R;
import com.android.flamingo.litnyc.db_tasks.user_tasks;
import com.anton46.collectionitempicker.CollectionPicker;
import com.anton46.collectionitempicker.Item;
import com.anton46.collectionitempicker.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class picker_activity extends AppCompatActivity {
    private String userID;
    private user usr;
    private String[] att;

    public static void callMe(Activity activity,String userID) {
        Intent intent = new Intent(activity, picker_activity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(mapBox_activity.USER_ID,userID);
        activity.startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picker_activity);
        this.userID=getIntent().getExtras().getString(mapBox_activity.USER_ID);
        this.usr= user_tasks.queryDB(this,userID);
        this.usr.new_user=false;
        List<Item> items = new ArrayList<>();
        att=getResources().getStringArray(R.array.attributes);


        for(String itt:att){
            items.add(new Item(itt,itt));
        }


        CollectionPicker picker = (CollectionPicker) findViewById(R.id.collection_item_picker);
        picker.setItems(items);
        picker.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onClick(Item item, int position) {
                    usr.incrementAtt(item.text,2);

            }
        });
    }
    public void finished(View v){
        user_tasks.updateNoise(this,usr.Noise_Level);
        user_tasks.updateDB(this,usr);
        updatePref();
        finish();
        mapBox_activity.callMe(this,usr.id);


    }
    private boolean updatePref(){
        try {
            updateresponse response = NetworkHelper.makeRequestAdapter(getApplicationContext())
                    .create(EndPoints.class).uresponse(usr);
            return(response.result);
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return false;
    }
}
