package com.example.mehmet.gymautomation.activities;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.mehmet.gymautomation.controllers.AppController;
import com.example.mehmet.gymautomation.R;
import com.example.mehmet.gymautomation.controllers.ConnectionController;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    EditText txtKuladi,txtSifre;
    Button btnLogin;
    String url = "http://192.168.2.99/Gym/android_Json/jsonLogin.php";
    Context context = this;
    public static int uye_id;
    public static Activity login_activity;
    public static boolean baglanti_durumu;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        txtKuladi = (EditText)findViewById(R.id.txt_Kuladi);
        txtSifre = (EditText)findViewById(R.id.txt_Sifre);
        login_activity = this;





    }

    public void Kontrol(View abc){
        // Giriş butonu onClick Fonksiyonu

        try{
            ConnectionController cc =  new ConnectionController();
            ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
            baglanti_durumu = cc.isNetworkConnected(cm);
            if(baglanti_durumu){
                getData();
            }
            else{
                Toast.makeText(context,R.string.baglanti_hatasi,Toast.LENGTH_LONG).show();
            }



        }catch (Exception e){

            Toast.makeText(context,e.toString(),Toast.LENGTH_LONG).show();

        }



    }

    public void getData() {
//TODO adding request to POST method and URL
        //Burada issteğimizi oluşturuyoruz, method parametresi olarak post seçiyoruz ve url'imizi const'dan alıyoruz.

        StringRequest myReq = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        // // TODO datalarımız geldi parse işlemi yapılmalı
                        // // TODO lets parse it


                        JSONObject veri_json;
                        try {
                            veri_json = new JSONObject(response);
                            int id = veri_json.getInt("id");
                            // gelen
                            // veri_string
                            // değerini
                            // json
                            // arraye
                            // çeviriyoruz.
                            // try içinde yapmak zorunlu çünkü çıkabilecek bir
                            // sorunda uygulamanın patlamaması için
                            if(id != 0){
                                uye_id = id;
                                Intent girisYonlendirme = new Intent(getApplicationContext(), HomeActivity.class);
                                startActivity(girisYonlendirme);
                            }
                            else{
                                Toast.makeText(context,R.string.kuladi_sifre_hata,Toast.LENGTH_LONG).show();
                            }


                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO there is an error to connecting server or getting data
                // Server'a bağlanırken yada veri çekilirken hata oldu
                Toast.makeText(context,error.getMessage(),Toast.LENGTH_LONG).show();
                System.out.println(error.getMessage());
            }
        }) {
            // TODO let put params to volley request
            // Burada göndereceğimiz request parametrelerini(birden fazla olabilir) set'liyoruz

            protected Map<String, String> getParams()
                    throws com.android.volley.AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                //TODO seçilen kişinin id'sini kisi parametresine ekliyoruz
                params.put("kulAdi", txtKuladi.getText().toString() );
                params.put("sifre", txtSifre.getText().toString() );
                return params;
            };
        };
        myReq.setShouldCache(false); //cache kapatıyoruz.

        // Request(İstek)'i Volley'in Requst sırasına atıyoruz
        // Adding request to volley request queue
        AppController.getInstance().addToRequestQueue(myReq);

    }

}
