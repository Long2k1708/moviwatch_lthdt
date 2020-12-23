package com.princefrog2k.lthdt.moviwatch.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.princefrog2k.lthdt.moviwatch.R;

import java.util.ArrayList;
import java.util.Map;

public class FilmCategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    Map<String, String> moviesCategory;
    private ArrayList<String> movieCategoryKeys;
    private ArrayList<String> movieCategoryName;

    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(View itemView, int position, String categoryKey);
    }

    public FilmCategoryAdapter(Context context, Map<String, String> moviesCategory) {
        this.context = context;
        this.moviesCategory = moviesCategory;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public class FilmCategoryViewHolder extends RecyclerView.ViewHolder {

        TextView tvCategoryName;
        ImageView imvCategoryBgr;

        public FilmCategoryViewHolder(View itemView) {
            super(itemView);
            tvCategoryName = (TextView) itemView.findViewById(R.id.tv_film_category_name);
            imvCategoryBgr = (ImageView) itemView.findViewById(R.id.imv_category_item_bgr);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        listener.onItemClick(itemView, getLayoutPosition(), movieCategoryKeys.get(getLayoutPosition()));
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rcvItemView = LayoutInflater.from(context).inflate(R.layout.rcv_film_category_item, parent, false);
        return new FilmCategoryViewHolder(rcvItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        // Convert movie category maps to 2 arraylist
        movieCategoryKeys = new ArrayList<String>();
        movieCategoryName = new ArrayList<String>();
        for (Map.Entry<String, String> categoryEntry : moviesCategory.entrySet()) {
            movieCategoryKeys.add(categoryEntry.getKey());
            movieCategoryName.add(categoryEntry.getValue());
        }

        FilmCategoryViewHolder itemViewHolder = (FilmCategoryViewHolder) holder;
        itemViewHolder.tvCategoryName.setText(movieCategoryName.get(position));
        Glide.with(context).load(R.drawable.category_item_bgr).centerCrop().into(itemViewHolder.imvCategoryBgr);
    }

    @Override
    public int getItemCount() {
        return moviesCategory.size();
    }
}
