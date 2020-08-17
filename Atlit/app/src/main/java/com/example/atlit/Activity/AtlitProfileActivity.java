package com.example.atlit.Activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.atlit.Model.Pelari;
import com.example.atlit.R;
import com.example.atlit.RetrofitModel.atlitModel;
import com.example.atlit.RetrofitModel.deleteDataModel;
import com.example.atlit.Utils.ApiClient;
import com.example.atlit.Utils.ApiInterface;
import com.example.atlit.Utils.Encrypts;
import com.example.atlit.Utils.GlobalVars;
import com.example.atlit.Utils.Loginsharedpreference;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AtlitProfileActivity extends AppCompatActivity {

    private ImageButton btnDate;
    private EditText txtNama, txtTanggalLahir, txtTinggiBadan, txtBeratBadan, txtPassword;
    private RadioGroup rgGender;
    private RadioButton rbLaki, rbPerempuan;
    private Button btnSimpan, btnHapus;
    private Loginsharedpreference loginsharedpreference;
    private ApiInterface apiInterface;

    private ProgressBar progress;
    private ScrollView sv;
    private LinearLayout error_layout;
    private TextView error_txt_cause;
    private Button error_btn_retry;
    private final static String TAG = PelatihProfileActivity.class.getSimpleName();
    private Toolbar toolbar;
    private Calendar cal = Calendar.getInstance();
    private SimpleDateFormat dateFormat;
    private Gson gson;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atlit_profile);

        btnDate = findViewById(R.id.btnDate);
        txtNama = findViewById(R.id.txtNama);
        txtTanggalLahir = findViewById(R.id.txtTanggalLahir);
        txtTinggiBadan = findViewById(R.id.txtTinggiBadan);
        txtBeratBadan = findViewById(R.id.txtBeratBadan);
        txtPassword = findViewById(R.id.txtPassword);
        rgGender = findViewById(R.id.rgGender);
        rbLaki = findViewById(R.id.rbLaki);
        rbPerempuan = findViewById(R.id.rbPerempuan);
        btnSimpan = findViewById(R.id.btnSimpan);
        btnHapus = findViewById(R.id.btnHapus);
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        loginsharedpreference = new Loginsharedpreference(this);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        gson = new Gson();

        sv = findViewById(R.id.sv);
        progress = findViewById(R.id.progress);
        error_btn_retry = findViewById(R.id.error_btn_retry);
        error_layout = findViewById(R.id.error_layout);
        error_txt_cause = findViewById(R.id.error_txt_cause);
        initToolbar();
        hideProgress(1);
        fillData();
        error_btn_retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fillData();
            }
        });


        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AtlitProfileActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Calendar newCal = Calendar.getInstance();
                        newCal.set(year, month, dayOfMonth);
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        txtTanggalLahir.setText(dateFormat.format(newCal.getTime()));
                    }
                }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(txtNama.getText())) {
                    Toast.makeText(AtlitProfileActivity.this, "Pastikan anda telah mengisi nama", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(txtTanggalLahir.getText())) {
                    Toast.makeText(AtlitProfileActivity.this, "Pastikan anda telah mengisi usia", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(txtTinggiBadan.getText())) {
                    Toast.makeText(AtlitProfileActivity.this, "Pastikan anda telah mengisi tinggi bedan", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(txtBeratBadan.getText())) {
                    Toast.makeText(AtlitProfileActivity.this, "Pastikan anda telah mengisi berat badan", Toast.LENGTH_SHORT).show();
                } else if (!GlobalVars.isValidFormatDate(txtTanggalLahir.getText().toString())) {
                    Toast.makeText(AtlitProfileActivity.this, "Pastikan format tanggal lahir telah benar", Toast.LENGTH_SHORT).show();
                } else if (rbPerempuan.isChecked() == false && rbLaki.isChecked() == false) {
                    Toast.makeText(AtlitProfileActivity.this, "Pastikan anda telah memilih jenis kelamin", Toast.LENGTH_SHORT).show();
                } else {
                    final ProgressDialog progress = new ProgressDialog(AtlitProfileActivity.this);
                    progress.setMessage(getString(R.string.information));
                    progress.setTitle(getString(R.string.please_wait));
                    progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progress.show();

                    final String jk = rbLaki.isChecked() == true ? "Laki-Laki" : "Perempuan";
                    Log.e(TAG, "onClick: " + loginsharedpreference.getUserLogin().getId() );
                    Call<atlitModel> updateAtlit = apiInterface.updateDataAtlit(
                            loginsharedpreference.getUserLogin().getId(),
                            txtNama.getText().toString(),
                            txtTanggalLahir.getText().toString(),
                            Float.parseFloat(txtTinggiBadan.getText().toString()),
                            Float.parseFloat(txtBeratBadan.getText().toString()),
                            jk
                    );

                    updateAtlit.enqueue(new Callback<atlitModel>() {
                        @Override
                        public void onResponse(Call<atlitModel> call, Response<atlitModel> response) {
                            if (response.isSuccessful()) {
                                if (TextUtils.isEmpty(txtPassword.getText().toString())) {
                                    Toast.makeText(AtlitProfileActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                    try {
                                        Pelari pelari = loginsharedpreference.getPelari();
                                        pelari.setName(txtNama.getText().toString());
                                        pelari.setJk(jk);
                                        Date date = null;
                                        date = dateFormat.parse(response.body().getData().getTanggal_lahir());
                                        Calendar cal2 = Calendar.getInstance();
                                        Calendar cal3 = Calendar.getInstance();
                                        cal3.setTime(date);
                                        int umur = cal2.get(Calendar.YEAR) - cal3.get(Calendar.YEAR);
                                        pelari.setUmur(umur);
                                        loginsharedpreference.setPelari(pelari);
                                    } catch (ParseException e) {
                                        Log.e(TAG, "onResponse: " + e.getMessage() );
                                    }

                                    progress.dismiss();
                                } else {
                                    final String tgl = txtTanggalLahir.getText().toString();
                                    Call<deleteDataModel> changePassword = apiInterface.changePassword(
                                            loginsharedpreference.getUserLogin().getUsername(),
                                            Encrypts.encrypt(txtPassword.getText().toString())
                                    );

                                    changePassword.enqueue(new Callback<deleteDataModel>() {
                                        @Override
                                        public void onResponse(Call<deleteDataModel> call, Response<deleteDataModel> response) {
                                            if (response.isSuccessful()) {
                                                Toast.makeText(AtlitProfileActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                                txtPassword.setText("");

                                                try {
                                                    Pelari pelari = loginsharedpreference.getPelari();
                                                    pelari.setName(txtNama.getText().toString());
                                                    pelari.setJk(jk);
                                                    Date date = null;
                                                    date = dateFormat.parse(tgl);
                                                    Calendar cal2 = Calendar.getInstance();
                                                    Calendar cal3 = Calendar.getInstance();
                                                    cal3.setTime(date);
                                                    int umur = cal2.get(Calendar.YEAR) - cal3.get(Calendar.YEAR);
                                                    pelari.setUmur(umur);
                                                    loginsharedpreference.setPelari(pelari);
                                                } catch (Exception e) {
                                                    Log.e(TAG, "onResponse: " + e.getMessage() );
                                                }
                                            }
                                            else
                                                Toast.makeText(AtlitProfileActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                                            progress.dismiss();
                                        }

                                        @Override
                                        public void onFailure(Call<deleteDataModel> call, Throwable t) {
                                            progress.dismiss();
                                            Log.e(TAG, "onFailure: " + t.getMessage());
                                            Toast.makeText(
                                                    AtlitProfileActivity.this,
                                                    "onFailure: " + String.valueOf(t.getMessage()),
                                                    Toast.LENGTH_SHORT
                                            ).show();
                                        }
                                    });
                                }
                            } else {
                                Toast.makeText(AtlitProfileActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                                progress.dismiss();
                            }
                        }

                        @Override
                        public void onFailure(Call<atlitModel> call, Throwable t) {
                            progress.dismiss();
                            Log.e(TAG, "onFailure: " + t.getMessage());
                            Toast.makeText(
                                    AtlitProfileActivity.this,
                                    "onFailure: " + String.valueOf(t.getMessage()),
                                    Toast.LENGTH_SHORT
                            ).show();
                        }
                    });
                }
            }
        });

        btnHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intentToBack();
            }
        });
    }

    @Override
    public void onBackPressed() {
        intentToBack();
    }

    private void fillData() {
        Call<atlitModel> getData = apiInterface.atlitGets(loginsharedpreference.getUserLogin().getId());
        getData.enqueue(new Callback<atlitModel>() {
            @Override
            public void onResponse(Call<atlitModel> call, Response<atlitModel> response) {
                if (response.isSuccessful()) {
                    if (response.body().getMessage().equals("data berhasil ditemukan")) {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        try {
                            cal.setTime(dateFormat.parse(response.body().getData().getTanggal_lahir()));
                        } catch (ParseException e) {
                            Log.e(TAG, "onResponse: " + e.getMessage() );
                        }
                        txtNama.setText(response.body().getData().getNama());
                        txtTanggalLahir.setText(response.body().getData().getTanggal_lahir());
                        txtTinggiBadan.setText(String.valueOf(response.body().getData().getTinggi_badan()));
                        txtBeratBadan.setText(String.valueOf(response.body().getData().getBerat_badan()));
                        if (response.body().getData().getJenis_kelamin().equals("Laki-Laki")) rbLaki.setChecked(true);
                        else rbPerempuan.setChecked(true);
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

    private void hideProgress(int value) {
        if (value == 1) {
            progress.setVisibility(View.VISIBLE);
            error_layout.setVisibility(View.GONE);
            sv.setVisibility(View.GONE);
        } else if (value == 2) {
            progress.setVisibility(View.GONE);
            error_layout.setVisibility(View.VISIBLE);
            sv.setVisibility(View.GONE);
        } else {
            progress.setVisibility(View.GONE);
            error_layout.setVisibility(View.GONE);
            sv.setVisibility(View.VISIBLE);
        }
    }

    private void intentToBack() {
        Intent intent = new Intent(AtlitProfileActivity.this, BerandaActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private void initToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Profil Atlit");
    }
}
