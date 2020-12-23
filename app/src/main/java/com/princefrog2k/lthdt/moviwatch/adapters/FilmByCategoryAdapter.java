package com.princefrog2k.lthdt.moviwatch.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView.LayoutManager;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.princefrog2k.lthdt.moviwatch.R;
import com.princefrog2k.lthdt.moviwatch.data.FilmItem;
import com.princefrog2k.lthdt.moviwatch.utils.FirebaseDatabaseFetcher;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FilmByCategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    Map<String, String> moviesCategory;

    private OnClickFilmItem onClickFilmItem;

    public interface OnClickFilmItem {
        void onClickFilmItem(FilmItem filmItem);
    }

    public FilmByCategoryAdapter(Context context, Map<String, String> moviesCategory) {
        this.context = context;
        this.moviesCategory = moviesCategory;
    }

    public class FilmByCategoryAdapterViewHolder extends RecyclerView.ViewHolder {

        TextView tvCategoryTitle;
        RecyclerView rcvCategoryFilms;

        public FilmByCategoryAdapterViewHolder(View itemView) {
            super(itemView);
            tvCategoryTitle = (TextView) itemView.findViewById(R.id.tv_category_title);
            rcvCategoryFilms = (RecyclerView) itemView.findViewById(R.id.rcv_category_film_item);
        }
    }

    public void setOnClickFilmItem(OnClickFilmItem onClickFilmItem) {
        this.onClickFilmItem = onClickFilmItem;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rcvItemView = LayoutInflater.from(context).inflate(R.layout.rcv_film_by_category_item, parent, false);
        return new FilmByCategoryAdapterViewHolder(rcvItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        // Convert movie category maps to 2 arraylist
        ArrayList<String> movieCategoryKeys = new ArrayList<String>();
        ArrayList<String> movieCategoryName = new ArrayList<String>();
        LayoutManager rcvLayoutFilmBannerMgr = new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false);
        for (Map.Entry<String, String> categoryEntry : moviesCategory.entrySet()) {
            movieCategoryKeys.add(categoryEntry.getKey());
            movieCategoryName.add(categoryEntry.getValue());
        }

        FilmByCategoryAdapterViewHolder itemViewHolder = (FilmByCategoryAdapterViewHolder) holder;
        itemViewHolder.tvCategoryTitle.setText(movieCategoryName.get(position));
        itemViewHolder.rcvCategoryFilms.setLayoutManager(rcvLayoutFilmBannerMgr);
        itemViewHolder.rcvCategoryFilms.setHasFixedSize(true);
        fetchMovieData(itemViewHolder.rcvCategoryFilms, movieCategoryKeys.get(position));
    }

    @Override
    public int getItemCount() {
        return moviesCategory.size();
    }

    private void fetchMovieData(RecyclerView recyclerView, String categoryKey) {
        FirebaseDatabaseFetcher.getTopNewestByCategory("movies", categoryKey, 5, new FirebaseDatabaseFetcher.DataFetchInterface() {
            @Override
            public void onGetData() {

            }

            @Override
            public void onGetDataFinish(List<FilmItem> dataGetted) {
                HomeScreenFilmAdapter latestFilmBannerAdapter = new HomeScreenFilmAdapter(context, dataGetted);
                latestFilmBannerAdapter.setOnItemClickListener(new HomeScreenFilmAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View itemView, int position) {
                        onClickFilmItem.onClickFilmItem(dataGetted.get(position));
                    }
                });
                recyclerView.setAdapter(latestFilmBannerAdapter);
            }
        });
    }
}
