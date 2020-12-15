package com.xiaoyou.face;

import com.xuexiang.xui.XUI;

/**
 * application类
 * @author 小游
 * @date 2020/12/14
 */
public class Application extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化UI框架
        XUI.init(this);
        //开启UI框架调试日志
        XUI.debug(true);
    }
}
