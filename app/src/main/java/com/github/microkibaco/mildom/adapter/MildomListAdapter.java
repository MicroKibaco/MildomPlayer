package com.github.microkibaco.mildom.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.github.microkibaco.mildom.R;
import com.github.microkibaco.mildom.bean.MildomInfo;
import com.github.microkibaco.mildom.play.MildomListPlayer;
import com.github.microkibaco.mildom.utils.Utils;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

public class MildomListAdapter extends RecyclerView.Adapter<MildomListAdapter.VideoItemHolder> {


    private Context mContext;
    private List<MildomInfo> mItems;

    private RecyclerView mRecycler;

    private OnListListener onListListener;

    private int mScreenUseW;

    private int mScreenH;

    private int mPlayPosition = -1;
    private int mVerticalRecyclerStart;

    public MildomListAdapter(Context context, RecyclerView recyclerView, List<MildomInfo> list) {
        this.mContext = context;
        this.mRecycler = recyclerView;
        this.mItems = list;
        mScreenUseW = Utils.getScreenW(context) - Utils.dip2px(context, 6 * 2);
        mScreenH = Utils.getScreenH(mRecycler.getContext());

        mRecycler.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int[] location = new int[2];
                mRecycler.getLocationOnScreen(location);
                mVerticalRecyclerStart = location[1];
                mRecycler.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });

        mRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int itemVisibleRectHeight = getItemVisibleRectHeight(mPlayPosition);
                if (mPlayPosition >= 0 && itemVisibleRectHeight <= Utils.dip2px(mContext, 120) && dy != 0) {
                    MildomListPlayer.get().stop();
                    notifyItemChanged(mPlayPosition);
                    mPlayPosition = -1;
                }
            }
        });
    }


    public void setOnListListener(OnListListener onListListener) {
        this.onListListener = onListListener;
    }

    @NonNull
    @Override
    public VideoItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VideoItemHolder(View.inflate(mContext, R.layout.item_video, null));
    }

    @Override
    public void onBindViewHolder(final VideoItemHolder holder, final int position) {
        ViewCompat.setElevation(holder.card, Utils.dip2px(mContext, 3));
        updateWh(holder);
        final MildomInfo item = getItem(position);
        holder.albumImage.setBackground(mContext.getResources().getDrawable(R.mipmap.ic_launcher));

        holder.layoutContainer.removeAllViews();
        holder.playIcon.setVisibility(mPlayPosition == position ? View.GONE : View.VISIBLE);
        if (onListListener != null) {
            holder.albumLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mPlayPosition >= 0) {
                        notifyItemChanged(mPlayPosition);
                    }
                    holder.playIcon.setVisibility(View.GONE);
                    mPlayPosition = position;
                    onListListener.playItem(holder, item, position);
                }
            });

        }
    }

    private void updateWh(MildomListAdapter.VideoItemHolder holder) {
        ViewGroup.LayoutParams layoutParams = holder.layoutBox.getLayoutParams();
        layoutParams.width = mScreenUseW;
        layoutParams.height = mScreenUseW * 9 / 16;
        holder.layoutBox.setLayoutParams(layoutParams);
    }

    public MildomInfo getItem(int position) {
        if (mItems == null) {
            return null;
        }
        return mItems.get(position);
    }

    @Override
    public int getItemCount() {
        if (mItems == null) {
            return 0;
        }
        return mItems.size();
    }

    public static class VideoItemHolder extends RecyclerView.ViewHolder {

        View card;
        public FrameLayout layoutContainer;
        public RelativeLayout layoutBox;
        View albumLayout;
        ImageView albumImage;
        ImageView playIcon;


        public VideoItemHolder(View itemView) {
            super(itemView);
            card = itemView.findViewById(R.id.card);
            layoutContainer = itemView.findViewById(R.id.layoutContainer);
            layoutBox = itemView.findViewById(R.id.layBox);
            albumLayout = itemView.findViewById(R.id.album_layout);
            albumImage = itemView.findViewById(R.id.albumImage);
            playIcon = itemView.findViewById(R.id.playIcon);

        }

    }

    public VideoItemHolder getCurrentHolder() {
        if (mPlayPosition < 0) {
            return null;
        }
        return getItemHolder(mPlayPosition);
    }

    /**
     * 获取Item中渲染视图的可见高度
     */
    private int getItemVisibleRectHeight(int position) {
        VideoItemHolder itemHolder = getItemHolder(position);
        if (itemHolder == null) {
            return 0;
        }
        int[] location = new int[2];
        itemHolder.layoutBox.getLocationOnScreen(location);
        int height = itemHolder.layoutBox.getHeight();

        int visibleRect;
        if (location[1] <= mVerticalRecyclerStart) {
            visibleRect = location[1] - mVerticalRecyclerStart + height;
        } else {
            if (location[1] + height >= mScreenH) {
                visibleRect = mScreenH - location[1];
            } else {
                visibleRect = height;
            }
        }
        return visibleRect;
    }

    private VideoItemHolder getItemHolder(int position) {
        RecyclerView.ViewHolder viewHolder = mRecycler.findViewHolderForAdapterPosition(position);
        if (viewHolder instanceof VideoItemHolder) {
            return (VideoItemHolder) viewHolder;
        }
        return null;
    }

    public interface OnListListener {
        void playItem(VideoItemHolder holder, MildomInfo item, int position);
    }

}
