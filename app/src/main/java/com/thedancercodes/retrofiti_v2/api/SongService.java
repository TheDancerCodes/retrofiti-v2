package com.thedancercodes.retrofiti_v2.api;

import com.thedancercodes.retrofiti_v2.models.Login;
import com.thedancercodes.retrofiti_v2.models.User;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by TheDancerCodes on 03/10/2018.
 */

// The Retrofit Interface
public interface SongService {

    // Define the Login
    @POST("users/login")
    Call<User> login(@Body Login login);

    // Access the music profiles
    @GET("profile/all")
    Call<ResponseBody> getSongs(@Header("Authorization") String authToken);
}
