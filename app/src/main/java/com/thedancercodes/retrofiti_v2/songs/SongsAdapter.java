package com.thedancercodes.retrofiti_v2.songs;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.thedancercodes.retrofiti_v2.R;
import com.thedancercodes.retrofiti_v2.models.User;

import java.lang.ref.WeakReference;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import timber.log.Timber;

/**
 * Created by TheDancerCodes on 04/10/2018.
 */
public class SongsAdapter extends RecyclerView.Adapter<SongsAdapter.ViewHolder> {

    private WeakReference<Context> context;
    private List<User> songs;
    private SongItemListener itemListener;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.titleTextView)
        TextView titleTextView;
        @BindView(R.id.authorTextView)
        TextView authorTextView;
        @BindView(R.id.publishedTextView)
        TextView publishedTextView;
        @BindView(R.id.pagesTextView)
        TextView pagesTextView;
        @BindView(R.id.imageView)
        ImageView imageView;

        SongItemListener itemListener;

        public ViewHolder(View v, SongItemListener itemListener) {
            super(v);

            ButterKnife.bind(this, v);

            this.itemListener = itemListener;
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            User songs = getItem(getAdapterPosition());
            this.itemListener.onBookClick(songs.getId());
        }
    }

    public SongsAdapter(Context context, List<User> songs, SongItemListener itemListener) {
        this.context = new WeakReference<>(context);
        this.songs = songs;
        this.itemListener = itemListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.book_row_item, viewGroup, false);

        return new ViewHolder(v, this.itemListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Timber.d("Element " + position + " set.");

        User song = songs.get(position);

        viewHolder.titleTextView.setText(song.getSongTitle());
        viewHolder.authorTextView.setText(song.getHandle());

        Context contextLocal = context.get();
        if (contextLocal != null) {
            viewHolder.publishedTextView.setText(song.getReleaseDate());
            viewHolder.pagesTextView.setText(
                    String.format(contextLocal.getString(R.string.pages_label), song.getId()));

            Picasso.with(contextLocal)
                    .load(song.getAlbumArt())
                    .resize(80, 108)
                    .centerInside()
                    .into(viewHolder.imageView);
        }
    }

    @Override
    public int getItemCount() {
        return songs.size();
    }

    public void updateSongs(List<User> songs) {
        this.songs = songs;
        notifyDataSetChanged();
    }


    private User getItem(int adapterPosition) {
        return songs.get(adapterPosition);
    }

    public interface SongItemListener {
        void onBookClick(long id);
    }
}
