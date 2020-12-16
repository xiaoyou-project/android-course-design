package com.xiaoyou.face.utils;

import android.annotation.SuppressLint;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

import com.xuexiang.xui.XUI;
import com.xuexiang.xui.widget.toast.XToast;

/**
 * 自定义弹框函数
 * @author 小游
 * @date 2020/12/15
 */
@SuppressLint("CheckResult")
public final class ToastUtils {

    private ToastUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    static {
        XToast.Config.get()
                .setAlpha(200)
                .setToastTypeface(XUI.getDefaultTypeface())
                .allowQueue(false);
    }


    @MainThread
    public static void toast(@NonNull CharSequence message) {
        XToast.normal(XUI.getContext(), message).show();
    }

    @MainThread
    public static void toast(@StringRes int message) {
        XToast.normal(XUI.getContext(), message).show();
    }

    @MainThread
    public static void toast(@NonNull CharSequence message, int duration) {
        XToast.normal(XUI.getContext(), message, duration).show();
    }

    @MainThread
    public static void toast(@StringRes int message, int duration) {
        XToast.normal(XUI.getContext(), message, duration).show();
    }

    //=============//

    @MainThread
    public static void error(@NonNull CharSequence message) {
        XToast.error(XUI.getContext(), message).show();
    }

    @MainThread
    public static void error(@NonNull Exception error) {
        XToast.error(XUI.getContext(), error.getMessage()).show();
    }

    @MainThread
    public static void error(@StringRes int message) {
        XToast.error(XUI.getContext(), message).show();
    }

    @MainThread
    public static void error(@NonNull CharSequence message, int duration) {
        XToast.error(XUI.getContext(), message, duration).show();
    }

    @MainThread
    public static void error(@StringRes int message, int duration) {
        XToast.error(XUI.getContext(), message, duration).show();
    }

    //=============//

    @MainThread
    public static void success(@NonNull CharSequence message) {
        XToast.success(XUI.getContext(), message).show();
    }

    @MainThread
    public static void success(@StringRes int message) {
        XToast.success(XUI.getContext(), message).show();
    }

    @MainThread
    public static void success(@NonNull CharSequence message, int duration) {
        XToast.success(XUI.getContext(), message, duration).show();
    }

    @MainThread
    public static void success(@StringRes int message, int duration) {
        XToast.success(XUI.getContext(), message, duration).show();
    }

    //=============//

    @MainThread
    public static void info(@NonNull CharSequence message) {
        XToast.info(XUI.getContext(), message).show();
    }

    @MainThread
    public static void info(@StringRes int message) {
        XToast.info(XUI.getContext(), message).show();
    }

    @MainThread
    public static void info(@NonNull CharSequence message, int duration) {
        XToast.info(XUI.getContext(), message, duration).show();
    }

    @MainThread
    public static void info(@StringRes int message, int duration) {
        XToast.info(XUI.getContext(), message, duration).show();
    }

    //=============//

    @MainThread
    public static void warning(@NonNull CharSequence message) {
        XToast.warning(XUI.getContext(), message).show();
    }

    @MainThread
    public static void warning(@StringRes int message) {
        XToast.warning(XUI.getContext(), message).show();
    }

    @MainThread
    public static void warning(@NonNull CharSequence message, int duration) {
        XToast.warning(XUI.getContext(), message, duration).show();
    }

    @MainThread
    public static void warning(@StringRes int message, int duration) {
        XToast.warning(XUI.getContext(), message, duration).show();
    }

}