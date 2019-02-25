package com.eic.oylama.data;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import com.eic.oylama.R;

import java.util.ArrayList;

public class AdapterKriterList extends RecyclerView.Adapter<AdapterKriterList.KriterHolder> {

    LayoutInflater layoutInflater;
    String[] Oylar = {"1","1","1","1","1","1","1"};

    ArrayList<String> kriterList ;


    public AdapterKriterList (Context context){
        layoutInflater = LayoutInflater.from(context);
        kriterList = new ArrayList<String>();
        kriterList.add("Özgünlük");
        kriterList.add("Ticarileşme");
        kriterList.add("AR-GE Niteliği");
        kriterList.add("Hedefine Ulaşma Potansiyeli");
        kriterList.add("Zaman ve Finans Planması");
        kriterList.add("Milli Kaynak Kullanımının Oranı");
        kriterList.add("Pantent/Endüstiriel Tasarım Çıkma Potansiyeli");
    }


    public String[] getOylar(){
        return Oylar;
    }

    @Override
    public KriterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =layoutInflater.inflate(R.layout.oy_gorunum,parent,false);

        KriterHolder holder = new KriterHolder(view);

        return holder;

    }

    @Override
    public void onBindViewHolder(final KriterHolder holder, final int position) {

        holder.tv.setText(kriterList.get(position));

        holder.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String f = ""+position+"  "+i+"";

                //Log.e("item",holder.spinner.getItemAtPosition(i).toString()+f);
                Oylar[position] = holder.spinner.getItemAtPosition(i).toString();


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



    }

    @Override
    public int getItemCount() {
        return kriterList.size();
    }

    public class KriterHolder extends RecyclerView.ViewHolder{

       TextView tv;
       Spinner spinner;


       public KriterHolder(View itemView) {
           super(itemView);
           tv = itemView.findViewById(R.id.textView2);
           spinner = itemView.findViewById(R.id.spinner);




       }
   }
}
