package com.github.microkibaco.mildom;

import android.app.Application;

import com.github.microkibaco.mildom.utils.IjkPlayer;
import com.kk.taurus.playerbase.config.PlayerConfig;
import com.kk.taurus.playerbase.config.PlayerLibrary;
import com.kk.taurus.playerbase.entity.DecoderPlan;
import com.kk.taurus.playerbase.record.PlayRecordManager;

public class MildomApplication extends Application {
    private static MildomApplication instance;
    public static final int PLAN_ID_IJK = 1;

    public static MildomApplication get() {
        return instance;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        // 初始化ijkPlayer
        PlayerConfig.addDecoderPlan(new DecoderPlan(PLAN_ID_IJK, IjkPlayer.class.getName(), "IjkPlayer"));
        PlayerConfig.setDefaultPlanId(PLAN_ID_IJK);
        PlayerConfig.setUseDefaultNetworkEventProducer(true);
        PlayerConfig.playRecord(true);
        PlayRecordManager.setRecordConfig(
                new PlayRecordManager.RecordConfig.Builder()
                        .setMaxRecordCount(100).build());
        PlayerLibrary.init(this);
    }

}
