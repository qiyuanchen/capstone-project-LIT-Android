package com.android.flamingo.litnyc.Activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.flamingo.litnyc.Network.result_response;
import com.android.flamingo.litnyc.R;
import com.anton46.collectionitempicker.CollectionPicker;
import com.anton46.collectionitempicker.Item;
import com.anton46.collectionitempicker.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class picker_activity extends AppCompatActivity {

    public static void callMe(Activity activity) {
        Intent intent = new Intent(activity, picker_activity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picker_activity);
        List<Item> items = new ArrayList<>();
        String[] att=getResources().getStringArray(R.array.attributes);
        for(String itt:att){
            items.add(new Item(itt,itt));
        }


        CollectionPicker picker = (CollectionPicker) findViewById(R.id.collection_item_picker);
        picker.setItems(items);
        picker.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onClick(Item item, int position) {

            }
        });
    }
}
