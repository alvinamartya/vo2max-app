package com.example.atlit.Adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.atlit.Model.Balke;
import com.example.atlit.R;

import java.util.Calendar;
import java.util.List;

public class BalkeAdapter extends RecyclerView.Adapter<BalkeAdapter.ViewHolder> {

    Context context;
    List<Balke> balkeList;

    public BalkeAdapter(Context context, List<Balke> balkeList) {
        this.context = context;
        this.balkeList = balkeList;
    }

    public void setData(List<Balke> data){
        this.balkeList = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.row_data_balke,viewGroup,false);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        Balke balke = balkeList.get(i);

        Calendar cal = Calendar.getInstance();
        int umur =  cal.get(Calendar.YEAR) - Integer.parseInt(balke.getTanggal_lahir().split("-")[0]);
        holder.tvnama.setText(balke.getNama());
        holder.tvusia.setText(String.valueOf(umur));
        holder.tvjenisKelamin.setText(balke.getJenis_kelamin().equals("Laki-Laki") ? "L" : "P");
        holder.tvJarak.setText(String.valueOf(balke.getJarak_ditempuh()));
        holder.tvLevel.setText(balke.getTingkat_kebugaran());
        holder.tvV02max.setText(String.valueOf(balke.getVo2max()));
        holder.tvSolusi.setText(balke.getSolusi() == null ? "-" : balke.getSolusi());
    }

    @Override
    public int getItemCount() {
        if(balkeList == null) return 0;
        return balkeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvnama, tvusia, tvjenisKelamin, tvJarak, tvLevel, tvV02max,tvSolusi;
        public ViewHolder(@NonNull View v) {
            super(v);
            tvnama= v.findViewById(R.id.tvnama);
            tvusia=v.findViewById(R.id.tvusia);
            tvjenisKelamin=v.findViewById(R.id.tvjenisKelamin);
            tvJarak=v.findViewById(R.id.tvJarak);
            tvLevel=v.findViewById(R.id.tvLevel);
            tvV02max=v.findViewById(R.id.tvV02max);
            tvSolusi= v.findViewById(R.id.tvSolusi);
        }
    }
}
