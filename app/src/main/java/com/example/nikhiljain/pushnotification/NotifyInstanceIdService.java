package com.example.nikhiljain.pushnotification;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import static android.content.ContentValues.TAG;

/**
 * Created by nikhiljain on 4/3/17.
 */

public class NotifyInstanceIdService extends FirebaseInstanceIdService {


    @Override
    public void onTokenRefresh() {
        String recent_token = FirebaseInstanceId.getInstance().getToken();
        Log.d("myfirebaseid", "Refreshed token: " + recent_token);
        SharedPreferences sharedPreferences=getApplicationContext().
                getSharedPreferences(getString(R.string.NOTIFY_PREF), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(getString(R.string.NOTIFY_TOKEN),recent_token);
        editor.commit();
//        String refreshedToken = FirebaseInstanceId.getInstance().getToken();


        // TODO: Implement this method to send any registration to your app's servers.
       // sendRegistrationToServer(refreshedToken);
    }
}
