package com.eic.oylama;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.eic.oylama.data.Proje;

import java.util.ArrayList;

public class AdapterOzetList extends RecyclerView.Adapter<AdapterOzetList.OzetHolder> {

    LayoutInflater layoutInflater;
    ArrayList<Proje> projeList;



    public AdapterOzetList (Context context, ArrayList<Proje> projeler){
        layoutInflater = LayoutInflater.from(context);
        projeList = projeler;
    }

    @Override
    public OzetHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view =layoutInflater.inflate(R.layout.ozet_gorunum,parent,false);

        OzetHolder holder = new OzetHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(OzetHolder holder, final int position) {

            final int id = projeList.get(position).getProjeId();

            holder.tv_projeId.setText(id+"");
            holder.tv_projeAd.setText(projeList.get(position).getProjeAdi());



            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Toast.makeText(view.getContext(),id+"",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(view.getContext(),ProjeAyrinti.class);


                    Proje tempProje = projeList.get(position);

                    intent.putExtra("projeId", tempProje.getProjeId());
                    intent.putExtra("projeAd", tempProje.getProjeAdi());
                    intent.putExtra("projeSahibi",tempProje.getProjeSahaibi());
                    //Toast.makeText(view.getContext(),tempProje.getProjeSahaibi(),Toast.LENGTH_LONG).show();
                    intent.putExtra("projeOzet",tempProje.getProjeOzet());

                    view.getContext().startActivity(intent);
                }
            });
    }

    @Override
    public int getItemCount() {
        return projeList.size();
    }

    public class OzetHolder extends RecyclerView.ViewHolder{

        TextView tv_projeId;
        TextView tv_projeAd;

        public OzetHolder(View itemView) {
            super(itemView);

            tv_projeId = itemView.findViewById(R.id.rv_tv_index);
            tv_projeAd = itemView.findViewById(R.id.rv_tv_projeisim);

        }
    }
}
