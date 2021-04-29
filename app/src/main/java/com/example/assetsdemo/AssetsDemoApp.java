package com.example.assetsdemo;

import android.app.Application;
import android.content.Context;

/**
 * Created by wangxiaoyan1 on 4/29/21
 */
public class AssetsDemoApp extends Application {
    public static Context sContext;

    public AssetsDemoApp() {
        this.sContext = this;
    }
}
