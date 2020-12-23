package com.princefrog2k.lthdt.moviwatch.activities;

import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import jp.wasabeef.glide.transformations.BlurTransformation;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;
import com.princefrog2k.lthdt.moviwatch.R;
import com.princefrog2k.lthdt.moviwatch.data.FilmItem;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class WatchMovieActivity extends AppCompatActivity {

    TextView tvFilmTitle, tvFilmLength, tvDirector, tvCountry, tvReleaseTime, tvActor, tvDescription;
    ImageView imvViewBackground, imvFilmPoster;
    VideoView videoFilmPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_movie);

        videoFilmPlayer = (VideoView) this.findViewById(R.id.video_film_player);

        tvFilmTitle = (TextView) this.findViewById(R.id.tv_film_title);
        tvFilmLength = (TextView) this.findViewById(R.id.tv_movie_length);
        tvDirector = (TextView) this.findViewById(R.id.tv_movie_director);
        tvCountry = (TextView) this.findViewById(R.id.tv_country);
        tvReleaseTime = (TextView) this.findViewById(R.id.tv_release_time);
        tvActor = (TextView) this.findViewById(R.id.tv_actor);
        tvDescription = (TextView) this.findViewById(R.id.tv_film_description);

        imvViewBackground = (ImageView) this.findViewById(R.id.imv_movie_view_bgr);
        imvFilmPoster = (ImageView) this.findViewById(R.id.imv_movie_poster);

        initDataForScreen();
    }

    private void initDataForScreen() {
        FilmItem filmItem = (FilmItem) getIntent().getSerializableExtra("film_item");
        DateFormat releaseDateFormatter = new SimpleDateFormat("dd/MM/yyyy");
        tvFilmTitle.setText(filmItem.getTitle());
        tvFilmLength.setText(filmItem.getMinutesLength() + " phút");
        tvDirector.setText(filmItem.getDirector());
        tvCountry.setText(filmItem.getCountry().toString());
        tvReleaseTime.setText(releaseDateFormatter.format(new Date(filmItem.getReleaseTime())));
        tvActor.setText(filmItem.getActor());
        tvDescription.setText(filmItem.getDescription());

        // Media controller for video view
        MediaController mMedia = new MediaController(this);
        mMedia.setMediaPlayer(videoFilmPlayer);
        videoFilmPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mMedia.setAnchorView(videoFilmPlayer);
                videoFilmPlayer.start();
            }
        });
        videoFilmPlayer.setMediaController(mMedia);
        videoFilmPlayer.setVideoURI(Uri.parse(filmItem.getTrailerUrl()));

        List<Transformation<Bitmap>> transforms = new LinkedList<>();
        transforms.add(new CenterCrop());
        transforms.add(new BlurTransformation(25, 5));
        MultiTransformation transformation = new MultiTransformation<Bitmap>(transforms);
        Glide
                .with(this)
                .load(filmItem.getPosterUrl())
                .apply(RequestOptions.bitmapTransform(transformation))
                .into(imvViewBackground);
        Glide.with(this).load(filmItem.getPosterUrl()).centerCrop().into(imvFilmPoster);
    }
}