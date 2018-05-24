package com.example.mehmet.gymautomation.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
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

public class RegisterActivity extends AppCompatActivity {
    private TextView txtView_KulAdi,txtView_Parola;
    private EditText txt_Tcno,txt_Kuladi,txt_Sifre;
    private Context context = this;
    private Switch switch_TcKontrol;
    private Button btnRegister;
    public static Activity register_activity;
    String url = "http://192.168.2.99/Gym/android_Json/jsonRegister.php";
    public String uye_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        txt_Tcno = (EditText) findViewById(R.id.txt_Tcno);
        switch_TcKontrol = (Switch) findViewById(R.id.switch_tcKontrol);
        txt_Kuladi = (EditText) findViewById(R.id.txt_Kuladi);
        txtView_KulAdi = (TextView) findViewById(R.id.txtView_KulAdi);
        txtView_Parola = (TextView) findViewById(R.id.txtView_Parola);
        txt_Sifre = (EditText) findViewById(R.id.txt_Sifre);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        register_activity = this;
        // Tc Kimlik Numarası yazmayı bırakınca devreye girer
        txt_Tcno.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction()== KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    if(txt_Tcno.getText().length() != 11){
                        switch_TcKontrol.setVisibility(View.GONE);
                        Toast.makeText(context,R.string.tcno_uzunluk,Toast.LENGTH_LONG).show();
                    }
                    else{
                        switch_TcKontrol.setVisibility(View.VISIBLE);
                    }

                    return true;
                }
                return false;
            }
        });
        switch_TcKontrol.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // do something, the isChecked will be
                // true if the switch is in the On position
                if(switch_TcKontrol.isChecked() == true){
                    TcNoKontrol();
                }

            }
        });
    }

    public void TcNoKontrol() {
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
                            String id = veri_json.getString("id");
                            String kul_adi = veri_json.getString("kul_adi");
                            if(!id.equals("0")){
                                if(!kul_adi.equals("null")){
                                    switch_TcKontrol.setChecked(false);
                                    Toast.makeText(context,R.string.uyelik_var,Toast.LENGTH_LONG).show();
                                }
                                else{
                                    Toast.makeText(context,R.string.tcno_var,Toast.LENGTH_LONG).show();
                                    uye_id =    id;
                                    switch_TcKontrol.setVisibility(View.INVISIBLE);
                                    txt_Tcno.setVisibility(View.INVISIBLE);
                                    txtView_KulAdi.setVisibility((View.VISIBLE));
                                    txt_Kuladi.setVisibility(View.VISIBLE);
                                    txtView_Parola.setVisibility(View.VISIBLE);
                                    txt_Sifre.setVisibility(View.VISIBLE);
                                    btnRegister.setVisibility(View.VISIBLE);
                                }
                            }
                            else{
                                switch_TcKontrol.setChecked(false);
                                Toast.makeText(context,R.string.tcno_yok,Toast.LENGTH_LONG).show();
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
                params.put("tcNoDogrulama", txt_Tcno.getText().toString());

                return params;
            };
        };
        myReq.setShouldCache(false); //cache kapatıyoruz.
        // Request(İstek)'i Volley'in Requst sırasına atıyoruz
        // Adding request to volley request queue
        AppController.getInstance().addToRequestQueue(myReq);
    }
    public void kayitYap(View abc){
        String kuladi = txt_Kuladi.getText().toString();
        String sifre = txt_Sifre.getText().toString();
        if(!kuladi.isEmpty() && !sifre.isEmpty() && kuladi.length() >8 && sifre.length() >8){
            StringRequest myReq = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {
                            // // TODO datalarımız geldi parse işlemi yapılmalı
                            // // TODO lets parse it
                            JSONObject veri_json;
                            try {
                                veri_json = new JSONObject(response);
                                String durum = veri_json.getString("durum");
                                if(durum.equals("1") ){
                                    Toast.makeText(context,R.string.txt_KayitBasarili,Toast.LENGTH_LONG).show();
                                    Intent abc = new Intent(getApplicationContext(), LoginActivity.class);
                                    startActivity(abc);
                                    finish();

                                }
                                else if(durum.equals("2")){
                                    Toast.makeText(context,R.string.kullanici_adi_mevcut,Toast.LENGTH_LONG).show();
                                }
                                else{


                                    Toast.makeText(context,R.string.txt_KayitBasarisiz,Toast.LENGTH_LONG).show();
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
                    params.put("kayitId",uye_id);
                    params.put("kayitKulAdi", txt_Kuladi.getText().toString());
                    params.put("kayitSifre",txt_Sifre.getText().toString());
                    return params;
                };
            };
            myReq.setShouldCache(false); //cache kapatıyoruz.
            // Request(İstek)'i Volley'in Requst sırasına atıyoruz
            // Adding request to volley request queue
            AppController.getInstance().addToRequestQueue(myReq);
        }
        else{
            Toast.makeText(context,R.string.kuladi_sifre_uzunluk_hata,Toast.LENGTH_LONG).show();
        }
    }

}
