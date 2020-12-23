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
import com.princefrog2k.lthdt.moviwatch.data.FilmItem;

import java.util.List;

public class HomeScreenFilmAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<FilmItem> filmItems;

    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public HomeScreenFilmAdapter(Context context, List<FilmItem> filmItems) {
        this.context = context;
        this.filmItems = filmItems;
    }

    public class HomeScreenFilmViewHolder extends RecyclerView.ViewHolder {

        TextView tvFilmTitle, tvViewCount, tvTimeLength;
        ImageView imvFilmBanner;

        public HomeScreenFilmViewHolder(View itemView) {
            super(itemView);
            tvFilmTitle = (TextView) itemView.findViewById(R.id.tv_film_title);
            tvViewCount = (TextView) itemView.findViewById(R.id.tv_view_count);
            tvTimeLength = (TextView) itemView.findViewById(R.id.tv_time_length);
            imvFilmBanner = (ImageView) itemView.findViewById(R.id.imv_movie_banner);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        listener.onItemClick(itemView, getLayoutPosition());
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rcvItemView = LayoutInflater.from(context).inflate(R.layout.rcv_main_screen_film_item, parent, false);
        return new HomeScreenFilmViewHolder(rcvItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        HomeScreenFilmViewHolder itemViewHolder = (HomeScreenFilmViewHolder) holder;
        FilmItem filmItem = filmItems.get(position);
        itemViewHolder.tvFilmTitle.setText(filmItems.get(position).getTitle());
        itemViewHolder.tvViewCount.setText(filmItems.get(position).getViewCount() + "");
        itemViewHolder.tvTimeLength.setText(filmItems.get(position).getMinutesLength() + " phút");
        Glide.with(context).load(filmItem.getPosterUrl()).centerCrop().into(itemViewHolder.imvFilmBanner);
    }

    @Override
    public int getItemCount() {
        return filmItems.size();
    }
}
