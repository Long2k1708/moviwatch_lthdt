package com.princefrog2k.lthdt.moviwatch.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.LayoutManager;
import androidx.recyclerview.widget.SnapHelper;

import com.princefrog2k.lthdt.moviwatch.R;
import com.princefrog2k.lthdt.moviwatch.activities.WatchMovieActivity;
import com.princefrog2k.lthdt.moviwatch.adapters.FilmByCategoryAdapter;
import com.princefrog2k.lthdt.moviwatch.adapters.HomeScreenFilmAdapter;
import com.princefrog2k.lthdt.moviwatch.adapters.LatestFilmBannerAdapter;
import com.princefrog2k.lthdt.moviwatch.data.FilmItem;
import com.princefrog2k.lthdt.moviwatch.utils.FirebaseDatabaseFetcher;

import java.util.List;
import java.util.Map;

public class HomeFragment extends Fragment {
    View mainView;
    SnapHelper rcvSnapHelper;
    LayoutManager rcvLayoutFilmBannerMgr, rcvLayoutTopTrendingFilmMgr, rcvLayoutFilmByCategoryMgr;
    RecyclerView rcvLatestFilm, rcvTopTrendingFilm, rcvLayoutFilmByCategory;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fetchHomeScreenData();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        mainView = inflater.inflate(R.layout.fragment_home, container, false);

        rcvSnapHelper = new LinearSnapHelper();
        rcvLayoutFilmBannerMgr = new LinearLayoutManager(getActivity().getApplicationContext(), RecyclerView.HORIZONTAL, false);
        rcvLayoutTopTrendingFilmMgr = new LinearLayoutManager(getActivity().getApplicationContext(), RecyclerView.HORIZONTAL, false);
        rcvLayoutFilmByCategoryMgr = new LinearLayoutManager(getActivity().getApplicationContext(), RecyclerView.VERTICAL, false);
        rcvLatestFilm = (RecyclerView) mainView.findViewById(R.id.rcv_latest_film);
        rcvTopTrendingFilm = (RecyclerView) mainView.findViewById(R.id.rcv_top_trending_film);
        rcvLayoutFilmByCategory = (RecyclerView) mainView.findViewById(R.id.rcv_flim_by_category);

        return mainView;
    }

    private void initRecyclerViewLatestFilmBanner(List<FilmItem> filmItems) {
        LatestFilmBannerAdapter latestFilmBannerAdapter = new LatestFilmBannerAdapter(getActivity().getApplicationContext(), filmItems);
        latestFilmBannerAdapter.setOnItemClickListener(new LatestFilmBannerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                openWatchMovieActivity(filmItems.get(position));
            }
        });
        rcvLatestFilm.setHasFixedSize(true);
        rcvLatestFilm.setLayoutManager(rcvLayoutFilmBannerMgr);
        rcvLatestFilm.setAdapter(latestFilmBannerAdapter);
        rcvSnapHelper.attachToRecyclerView(rcvLatestFilm);
    }

    private void initRecyclerViewTopTrendingFilm(List<FilmItem> filmItems) {
        HomeScreenFilmAdapter latestFilmBannerAdapter = new HomeScreenFilmAdapter(getActivity().getApplicationContext(), filmItems);
        latestFilmBannerAdapter.setOnItemClickListener(new HomeScreenFilmAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                openWatchMovieActivity(filmItems.get(position));
            }
        });
        rcvTopTrendingFilm.setHasFixedSize(true);
        rcvTopTrendingFilm.setLayoutManager(rcvLayoutTopTrendingFilmMgr);
        rcvTopTrendingFilm.setAdapter(latestFilmBannerAdapter);
    }

    private void initRecyclerViewFilmByCategory(Map<String, String> moviesCategory) {
        FilmByCategoryAdapter filmByCategoryAdapter = new FilmByCategoryAdapter(getActivity().getApplicationContext(), moviesCategory);
        filmByCategoryAdapter.setOnClickFilmItem(new FilmByCategoryAdapter.OnClickFilmItem() {
            @Override
            public void onClickFilmItem(FilmItem filmItem) {
                openWatchMovieActivity(filmItem);
            }
        });
        rcvLayoutFilmByCategory.setHasFixedSize(true);
        rcvLayoutFilmByCategory.setLayoutManager(rcvLayoutFilmByCategoryMgr);
        rcvLayoutFilmByCategory.setAdapter(filmByCategoryAdapter);
        rcvLayoutFilmByCategory.setNestedScrollingEnabled(false);
    }

    private void openWatchMovieActivity(FilmItem filmItem) {
        Intent i = new Intent(getActivity(), WatchMovieActivity.class);
        i.putExtra("film_item", filmItem);
        startActivity(i);
    }

    private void fetchHomeScreenData() {
        FirebaseDatabaseFetcher.getTopNewest("movies", new FirebaseDatabaseFetcher.DataFetchInterface() {
            @Override
            public void onGetData() {

            }

            @Override
            public void onGetDataFinish(List<FilmItem> dataGetted) {
                initRecyclerViewLatestFilmBanner(dataGetted);
            }
        });

        FirebaseDatabaseFetcher.getTopTrending("movies", 5, new FirebaseDatabaseFetcher.DataFetchInterface() {
            @Override
            public void onGetData() {

            }

            @Override
            public void onGetDataFinish(List<FilmItem> dataGetted) {
                initRecyclerViewTopTrendingFilm(dataGetted);
            }
        });

        FirebaseDatabaseFetcher.getSingleData("categories_vi", new FirebaseDatabaseFetcher.GetSingleDataFromFirebase() {
            @Override
            public void onGetDone(List dataGetted) {
                Map<String, String> moviesCategory = (Map<String, String>) dataGetted.get(0);
                initRecyclerViewFilmByCategory(moviesCategory);
            }

            @Override
            public void onGetError() {

            }
        });
    }
}