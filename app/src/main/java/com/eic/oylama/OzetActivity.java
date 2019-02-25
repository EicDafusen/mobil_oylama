package com.eic.oylama;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;

import android.view.MenuItem;
import android.widget.Toast;

import com.eic.oylama.data.Proje;


import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class OzetActivity extends AppCompatActivity {

    RecyclerView ozetListe ;


    static final String API_URL = "https://oylamaapp.herokuapp.com";
    static final String TAG_PROJELER = "/proje/";  //id gelicek
    static final  String TAG_OYVER = "/oy";
    static final String TAG_TUMPROJELER ="/tumprojeler";
    String apiserverurl;
    ProgressDialog progress;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ozet);



        ozetListe = findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        ozetListe.setLayoutManager(linearLayoutManager);

        progress = new ProgressDialog(this);
        progress.setTitle("Yükleniyor");
        progress.setMessage("Lütfen Bekleyiniz...");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();

        GETCall getCall = new GETCall();
        getCall.execute();






    }


    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent intent = new Intent(this,AyarlarActivity.class);

                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    public  class GETCall extends AsyncTask<String,Void,String> {

        int resCode;
        @Override
        protected void onPreExecute() {

            SharedPreferences preferences = getApplicationContext().getSharedPreferences("ayarlar",MODE_PRIVATE);

            apiserverurl = preferences.getString("server","https://oylamaapp.herokuapp.com");

            super.onPreExecute();
        }


        @Override
        protected String doInBackground(String... strings) {


            StringBuilder stringBuilder = new StringBuilder();
            try {


                URL url = new URL(apiserverurl + TAG_TUMPROJELER);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                try{
                    BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(httpURLConnection.getInputStream()));

                    String line;

                    while((line = bufferedReader.readLine()) != null){
                        stringBuilder.append(line).append("\n");
                    }
                    bufferedReader.close();
                    resCode = httpURLConnection.getResponseCode();
                    return stringBuilder.toString();

                }finally {
                    httpURLConnection.disconnect();
                }

            }catch (Exception e){
                //Log.e("ERROR", e.getMessage(), e);
                return null;
            }



        }


        @Override
        protected void onPostExecute(String result) {
            progress.dismiss();
            AlertDialog.Builder builder = new AlertDialog.Builder(OzetActivity.this,R.style.Theme_AppCompat_Light_Dialog_Alert);

            if(result == null ||  resCode != 200){
                builder.setTitle("HATA!");
                builder.setMessage("Oyunuz Gönderilemedi Lütfen Bağlantınızı Kontrol Edip Tekrar Deneyin");
                builder.setIcon(android.R.drawable.ic_dialog_alert);

                builder.setNeutralButton("Tamam", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        Intent intent = new Intent(OzetActivity.this,OzetActivity.class);
                        startActivity(intent);

                    }
                });

                AlertDialog alert = builder.create();

                alert.show();
            }

            ArrayList<Proje> projeArrayList = new ArrayList<Proje>();

            try{

                JSONArray jsonArray = new JSONArray(result);

                for (int i = 0 ; i<jsonArray.length();i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    Log.e("ERROR",jsonObject.toString());
                    Proje tempproje = new Proje();

                    tempproje.setProjeId(jsonObject.getInt("projeid"));
                    tempproje.setProjeAdi(jsonObject.getString("projeadi"));
                    tempproje.setProjeSahaibi(jsonObject.getString("projesahibi"));
                    tempproje.setProjeOzet(jsonObject.getString("projeozet"));

                    projeArrayList.add(tempproje);
                }

                AdapterOzetList adapter = new AdapterOzetList(getBaseContext(),projeArrayList);
                ozetListe.setAdapter(adapter);





            }catch (Exception e){
                Log.e("ERROR", e.getMessage(), e);

            }



        }

    }


}
