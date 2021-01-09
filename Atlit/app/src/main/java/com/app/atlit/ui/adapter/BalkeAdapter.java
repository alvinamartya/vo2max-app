package com.app.atlit.ui.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.atlit.model.pojo.BalkePojo;
import com.app.atlit.R;

import java.util.Calendar;
import java.util.List;

public class BalkeAdapter extends RecyclerView.Adapter<BalkeAdapter.ViewHolder> {

    Context context;
    List<BalkePojo> balkePojoList;

    public BalkeAdapter(Context context, List<BalkePojo> balkePojoList) {
        this.context = context;
        this.balkePojoList = balkePojoList;
    }

    public void setData(List<BalkePojo> data){
        this.balkePojoList = data;
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
        BalkePojo balkePojo = balkePojoList.get(i);

        Calendar cal = Calendar.getInstance();
        int umur =  cal.get(Calendar.YEAR) - Integer.parseInt(balkePojo.getTanggal_lahir().split("-")[0]);
        holder.tvnama.setText(balkePojo.getNama());
        holder.tvusia.setText(String.valueOf(umur));
        holder.tvjenisKelamin.setText(balkePojo.getJenis_kelamin().equals("Laki-Laki") ? "L" : "P");
        holder.tvJarak.setText(String.valueOf(balkePojo.getJarak_ditempuh()));
        holder.tvLevel.setText(balkePojo.getTingkat_kebugaran());
        holder.tvV02max.setText(String.valueOf(balkePojo.getVo2max()));
        holder.tvSolusi.setText(balkePojo.getSolusi() == null ? "-" : balkePojo.getSolusi());
    }

    @Override
    public int getItemCount() {
        if(balkePojoList == null) return 0;
        return balkePojoList.size();
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
