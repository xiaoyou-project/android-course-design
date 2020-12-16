package com.xiaoyou.face.utils;

import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.fragment.app.Fragment;

import com.just.agentweb.core.AgentWeb;
import com.just.agentweb.core.client.DefaultWebClient;
import com.xiaoyou.face.activity.StatisticsActivity;

/**
 * 常用的工具类
 * @author 小游
 * @date 2020/12/16
 */
public class Tools {
    /**
     * 创建AgentWeb
     *
     * @param fragment
     * @param viewGroup
     * @param url
     * @return
     */
    public static AgentWeb createAgentWeb(StatisticsActivity fragment, ViewGroup viewGroup, String url) {
        return AgentWeb.with(fragment)
                .setAgentWebParent(viewGroup, -1, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
                .useDefaultIndicator(-1, 3)
//                .useMiddlewareWebClient(new MiddlewareWebViewClient())
                .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.ASK)
                .createAgentWeb()
                .ready()
                .go(url);
    }
}
