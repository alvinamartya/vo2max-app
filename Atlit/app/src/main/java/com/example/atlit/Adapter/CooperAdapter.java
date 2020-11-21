package com.example.atlit.Adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.atlit.Model.Cooper;
import com.example.atlit.R;
import com.google.gson.Gson;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CooperAdapter extends RecyclerView.Adapter<CooperAdapter.ViewHolder> {
    Context context;
    List<Cooper> cooperList;

    public CooperAdapter(Context context, List<Cooper> cooperList) {
        this.context = context;
        this.cooperList = cooperList;
    }

    public void setData(List<Cooper> data) {
        this.cooperList = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CooperAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.row_data_balke, viewGroup, false);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        Cooper cooper = cooperList.get(i);

        Log.e("test", new Gson().toJson(cooper));

        Calendar cal = Calendar.getInstance();
        int umur =  cal.get(Calendar.YEAR) - Integer.parseInt(cooper.getTanggal_lahir().split("-")[0]);
        holder.tvnama.setText(cooper.getNama());
        holder.tvusia.setText(String.valueOf(umur));
        holder.tvjenisKelamin.setText(cooper.getJenis_kelamin().equals("Laki-Laki") ? "L" : "P");
        holder.tvWaktu.setText(String.valueOf(cooper.getWaktu()));
        holder.tvLevel.setText(cooper.getTingkat_kebugaran());
        holder.tvV02max.setText(String.valueOf(cooper.getVo2max()));
        holder.tvSolusi.setText(cooper.getSolusi() == null ? "-" : cooper.getSolusi());
    }

    @Override
    public int getItemCount() {
        if (cooperList == null) return 0;
        return cooperList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvnama, tvusia, tvjenisKelamin, tvWaktu, tvLevel, tvV02max, tvSolusi;

        public ViewHolder(@NonNull View v) {
            super(v);
            tvnama = v.findViewById(R.id.tvnama);
            tvusia = v.findViewById(R.id.tvusia);
            tvjenisKelamin = v.findViewById(R.id.tvjenisKelamin);
            tvWaktu = v.findViewById(R.id.tvJarak);
            tvLevel = v.findViewById(R.id.tvLevel);
            tvV02max = v.findViewById(R.id.tvV02max);
            tvSolusi = v.findViewById(R.id.tvSolusi);
        }
    }
}
