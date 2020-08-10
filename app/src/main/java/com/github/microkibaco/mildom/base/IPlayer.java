package com.github.microkibaco.mildom.base;


import android.view.ViewGroup;

import com.kk.taurus.playerbase.entity.DataSource;
import com.kk.taurus.playerbase.receiver.GroupValue;
import com.kk.taurus.playerbase.receiver.IReceiverGroup;

/**
 * Created by JiaJunHui on 2018/6/21.
 */
public interface IPlayer {

     int RECEIVER_GROUP_CONFIG_LIST_STATE = 2;
     int RECEIVER_GROUP_CONFIG_FULL_SCREEN_STATE = 4;



    IReceiverGroup getReceiverGroup();

    void removeReceiver(String receiverKey);


    void attachContainer(ViewGroup userContainer);

    void play(DataSource dataSource);

    void play(DataSource dataSource, boolean updateRender);


    GroupValue getGroupValue();

    void updateGroupValue(String key, Object value);


    boolean isInPlaybackState();



    int getState();

    void pause();

    void resume();

    void stop();

    void reset();



    void destroy() ;

}
