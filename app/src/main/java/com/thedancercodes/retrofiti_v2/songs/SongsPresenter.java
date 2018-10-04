package com.thedancercodes.retrofiti_v2.songs;

import com.thedancercodes.retrofiti_v2.api.SongService;
import com.thedancercodes.retrofiti_v2.models.Login;
import com.thedancercodes.retrofiti_v2.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

/**
 * Created by TheDancerCodes on 04/10/2018.
 */
public class SongsPresenter {

    private final SongsContract.View songsView;
    private final SongService service;


    public SongsPresenter(SongsContract.View songsView, SongService service) {
        this.songsView = songsView;
        this.service = service;
    }


    private static String token;

    public void login() {

        // Create the Login object
        Login login = new Login("thedancercodes@gmail.com", "gr00v31ng");

        // Creating the request call using the Retrofit Interface.
        Call<User> call = service.login(login);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                // Check whether server response was successful
                if (response.isSuccessful()) {

                    // You get the user object back.
                    songsView.showSuccessMessage(response.body().getToken());

                    // Store token. Try SharedPreferences or DB in future
                    token = response.body().getToken();

                    initDataSet();

                } else {
                    songsView.showTokenErrorMessage();
                }

            }

            // If Something goes wrong
            @Override
            public void onFailure(Call<User> call, Throwable t) {

                songsView.showErrorMessage();

            }
        });

    }

    public void initDataSet() {
        service.getSongs(token).enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {

                // If response is successful, we pass in the list of Books.
                if (response.isSuccessful()) {
                    songsView.showSongs(response.body());
                    Timber.i("Songs data was loaded from API."); // Log a message
                }

            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {

                // Show an error message to our users and the log the failure.
                songsView.showErrorMessage();
                Timber.e(t, "Unable to load the books data from the API.");

            }
        });
    }
}
