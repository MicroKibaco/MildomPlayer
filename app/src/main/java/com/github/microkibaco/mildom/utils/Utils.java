package com.github.microkibaco.mildom.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.text.TextUtils;
import android.util.DisplayMetrics;

import com.github.microkibaco.mildom.bean.MildomInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Taurus on 2018/4/19.
 */

public class Utils {


    public static List<MildomInfo> getVideoList() {
        List<MildomInfo> videoList = new ArrayList<>();
        videoList.add(new MildomInfo("https://d3ooprpqd2179o.cloudfront.net/vod/jp/10328078/10328078-1586756885/transcode/540p/10328078-1586756885-0.m3u8"));
        videoList.add(new MildomInfo("https://d3ooprpqd2179o.cloudfront.net/vod/jp/10045957/10045957-1586728344712-9512/transcode/540p/10045957-1586728344712-9512-0.m3u8"));
        videoList.add(new MildomInfo("https://d3ooprpqd2179o.cloudfront.net/vod/jp/10317434/10317434-1586719422/transcode/540p/10317434-1586719422-0.m3u8"));
        videoList.add(new MildomInfo("https://d3ooprpqd2179o.cloudfront.net/vod/jp/10317408/10317408-1586680652/transcode/540p/10317408-1586680652-0.m3u8"));
        videoList.add(new MildomInfo("https://d3ooprpqd2179o.cloudfront.net/vod/jp/10328078/10328078-1586646377560-3069/transcode/540p/10328078-1586646377560-3069-0.m3u8"));
        videoList.add(new MildomInfo("https://d3ooprpqd2179o.cloudfront.net/vod/jp/10045957/10045957-1586638227507-8568/transcode/540p/10045957-1586638227507-8568-0.m3u8"));
        videoList.add(new MildomInfo("https://d3ooprpqd2179o.cloudfront.net/vod/jp/10045957/10045957-1586638227507-8568/transcode/540p/10045957-1586638227507-8568-0.m3u8"));
        videoList.add(new MildomInfo("https://d3ooprpqd2179o.cloudfront.net/vod/jp/10088339/10088339-1586611112334-51/transcode/540p/10088339-1586611112334-51-0.m3u8"));
        return videoList;
    }
    public static boolean isTopActivity(Activity activity) {
        return activity != null && isTopActivity(activity, activity.getClass().getName());
    }

    public static boolean isTopActivity(Context context, String activityName) {
        return Utils.isForeground(context, activityName);
    }

    public static int getScreenW(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return displayMetrics.widthPixels;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    public static int getScreenH(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return displayMetrics.heightPixels;
    }

    public static boolean isForeground(Context context, String className) {
        if (context == null || TextUtils.isEmpty(className)) {
            return false;
        }
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        assert am != null;
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(1);
        if (list != null && list.size() > 0) {
            ComponentName cpn = list.get(0).topActivity;
            return className.equals(cpn.getClassName());
        }
        return false;
    }

}
