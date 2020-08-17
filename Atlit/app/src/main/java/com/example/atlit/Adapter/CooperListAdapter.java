package com.example.atlit.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.atlit.Activity.BalkePelatihActivity;
import com.example.atlit.Activity.CooperPelatihActivity;
import com.example.atlit.Dialog.BalkeDialog;
import com.example.atlit.Dialog.CooperDialog;
import com.example.atlit.Model.Cooper;
import com.example.atlit.Model.CooperGet;
import com.example.atlit.R;

import java.util.List;

public class CooperListAdapter extends RecyclerView.Adapter<CooperListAdapter.ViewHolder> {
    private List<CooperGet> cooperGetList;
    private Context context;

    public CooperListAdapter(List<CooperGet> cooperGetList, Context context) {
        this.cooperGetList = cooperGetList;
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
        CooperGet cooperGet = cooperGetList.get(position);

        holder.tvName.setText(cooperGet.getNama());
        holder.tvVo2max.setText(String.valueOf(cooperGet.getVo2max()));
        holder.tvMinggu.setText(String.valueOf(cooperGet.getMinggu()));
        holder.tvBulan.setText(String.valueOf(cooperGet.getBulan()));
        holder.tvTingkatKebugaran.setText(cooperGet.getTingkat_kebugaran());

        int m = cooperGet.getWaktu() / 60;
        int s = cooperGet.getWaktu() % 60;

        String minute = String.valueOf(m).length() > 1 ? String.valueOf(m) : "0" + m;
        String second = String.valueOf(s).length() > 1 ? String.valueOf(s) : "0" + s;

        holder.tvWaktu.setText(minute + ":" + second);
        if (position == cooperGetList.size() - 1) holder.line.setVisibility(View.GONE);
        holder.llCooper.setOnClickListener(v -> {
            CooperDialog cooperDialog = new CooperDialog(cooperGet, context);
            cooperDialog.show(((CooperPelatihActivity)context).getSupportFragmentManager(), "CooperDialog");
        });
    }

    @Override
    public int getItemCount() {
        return cooperGetList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName, tvWaktu, tvTingkatKebugaran, tvVo2max, tvMinggu, tvBulan;
        private View line;
        private LinearLayout llCooper;

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
