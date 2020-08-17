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
import com.example.atlit.Dialog.BalkeDialog;
import com.example.atlit.Model.BalkeGet;
import com.example.atlit.R;

import java.util.List;

public class BalkeListAdapter extends RecyclerView.Adapter<BalkeListAdapter.ViewHolder> {
    private List<BalkeGet> balkeGetList;
    private Context context;

    public BalkeListAdapter(List<BalkeGet> balkeGetList, Context context) {
        this.balkeGetList = balkeGetList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.view_balke_pelatih, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BalkeGet balkeGet = balkeGetList.get(position);

        holder.tvName.setText(balkeGet.getNama());
        holder.tvVo2max.setText(String.valueOf(balkeGet.getVo2max()));
        holder.tvMinggu.setText(String.valueOf(balkeGet.getMinggu()));
        holder.tvBulan.setText(String.valueOf(balkeGet.getBulan()));
        holder.tvTingkatKebugaran.setText(balkeGet.getTingkat_kebugaran());
        holder.tvJarak.setText(String.valueOf(balkeGet.getJarak_ditempuh()));
        if (position == balkeGetList.size() - 1) holder.lineBalke.setVisibility(View.GONE);

        holder.llBalke.setOnClickListener(v -> {
            BalkeDialog balkeDialog = new BalkeDialog(balkeGet, context);
            balkeDialog.show(((BalkePelatihActivity) context).getSupportFragmentManager(), "BalkeDialog");
        });
    }

    @Override
    public int getItemCount() {
        return balkeGetList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName, tvJarak, tvTingkatKebugaran, tvVo2max, tvMinggu, tvBulan;
        private View lineBalke;
        private LinearLayout llBalke;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            llBalke = itemView.findViewById(R.id.ll_balke);
            tvJarak = itemView.findViewById(R.id.tv_jarak_ditempuh_balke);
            tvTingkatKebugaran = itemView.findViewById(R.id.tv_tingkat_kebugaran_balke);
            tvName = itemView.findViewById(R.id.tv_name_balke);
            tvVo2max = itemView.findViewById(R.id.tv_vo2max_balke);
            tvMinggu = itemView.findViewById(R.id.tv_minggu_balke);
            tvBulan = itemView.findViewById(R.id.tv_bulan_balke);
            lineBalke = itemView.findViewById(R.id.line_balke);
        }
    }
}
