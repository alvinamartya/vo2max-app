package com.app.atlit.ui.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.atlit.model.pojo.CooperPojo;
import com.app.atlit.R;
import com.google.gson.Gson;

import java.util.Calendar;
import java.util.List;

public class CooperAdapter extends RecyclerView.Adapter<CooperAdapter.ViewHolder> {
    Context context;
    List<CooperPojo> cooperPojoList;

    public CooperAdapter(Context context, List<CooperPojo> cooperPojoList) {
        this.context = context;
        this.cooperPojoList = cooperPojoList;
    }

    public void setData(List<CooperPojo> data) {
        this.cooperPojoList = data;
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
        CooperPojo cooperPojo = cooperPojoList.get(i);

        Log.e("test", new Gson().toJson(cooperPojo));

        Calendar cal = Calendar.getInstance();
        int umur =  cal.get(Calendar.YEAR) - Integer.parseInt(cooperPojo.getTanggal_lahir().split("-")[0]);
        holder.tvnama.setText(cooperPojo.getNama());
        holder.tvusia.setText(String.valueOf(umur));
        holder.tvjenisKelamin.setText(cooperPojo.getJenis_kelamin().equals("Laki-Laki") ? "L" : "P");
        holder.tvWaktu.setText(String.valueOf(cooperPojo.getWaktu()));
        holder.tvLevel.setText(cooperPojo.getTingkat_kebugaran());
        holder.tvV02max.setText(String.valueOf(cooperPojo.getVo2max()));
        holder.tvSolusi.setText(cooperPojo.getSolusi() == null ? "-" : cooperPojo.getSolusi());
    }

    @Override
    public int getItemCount() {
        if (cooperPojoList == null) return 0;
        return cooperPojoList.size();
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
