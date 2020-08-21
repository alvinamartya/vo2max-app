package com.example.atlit.Dialog;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.atlit.Activity.BalkePelatihActivity;
import com.example.atlit.Activity.BeepPelatihActivity;
import com.example.atlit.Model.BeepGet;
import com.example.atlit.Model.CooperGet;
import com.example.atlit.Model.Process;
import com.example.atlit.R;
import com.example.atlit.Utils.ApiClient;
import com.example.atlit.Utils.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BeepDialog extends DialogFragment {

    private BeepGet beepGet;
    private ApiInterface mApiInterface;
    private Context context;

    public BeepDialog(BeepGet beepGet, Context context) {
        this.beepGet = beepGet;
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

        tvNama.setText(beepGet.getNama());
        tvShuttle.setText(String.valueOf(beepGet.getShutle()));
        tvLevel.setText(String.valueOf(beepGet.getLevel()));
        tvVo2max.setText(String.valueOf(beepGet.getVo2max()));
        tvTingkatKebugaran.setText(beepGet.getTingkat_kebugaran());
        tvBulan.setText(String.valueOf(beepGet.getBulan()));
        tvMinggu.setText(String.valueOf(beepGet.getMinggu()));

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

                mApiInterface.setSolusiBeep(beepGet.getId(), edtSolusi.getText().toString()).enqueue(new Callback<Process>() {
                    @Override
                    public void onResponse(Call<Process> call, Response<Process> response) {
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
                    public void onFailure(Call<Process> call, Throwable t) {
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