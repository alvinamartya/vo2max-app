package com.example.atlit.Activity;

import android.app.ProgressDialog;

import androidx.room.Room;

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
import com.example.atlit.RetrofitModel.BalkeModel;
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

public class BalkeAtlitActivity extends AppCompatActivity {
    private final static String TAG = BalkeAtlitActivity.class.getSimpleName();
    private Toolbar toolbar;
    private RadioGroup radiogroup;
    private RadioButton rbL, rbP;
    private EditText edtBulan, edtMinggu, edtnama, edtUmur, edtJarak, edtVo2Max, edtTingkatKebugaran;
    private Button btnProses, btnSimpan, btnHapus, btnData, btnSolusi;
    private ProgressBar progress;
    private TextView error_txt_cause;
    private LinearLayout error_layout, data_balke;
    private boolean isProcess = false;
    private Loginsharedpreference loginsharedpreference;
    private ApiInterface mApiInterface;

    public static final String Distance_Key = "distance";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balke_atlit);
        initToolbar();
        radiogroup = findViewById(R.id.radiogroup);
        rbL = findViewById(R.id.rbL);
        rbP = findViewById(R.id.rbP);
        edtBulan = findViewById(R.id.edtBulan);
        edtMinggu = findViewById(R.id.edtMinggu);
        edtnama = findViewById(R.id.edtnama);
        edtUmur = findViewById(R.id.edtUmur);
        edtJarak = findViewById(R.id.edtJarak);
        edtVo2Max = findViewById(R.id.edtVo2Max);
        edtTingkatKebugaran = findViewById(R.id.edtTingkatKebugaran);
        btnProses = findViewById(R.id.btnProses);
        btnSimpan = findViewById(R.id.btnSimpan);
        btnHapus = findViewById(R.id.btnHapus);
        btnData = findViewById(R.id.btnData);
        btnSolusi = findViewById(R.id.btnSolusi);

        progress = findViewById(R.id.progress);
        error_layout = findViewById(R.id.error_layout);
        data_balke = findViewById(R.id.ll_data_balke);
        error_txt_cause = findViewById(R.id.error_txt_cause);

        final Calendar cal = Calendar.getInstance();
        edtBulan.setText(String.valueOf(cal.get(Calendar.MONTH)));
        edtMinggu.setText(String.valueOf(cal.get(Calendar.WEEK_OF_MONTH)));
        mApiInterface = ApiClient.getClient().create(ApiInterface.class);
        loginsharedpreference = new Loginsharedpreference(this);

        double distance = getIntent().getDoubleExtra(Distance_Key, 0);
        edtJarak.setText(String.valueOf(distance));
        edtJarak.setEnabled(false);

        btnSolusi.setOnClickListener(v -> {
            SolusiDialog dialog = new SolusiDialog();
            dialog.show(getSupportFragmentManager(), "SolusiDialog");
        });

        btnProses.setOnClickListener(v -> {
            // cek format jarak
            try {
                Double.parseDouble(edtJarak.getText().toString());
            } catch (Exception e) {
                Log.e(TAG, "onClick: " + e.getMessage());
                Toast.makeText(BalkeAtlitActivity.this, "Format jarak salah", Toast.LENGTH_SHORT).show();
                return;
            }

            // cek format umur
            try {
                Integer.parseInt(edtUmur.getText().toString());
            } catch (Exception e) {
                Log.e(TAG, "onClick: " + e.getMessage());
                Toast.makeText(BalkeAtlitActivity.this, "Format umur salah", Toast.LENGTH_SHORT).show();
                return;
            }

            if (TextUtils.isEmpty(edtJarak.getText())) {
                Toast.makeText(BalkeAtlitActivity.this, "Pastikan jarak telah diisi", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(edtUmur.getText())) {
                Toast.makeText(BalkeAtlitActivity.this, "Pastikan umur telah diisi", Toast.LENGTH_SHORT).show();
            } else if (Integer.parseInt(edtUmur.getText().toString()) < 13) {
                Toast.makeText(BalkeAtlitActivity.this, "Pastikan umur harus lebih besar dari 12 tahun", Toast.LENGTH_SHORT).show();
            } else if (radiogroup.getCheckedRadioButtonId() != R.id.rbL && radiogroup.getCheckedRadioButtonId() != R.id.rbP) {
                Toast.makeText(BalkeAtlitActivity.this, "Pastikan anda telah memilih jenis kelamin", Toast.LENGTH_SHORT).show();
            } else {
                try {
                    double vo2max = 33.3 + (Double.parseDouble(edtJarak.getText().toString()) / 15 - 133) * 0.172;
                    String tingkatKebugaran = tableVo2Max(
                            Integer.parseInt(edtUmur.getText().toString()),
                            vo2max
                    );

                    edtTingkatKebugaran.setText(tingkatKebugaran);
                    edtVo2Max.setText(String.valueOf(vo2max));
                    isProcess = true;
                    edtJarak.setEnabled(false);
                    rbL.setEnabled(false);
                    rbP.setEnabled(false);
                    edtUmur.setEnabled(false);
                } catch (Exception e) {
                    Log.e(TAG, "onClick: " + e.getMessage());
                    Toast.makeText(BalkeAtlitActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnHapus.setOnClickListener(v -> {
            isProcess = false;
            edtJarak.setEnabled(true);
            rbL.setEnabled(true);
            rbP.setEnabled(true);
            edtUmur.setEnabled(true);
            edtTingkatKebugaran.setText("");
            edtVo2Max.setText("");
            edtJarak.setText("");
            edtUmur.setText("");
            rbL.setChecked(false);
            rbP.setChecked(false);
            edtnama.setText("");
        });

        btnSimpan.setOnClickListener(v -> {
            if (btnSimpan.getText().equals("Kembali")) {
                Intent intent = new Intent(BalkeAtlitActivity.this, StopWatchActivity.class);
                Constants.Key_Method = "balke";
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            } else {
                final ProgressDialog pDialog = new ProgressDialog(BalkeAtlitActivity.this);
                pDialog.setTitle(getString(R.string.information));
                pDialog.setMessage(getString(R.string.please_wait));
                pDialog.show();
                if (isProcess) {
                    if (TextUtils.isEmpty(edtnama.getText())) {
                        Toast.makeText(BalkeAtlitActivity.this, "Pastikan nama telah diisi", Toast.LENGTH_SHORT).show();
                        pDialog.dismiss();
                    } else if (TextUtils.isEmpty(edtMinggu.getText())) {
                        Toast.makeText(BalkeAtlitActivity.this, "Pastikan minggu telah diisi", Toast.LENGTH_SHORT).show();
                        pDialog.dismiss();
                    } else if (TextUtils.isEmpty(edtBulan.getText())) {
                        Toast.makeText(BalkeAtlitActivity.this, "Pastikan bulan telah diisi", Toast.LENGTH_SHORT).show();
                        pDialog.dismiss();
                    } else {

                        String jk = rbL.isChecked() == true ? "L" : "P";
                        Call<BalkeModel> balkePost = mApiInterface.balkePost(
                                Integer.parseInt(edtBulan.getText().toString()),
                                Integer.parseInt(edtMinggu.getText().toString()),
                                Float.parseFloat(edtJarak.getText().toString()),
                                Float.parseFloat(edtVo2Max.getText().toString()),
                                edtTingkatKebugaran.getText().toString(),
                                loginsharedpreference.getUserLogin().getId()
                        );


                        balkePost.enqueue(new Callback<BalkeModel>() {
                            @Override
                            public void onResponse(Call<BalkeModel> call, Response<BalkeModel> response) {
                                if (response.isSuccessful()) {
                                    if (response.body().getMessage().equals("berhasil menambahkan data")) {
                                        Toast.makeText(BalkeAtlitActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                        isProcess = false;
                                        edtTingkatKebugaran.setText("");
                                        edtVo2Max.setText("");
                                        edtJarak.setText("");

                                        btnSimpan.setText("Kembali");
                                    } else {
                                        Toast.makeText(BalkeAtlitActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(BalkeAtlitActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                                }
                                pDialog.dismiss();
                            }

                            @Override
                            public void onFailure(Call<BalkeModel> call, Throwable t) {
                                Log.e(TAG, "onFailure: " + t.getMessage());
                                Toast.makeText(BalkeAtlitActivity.this, "onFailure: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                                pDialog.dismiss();
                            }
                        });
                    }
                } else {
                    Toast.makeText(BalkeAtlitActivity.this, "Pastikan anda telah menginput data", Toast.LENGTH_SHORT).show();
                    pDialog.dismiss();
                }
            }
        });

        btnData.setOnClickListener(v -> intentToBalkeData());
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
        Intent intent = new Intent(BalkeAtlitActivity.this, ProgramTestActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private void intentToBalkeData() {
        Intent intent = new Intent(BalkeAtlitActivity.this, BalkeDataActivity.class);
        intent.putExtra("from", "Atlit");
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private String tableVo2Max(int umur, double vo2max) {
        if (
                ((umur >= 13 && umur <= 19) && vo2max < 35) ||
                        ((umur >= 20 && umur <= 29) && vo2max < 33) ||
                        ((umur >= 30 && umur <= 39) && vo2max < 31) ||
                        ((umur >= 40 && umur <= 49) && vo2max < 30) ||
                        ((umur >= 50 && umur <= 59) && vo2max < 26) ||
                        (umur > 60 && vo2max < 20)
        ) return "Very Poor";
        else if (
                ((umur >= 13 && umur <= 19) && (vo2max > 34 && vo2max <= 37)) ||
                        ((umur >= 20 && umur <= 29) && (vo2max > 32 && vo2max <= 35)) ||
                        ((umur >= 30 && umur <= 39) && (vo2max > 30 && vo2max <= 34)) ||
                        ((umur >= 40 && umur <= 49) && (vo2max > 29 && vo2max <= 32)) ||
                        ((umur >= 50 && umur <= 59) && (vo2max > 25 && vo2max <= 30)) ||
                        (umur > 60 && (vo2max > 19 && vo2max <= 25))
        ) return "Poor";
        else if (
                ((umur >= 13 && umur <= 19) && (vo2max > 37 && vo2max <= 44)) ||
                        ((umur >= 20 && umur <= 29) && (vo2max > 35 && vo2max <= 41)) ||
                        ((umur >= 30 && umur <= 39) && (vo2max > 34 && vo2max <= 40)) ||
                        ((umur >= 40 && umur <= 49) && (vo2max > 32 && vo2max <= 38)) ||
                        ((umur >= 50 && umur <= 59) && (vo2max > 30 && vo2max <= 35)) ||
                        (umur > 60 && (vo2max > 25 && vo2max <= 31))
        ) return "Fair";
        else if (
                ((umur >= 13 && umur <= 19) && (vo2max > 44 && vo2max <= 50)) ||
                        ((umur >= 20 && umur <= 29) && (vo2max > 41 && vo2max <= 52)) ||
                        ((umur >= 30 && umur <= 39) && (vo2max > 40 && vo2max <= 44)) ||
                        ((umur >= 40 && umur <= 49) && (vo2max > 38 && vo2max <= 42)) ||
                        ((umur >= 50 && umur <= 59) && (vo2max > 35 && vo2max <= 40)) ||
                        (umur > 60 && (vo2max > 31 && vo2max <= 35))
        ) return "Good";
        else if (
                ((umur >= 13 && umur <= 19) && (vo2max > 50 && vo2max <= 55)) ||
                        ((umur >= 20 && umur <= 29) && (vo2max > 45 && vo2max <= 52)) ||
                        ((umur >= 30 && umur <= 39) && (vo2max > 44 && vo2max <= 49)) ||
                        ((umur >= 40 && umur <= 49) && (vo2max > 42 && vo2max <= 47)) ||
                        ((umur >= 50 && umur <= 59) && (vo2max > 40 && vo2max <= 45)) ||
                        (umur > 60 && (vo2max > 35 && vo2max <= 44))
        ) return "Excelent";
        else if (
                ((umur >= 13 && umur <= 19) && vo2max > 55) ||
                        ((umur >= 20 && umur <= 29) && vo2max > 52) ||
                        ((umur >= 30 && umur <= 39) && vo2max > 49) ||
                        ((umur >= 40 && umur <= 49) && vo2max > 48) ||
                        ((umur >= 50 && umur <= 59) && vo2max > 45) ||
                        (umur > 60 && vo2max > 44)
        ) return "Superior";
        return "";
    }

    private void initToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.balke_atlit));
    }

    private void hideProgress(int value) {
        if (value == 1) {
            progress.setVisibility(View.VISIBLE);
            error_layout.setVisibility(View.GONE);
            data_balke.setVisibility(View.GONE);
        } else if (value == 2) {
            progress.setVisibility(View.GONE);
            error_layout.setVisibility(View.VISIBLE);
            data_balke.setVisibility(View.GONE);
        } else {
            progress.setVisibility(View.GONE);
            error_layout.setVisibility(View.GONE);
            data_balke.setVisibility(View.VISIBLE);
        }
    }
}
