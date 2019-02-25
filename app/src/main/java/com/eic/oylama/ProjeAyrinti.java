package com.eic.oylama;

import android.content.Intent;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.TextView;


public class ProjeAyrinti extends AppCompatActivity {


    TextView tv_ProjeId;
    TextView tv_projeAd;
    TextView tv_projeSahibi;
    TextView tv_projeOzet;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_projeayrinti);
       setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);

       tv_ProjeId     = findViewById(R.id.pa_tv_projeId);
       tv_projeAd     = findViewById(R.id.pa_tv_projeAd);
       tv_projeSahibi = findViewById(R.id.pa_tv_projeSahibi);
       tv_projeOzet   = findViewById(R.id.pa_tv_projeOzet);



       tv_ProjeId.setText(getIntent().getIntExtra("projeId",-1)+"");
       tv_projeAd.setText(getIntent().getStringExtra("projeAd"));
       tv_projeSahibi.setText(getIntent().getStringExtra("projeSahibi"));
       tv_projeOzet.setText(getIntent().getStringExtra("projeOzet"));




    }


    public void puanlaClick(View view) {
        Intent intent = new Intent(view.getContext(),Puanlama.class);
        intent.putExtra("projeID",Integer.valueOf(tv_ProjeId.getText().toString()));


        startActivity(intent);
    }
}
