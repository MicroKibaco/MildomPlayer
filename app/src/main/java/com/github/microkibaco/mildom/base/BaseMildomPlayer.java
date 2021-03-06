package com.github.microkibaco.mildom.base;

import android.os.Bundle;
import android.view.ViewGroup;

import com.kk.taurus.playerbase.assist.RelationAssist;
import com.kk.taurus.playerbase.entity.DataSource;
import com.kk.taurus.playerbase.event.OnErrorEventListener;
import com.kk.taurus.playerbase.event.OnPlayerEventListener;
import com.kk.taurus.playerbase.log.PLog;
import com.kk.taurus.playerbase.receiver.GroupValue;
import com.kk.taurus.playerbase.receiver.IReceiverGroup;
import com.kk.taurus.playerbase.receiver.OnReceiverEventListener;

import java.util.ArrayList;
import java.util.List;


/**
 * @author MicroKibaco
 */
public abstract class BaseMildomPlayer implements IPlayer {

    private RelationAssist mRelationAssist;

    protected BaseMildomPlayer(){
        mRelationAssist = onCreateRelationAssist();
        mOnPlayerEventListeners = new ArrayList<>();
        mOnErrorEventListeners = new ArrayList<>();
        mOnReceiverEventListeners = new ArrayList<>();
        onInit();
    }

    protected abstract RelationAssist onCreateRelationAssist();

    protected abstract void onInit();

    private List<OnPlayerEventListener> mOnPlayerEventListeners;
    private List<OnErrorEventListener> mOnErrorEventListeners;
    private List<OnReceiverEventListener> mOnReceiverEventListeners;





    private OnPlayerEventListener mInternalPlayerEventListener =
            new OnPlayerEventListener() {
                @Override
                public void onPlayerEvent(int eventCode, Bundle bundle) {
                    onCallBackPlayerEvent(eventCode, bundle);
                    callBackPlayerEventListeners(eventCode, bundle);
                }
            };

    protected abstract void onCallBackPlayerEvent(int eventCode, Bundle bundle);

    private void callBackPlayerEventListeners(int eventCode, Bundle bundle) {
        for(OnPlayerEventListener listener:mOnPlayerEventListeners){
            listener.onPlayerEvent(eventCode, bundle);
        }
    }

    private OnErrorEventListener mInternalErrorEventListener =
            new OnErrorEventListener() {
                @Override
                public void onErrorEvent(int eventCode, Bundle bundle) {
                    onCallBackErrorEvent(eventCode, bundle);
                    callBackErrorEventListeners(eventCode, bundle);
                }
            };

    protected abstract void onCallBackErrorEvent(int eventCode, Bundle bundle);

    private void callBackErrorEventListeners(int eventCode, Bundle bundle) {
        for(OnErrorEventListener listener:mOnErrorEventListeners){
            listener.onErrorEvent(eventCode, bundle);
        }
    }

    private OnReceiverEventListener mInternalReceiverEventListener =
            new OnReceiverEventListener() {
                @Override
                public void onReceiverEvent(int eventCode, Bundle bundle) {
                    onCallBackReceiverEvent(eventCode, bundle);
                    callBackReceiverEventListeners(eventCode, bundle);
                }
            };

    protected abstract void onCallBackReceiverEvent(int eventCode, Bundle bundle);

    private void callBackReceiverEventListeners(int eventCode, Bundle bundle) {
        for(OnReceiverEventListener listener:mOnReceiverEventListeners){
            listener.onReceiverEvent(eventCode, bundle);
        }
    }

    private void attachListener(){
        mRelationAssist.setOnPlayerEventListener(mInternalPlayerEventListener);
        mRelationAssist.setOnErrorEventListener(mInternalErrorEventListener);
        mRelationAssist.setOnReceiverEventListener(mInternalReceiverEventListener);
    }

    @Override
    public GroupValue getGroupValue(){
        IReceiverGroup receiverGroup = getReceiverGroup();
        return receiverGroup==null?null:receiverGroup.getGroupValue();
    }

    @Override
    public void updateGroupValue(String key, Object value){
        GroupValue groupValue = getGroupValue();
        if(groupValue!=null){
            groupValue.putObject(key, value);
        }
    }

    @Override
    public IReceiverGroup getReceiverGroup(){
        return mRelationAssist.getReceiverGroup();
    }

    @Override
    public final void removeReceiver(String receiverKey) {
        IReceiverGroup receiverGroup = getReceiverGroup();
        if(receiverGroup!=null)
            receiverGroup.removeReceiver(receiverKey);
    }



    @Override
    public void attachContainer(ViewGroup userContainer){
        attachContainer(userContainer, true);
    }

    public void attachContainer(ViewGroup userContainer, boolean updateRender){
        mRelationAssist.attachContainer(userContainer, updateRender);
    }

    @Override
    public void play(DataSource dataSource){
        play(dataSource, false);
    }

    @Override
    public void play(DataSource dataSource, boolean updateRender){
        onSetDataSource(dataSource);
        attachListener();
        mRelationAssist.setDataSource(dataSource);
        mRelationAssist.play(updateRender);
    }

    protected abstract void onSetDataSource(DataSource dataSource);



    @Override
    public boolean isInPlaybackState(){
        int state = getState();
        PLog.d("BSPlayer","isInPlaybackState : state = " + state);
        return state!= com.kk.taurus.playerbase.player.IPlayer.STATE_END
                && state!= com.kk.taurus.playerbase.player.IPlayer.STATE_ERROR
                && state!= com.kk.taurus.playerbase.player.IPlayer.STATE_IDLE
                && state!= com.kk.taurus.playerbase.player.IPlayer.STATE_INITIALIZED
                && state!= com.kk.taurus.playerbase.player.IPlayer.STATE_PLAYBACK_COMPLETE
                && state!= com.kk.taurus.playerbase.player.IPlayer.STATE_STOPPED;
    }



    @Override
    public int getState() {
        return mRelationAssist.getState();
    }

    @Override
    public void pause() {
        mRelationAssist.pause();
    }

    @Override
    public void resume() {
        mRelationAssist.resume();
    }

    @Override
    public void stop() {
        mRelationAssist.stop();
    }

    @Override
    public void reset() {
        mRelationAssist.reset();
    }



    @Override
    public void destroy() {
        mOnPlayerEventListeners.clear();
        mOnErrorEventListeners.clear();
        mOnReceiverEventListeners.clear();
        IReceiverGroup receiverGroup = getReceiverGroup();
        if(receiverGroup!=null){
            receiverGroup.clearReceivers();
        }
        mRelationAssist.destroy();
    }

}
