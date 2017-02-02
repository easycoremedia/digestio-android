package com.easycore.digestio.view.adapters;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.ColorMatrixColorFilter;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.easycore.digestio.R;
import com.easycore.digestio.data.model.AudioItem;
import com.easycore.digestio.utils.ObservableColorMatrix;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.easycore.digestio.utils.AnimUtils.getFastOutSlowInInterpolator;

/**
 * Created by Jakub Begera (jakub@easycoreapps.com) on 02.02.17.
 */
public class AudioItemAdapter extends RecyclerView.Adapter<AudioItemAdapter.AudioItemViewHolder> {

    private final ArrayList<AudioItem> items;
    private final Context context;
    private Callback callback;

    public AudioItemAdapter(@NonNull ArrayList<AudioItem> items, @NonNull Context context,
                            @Nullable Callback callback) {
        this.items = items;
        this.context = context;
        this.callback = callback;
    }

    @Override
    public AudioItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.cri_audio_item, parent, false);
        return new AudioItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final AudioItemViewHolder holder, final int position) {
        final AudioItem item = items.get(position);

        holder.txvName.setText(item.getName());
        holder.txvDuration.setText(item.getDuration());
        holder.txvTags.setText(item.getTags().toString());

        Glide.with(context)
                .load(item.getPictureUrl())
                .centerCrop()
                .listener(new RequestListener<String, GlideDrawable>() {

                    @Override
                    public boolean onException(java.lang.Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource,
                                                   String model,
                                                   Target<GlideDrawable> target,
                                                   boolean isFromMemoryCache,
                                                   boolean isFirstResource) {
                        holder.imvPicture.setHasTransientState(true);
                        final ObservableColorMatrix cm = new ObservableColorMatrix();
                        final ObjectAnimator saturation = ObjectAnimator.ofFloat(
                                cm, ObservableColorMatrix.SATURATION, 0f, 1f);
                        saturation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener
                                () {
                            @Override
                            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                                holder.imvPicture.setColorFilter(new ColorMatrixColorFilter(cm));
                            }
                        });
                        saturation.setDuration(2000L);
                        saturation.setInterpolator(getFastOutSlowInInterpolator(context));
                        saturation.addListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                holder.imvPicture.clearColorFilter();
                                holder.imvPicture.setHasTransientState(false);
                            }
                        });
                        saturation.start();
                        return false;
                    }
                })
                .into(holder.imvPicture);

        if (callback != null) {
            holder.container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callback.onItemClick(item, position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class AudioItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.audio_item_imv_picture)
        protected ImageView imvPicture;
        @BindView(R.id.audio_item_txv_name)
        protected TextView txvName;
        @BindView(R.id.audio_item_txv_tags)
        protected TextView txvTags;
        @BindView(R.id.audio_item_txv_duration)
        protected TextView txvDuration;
        @BindView(R.id.audio_item_container)
        protected ViewGroup container;

        public AudioItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface Callback {
        void onItemClick(AudioItem item, int position);
    }
}
