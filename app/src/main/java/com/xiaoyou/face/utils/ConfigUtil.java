package com.xiaoyou.face.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.arcsoft.face.enums.DetectFaceOrientPriority;

public class ConfigUtil {
    private static final String APP_NAME = "faceapp";
    private static final String TRACKED_FACE_COUNT = "trackedFaceCount";
    private static final String FT_ORIENT = "ftOrientPriority";
    private static final String MAC_PRIORITY = "macPriority";

    public static boolean setTrackedFaceCount(Context context, int trackedFaceCount) {
        if (context == null) {
            return false;
        }
        SharedPreferences sharedPreferences = context.getSharedPreferences(APP_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.edit()
                .putInt(TRACKED_FACE_COUNT, trackedFaceCount)
                .commit();
    }

    public static int getTrackedFaceCount(Context context) {
        if (context == null) {
            return 0;
        }
        SharedPreferences sharedPreferences = context.getSharedPreferences(APP_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(TRACKED_FACE_COUNT, 0);
    }

    public static boolean setFtOrient(Context context, DetectFaceOrientPriority ftOrient) {
        if (context == null) {
            return false;
        }
        SharedPreferences sharedPreferences = context.getSharedPreferences(APP_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.edit().putString(FT_ORIENT, ftOrient.name()).commit();
    }

    public static DetectFaceOrientPriority getFtOrient(Context context) {
        if (context == null) {
            return DetectFaceOrientPriority.ASF_OP_270_ONLY;
        }
        SharedPreferences sharedPreferences = context.getSharedPreferences(APP_NAME, Context.MODE_PRIVATE);
        return DetectFaceOrientPriority.valueOf(sharedPreferences.getString(FT_ORIENT, DetectFaceOrientPriority.ASF_OP_270_ONLY.name()));
    }
}
