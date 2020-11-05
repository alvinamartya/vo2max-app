package com.example.atlit.Activity;

import android.app.ProgressDialog;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.atlit.Dialog.SolusiDialog;
import com.example.atlit.R;
import com.example.atlit.RetrofitModel.CooperModel;
import com.example.atlit.RetrofitModel.atlitModel;
import com.example.atlit.Utils.ApiClient;
import com.example.atlit.Utils.ApiInterface;
import com.example.atlit.Utils.Constants;
import com.example.atlit.Utils.Loginsharedpreference;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CooperAtlitActivity extends AppCompatActivity {
    private final static String TAG = CooperAtlitActivity.class.getSimpleName();
    private Toolbar toolbar;
    private RadioGroup radiogroup;
    private RadioButton rbL, rbP;
    private EditText edtBulan, edtMinggu, edtnama, edtUmur, edtWaktu, edtVo2Max, edtTingkatKebugaran, edtDetik;
    private Button btnProses, btnSimpan, btnHapus, btnData, btnSolusi;
    private ProgressBar progress;
    private TextView error_txt_cause;
    private LinearLayout error_layout, data_cooper;
    private boolean isProcess = false;
    private Loginsharedpreference loginsharedpreference;
    private ApiInterface mApiInterface;

    public static final String Time_Key = "time";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cooper_atlit);
        initToolbar();
        radiogroup = findViewById(R.id.radiogroup);
        rbL = findViewById(R.id.rbL);
        rbP = findViewById(R.id.rbP);
        edtBulan = findViewById(R.id.edtBulan);
        edtMinggu = findViewById(R.id.edtMinggu);
        edtnama = findViewById(R.id.edtnama);
        edtUmur = findViewById(R.id.edtUmur);
        edtWaktu = findViewById(R.id.edtWaktu);
        edtVo2Max = findViewById(R.id.edtVo2Max);
        edtTingkatKebugaran = findViewById(R.id.edtTingkatKebugaran);
        btnProses = findViewById(R.id.btnProses);
        btnSimpan = findViewById(R.id.btnSimpan);
        btnHapus = findViewById(R.id.btnHapus);
        btnData = findViewById(R.id.btnData);
        btnSolusi = findViewById(R.id.btnSolusi);
        edtDetik = findViewById(R.id.edtDetik);
        Calendar cal = Calendar.getInstance();
        edtBulan.setText(String.valueOf(cal.get(Calendar.MONTH)));
        edtMinggu.setText(String.valueOf(cal.get(Calendar.WEEK_OF_MONTH)));
        loginsharedpreference = new Loginsharedpreference(this);
        mApiInterface = ApiClient.getClient().create(ApiInterface.class);

        int ms = getIntent().getIntExtra(Time_Key, 0);
        long m = ms / 60 % 60;
        long h = ms / 60 / 60;

        progress = findViewById(R.id.progress);
        error_layout = findViewById(R.id.error_layout);
        data_cooper = findViewById(R.id.ll_data_cooper);
        error_txt_cause = findViewById(R.id.error_txt_cause);

        edtWaktu.setText(String.valueOf(h));
        edtDetik.setText(String.valueOf(m));
        edtWaktu.setEnabled(false);
        edtDetik.setEnabled(false);

        btnSolusi.setOnClickListener(v -> {
            SolusiDialog dialog = new SolusiDialog();
            dialog.show(getSupportFragmentManager(), "SolusiDialog");
        });

        btnProses.setOnClickListener(v -> {
            // cek format jarak
            try {
                Integer.parseInt(edtWaktu.getText().toString());
                Integer.parseInt(edtDetik.getText().toString());
            } catch (Exception e) {
                Log.e(TAG, "onClick: " + e.getMessage());
                Toast.makeText(CooperAtlitActivity.this, "Format waktu salah", Toast.LENGTH_SHORT).show();
                return;
            }

            // cek format umur
            try {
                Integer.parseInt(edtUmur.getText().toString());
            } catch (Exception e) {
                Log.e(TAG, "onClick: " + e.getMessage());
                Toast.makeText(CooperAtlitActivity.this, "Format umur salah", Toast.LENGTH_SHORT).show();
                return;
            }

            if (TextUtils.isEmpty(edtWaktu.getText().toString()) || TextUtils.isEmpty(edtDetik.getText().toString())) {
                Toast.makeText(CooperAtlitActivity.this, "Pastikan waktu telah diisi", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(edtUmur.getText().toString())) {
                Toast.makeText(CooperAtlitActivity.this, "Pastikan umur telah diisi", Toast.LENGTH_SHORT).show();
            } else if (Integer.parseInt(edtUmur.getText().toString()) < 13) {
                Toast.makeText(CooperAtlitActivity.this, "Pastikan umur harus lebih besar dari 12 tahun", Toast.LENGTH_SHORT).show();
            } else if (radiogroup.getCheckedRadioButtonId() != R.id.rbL && radiogroup.getCheckedRadioButtonId() != R.id.rbP) {
                Toast.makeText(CooperAtlitActivity.this, "Pastikan anda telah memilih jenis kelamin", Toast.LENGTH_SHORT).show();
            } else {
                try {
                    char jk = rbL.isChecked() == true ? 'l' : 'p';
                    double waktu = Double.parseDouble(edtWaktu.getText().toString()) + (Double.parseDouble(edtDetik.getText().toString()) / 60);
                    Log.e(TAG, "onClick: " + String.valueOf(waktu));
                    double vo2max = 85.95 - (3.079 * waktu);
                    String tingkatKebugaran = tableVo2Max(
                            Integer.parseInt(edtUmur.getText().toString()),
                            vo2max,
                            jk
                    );

                    edtTingkatKebugaran.setText(tingkatKebugaran);
                    edtVo2Max.setText(String.valueOf(vo2max));
                    isProcess = true;
                    edtWaktu.setEnabled(false);
                    rbL.setEnabled(false);
                    rbP.setEnabled(false);
                    edtUmur.setEnabled(false);
                    edtDetik.setEnabled(false);
                } catch (Exception e) {
                    Log.e(TAG, "onClick: " + e.getMessage());
                    Toast.makeText(CooperAtlitActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnHapus.setOnClickListener(v -> {
            isProcess = false;
            rbL.setEnabled(true);
            rbP.setEnabled(true);
            edtUmur.setEnabled(true);
            edtTingkatKebugaran.setText("");
            edtVo2Max.setText("");
            edtnama.setText("");
            edtWaktu.setText("");
            edtUmur.setText("");
            edtDetik.setText("");
            rbL.setChecked(false);
            rbP.setChecked(false);

            btnSimpan.setText("Kembali");
        });

        btnSimpan.setOnClickListener(v -> {
            if (btnSimpan.getText().equals("Kembali")) {
                Intent intent = new Intent(CooperAtlitActivity.this, StopWatchActivity.class);
                intent.putExtra(StopWatchActivity.KEY_METHOD, "cooper");
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            } else {
                final ProgressDialog pDialog = new ProgressDialog(CooperAtlitActivity.this);
                pDialog.setTitle(getString(R.string.information));
                pDialog.setMessage(getString(R.string.please_wait));
                pDialog.show();
                if (isProcess) {
                    if (TextUtils.isEmpty(edtnama.getText())) {
                        Toast.makeText(CooperAtlitActivity.this, "Pastikan nama telah diisi", Toast.LENGTH_SHORT).show();
                        pDialog.dismiss();
                    } else if (TextUtils.isEmpty(edtMinggu.getText())) {
                        Toast.makeText(CooperAtlitActivity.this, "Pastikan minggu telah diisi", Toast.LENGTH_SHORT).show();
                        pDialog.dismiss();
                    } else if (TextUtils.isEmpty(edtBulan.getText())) {
                        Toast.makeText(CooperAtlitActivity.this, "Pastikan bulan telah diisi", Toast.LENGTH_SHORT).show();
                        pDialog.dismiss();
                    } else {
                        String jk = rbL.isChecked() == true ? "L" : "P";
                        Call<CooperModel> cooper = mApiInterface.cooperPost(
                                Integer.parseInt(edtBulan.getText().toString()),
                                Integer.parseInt(edtMinggu.getText().toString()),
                                (Integer.parseInt(edtWaktu.getText().toString()) * 60) + Integer.parseInt(edtDetik.getText().toString()),
                                Float.parseFloat(edtVo2Max.getText().toString()),
                                edtTingkatKebugaran.getText().toString(),
                                loginsharedpreference.getUserLogin().getId()
                        );

                        cooper.enqueue(new Callback<CooperModel>() {
                            @Override
                            public void onResponse(Call<CooperModel> call, Response<CooperModel> response) {
                                if (response.isSuccessful()) {
                                    if (response.body().getMessage().equals("berhasil menambahkan data")) {
                                        Toast.makeText(CooperAtlitActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                        isProcess = false;
                                        edtTingkatKebugaran.setText("");
                                        edtVo2Max.setText("");
                                        edtWaktu.setText("");

                                        btnSimpan.setText("Kembali");
                                    } else
                                        Toast.makeText(CooperAtlitActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                } else
                                    Toast.makeText(CooperAtlitActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                                pDialog.dismiss();
                            }

                            @Override
                            public void onFailure(Call<CooperModel> call, Throwable t) {
                                Log.e(TAG, "onFailure: " + t.getMessage());
                                Toast.makeText(CooperAtlitActivity.this, "onFailure: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                                pDialog.dismiss();
                            }
                        });
                    }
                } else {
                    Toast.makeText(CooperAtlitActivity.this, "Pastikan anda telah menginput data", Toast.LENGTH_SHORT).show();
                    pDialog.dismiss();
                }
            }
        });

        btnData.setOnClickListener(v -> intentToCooperData());
        fillData();
    }


    private void fillData() {
        hideProgress(1);
        Call<atlitModel> getData = mApiInterface.atlitGets(loginsharedpreference.getUserLogin().getId());
        getData.enqueue(new Callback<atlitModel>() {
            @Override
            public void onResponse(Call<atlitModel> call, Response<atlitModel> response) {
                if (response.isSuccessful()) {
                    if (response.body().getMessage().equals("data berhasil ditemukan")) {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        edtnama.setText(response.body().getData().getNama());

                        Calendar cal = Calendar.getInstance();
                        int umur = 0;
                        try {
                            Date birthDate = dateFormat.parse(response.body().getData().getTanggal_lahir());
                            Calendar cal2 = Calendar.getInstance();
                            cal2.setTime(birthDate);
                            umur = cal.get(Calendar.YEAR) - cal2.get(Calendar.YEAR);
                        } catch (ParseException e) {
                            Log.e("Balke Atlit", e.getMessage());
                        }

                        edtUmur.setText(String.valueOf(umur));
                        if (response.body().getData().getJenis_kelamin().equals("Laki-Laki")) rbL.setChecked(true);
                        else rbP.setChecked(true);
                        hideProgress(3);
                    } else {
                        error_txt_cause.setText(response.body().getMessage());
                        hideProgress(2);
                    }
                } else {
                    error_txt_cause.setText(response.message());
                    hideProgress(2);
                }
            }

            @Override
            public void onFailure(Call<atlitModel> call, Throwable t) {
                error_txt_cause.setText(t.getMessage());
                hideProgress(2);
            }
        });

    }

    @Override
    public void onBackPressed() {
        intetToProgramTest();
    }

    private void intetToProgramTest() {
        Intent intent = new Intent(CooperAtlitActivity.this, ProgramTestActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private void intentToCooperData() {
        Intent intent = new Intent(CooperAtlitActivity.this, CooperDataActivity.class);
        intent.putExtra("from", "Atlit");
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    private String tableVo2Max(int umur, double vo2max, char jk) {
        if (
                (jk == 'p' && (umur >= 13 && umur <= 19) && (vo2max < 25)) ||
                        (jk == 'p' && (umur >= 20 && umur <= 29) && (vo2max < 23.6)) ||
                        (jk == 'p' && (umur >= 30 && umur <= 39) && (vo2max < 22.8)) ||
                        (jk == 'p' && (umur >= 40 && umur <= 49) && (vo2max < 21.0)) ||
                        (jk == 'p' && (umur >= 50 && umur <= 59) && (vo2max < 20.2)) ||
                        (jk == 'p' && (umur > 60) && (vo2max < 17.5))
        ) {
            return "Very Poor";
        } else if (
                (jk == 'p' && (umur >= 13 && umur <= 19) && (vo2max >= 25 && vo2max < 31)) ||
                        (jk == 'p' && (umur >= 20 && umur <= 29) && (vo2max >= 23.6 && vo2max < 29)) ||
                        (jk == 'p' && (umur >= 30 && umur <= 39) && (vo2max >= 22.8 && vo2max < 27)) ||
                        (jk == 'p' && (umur >= 40 && umur <= 49) && (vo2max >= 21.0 && vo2max < 20.3)) ||
                        (jk == 'p' && (umur >= 50 && umur <= 59) && (vo2max >= 20.2 && vo2max < 22.8)) ||
                        (jk == 'p' && (umur > 60) && (vo2max >= 17.5 && vo2max < 20.2))
        ) {
            return "Poor";
        } else if (
                (jk == 'p' && (umur >= 13 && umur <= 19) && (vo2max >= 31.0 && vo2max < 35)) ||
                        (jk == 'p' && (umur >= 20 && umur <= 29) && (vo2max >= 29.0 && vo2max < 33)) ||
                        (jk == 'p' && (umur >= 30 && umur <= 39) && (vo2max >= 27.0 && vo2max < 31.5)) ||
                        (jk == 'p' && (umur >= 40 && umur <= 49) && (vo2max >= 24.5 && vo2max < 29)) ||
                        (jk == 'p' && (umur >= 50 && umur <= 59) && (vo2max >= 22.8 && vo2max < 27)) ||
                        (jk == 'p' && (umur > 60) && (vo2max >= 20.2 && vo2max < 24.5))
        ) {
            return "Fair";
        } else if (
                (jk == 'p' && (umur >= 13 && umur <= 19) && (vo2max >= 35 && vo2max < 39)) ||
                        (jk == 'p' && (umur >= 20 && umur <= 29) && (vo2max >= 33 && vo2max < 37)) ||
                        (jk == 'p' && (umur >= 30 && umur <= 39) && (vo2max >= 31.5 && vo2max < 35.7)) ||
                        (jk == 'p' && (umur >= 40 && umur <= 49) && (vo2max >= 29 && vo2max < 32.9)) ||
                        (jk == 'p' && (umur >= 50 && umur <= 59) && (vo2max >= 27.0 && vo2max < 31.5)) ||
                        (jk == 'p' && (umur > 60) && (vo2max >= 24.5 && vo2max < 30.3))
        ) {
            return "Good";
        } else if (
                (jk == 'p' && (umur >= 13 && umur <= 19) && (vo2max >= 39 && vo2max < 42)) ||
                        (jk == 'p' && (umur >= 20 && umur <= 29) && (vo2max >= 37.0 && vo2max <= 41.0)) ||
                        (jk == 'p' && (umur >= 30 && umur <= 39) && (vo2max >= 35.7 && vo2max <= 40)) ||
                        (jk == 'p' && (umur >= 40 && umur <= 49) && (vo2max >= 32.9 && vo2max < 37)) ||
                        (jk == 'p' && (umur >= 50 && umur <= 59) && (vo2max >= 31.5 && vo2max < 35.8)) ||
                        (jk == 'p' && (umur > 60) && (vo2max >= 30.4 && vo2max < 31.5))
        ) {
            return "Excellent";
        } else if (
                (jk == 'p' && (umur >= 13 && umur <= 19) && (vo2max > 41.9)) ||
                        (jk == 'p' && (umur >= 20 && umur <= 29) && (vo2max > 41.0)) ||
                        (jk == 'p' && (umur >= 30 && umur <= 39) && (vo2max > 40.0)) ||
                        (jk == 'p' && (umur >= 40 && umur <= 49) && (vo2max > 36.9)) ||
                        (jk == 'p' && (umur >= 50 && umur <= 59) && (vo2max > 35.7)) ||
                        (jk == 'p' && (umur > 60) && (vo2max >= 17.5 && vo2max > 31.4))
        ) {
            return "Superior";
        } else if (
                (jk == 'l' && (umur >= 13 && umur <= 19) && (vo2max < 35)) ||
                        (jk == 'l' && (umur >= 20 && umur <= 29) && (vo2max < 33)) ||
                        (jk == 'l' && (umur >= 30 && umur <= 39) && (vo2max < 31.5)) ||
                        (jk == 'l' && (umur >= 40 && umur <= 49) && (vo2max < 30.2)) ||
                        (jk == 'l' && (umur >= 50 && umur <= 59) && (vo2max < 26.1)) ||
                        (jk == 'l' && (umur > 60) && (vo2max < 20.5))
        ) {
            return "Very Poor";
        } else if (
                (jk == 'l' && (umur >= 13 && umur <= 19) && (vo2max >= 35 && vo2max < 38.4)) ||
                        (jk == 'l' && (umur >= 20 && umur <= 29) && (vo2max >= 33.0 && vo2max < 36.5)) ||
                        (jk == 'l' && (umur >= 30 && umur <= 39) && (vo2max >= 31.5 && vo2max < 35.5)) ||
                        (jk == 'l' && (umur >= 40 && umur <= 49) && (vo2max >= 30.2 && vo2max < 33.6)) ||
                        (jk == 'l' && (umur >= 50 && umur <= 59) && (vo2max >= 26.1 && vo2max < 31)) ||
                        (jk == 'l' && (umur > 60) && (vo2max >= 20.5 && vo2max < 26.1))
        ) {
            return "Poor";
        } else if (
                (jk == 'l' && (umur >= 13 && umur <= 19) && (vo2max >= 38.4 && vo2max < 45.2)) ||
                        (jk == 'l' && (umur >= 20 && umur <= 29) && (vo2max >= 36.5 && vo2max < 42.5)) ||
                        (jk == 'l' && (umur >= 30 && umur <= 39) && (vo2max >= 35.5 && vo2max < 41)) ||
                        (jk == 'l' && (umur >= 40 && umur <= 49) && (vo2max >= 33.6 && vo2max < 39)) ||
                        (jk == 'l' && (umur >= 50 && umur <= 59) && (vo2max >= 31 && vo2max < 35.8)) ||
                        (jk == 'l' && (umur > 60) && (vo2max >= 26.1 && vo2max < 32.3))
        ) {
            return "Fair";
        } else if (
                (jk == 'l' && (umur >= 13 && umur <= 19) && (vo2max >= 45.2 && vo2max < 51)) ||
                        (jk == 'l' && (umur >= 20 && umur <= 29) && (vo2max >= 42.5 && vo2max < 46.5)) ||
                        (jk == 'l' && (umur >= 30 && umur <= 39) && (vo2max >= 41.0 && vo2max < 45)) ||
                        (jk == 'l' && (umur >= 40 && umur <= 49) && (vo2max >= 39 && vo2max < 43.8)) ||
                        (jk == 'l' && (umur >= 50 && umur <= 59) && (vo2max >= 35.8 && vo2max < 41)) ||
                        (jk == 'l' && (umur > 60) && (vo2max >= 32.3 && vo2max < 36.5))
        ) {
            return "Good";
        } else if (
                (jk == 'l' && (umur >= 13 && umur <= 19) && (vo2max >= 51.0 && vo2max < 56)) ||
                        (jk == 'l' && (umur >= 20 && umur <= 29) && (vo2max >= 46.5 && vo2max < 52.5)) ||
                        (jk == 'l' && (umur >= 30 && umur <= 39) && (vo2max >= 45.0 && vo2max < 49.5)) ||
                        (jk == 'l' && (umur >= 40 && umur <= 49) && (vo2max >= 43.8 && vo2max <= 48.0)) ||
                        (jk == 'l' && (umur >= 50 && umur <= 59) && (vo2max >= 41.0 && vo2max < 45.4)) ||
                        (jk == 'l' && (umur > 60) && (vo2max >= 36.5 && vo2max < 44.3))
        ) {
            return "Excellent";
        } else if (
                (jk == 'l' && (umur >= 13 && umur <= 19) && (vo2max > 55.9)) ||
                        (jk == 'l' && (umur >= 20 && umur <= 29) && (vo2max > 52.4)) ||
                        (jk == 'l' && (umur >= 30 && umur <= 39) && (vo2max > 49.4)) ||
                        (jk == 'l' && (umur >= 40 && umur <= 49) && (vo2max > 48.0)) ||
                        (jk == 'l' && (umur >= 50 && umur <= 59) && (vo2max > 45.3)) ||
                        (jk == 'l' && (umur > 60) && (vo2max >= 17.5 && vo2max > 44.2))
        ) {
            return "Superior";
        }
        return "";
    }

    private void initToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.cooper_atlit));
    }


    private void hideProgress(int value) {
        if (value == 1) {
            progress.setVisibility(View.VISIBLE);
            error_layout.setVisibility(View.GONE);
            data_cooper.setVisibility(View.GONE);
        } else if (value == 2) {
            progress.setVisibility(View.GONE);
            error_layout.setVisibility(View.VISIBLE);
            data_cooper.setVisibility(View.GONE);
        } else {
            progress.setVisibility(View.GONE);
            error_layout.setVisibility(View.GONE);
            data_cooper.setVisibility(View.VISIBLE);
        }
    }
}
