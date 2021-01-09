package com.app.atlit.ui.adapter;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.atlit.ui.activity.CooperPelatihActivity;
import com.app.atlit.model.pojo.CooperGetPojo;
import com.app.atlit.R;
import com.app.atlit.ui.dialog.CooperDialog;

import java.util.List;

public class CooperListAdapter extends RecyclerView.Adapter<CooperListAdapter.ViewHolder> {
    private final List<CooperGetPojo> cooperGetPojoList;
    private final Context context;

    public CooperListAdapter(List<CooperGetPojo> cooperGetPojoList, Context context) {
        this.cooperGetPojoList = cooperGetPojoList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.view_cooper_pelatih, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CooperGetPojo cooperGetPojo = cooperGetPojoList.get(position);

        holder.tvName.setText(cooperGetPojo.getNama());
        holder.tvVo2max.setText(String.valueOf(cooperGetPojo.getVo2max()));
        holder.tvMinggu.setText(String.valueOf(cooperGetPojo.getMinggu()));
        holder.tvBulan.setText(String.valueOf(cooperGetPojo.getBulan()));
        holder.tvTingkatKebugaran.setText(cooperGetPojo.getTingkat_kebugaran());

        Log.e("minute", String.valueOf(cooperGetPojo.getWaktu()));
        int m = cooperGetPojo.getWaktu() / 60;
        int s = cooperGetPojo.getWaktu() % 60;

        String minute = String.valueOf(m).length() > 1 ? String.valueOf(m) : "0" + m;
        String second = String.valueOf(s).length() > 1 ? String.valueOf(s) : "0" + s;

        holder.tvWaktu.setText(minute + ":" + second);
        if (position == cooperGetPojoList.size() - 1) holder.line.setVisibility(View.GONE);
        holder.llCooper.setOnClickListener(v -> {
            CooperDialog cooperDialog = new CooperDialog(cooperGetPojo, context);
            cooperDialog.show(((CooperPelatihActivity)context).getSupportFragmentManager(), "CooperDialog");
        });
    }

    @Override
    public int getItemCount() {
        return cooperGetPojoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvName;
        private final TextView tvWaktu;
        private final TextView tvTingkatKebugaran;
        private final TextView tvVo2max;
        private final TextView tvMinggu;
        private final TextView tvBulan;
        private final View line;
        private final LinearLayout llCooper;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            llCooper = itemView.findViewById(R.id.ll_cooper);
            tvWaktu = itemView.findViewById(R.id.tv_waktu_cooper);
            tvTingkatKebugaran = itemView.findViewById(R.id.tv_tingkat_kebugaran_cooper);
            tvName = itemView.findViewById(R.id.tv_name_cooper);
            tvVo2max = itemView.findViewById(R.id.tv_vo2max_cooper);
            tvMinggu = itemView.findViewById(R.id.tv_minggu_cooper);
            tvBulan = itemView.findViewById(R.id.tv_bulan_cooper);
            line = itemView.findViewById(R.id.line_cooper);
        }
    }
}
