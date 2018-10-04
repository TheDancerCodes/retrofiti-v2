package com.thedancercodes.retrofiti_v2.di;

import com.thedancercodes.retrofiti_v2.BuildConfig;
import com.thedancercodes.retrofiti_v2.Constants;
import com.thedancercodes.retrofiti_v2.api.SongService;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by TheDancerCodes on 04/10/2018.
 */
public class Injector {

    // Interceptor
    public OkHttpClient provideLoggingCapableHttpClient() {

        // HttpLoggingInterceptor:
        // Intercepts requests & responses and allows us to log the information on LogCat.
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();

        // Check whether its a DEBUG build and set the Level to BODY (Full Logging).
        // If it isn’t, default back to NONE.
        // NOTE: Don’t log BODY in Production. 🙅🏾‍♂️
        logging.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY
                : HttpLoggingInterceptor.Level.NONE);

        return new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();
    }

    // Retrofit instance
    public static Retrofit provideRetrofit (String baseUrl) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    // The SongService
    public static SongService provideBookService() {
        return provideRetrofit(Constants.API.BASE_URL).create(SongService.class);
    }
}