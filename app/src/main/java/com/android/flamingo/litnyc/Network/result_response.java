package com.android.flamingo.litnyc.Network;

import com.android.flamingo.litnyc.Data.Location;

import org.json.JSONArray;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Qiyuan on 5/2/2016.
 */
public class result_response implements Serializable {

    public List<network_response> result;

    public static class network_response implements Serializable {
        public String category;
        public int level=0;
        public HashMap<String, Integer> stats;
        public HashMap<String, String> contact;
        public HashMap<String, String> location;
        public HashMap<String, String> attributes;
        public String id;
        public String name;
        public String url;

        public network_response() {

        }
    }

    public result_response() {

    }
}
