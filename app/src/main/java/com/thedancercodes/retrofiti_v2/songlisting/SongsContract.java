package com.thedancercodes.retrofiti_v2.songlisting;

import com.thedancercodes.retrofiti_v2.models.User;

import java.util.List;

/**
 * The Contract between the View and Presenter
 */
public interface SongsContract {

    interface View {
        void showSongs(List<User> songs);

        void showErrorMessage();

        void showTokenErrorMessage();

        void showSuccessMessage(String token);
    }
}
