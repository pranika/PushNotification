package com.example.nikhiljain.pushnotification;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by nikhiljain on 4/16/17.
 */

public class PushNotification extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}
