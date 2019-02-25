package com.eic.oylama;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AyarlarActivity extends AppCompatActivity {

    EditText Server;
    EditText JuriId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ayarlar);

        Server = findViewById(R.id.a_et_server);
        JuriId = findViewById(R.id.a_et_juriid);

        Server.setText("https://oylamaapp.herokuapp.com");
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("ayarlar",MODE_PRIVATE);
        String preJuriid = preferences.getString("juriid","-1");
        if(preJuriid != null){
            JuriId.setText(preJuriid);
        }
    }

    public void ayarClick(View view) {
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("ayarlar",MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("server",Server.getText().toString());
        editor.putString("juriid",JuriId.getText().toString());
        editor.commit();
        onBackPressed();
    }
}
