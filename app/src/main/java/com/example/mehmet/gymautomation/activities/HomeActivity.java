package com.example.mehmet.gymautomation.activities;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.mehmet.gymautomation.R;
import com.example.mehmet.gymautomation.controllers.AppController;
import com.example.mehmet.gymautomation.models.Tarife;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeActivity extends AppCompatActivity {
    final Context context = this;
    private ListView list_tarifeler;
    public static String tarife_id;
    public ArrayList<String> arrayTarifeler = new ArrayList<String>();
    public ArrayList<String> arrayTarifeIdler = new ArrayList<String>();
    String url = "http://192.168.2.99/Gym/android_Json/jsonMain.php";
    public TextView txt_Salondakiler;
    public static Activity HomeActivity;
    public static ArrayAdapter<String> veriAdaptoru;
    public int durum ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        tarifeGetir();
        txt_Salondakiler = (TextView) findViewById(R.id.txt_Salondakiler);
        aktif_uyeler();

        list_tarifeler = (ListView) findViewById(R.id.list_tarifeler);
        veriAdaptoru=new ArrayAdapter<String>
                (this, R.layout.textcenter, R.id.textItem, arrayTarifeler);

        //(C) adımı


        list_tarifeler.setAdapter(veriAdaptoru);
        veriAdaptoru.notifyDataSetChanged();
        View headerView = getLayoutInflater().inflate(R.layout.listview_header, null);
        list_tarifeler.addHeaderView(headerView,null,false);
        list_tarifeler.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tarife_id = arrayTarifeIdler.get(Integer.parseInt(id+""));
                Intent SubscriptionActivity = new Intent(getApplicationContext(), SubscriptionActivity.class);
                startActivity(SubscriptionActivity);
            }
        });

    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

            // alert dialog başlığını tanımlıyoruz.
            alertDialogBuilder.setTitle("Uyarı!");

            // alert dialog özelliklerini oluşturuyoruz.
            alertDialogBuilder
                    .setMessage("Çıkmak istiyor musunuz?")
                    .setCancelable(false)
                    .setIcon(R.mipmap.my)
                    // Evet butonuna tıklanınca yapılacak işlemleri buraya yazıyoruz.
                    .setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                            LoginActivity.login_activity.finish();
                        }
                    })
                    // İptal butonuna tıklanınca yapılacak işlemleri buraya yazıyoruz.
                    .setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

            // alert dialog nesnesini oluşturuyoruz
            AlertDialog alertDialog = alertDialogBuilder.create();

            // alerti gösteriyoruz
            alertDialog.show();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    public void tarifeGetir(){

        StringRequest myReq = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        // // TODO datalarımız geldi parse işlemi yapılmalı
                        // // TODO lets parse it
                        JSONArray veri_json;
                            Gson gson = new Gson();
                            ArrayList<Tarife> tarifeler = gson.fromJson(response,new TypeToken<ArrayList<Tarife>>(){}.getType());
                        try {

                            veri_json = new JSONArray(response);
                            for(int i = 0 ;i<veri_json.length();i++){
                                JSONObject jsonobject = veri_json.getJSONObject(i);
                                arrayTarifeler.add( tarifeler.get(i).getTarife_adi());
                                arrayTarifeIdler.add(Integer.toString(tarifeler.get(i).getTarife_id()));
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
                String uye_id = Integer.toString(LoginActivity.uye_id);
                params.put("tarifeGetir",uye_id);

                return params;
            };
        };
        myReq.setShouldCache(false); //cache kapatıyoruz.
        // Request(İstek)'i Volley'in Requst sırasına atıyoruz
        // Adding request to volley request queue
        AppController.getInstance().addToRequestQueue(myReq);
    }
    public void cikisYap(View abc){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

        // alert dialog başlığını tanımlıyoruz.
        alertDialogBuilder.setTitle("Uyarı!");

        // alert dialog özelliklerini oluşturuyoruz.
        alertDialogBuilder
                .setMessage("Çıkmak istiyor musunuz?")
                .setCancelable(false)
                .setIcon(R.mipmap.my)
                // İptal butonuna tıklanınca yapılacak işlemleri buraya yazıyoruz.
                .setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                // Evet butonuna tıklanınca yapılacak işlemleri buraya yazıyoruz.
                .setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        LoginActivity.login_activity.finish();
                    }
                });


        // alert dialog nesnesini oluşturuyoruz
        AlertDialog alertDialog = alertDialogBuilder.create();

        // alerti gösteriyoruz
        alertDialog.show();
    }
    public void aktif_uyeler(){
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
                            int salondaki_aktif_uye = veri_json.getInt("salondaki_aktif_uye");
                            // gelen
                            // veri_string
                            // değerini
                            // json
                            // arraye
                            // çeviriyoruz.
                            // try içinde yapmak zorunlu çünkü çıkabilecek bir
                            // sorunda uygulamanın patlamaması için
                            txt_Salondakiler.setText("Salondaki Aktif Üye Sayısı: "+salondaki_aktif_uye);


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
                params.put("salondaki_aktif_uye","1");

                return params;
            };
        };
        myReq.setShouldCache(false); //cache kapatıyoruz.

        // Request(İstek)'i Volley'in Requst sırasına atıyoruz
        // Adding request to volley request queue
        AppController.getInstance().addToRequestQueue(myReq);

    }

}
