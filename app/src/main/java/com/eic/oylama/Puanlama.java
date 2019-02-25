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
import android.view.View;
import android.widget.Toast;

import com.eic.oylama.data.AdapterKriterList;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class Puanlama extends AppCompatActivity {


    RecyclerView recyclerView;
    AdapterKriterList adapterKriterList;
    String apiserverurl;
    String juriid;
    ProgressDialog progress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puanlama);

        recyclerView = findViewById(R.id.o_rv_kriterler);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);


        adapterKriterList = new AdapterKriterList(this);
        recyclerView.setAdapter(adapterKriterList);


    }

    public void oylamaTamamlaOnClick(View view) {

        progress = new ProgressDialog(this);
        progress.setTitle("Gönderiliyor");
        progress.setMessage("Lütfen Bekleyiniz...");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();

        GETCall getCall = new GETCall();
        getCall.execute();

    }




    public  class GETCall extends AsyncTask<String,Void,String> {


        JSONObject yeniOy;

        @Override
        protected void onPreExecute() {

            super.onPreExecute();


            SharedPreferences preferences = getApplicationContext().getSharedPreferences("ayarlar",MODE_PRIVATE);
            juriid  = preferences.getString("juriid","-1");
            apiserverurl = preferences.getString("server","https://oylamaapp.herokuapp.com/");

            if(juriid.equals("-1")){
                //Toast.makeText(getApplicationContext(),"JURI ID BULUNAMADI LÜTFEN AYARLARDAN IDNİZİ GİRİNİZ",Toast.LENGTH_LONG).show();
                return;
            }





            String[] oylar = adapterKriterList.getOylar();
         yeniOy = new JSONObject();
            try {
                yeniOy.put("projeid",getIntent().getIntExtra("projeID",-1));
                yeniOy.put("juriid",Integer.valueOf(juriid));
                yeniOy.put("kategori1",Integer.valueOf(oylar[0]));
                yeniOy.put("kategori2",Integer.valueOf(oylar[1]));
                yeniOy.put("kategori3",Integer.valueOf(oylar[2]));
                yeniOy.put("kategori4",Integer.valueOf(oylar[3]));
                yeniOy.put("kategori5",Integer.valueOf(oylar[4]));
                yeniOy.put("kategori6",Integer.valueOf(oylar[5]));
                yeniOy.put("kategori7",Integer.valueOf(oylar[6]));


                Log.e("olsturlaın jsobect",yeniOy.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }


        @Override
        protected String doInBackground(String... strings) {

            try {

                URL url = new URL(apiserverurl+"/oy");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                conn.setRequestProperty("Accept","application/json");
                conn.setDoOutput(true);
                conn.setDoInput(true);

                try {
                    DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                    os.writeBytes(yeniOy.toString());
                    os.flush();
                    os.close();


                    Log.i("STATUS", String.valueOf(conn.getResponseCode()));
                   //Log.i("MSG" , conn.getResponseMessage());
                    return String.valueOf(conn.getResponseCode());

                } finally {
                    conn.disconnect();
                }

            } catch (Exception e) {
                Log.e("oylamaeeer", e.getMessage(), e);
                return "404";
            }


        }


        @Override
        protected void onPostExecute(String result) {
            progress.dismiss();
            AlertDialog.Builder builder = new AlertDialog.Builder(Puanlama.this,R.style.Theme_AppCompat_Light_Dialog_Alert);

            if (result.equals("200")) {


                builder.setTitle("");
                builder.setMessage("Oyunuz Gönderildi");

                builder.setNeutralButton("Tamam", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        Intent intent = new Intent(Puanlama.this,OzetActivity.class);
                        startActivity(intent);

                    }
                });
                AlertDialog alert = builder.create();
                alert.show();

            }else if(result.equals("401")){

                builder.setTitle("HATA!");
                builder.setMessage("Bu Projeyi Zaten Oyladınız");
                builder.setIcon(android.R.drawable.ic_dialog_alert);


                builder.setNeutralButton("Tamam", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        Intent intent = new Intent(Puanlama.this,OzetActivity.class);
                        startActivity(intent);

                    }
                });

                AlertDialog alert = builder.create();

                alert.show();

            }
            else{

                builder.setTitle("HATA!");
                builder.setMessage("Oyunuz Gönderilemedi Lütfen Bağlantınızı Kontrol Edip Tekrar Deneyin");
                builder.setIcon(android.R.drawable.ic_dialog_alert);

                builder.setNeutralButton("Tamam", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        Intent intent = new Intent(Puanlama.this,OzetActivity.class);
                        startActivity(intent);

                    }
                });

                AlertDialog alert = builder.create();

                alert.show();

            }




        }

    }



}
