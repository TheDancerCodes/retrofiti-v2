package com.thedancercodes.retrofiti_v2.di;

import com.thedancercodes.retrofiti_v2.BuildConfig;
import com.thedancercodes.retrofiti_v2.Constants;
import com.thedancercodes.retrofiti_v2.api.SongService;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by TheDancerCodes on 04/10/2018.
 */
public class Injector {

    // Instantiate local variables
    private static final String VERSION = "version";
    private static final String USER_AGENT = "User-Agent";
    private static final String RETROFITI_ANDROID_APP = "Retrofiti-Android-App";


    // Retrofit instance
    public static Retrofit provideRetrofit(String baseUrl) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(provideOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    // The OkHttpClient to which we add different interceptors
    private static OkHttpClient provideOkHttpClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(provideHttpLoggingInterceptor())
                .addInterceptor(provideUrlAndHeaderInterceptor())
                .build();
    }

    // Application Interceptor that adds Headers and Manipulates URLs
    private static Interceptor provideUrlAndHeaderInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                HttpUrl url = request.url()
                        .newBuilder()
                        .addQueryParameter(VERSION, BuildConfig.VERSION_NAME)
                        .build();

                Request.Builder builder = request.newBuilder().url(url);
                builder.addHeader(USER_AGENT, RETROFITI_ANDROID_APP);

                return chain.proceed(builder.build());
            }
        };
    }

    // HttpLoggingInterceptor:
    // Intercepts requests & responses and allows us to log the information on LogCat.
    private static HttpLoggingInterceptor provideHttpLoggingInterceptor() {
        HttpLoggingInterceptor httpLoggingInterceptor =
                new HttpLoggingInterceptor();

        // Check whether its a DEBUG build and set the Level to BODY (Full Logging).
        // If it isn’t, default back to NONE.
        // NOTE: Don’t log BODY in Production. 🙅🏾‍♂️
        httpLoggingInterceptor.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.HEADERS
                : HttpLoggingInterceptor.Level.NONE);
        return httpLoggingInterceptor;
    }


    // The SongService
    public static SongService provideBookService() {
        return provideRetrofit(Constants.API.BASE_URL).create(SongService.class);
    }
}