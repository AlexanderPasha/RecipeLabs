package com.mannydev.recipelabs.controller;

import android.app.Application;

import com.mannydev.recipelabs.model.MyObjectBox;

import io.objectbox.BoxStore;

/**
 * Синглтон для работы с БД
 */

public class App extends Application {

    private static App sApp;
    private BoxStore mBoxStore;

    @Override
    public void onCreate() {
        super.onCreate();
        sApp = this;
        mBoxStore = MyObjectBox.builder().androidContext(App.this).build();
    }

    public static App getApp() {
        return sApp;
    }

    public BoxStore getBoxStore() {
        return mBoxStore;
    }
}
