package com.condingblocks.pi_bot.network;

import android.content.Context;

import java.io.File;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by rishabhkhanna on 14/12/17.
 */

public class API {
    private static API apiInstance;
    public Retrofit retrofit;
    Context context;
    private API (Context context) {
        this.context = context;
        retrofit
                = new Retrofit.Builder().baseUrl("https://api.gitbook.com/")
                .addConverterFactory(
                        GsonConverterFactory.create()
                )
                .build();
    }

    public static API getInstance(Context context){

        if(apiInstance == null){
            apiInstance = new API(context);
        }
        return apiInstance;
    }
}
