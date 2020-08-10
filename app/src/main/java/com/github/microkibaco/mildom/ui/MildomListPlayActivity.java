package com.github.microkibaco.mildom.ui;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.github.microkibaco.mildom.R;
import com.github.microkibaco.mildom.adapter.MildomListAdapter;
import com.github.microkibaco.mildom.base.IPlayer;
import com.github.microkibaco.mildom.bean.MildomInfo;
import com.github.microkibaco.mildom.play.IDataInter;
import com.github.microkibaco.mildom.play.MildomListPlayer;
import com.github.microkibaco.mildom.play.IonHandleListener;
import com.github.microkibaco.mildom.utils.MildomOrientationSensor;
import com.github.microkibaco.mildom.utils.Utils;
import com.kk.taurus.playerbase.entity.DataSource;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 视频播放列表
 */
public class MildomListPlayActivity extends AppCompatActivity implements MildomListAdapter.OnListListener {

    private RecyclerView mRecycler;
    private FrameLayout mPlayerContainer;

    private boolean isLandScape;
    private MildomListAdapter mAdapter;
    private boolean toDetail;

    private MildomOrientationSensor mOrientationSensor;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);


        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        mRecycler = findViewById(R.id.recycler);
        mPlayerContainer = findViewById(R.id.listPlayContainer);

        mRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mAdapter = new MildomListAdapter(this, mRecycler, Utils.getVideoList());
        mAdapter.setOnListListener(this);
        mRecycler.setAdapter(mAdapter);

        mOrientationSensor = new MildomOrientationSensor(this, onOrientationListener);
        mOrientationSensor.enable();

    }

    private MildomOrientationSensor.OnOrientationListener onOrientationListener =
            new MildomOrientationSensor.OnOrientationListener() {
                @Override
                public void onLandScape(int orientation) {
                    if (MildomListPlayer.get().isInPlaybackState()) {
                        setRequestedOrientation(orientation);
                    }
                }

                @Override
                public void onPortrait(int orientation) {
                    if (MildomListPlayer.get().isInPlaybackState()) {
                        setRequestedOrientation(orientation);
                    }
                }
            };

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        isLandScape = newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE;
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mPlayerContainer.setBackgroundColor(Color.BLACK);
            MildomListPlayer.get().attachContainer(mPlayerContainer, false);
            MildomListPlayer.get().setReceiverConfigState(this, IPlayer.RECEIVER_GROUP_CONFIG_FULL_SCREEN_STATE);
        } else {
            mPlayerContainer.setBackgroundColor(Color.TRANSPARENT);
            mRecycler.post(new Runnable() {
                @Override
                public void run() {
                    MildomListAdapter.VideoItemHolder currentHolder = mAdapter.getCurrentHolder();
                    if (currentHolder != null) {
                        MildomListPlayer.get().attachContainer(currentHolder.layoutContainer, false);
                        MildomListPlayer.get().setReceiverConfigState(
                                MildomListPlayActivity.this, IPlayer.RECEIVER_GROUP_CONFIG_LIST_STATE);
                    }
                }
            });
        }
        MildomListPlayer.get().updateGroupValue(IDataInter.Key.KEY_CONTROLLER_TOP_ENABLE, isLandScape);
        MildomListPlayer.get().updateGroupValue(IDataInter.Key.KEY_IS_LANDSCAPE, isLandScape);
    }

    private void toggleScreen() {
        setRequestedOrientation(isLandScape ?
                ActivityInfo.SCREEN_ORIENTATION_PORTRAIT :
                ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MildomListPlayer.get().updateGroupValue(IDataInter.Key.KEY_CONTROLLER_TOP_ENABLE, isLandScape);
        MildomListPlayer.get().setOnHandleListener(new IonHandleListener() {
            @Override
            public void onBack() {
                onBackPressed();
            }

            @Override
            public void onToggleScreen() {
                toggleScreen();
            }
        });
        if (!toDetail && MildomListPlayer.get().isInPlaybackState()) {
            MildomListPlayer.get().resume();
        }
        toDetail = false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        int state = MildomListPlayer.get().getState();
        if (state == com.kk.taurus.playerbase.player.IPlayer.STATE_PLAYBACK_COMPLETE) {
            return;
        }
        if (!toDetail) {
            MildomListPlayer.get().pause();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        MildomListPlayer.get().attachActivity(this);
        mOrientationSensor.enable();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mOrientationSensor.disable();
    }

    @Override
    public void onBackPressed() {
        if (isLandScape) {
            toggleScreen();
            return;
        }
        super.onBackPressed();
    }


    @Override
    public void playItem(MildomListAdapter.VideoItemHolder holder, MildomInfo item, int position) {
        MildomListPlayer.get().setReceiverConfigState(this, IPlayer.RECEIVER_GROUP_CONFIG_LIST_STATE);
        MildomListPlayer.get().attachContainer(holder.layoutContainer);
        MildomListPlayer.get().play(new DataSource(item.getPath()));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mOrientationSensor.disable();
        MildomListPlayer.get().destroy();
    }
}
