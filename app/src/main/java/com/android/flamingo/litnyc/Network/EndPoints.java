package com.android.flamingo.litnyc.Network;

import com.android.flamingo.litnyc.Data.user;

import retrofit.http.Body;
import retrofit.http.Headers;
import retrofit.http.POST;

/**
 * Created by qc on 3/29/2016.
 */
public interface EndPoints {
    static final String API_VERSION = "1.5";

    //LOG IN
    @Headers("Content-Type: application/json")
    @POST("/auth")
    login_response login(@Body login_request request) throws Exception;
    //NEW USER
    @Headers("Content-Type: application/json")
    @POST("/newAccount")
    login_response create(@Body login_request request) throws Exception;
    //GET LOCATION RESULTS
    @Headers("Content-Type: application/json")
    @POST("/getResult")
    result_response result(@Body results_request request) throws Exception;
    @Headers("Content-Type: application/json")
    @POST("/updateAtt")
    updateresponse uresponse(@Body user user) throws Exception;
}
