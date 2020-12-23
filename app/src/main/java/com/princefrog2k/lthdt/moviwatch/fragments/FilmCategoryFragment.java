package com.princefrog2k.lthdt.moviwatch.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.princefrog2k.lthdt.moviwatch.R;
import com.princefrog2k.lthdt.moviwatch.activities.MovieListActivity;
import com.princefrog2k.lthdt.moviwatch.adapters.FilmCategoryAdapter;
import com.princefrog2k.lthdt.moviwatch.utils.FirebaseDatabaseFetcher;

import java.util.List;
import java.util.Map;

public class FilmCategoryFragment extends Fragment {

    View mainView;
    StaggeredGridLayoutManager rcvLayoutFilmCategoryMgr;
    RecyclerView rcvLayoutFilmByCategory;
    Map<String, String> moviesCategory;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        mainView = inflater.inflate(R.layout.fragment_film_category, container, false);
        rcvLayoutFilmByCategory = (RecyclerView) mainView.findViewById(R.id.rcv_category);
        rcvLayoutFilmCategoryMgr = new StaggeredGridLayoutManager(2, 1);
        fetchMovieCategoryData();
        return mainView;
    }

    private void initCategoryData(Map<String, String> moviesCategory) {
        FilmCategoryAdapter filmCategoryFragment = new FilmCategoryAdapter(getActivity().getApplicationContext(), moviesCategory);
        filmCategoryFragment.setOnItemClickListener(new FilmCategoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position, String categoryKey) {
                String movieCategoryName = moviesCategory.get(categoryKey);
                Intent i = new Intent(getActivity(), MovieListActivity.class);
                i.putExtra("category_key", categoryKey);
                i.putExtra("category_name", movieCategoryName);
                startActivity(i);
            }
        });
        rcvLayoutFilmByCategory.setHasFixedSize(true);
        rcvLayoutFilmByCategory.setLayoutManager(rcvLayoutFilmCategoryMgr);
        rcvLayoutFilmByCategory.setAdapter(filmCategoryFragment);
    }

    private void fetchMovieCategoryData() {
        FirebaseDatabaseFetcher.getSingleData("categories_vi", new FirebaseDatabaseFetcher.GetSingleDataFromFirebase() {
            @Override
            public void onGetDone(List dataGetted) {
                moviesCategory = (Map<String, String>) dataGetted.get(0);
                initCategoryData(moviesCategory);
            }

            @Override
            public void onGetError() {

            }
        });
    }
}