package com.example.mehmet.gymautomation.controllers;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
public class ConnectionController extends AppCompatActivity {
    public   boolean isNetworkConnected(ConnectivityManager cm) {
        //ConnectionController.java

        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }
}
