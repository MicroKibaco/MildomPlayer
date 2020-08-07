package com.github.microkibaco.mildom.play;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.github.microkibaco.mildom.App;
import com.github.microkibaco.mildom.base.BasePlayer;
import com.github.microkibaco.mildom.utils.Utils;
import com.kk.taurus.playerbase.assist.AssistPlay;
import com.kk.taurus.playerbase.assist.OnAssistPlayEventHandler;
import com.kk.taurus.playerbase.assist.RelationAssist;
import com.kk.taurus.playerbase.entity.DataSource;

import java.lang.ref.WeakReference;

public class ListPlayer extends BasePlayer {

    private static ListPlayer i = null;

    private ListPlayer() {
    }

    private OnHandleListener onHandleListener;

    private WeakReference<Activity> mActivityRefer;

    @Override
    protected RelationAssist onCreateRelationAssist() {
        RelationAssist assist = new RelationAssist(App.get().getApplicationContext());
        assist.setEventAssistHandler(new OnAssistPlayEventHandler() {
            @Override
            public void requestRetry(AssistPlay assistPlay, Bundle bundle) {
                if (Utils.isTopActivity(mActivityRefer != null ? mActivityRefer.get() : null)) {
                    super.requestRetry(assistPlay, bundle);
                }
            }
        });
        return assist;
    }



    public void attachActivity(Activity activity) {
        clearActivityRefer();
        mActivityRefer = new WeakReference<>(activity);
    }


    public static ListPlayer get() {
        if (i == null) {
            synchronized (ListPlayer.class) {
                if (i == null) {
                    i = new ListPlayer();
                }
            }
        }
        return i;
    }

    private void clearActivityRefer() {
        if (mActivityRefer != null) {
            mActivityRefer.clear();
        }
    }

    @Override
    protected void onInit() {

    }

    public void setOnHandleListener(OnHandleListener onHandleListener) {
        this.onHandleListener = onHandleListener;
    }



    public void setReceiverConfigState(Context context, int configState) {

        if (configState == RECEIVER_GROUP_CONFIG_LIST_STATE) {
            removeReceiver(DataInter.ReceiverKey.KEY_GESTURE_COVER);
        }
    }

    @Override
    protected void onCallBackPlayerEvent(int eventCode, Bundle bundle) {

    }

    @Override
    protected void onCallBackErrorEvent(int eventCode, Bundle bundle) {

    }

    @Override
    protected void onCallBackReceiverEvent(int eventCode, Bundle bundle) {
        switch (eventCode) {
            case DataInter.Event.EVENT_CODE_REQUEST_BACK:
                if (onHandleListener != null) {
                    onHandleListener.onBack();
                }
                break;
            case DataInter.Event.EVENT_CODE_REQUEST_TOGGLE_SCREEN:
                if (onHandleListener != null) {
                    onHandleListener.onToggleScreen();
                }
                break;
            case DataInter.Event.EVENT_CODE_ERROR_SHOW:
                reset();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onSetDataSource(DataSource dataSource) {

    }

    @Override
    public void destroy() {
        super.destroy();
        clearActivityRefer();
        i = null;
        onHandleListener = null;
    }

}
