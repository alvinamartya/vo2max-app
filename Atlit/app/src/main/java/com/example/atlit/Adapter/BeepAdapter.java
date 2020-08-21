package com.example.atlit.Adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.atlit.Model.Beep;
import com.example.atlit.R;

import java.util.List;

public class BeepAdapter extends RecyclerView.Adapter<BeepAdapter.ViewHolder> {
    private List<Beep> beeps;
    private Context context;

    public BeepAdapter(List<Beep> beeps, Context context) {
        this.beeps = beeps;
        this.context = context;
    }

    public void setData(List<Beep> data){
        this.beeps = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BeepAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.row_data_beep,viewGroup,false);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull BeepAdapter.ViewHolder holder, int i) {
        Beep data = beeps.get(i);
        holder.tvnama.setText(data.getNama());
        holder.tvusia.setText(String.valueOf(data.getUmur()));
        holder.tvjenisKelamin.setText(data.getJenis_kelamin());
        holder.tvLevel.setText(String.valueOf(data.getLevel()));
        holder.tvShuttle.setText(String.valueOf(data.getShutle()));
        holder.tvV02max.setText(String.valueOf(data.getVo2max()));
        holder.tvTingkatKebugaran.setText(data.getTingkat_kebugaran());
        holder.tvSolusi.setText(data.getSolusi() == null ? "-" : data.getSolusi());
    }

    @Override
    public int getItemCount() {
        if(beeps == null) return 0;
        return beeps.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvnama, tvusia, tvjenisKelamin, tvLevel, tvShuttle, tvV02max, tvTingkatKebugaran,tvSolusi;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvnama = itemView.findViewById(R.id.tvnama);
            tvusia = itemView.findViewById(R.id.tvusia);
            tvjenisKelamin = itemView.findViewById(R.id.tvjenisKelamin);
            tvLevel = itemView.findViewById(R.id.tvLevel);
            tvShuttle = itemView.findViewById(R.id.tvShuttle);
            tvV02max = itemView.findViewById(R.id.tvV02max);
            tvTingkatKebugaran = itemView.findViewById(R.id.tvTingkatKebugaran);
            tvSolusi= itemView.findViewById(R.id.tvSolusi);
        }
    }
}
