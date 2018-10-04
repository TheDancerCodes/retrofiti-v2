package com.thedancercodes.retrofiti_v2.songs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.thedancercodes.retrofiti_v2.R;
import com.thedancercodes.retrofiti_v2.di.Injector;
import com.thedancercodes.retrofiti_v2.models.User;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * Created by TheDancerCodes on 04/10/2018.
 */
public class SongsActivity extends AppCompatActivity implements SongsContract.View {

    private SongsAdapter songsAdapter;

    RecyclerView mRecyclerView;
    LinearLayoutManager mLayoutManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_songs);

        ButterKnife.bind(this);

        SongsPresenter songsPresenter = new SongsPresenter(this, Injector.provideBookService());

        songsAdapter = new SongsAdapter(this, new ArrayList<User>(0), itemListener);

        songsPresenter.login();

        configureLayout();

    }


    @Override
    public void showErrorMessage() {
        Toast.makeText(this, R.string.books_loading_unsuccessful, Toast.LENGTH_SHORT).show();
    }

    private void configureLayout() {
//        setSupportActionBar((Toolbar) ButterKnife.findById(this, R.id.toolbar));

        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(songsAdapter);
    }

    @Override
    public void showSuccessMessage(String token) {
        Toast.makeText(SongsActivity.this, token, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void showSongs(List<User> songs) {
        songsAdapter.updateSongs(songs);
    }

    @Override
    public void showTokenErrorMessage() {
        Toast.makeText(SongsActivity.this, "Token is not correct.", Toast.LENGTH_SHORT).show();
    }

//    @Override
//    public void showErrorMessage() {
//        Toast.makeText(SongsActivity.this, "Error :(", Toast.LENGTH_SHORT).show();
//    }

    private SongsAdapter.SongItemListener itemListener = new SongsAdapter.SongItemListener() {
        @Override
        public void onBookClick(long id) {

            Timber.d("book clicked id: %d", id);
//            Intent intent = new Intent(BooksActivity.this, BookActivity.class);
//            intent.putExtra(BookActivity.EXTRA_BOOK_ID, id);
//            startActivity(intent);
        }
    };


//    @Override
//    public void onPointerCaptureChanged(boolean hasCapture) {
//
//    }
}
