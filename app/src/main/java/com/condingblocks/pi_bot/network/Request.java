package com.condingblocks.pi_bot.network;

import com.condingblocks.pi_bot.models.Auth;
import com.condingblocks.pi_bot.models.Location;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by rishabhkhanna on 14/12/17.
 */

public interface Request {
    @GET("/auth")
    Call<Auth> getAuth();
}
