package com.github.microkibaco.mildom.play;

import com.kk.taurus.playerbase.assist.InterEvent;
import com.kk.taurus.playerbase.assist.InterKey;

public interface IDataInter {

    interface Event extends InterEvent{

        int EVENT_CODE_REQUEST_BACK = -100;

        int EVENT_CODE_REQUEST_TOGGLE_SCREEN = -104;

        int EVENT_CODE_ERROR_SHOW = -111;

    }

    interface Key extends InterKey{

         String KEY_IS_LANDSCAPE = "isLandscape";

         String KEY_CONTROLLER_TOP_ENABLE = "controller_top_enable";


    }

    interface ReceiverKey{

        String KEY_GESTURE_COVER = "gesture_cover";

    }



}
