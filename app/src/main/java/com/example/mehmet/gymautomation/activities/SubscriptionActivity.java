package com.example.mehmet.gymautomation.activities;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.mehmet.gymautomation.R;
import com.example.mehmet.gymautomation.controllers.AppController;
import com.example.mehmet.gymautomation.models.Program;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SubscriptionActivity extends AppCompatActivity  {
    private String tarife_id,uye_id;
    Context context = this;

    private Button btn_Donma;
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;
    public static ArrayList<String> programNames = new ArrayList<String>();
    public static ArrayList<String> actionNames = new ArrayList<String>();
    public static ArrayList<String> actionLink = new ArrayList<String>();
    public static ArrayList<String> set = new ArrayList<String>();
    public static ArrayList<String> tekrar = new ArrayList<String>();

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    String url = "http://192.168.2.99/Gym/android_Json/jsonProgramlar.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_subscription);
            programNames.clear();
            tarife_id = HomeActivity.tarife_id;
            uye_id = Integer.toString(LoginActivity.uye_id);
            programGetir(tarife_id,uye_id);


        }


    public void programGetir(final String tarife_id, final String uye_id){
        //TODO adding request to POST method and URL
        //Burada issteğimizi oluşturuyoruz, method parametresi olarak post seçiyoruz ve url'imizi const'dan alıyoruz.
        StringRequest myReq = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // // TODO datalarımız geldi parse işlemi yapılmalı
                        // // TODO lets parse it
                        JSONArray veri_json;
                        Gson gson = new Gson();
                        ArrayList<Program> programs = gson.fromJson(response,new TypeToken<ArrayList<Program>>(){}.getType());
                        try {
                            String url,videoLink;
                            veri_json = new JSONArray(response);
                            if(veri_json.length() != 0) {
                                for (int i = 0; i < veri_json.length(); i++) {
                                    JSONObject jsonobject = veri_json.getJSONObject(i);
                                    programNames.add(programs.get(i).getKategori_adi());
                                    actionNames.add(programs.get(i).getHareket_adi());
                                    actionLink.add(programs.get(i).getLink());
                                    set.add(Integer.toString(programs.get(i).getH_set()));
                                    tekrar.add(Integer.toString(programs.get(i).getH_tekrar()));

                                }
                                mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
                                mViewPager = (ViewPager) findViewById(R.id.container);
                                mViewPager.setAdapter(mSectionsPagerAdapter);
                                TextView tarifeYok = (TextView) findViewById(R.id.tarifeYok);
                                tarifeYok.setVisibility(View.INVISIBLE);

                            }
                            else{
                                TextView tarifeYok = (TextView) findViewById(R.id.tarifeYok);
                                tarifeYok.setVisibility(View.VISIBLE);
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
                params.put("programGetir", tarife_id);
                params.put("uye_id", uye_id);

                return params;
            };
        };
        myReq.setShouldCache(false); //cache kapatıyoruz.
        // Request(İstek)'i Volley'in Requst sırasına atıyoruz
        // Adding request to volley request queue
        AppController.getInstance().addToRequestQueue(myReq);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_subscription, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        private String baslik = "";
        private static final String ARG_SECTION_NUMBER = "section_number";
        private static final String ARG_INT_NUMBER = "int_number";
        private static final String program_name = "program_name";
        private static final String action_name = "action_name";
        private static final String action_link = "action_link";
        private static final String h_set = "set";
        private static final String h_tekrar = "tekrar";
        private Context context;
        public PlaceholderFragment(){ }
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
                args.putString(program_name,programNames.get(sectionNumber-1));
                args.putString(action_name,actionNames.get(sectionNumber-1));
                args.putString(action_link,actionLink.get(sectionNumber-1));
                args.putString(h_set,set.get(sectionNumber-1));
                args.putString(h_tekrar,tekrar.get(sectionNumber-1));
            //args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_subscription, container, false);
            TextView txtProgram_Names = (TextView) rootView.findViewById(R.id.programNames);
            TextView txtAction_Names = (TextView) rootView.findViewById(R.id.actionNames);
            TextView set = (TextView)  rootView.findViewById(R.id.set);
            TextView tekrar = (TextView)  rootView.findViewById(R.id.tekrar);

            txtProgram_Names.setText(getArguments().getString(program_name));
            txtAction_Names.setText(getArguments().getString(action_name));
            set.setText(set.getText()+getArguments().getString(h_set));
            tekrar.setText(tekrar.getText()+getArguments().getString(h_tekrar));

            //TODO Video Kısmı
            String videoID = getArguments().getString(action_link);
            String frameVideo = "<html><body style='align:center;'><iframe width='390' height='250' src='https://www.youtube.com/embed/"+videoID+"' frameborder='12' allowfullscreen></iframe></body></html>";
            WebView displayYoutubeVideo = (WebView) rootView.findViewById(R.id.videoView);
            displayYoutubeVideo.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    return false;
                }
            });
            WebSettings webSettings = displayYoutubeVideo.getSettings();
            webSettings.setJavaScriptEnabled(true);
            displayYoutubeVideo.loadData(frameVideo, "text/html", "utf-8");
            displayYoutubeVideo.setBackgroundColor(Color.TRANSPARENT);








           //textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }
    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).

            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            //TODO program sayısına göre sayfa döndürür.
            return programNames.size();
        }
    }
    //TODO Geri tuşuna basıldığında tetitklenen fonksiyon.
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
                            finish();


            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
