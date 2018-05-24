package com.example.mehmet.gymautomation.activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.content.Intent;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.mehmet.gymautomation.R;
import com.example.mehmet.gymautomation.controllers.AppController;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    Context context = this;
    String url = "http://192.168.2.99/Gym/android_Json/jsonMain.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void loginMethod(View abc){
        Intent girisYonlendirme = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(girisYonlendirme);
    }
    public void registerMethod(View abc){
        Intent kayitYonlendirme = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(kayitYonlendirme);
    }


    }


