package com.example.atlit.Activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import androidx.room.Room;
import android.content.DialogInterface;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.atlit.Model.Atlit;
import com.example.atlit.Model.CabangOlahraga;
import com.example.atlit.Model.Pelari;
import com.example.atlit.Model.UserLogin;
import com.example.atlit.R;

import com.example.atlit.RetrofitModel.atlitModel;
import com.example.atlit.RetrofitModel.cabangOlahragaModel;
import com.example.atlit.RetrofitModel.loginModel;
import com.example.atlit.Utils.ApiClient;
import com.example.atlit.Utils.ApiInterface;
import com.example.atlit.Utils.Encrypts;
import com.example.atlit.Utils.GlobalVars;
import com.example.atlit.Utils.Loginsharedpreference;
import com.google.gson.Gson;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterAtlitActivity extends AppCompatActivity {
    private final static String TAG = RegisterAtlitActivity.class.getSimpleName();
    private Toolbar toolbar;
    private EditText txtNama, txtTanggalLahir, txtTinggiBadan, txtBeratBadan, txtUsername, txtPassword;
    private RadioButton rbLaki, rbPerempuan;
    private Button btnSimpan, btnHapus;
    private Loginsharedpreference loginsharedpreference;
    private CheckBox isAtlit;
    private ImageButton btnDate;
    private ApiInterface mApiInterface;
    private Gson gson;
    private Spinner spCabor;
    List<String> data = new ArrayList<>();

    private ProgressBar progress;
    private ScrollView sv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_atlit);
        initToolbar();

        loginsharedpreference = new Loginsharedpreference(this);

        sv = findViewById(R.id.sv);
        progress = findViewById(R.id.progress);

        txtNama = findViewById(R.id.txtNama);
        txtTanggalLahir = findViewById(R.id.txtTanggalLahir);
        txtTinggiBadan = findViewById(R.id.txtTinggiBadan);
        txtBeratBadan = findViewById(R.id.txtBeratBadan);
        txtUsername = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);
        rbLaki = findViewById(R.id.rbLaki);
        rbPerempuan = findViewById(R.id.rbPerempuan);
        btnSimpan = findViewById(R.id.btnSimpan);
        btnHapus = findViewById(R.id.btnHapus);
        isAtlit = findViewById(R.id.isAtlit);
        btnDate = findViewById(R.id.btnDate);
        final Calendar cal = Calendar.getInstance();
        final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        txtTanggalLahir.setText(dateFormat.format(cal.getTime()));
        mApiInterface = ApiClient.getClient().create(ApiInterface.class);
        gson = new Gson();
        data = new ArrayList<>();
        data.add("Pilih Cabang Olahraga");
        spCabor = findViewById(R.id.spCabor);

        hideProgress(1);
        Call<cabangOlahragaModel> cabor = mApiInterface.getCabangOlahraga();
        cabor.enqueue(new Callback<cabangOlahragaModel>() {
            @Override
            public void onResponse(Call<cabangOlahragaModel> call, Response<cabangOlahragaModel> response) {
                if (response.isSuccessful()) {
                    data = new ArrayList<>();
                    data.add("Pilih Cabang Olahraga");
                    if(response.body().getMessage().equals("data berhasil ditemukan")) {
                        for (CabangOlahraga item : response.body().getData())
                            data.add(item.getCabang_olahraga());

                        Log.e(TAG, "onCreate: " + gson.toJson(data));
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(RegisterAtlitActivity.this, android.R.layout.simple_spinner_dropdown_item, data);
                    spCabor.setAdapter(adapter);
                    hideProgress(2);
                } else {
                    Log.e(TAG, "onResponse: " + response.message());

                    data = new ArrayList<>();
                    data.add("Pilih Cabang Olahraga");
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(RegisterAtlitActivity.this, android.R.layout.simple_spinner_dropdown_item, data);
                    spCabor.setAdapter(adapter);
                    hideProgress(2);
                }
            }

            @Override
            public void onFailure(Call<cabangOlahragaModel> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());

                data = new ArrayList<>();
                data.add("Pilih Cabang Olahraga");
                ArrayAdapter<String> adapter = new ArrayAdapter<>(RegisterAtlitActivity.this, android.R.layout.simple_spinner_dropdown_item, data);
                spCabor.setAdapter(adapter);
                hideProgress(2);
            }
        });

        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(RegisterAtlitActivity.this, new DatePickerDialog.OnDateSetListener() {
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

        btnHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtNama.setText("", TextView.BufferType.EDITABLE);
                txtTinggiBadan.setText("", TextView.BufferType.EDITABLE);
                txtBeratBadan.setText("", TextView.BufferType.EDITABLE);
                txtUsername.setText("", TextView.BufferType.EDITABLE);
                txtPassword.setText("", TextView.BufferType.EDITABLE);
                rbLaki.setChecked(false);
                rbPerempuan.setChecked(false);

                final Calendar cal = Calendar.getInstance();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                txtTanggalLahir.setText(dateFormat.format(cal.getTime()));
            }
        });

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(txtNama.getText())) {
                    Toast.makeText(RegisterAtlitActivity.this, "Pastikan anda telah mengisi nama", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(txtTanggalLahir.getText())) {
                    Toast.makeText(RegisterAtlitActivity.this, "Pastikan anda telah mengisi usia", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(txtTinggiBadan.getText())) {
                    Toast.makeText(RegisterAtlitActivity.this, "Pastikan anda telah mengisi tinggi bedan", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(txtBeratBadan.getText())) {
                    Toast.makeText(RegisterAtlitActivity.this, "Pastikan anda telah mengisi berat badan", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(txtUsername.getText())) {
                    Toast.makeText(RegisterAtlitActivity.this, "Pastikan anda telah mengisi username", Toast.LENGTH_SHORT).show();
                } else if (!Pattern.compile("^[A-Za-z0-9_]+$").matcher(txtUsername.getText()).find()) {
                    Toast.makeText(RegisterAtlitActivity.this, "Format username salah", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(txtPassword.getText())) {
                    Toast.makeText(RegisterAtlitActivity.this, "Pastikan anda telah mengisi password", Toast.LENGTH_SHORT).show();
                } else if (!Pattern.compile("^[A-Za-z0-9_]+$").matcher(txtPassword.getText()).find()) {
                    Toast.makeText(RegisterAtlitActivity.this, "Format password salah", Toast.LENGTH_SHORT).show();
                } else if (!GlobalVars.isValidFormatDate(txtTanggalLahir.getText().toString())) {
                    Toast.makeText(RegisterAtlitActivity.this, "Pastikan format tanggal lahir telah benar", Toast.LENGTH_SHORT).show();
                } else if (rbPerempuan.isChecked() == false && rbLaki.isChecked() == false) {
                    Toast.makeText(RegisterAtlitActivity.this, "Pastikan anda telah memilih jenis kelamin", Toast.LENGTH_SHORT).show();
                } else if (spCabor.getSelectedItemPosition() == 0) {
                    Toast.makeText(RegisterAtlitActivity.this, "Pastikan anda telah memilih cabang olahraga", Toast.LENGTH_SHORT).show();
                } else {
                    final ProgressDialog progress = new ProgressDialog(RegisterAtlitActivity.this);
                    progress.setMessage(getString(R.string.information));
                    progress.setTitle(getString(R.string.please_wait));
                    progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progress.show();

                    Call<loginModel> loginModel = mApiInterface.postLogin(
                            txtUsername.getText().toString(),
                            Encrypts.encrypt(txtPassword.getText().toString()),
                            "atlit"
                    );

                    loginModel.enqueue(new Callback<com.example.atlit.RetrofitModel.loginModel>() {
                        @Override
                        public void onResponse(Call<com.example.atlit.RetrofitModel.loginModel> call, Response<com.example.atlit.RetrofitModel.loginModel> response) {
                            if (response.isSuccessful()) {
                                Call<loginModel> login = mApiInterface.login(
                                        txtUsername.getText().toString(), Encrypts.encrypt(txtPassword.getText().toString()));
                                login.enqueue(new Callback<com.example.atlit.RetrofitModel.loginModel>() {
                                    @Override
                                    public void onResponse(Call<com.example.atlit.RetrofitModel.loginModel> call, Response<com.example.atlit.RetrofitModel.loginModel> response) {
                                        if (response.isSuccessful()) {
                                            Log.e(TAG, "onResponse: " + gson.toJson(response.body()));
                                            int atlit = isAtlit.isChecked() == true ? 1 : 0;
                                            String jenis_kelamin = rbLaki.isChecked() ? "Laki-Laki" : "Perempuan";
                                            int refid = response.body().getData().getId();
                                            final UserLogin user = response.body().getData();
                                            Call<atlitModel> postAtlit = mApiInterface.postAtlit(
                                                    refid,
                                                    txtNama.getText().toString(),
                                                    txtTanggalLahir.getText().toString(),
                                                    txtTinggiBadan.getText().toString(),
                                                    txtBeratBadan.getText().toString(),
                                                    jenis_kelamin,
                                                    atlit,
                                                    data.get(spCabor.getSelectedItemPosition()).toLowerCase()
                                            );

                                            postAtlit.enqueue(new Callback<atlitModel>() {
                                                @Override
                                                public void onResponse(Call<atlitModel> call, final Response<atlitModel> response) {
                                                    if (response.isSuccessful()) {
                                                        final Atlit atlit1 = response.body().getData();
                                                        AlertDialog.Builder dialog = new AlertDialog.Builder(RegisterAtlitActivity.this)
                                                                .setTitle("Register berhasil")
                                                                .setMessage("Anda telah berhasil mendaftar sebagai atlit")
                                                                .setPositiveButton("Lanjut", new DialogInterface.OnClickListener() {
                                                                    @Override
                                                                    public void onClick(DialogInterface dialog, int which) {
                                                                        Date date = null;
                                                                        try {
                                                                            date = dateFormat.parse(atlit1.getTanggal_lahir());
                                                                            Calendar cal = Calendar.getInstance();
                                                                            Calendar cal2 = Calendar.getInstance();
                                                                            cal2.setTime(date);
                                                                            int umur = cal.get(Calendar.YEAR) - cal2.get(Calendar.YEAR);
                                                                            loginsharedpreference.setPelari(new Pelari(
                                                                                    user.getId(),
                                                                                    atlit1.getNama(),
                                                                                    atlit1.getJenis_kelamin(),
                                                                                    umur
                                                                            ));
                                                                            loginsharedpreference.setUserLogin(user);
                                                                            loginsharedpreference.setHasLogin(true);
                                                                            intentToBeranda();
                                                                        } catch (ParseException e) {
                                                                            Toast.makeText(RegisterAtlitActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                                                        }
                                                                    }
                                                                });

                                                        dialog.show();
                                                    } else {
                                                        Toast.makeText(RegisterAtlitActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                                                    }
                                                    progress.dismiss();
                                                }

                                                @Override
                                                public void onFailure(Call<atlitModel> call, Throwable t) {
                                                    progress.dismiss();
                                                    Log.e(TAG, "onFailure: " + t.getMessage());
                                                    Toast.makeText(
                                                            RegisterAtlitActivity.this,
                                                            "onFailure: " + String.valueOf(t.getMessage()),
                                                            Toast.LENGTH_SHORT
                                                    ).show();
                                                }
                                            });
                                        } else {
                                            Toast.makeText(RegisterAtlitActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                                            progress.dismiss();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<com.example.atlit.RetrofitModel.loginModel> call, Throwable t) {
                                        progress.dismiss();
                                        Log.e(TAG, "onFailure: " + t.getMessage());
                                        Toast.makeText(
                                                RegisterAtlitActivity.this,
                                                "onFailure: " + String.valueOf(t.getMessage()),
                                                Toast.LENGTH_SHORT
                                        ).show();
                                    }
                                });
                            } else {
                                Toast.makeText(RegisterAtlitActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                                progress.dismiss();
                            }
                        }

                        @Override
                        public void onFailure(Call<com.example.atlit.RetrofitModel.loginModel> call, Throwable t) {
                            progress.dismiss();
                            Log.e(TAG, "onFailure: " + t.getMessage());
                            Toast.makeText(
                                    RegisterAtlitActivity.this,
                                    "onFailure: " + String.valueOf(t.getMessage()),
                                    Toast.LENGTH_SHORT
                            ).show();
                        }
                    });
                }
            }
        });
    }

    private void hideProgress(int value) {
        if (value == 1) {
            progress.setVisibility(View.VISIBLE);
            sv.setVisibility(View.GONE);
        } else {
            progress.setVisibility(View.GONE);
            sv.setVisibility(View.VISIBLE);
        }
    }

    private void initToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.daftar_atlit));

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void intentToRegister() {
        Intent intent = new Intent(RegisterAtlitActivity.this, RegisterActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private void intentToBeranda() {
        Intent intent = new Intent(RegisterAtlitActivity.this, BerandaActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    @Override
    public void onBackPressed() {
        intentToRegister();
    }
}
