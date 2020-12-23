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

public class LatestFilmBannerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<FilmItem> filmItems;

    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public LatestFilmBannerAdapter(Context context, List<FilmItem> filmItems) {
        this.context = context;
        this.filmItems = filmItems;
    }

    public class LatestFilmBannerViewHolder extends RecyclerView.ViewHolder {

        TextView tvFilmTitle, tvFilmDescription;
        ImageView imvFilmBanner;

        public LatestFilmBannerViewHolder(View itemView) {
            super(itemView);
            tvFilmTitle = (TextView) itemView.findViewById(R.id.tv_film_title);
            tvFilmDescription = (TextView) itemView.findViewById(R.id.tv_film_description);
            imvFilmBanner = (ImageView) itemView.findViewById(R.id.img_movie_banner);

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
        View rcvItemView = LayoutInflater.from(context).inflate(R.layout.rcv_latest_film_banner_item, parent, false);
        return new LatestFilmBannerViewHolder(rcvItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        LatestFilmBannerViewHolder itemViewHolder = (LatestFilmBannerViewHolder) holder;
        FilmItem filmItem = filmItems.get(position);
        Glide.with(context).load(filmItem.getPosterUrl()).centerCrop().into(itemViewHolder.imvFilmBanner);
        itemViewHolder.tvFilmTitle.setText(filmItem.getTitle());
        itemViewHolder.tvFilmDescription.setText(filmItem.getDescription());
        itemViewHolder.setIsRecyclable(false);
    }

    @Override
    public int getItemCount() {
        return filmItems.size();
    }
}
