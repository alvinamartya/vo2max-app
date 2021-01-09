package com.app.atlit.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.atlit.ui.activity.BalkePelatihActivity;
import com.app.atlit.ui.dialog.BalkeDialog;
import com.app.atlit.model.pojo.BalkeGetPojo;
import com.app.atlit.R;

import java.util.List;

public class BalkeListAdapter extends RecyclerView.Adapter<BalkeListAdapter.ViewHolder> {
    private final List<BalkeGetPojo> balkeGetPojoList;
    private final Context context;

    public BalkeListAdapter(List<BalkeGetPojo> balkeGetPojoList, Context context) {
        this.balkeGetPojoList = balkeGetPojoList;
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
        BalkeGetPojo balkeGetPojo = balkeGetPojoList.get(position);

        holder.tvName.setText(balkeGetPojo.getNama());
        holder.tvVo2max.setText(String.valueOf(balkeGetPojo.getVo2max()));
        holder.tvMinggu.setText(String.valueOf(balkeGetPojo.getMinggu()));
        holder.tvBulan.setText(String.valueOf(balkeGetPojo.getBulan()));
        holder.tvTingkatKebugaran.setText(balkeGetPojo.getTingkat_kebugaran());
        holder.tvJarak.setText(String.valueOf(balkeGetPojo.getJarak_ditempuh()));
        if (position == balkeGetPojoList.size() - 1) holder.lineBalke.setVisibility(View.GONE);

        holder.llBalke.setOnClickListener(v -> {
            BalkeDialog balkeDialog = new BalkeDialog(balkeGetPojo, context);
            balkeDialog.show(((BalkePelatihActivity) context).getSupportFragmentManager(), "BalkeDialog");
        });
    }

    @Override
    public int getItemCount() {
        return balkeGetPojoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvName;
        private final TextView tvJarak;
        private final TextView tvTingkatKebugaran;
        private final TextView tvVo2max;
        private final TextView tvMinggu;
        private final TextView tvBulan;
        private final View lineBalke;
        private final LinearLayout llBalke;

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
