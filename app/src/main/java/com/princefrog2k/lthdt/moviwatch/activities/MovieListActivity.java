package com.princefrog2k.lthdt.moviwatch.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.LayoutManager;

import com.princefrog2k.lthdt.moviwatch.R;
import com.princefrog2k.lthdt.moviwatch.adapters.VerticalFilmListAdapter;
import com.princefrog2k.lthdt.moviwatch.data.FilmItem;
import com.princefrog2k.lthdt.moviwatch.utils.FirebaseDatabaseFetcher;

import java.util.List;

public class MovieListActivity extends AppCompatActivity {

    RecyclerView rcvFilmList;
    LayoutManager rcvFilmListMgr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);
        Intent iParent = getIntent();
        String categoryName = iParent.getStringExtra("category_name");
        String categoryKey = iParent.getStringExtra("category_key");

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(categoryName);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        rcvFilmList = (RecyclerView) this.findViewById(R.id.rcv_film_list);
        rcvFilmListMgr = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);

        rcvFilmList.setLayoutManager(rcvFilmListMgr);
        rcvFilmList.setHasFixedSize(true);

        fetchMovieData(categoryKey);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void openWatchMovieActivity(FilmItem filmItem) {
        Intent i = new Intent(this, WatchMovieActivity.class);
        i.putExtra("film_item", filmItem);
        startActivity(i);
    }

    private void fetchMovieData(String categoryKey) {
        FirebaseDatabaseFetcher.getTopNewestByCategory("movies", categoryKey, new FirebaseDatabaseFetcher.DataFetchInterface() {
            @Override
            public void onGetData() {

            }

            @Override
            public void onGetDataFinish(List<FilmItem> dataGetted) {
                VerticalFilmListAdapter verticalFilmListAdapter = new VerticalFilmListAdapter(MovieListActivity.this, dataGetted);
                verticalFilmListAdapter.setOnItemClickListener(new VerticalFilmListAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View itemView, int position) {
                        openWatchMovieActivity(dataGetted.get(position));
                    }
                });
                rcvFilmList.setAdapter(verticalFilmListAdapter);
            }
        });
    }
}