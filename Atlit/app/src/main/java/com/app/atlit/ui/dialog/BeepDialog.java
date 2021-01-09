package com.app.atlit.ui.dialog;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.app.atlit.ui.activity.BeepPelatihActivity;
import com.app.atlit.model.pojo.BeepGetPojo;
import com.app.atlit.model.pojo.DataProcessPojo;
import com.app.atlit.R;
import com.app.atlit.utils.api.ApiClient;
import com.app.atlit.utils.api.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BeepDialog extends DialogFragment {

    private final BeepGetPojo beepGetPojo;
    private ApiInterface mApiInterface;
    private final Context context;

    public BeepDialog(BeepGetPojo beepGetPojo, Context context) {
        this.beepGetPojo = beepGetPojo;
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_beep_dialog, container, false);

        mApiInterface = ApiClient.getClient().create(ApiInterface.class);
        TextView tvNama = v.findViewById(R.id.tv_nama_beep);
        TextView tvShuttle = v.findViewById(R.id.tv_shuttle_beep);
        TextView tvLevel = v.findViewById(R.id.tv_level_beep);
        TextView tvVo2max = v.findViewById(R.id.tv_vo2max_beep);
        TextView tvTingkatKebugaran = v.findViewById(R.id.tv_tingkat_kebugaran_beep);
        TextView tvBulan = v.findViewById(R.id.tv_bulan_beep);
        TextView tvMinggu = v.findViewById(R.id.tv_minggu_beep);
        EditText edtSolusi = v.findViewById(R.id.edt_solusi_beep);
        Button btnSimpan = v.findViewById(R.id.btnSimpan);
        Button btnKembali = v.findViewById(R.id.btnKembali);

        tvNama.setText(beepGetPojo.getNama());
        tvShuttle.setText(String.valueOf(beepGetPojo.getShutle()));
        tvLevel.setText(String.valueOf(beepGetPojo.getLevel()));
        tvVo2max.setText(String.valueOf(beepGetPojo.getVo2max()));
        tvTingkatKebugaran.setText(beepGetPojo.getTingkat_kebugaran());
        tvBulan.setText(String.valueOf(beepGetPojo.getBulan()));
        tvMinggu.setText(String.valueOf(beepGetPojo.getMinggu()));

        btnKembali.setOnClickListener(view -> {
            dismiss();
        });

        btnSimpan.setOnClickListener(view -> {
            if(edtSolusi.getText().toString().isEmpty()) {
                new AlertDialog
                        .Builder(context)
                        .setTitle("Ubah solusi")
                        .setMessage("Solusi harus diisi")
                        .setPositiveButton("OK", (dialogInterface, i) -> {
                            // empty code
                        })
                        .show();
            } else {
                final ProgressDialog progress = new ProgressDialog(context);
                progress.setMessage(getString(R.string.information));
                progress.setTitle(getString(R.string.please_wait));
                progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progress.show();

                mApiInterface.setSolusiBeep(beepGetPojo.getId(), edtSolusi.getText().toString()).enqueue(new Callback<DataProcessPojo>() {
                    @Override
                    public void onResponse(Call<DataProcessPojo> call, Response<DataProcessPojo> response) {
                        if (response.isSuccessful()) {
                            if (response.body().isStatus()) {
                                ((BeepPelatihActivity) context).load();
                                dismiss();
                            }
                            new AlertDialog
                                    .Builder(context)
                                    .setTitle("Ubah solusi")
                                    .setMessage(response.body().getMessage())
                                    .setPositiveButton("OK", (dialogInterface, i) -> {
                                    })
                                    .show();
                        } else {
                            new AlertDialog
                                    .Builder(context)
                                    .setTitle("Ubah solusi")
                                    .setMessage(response.message())
                                    .setPositiveButton("OK", (dialogInterface, i) -> {
                                        // empty code
                                    })
                                    .show();
                        }

                        progress.dismiss();
                    }

                    @Override
                    public void onFailure(Call<DataProcessPojo> call, Throwable t) {
                        new AlertDialog
                                .Builder(context)
                                .setTitle("Ubah solusi")
                                .setMessage(t.getMessage())
                                .setPositiveButton("OK", (dialogInterface, i) -> {
                                    // empty code
                                })
                                .show();
                        progress.dismiss();
                    }
                });
            }
        });

        return v;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.ThemeOverlay_AppCompat_Dialog_Alert);
    }
}