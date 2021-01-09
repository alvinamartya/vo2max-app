package com.app.atlit.ui.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.atlit.ui.activity.BeepPelatihActivity;
import com.app.atlit.ui.dialog.BeepDialog;
import com.app.atlit.model.pojo.BeepGetPojo;
import com.app.atlit.R;

import java.util.List;

public class BeepListAdapter extends RecyclerView.Adapter<BeepListAdapter.ViewHolder> {
    private final List<BeepGetPojo> beepList;
    private final Context context;

    public BeepListAdapter(List<BeepGetPojo> beepLists, Context context) {
        this.beepList = beepLists;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.view_beep_pelatih, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BeepGetPojo beep = beepList.get(position);

        holder.tvName.setText(beep.getNama());
        holder.tvVo2max.setText(String.valueOf(beep.getVo2max()));
        holder.tvMinggu.setText(String.valueOf(beep.getMinggu()));
        holder.tvBulan.setText(String.valueOf(beep.getBulan()));
        holder.tvTingkatKebugaran.setText(beep.getTingkat_kebugaran());
        holder.tvShuttle.setText(String.valueOf(beep.getShutle()));
        holder.tvLevel.setText(String.valueOf(beep.getLevel()));
        if (position == beepList.size() - 1) holder.lineBeep.setVisibility(View.GONE);

        holder.llBeep.setOnClickListener(v -> {
            BeepDialog beepDialog = new BeepDialog(beep, context);
            beepDialog.show(((BeepPelatihActivity) context).getSupportFragmentManager(), "Beep Dialog");
        });
    }

    @Override
    public int getItemCount() {
        return beepList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvName;
        private final TextView tvShuttle;
        private final TextView tvTingkatKebugaran;
        private final TextView tvVo2max;
        private final TextView tvMinggu;
        private final TextView tvBulan;
        private final TextView tvLevel;
        private final View lineBeep;
        private final LinearLayout llBeep;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            llBeep = itemView.findViewById(R.id.ll_beep);
            tvShuttle = itemView.findViewById(R.id.tv_shuttle_beep);
            tvTingkatKebugaran = itemView.findViewById(R.id.tv_tingkat_kebugaran_beep);
            tvName = itemView.findViewById(R.id.tv_name_beep);
            tvVo2max = itemView.findViewById(R.id.tv_vo2max_beep);
            tvMinggu = itemView.findViewById(R.id.tv_minggu_beep);
            tvBulan = itemView.findViewById(R.id.tv_bulan_beep);
            tvLevel = itemView.findViewById(R.id.tv_level_beep);
            lineBeep = itemView.findViewById(R.id.line_beep);
        }
    }
}