package com.thedancercodes.retrofiti_v2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.thedancercodes.retrofiti_v2.models.Login;
import com.thedancercodes.retrofiti_v2.models.User;
import com.thedancercodes.retrofiti_v2.api.SongService;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://fierce-bayou-90424.herokuapp.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    SongService songsService = retrofit.create(SongService.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

        findViewById(R.id.btn_secret).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                getSecret();
            }
        });
    }


    private static String token;

    private void login() {

        // Create the Login object
        Login login = new Login("thedancercodes@gmail.com", "gr00v31ng");

        // Creating the request call using the Retrofit Interface.
        Call<User> call = songsService.login(login);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                // Check whether server response was successful
                if (response.isSuccessful()) {

                    // You get the user object back.
                    Toast.makeText(MainActivity.this, response.body().getToken(), Toast.LENGTH_SHORT).show();

                    // Store token. Try SharedPreferences or DB in future
                    token = response.body().getToken();

                }
                else {
                    Toast.makeText(MainActivity.this, "Login Failed.", Toast.LENGTH_SHORT).show();
                }

            }

            // If Something goes wrong
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error :(", Toast.LENGTH_SHORT).show();

            }
        });

    }

//    private void getSecret() {
//
//        Call<ResponseBody> call = songsService
//                .getSongs(token);
//
//        call.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//
//                // Check whether server response was successful
//                if (response.isSuccessful()) {
//
//                    // You get the user object back.
//                    try {
//                        Toast.makeText(MainActivity.this, response.body().string(), Toast.LENGTH_SHORT).show();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//
//
//                }
//                else {
//                    Toast.makeText(MainActivity.this, "Token is not correct.", Toast.LENGTH_SHORT).show();
//            }
//
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//
//                Toast.makeText(MainActivity.this, "Error :(", Toast.LENGTH_SHORT).show();
//
//            }
//        });
//
//    }


}
