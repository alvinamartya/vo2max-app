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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.atlit.Model.UserLogin;
import com.example.atlit.R;
import com.example.atlit.RetrofitModel.loginModel;
import com.example.atlit.RetrofitModel.pelatihModel;
import com.example.atlit.Utils.ApiClient;
import com.example.atlit.Utils.ApiInterface;
import com.example.atlit.Utils.Encrypts;
import com.example.atlit.Utils.GlobalVars;
import com.example.atlit.Utils.Loginsharedpreference;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterPelatihActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private final static String TAG = RegisterPelatihActivity.class.getSimpleName();
    private EditText txtNama, txtTanggalLahir, txtCabor, txtUsername, txtPassword;
    private Button btnSimpan, btnHapus;
    private Loginsharedpreference loginsharedpreference;
    private ImageButton btnDate;
    private ApiInterface mApiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_pelatih);
        initToolbar();

        loginsharedpreference = new Loginsharedpreference(this);
        txtNama = findViewById(R.id.txtNama);
        txtTanggalLahir = findViewById(R.id.txtTanggalLahir);
        txtCabor = findViewById(R.id.txtCabor);
        txtUsername = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);
        btnSimpan = findViewById(R.id.btnSimpan);
        btnHapus = findViewById(R.id.btnHapus);
        btnDate = findViewById(R.id.btnDate);
        final Calendar cal = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        txtTanggalLahir.setText(dateFormat.format(cal.getTime()));
        mApiInterface = ApiClient.getClient().create(ApiInterface.class);

        btnHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtNama.setText("", TextView.BufferType.EDITABLE);
                txtCabor.setText("", TextView.BufferType.EDITABLE);
                txtUsername.setText("", TextView.BufferType.EDITABLE);
                txtPassword.setText("", TextView.BufferType.EDITABLE);

                final Calendar cal = Calendar.getInstance();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                txtTanggalLahir.setText(dateFormat.format(cal.getTime()));
            }
        });
        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(RegisterPelatihActivity.this, new DatePickerDialog.OnDateSetListener() {
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
            public void onClick(View v) {
                if (TextUtils.isEmpty(txtNama.getText())) {
                    Toast.makeText(RegisterPelatihActivity.this, "Pastikan anda telah mengisi nama", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(txtTanggalLahir.getText())) {
                    Toast.makeText(RegisterPelatihActivity.this, "Pastikan anda telah mengisi usia", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(txtCabor.getText())) {
                    Toast.makeText(RegisterPelatihActivity.this, "Pastikan anda telah mengisi cabang olahraga", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(txtUsername.getText())) {
                    Toast.makeText(RegisterPelatihActivity.this, "Pastikan anda telah mengisi username", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(txtPassword.getText())) {
                    Toast.makeText(RegisterPelatihActivity.this, "Pastikan anda telah mengisi password", Toast.LENGTH_SHORT).show();
                } else if (!Pattern.compile("^[A-Za-z0-9_]+$").matcher(txtUsername.getText()).find()) {
                    Toast.makeText(RegisterPelatihActivity.this, "Format username salah", Toast.LENGTH_SHORT).show();
                } else if (!Pattern.compile("^[A-Za-z0-9_]+$").matcher(txtPassword.getText()).find()) {
                    Toast.makeText(RegisterPelatihActivity.this, "Format password salah", Toast.LENGTH_SHORT).show();
                } else if (!GlobalVars.isValidFormatDate(txtTanggalLahir.getText().toString())) {
                    Toast.makeText(RegisterPelatihActivity.this, "Pastikan format tanggal lahir telah benar", Toast.LENGTH_SHORT).show();
                } else {

                    final ProgressDialog progress = new ProgressDialog(RegisterPelatihActivity.this);
                    progress.setMessage(getString(R.string.information));
                    progress.setTitle(getString(R.string.please_wait));
                    progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progress.show();


                    // menyimpan data ke table login
                    Call<loginModel> postLogin = mApiInterface.postLogin(
                            txtUsername.getText().toString(),
                            Encrypts.encrypt(txtPassword.getText().toString()),
                            "pelatih"
                    );

                    postLogin.enqueue(new Callback<loginModel>() {
                        @Override
                        public void onResponse(Call<loginModel> call, Response<loginModel> response) {
                            if (response.isSuccessful()) {
                                Call<loginModel> login = mApiInterface.login(
                                        txtUsername.getText().toString(), Encrypts.encrypt(txtPassword.getText().toString()));

                                login.enqueue(new Callback<loginModel>() {
                                    @Override
                                    public void onResponse(Call<loginModel> call, Response<loginModel> response) {
                                        if (response.isSuccessful()) {
                                            int refid = response.body().getData().getId();
                                            final UserLogin user = response.body().getData();
                                            Call<pelatihModel> postPelatih = mApiInterface.postPelatih(
                                                    refid,
                                                    txtNama.getText().toString(),
                                                    txtTanggalLahir.getText().toString(),
                                                    txtCabor.getText().toString()
                                            );

                                            postPelatih.enqueue(new Callback<pelatihModel>() {
                                                @Override
                                                public void onResponse(Call<pelatihModel> call, Response<pelatihModel> response) {
                                                    if(response.isSuccessful()) {
                                                        AlertDialog.Builder dialog = new AlertDialog.Builder(RegisterPelatihActivity.this)
                                                                .setTitle("Register berhasil")
                                                                .setMessage("Anda telah berhasil mendaftar sebagai pelatih")
                                                                .setPositiveButton("Lanjut", new DialogInterface.OnClickListener() {
                                                                    @Override
                                                                    public void onClick(DialogInterface dialog, int which) {
                                                                        loginsharedpreference.setUserLogin(user);
                                                                        loginsharedpreference.setHasLogin(true);
                                                                        intentToBeranda();
                                                                    }
                                                                });
                                                        dialog.show();
                                                    } else
                                                        Toast.makeText(RegisterPelatihActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                                                    progress.dismiss();
                                                }

                                                @Override
                                                public void onFailure(Call<pelatihModel> call, Throwable t) {
                                                    progress.dismiss();
                                                    Log.e(TAG, "onFailure: " + t.getMessage());
                                                    Toast.makeText(
                                                            RegisterPelatihActivity.this,
                                                            "onFailure: " + String.valueOf(t.getMessage()),
                                                            Toast.LENGTH_SHORT
                                                    ).show();
                                                }
                                            });
                                        } else {
                                            Toast.makeText(RegisterPelatihActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                                            progress.dismiss();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<loginModel> call, Throwable t) {
                                        progress.dismiss();
                                        Log.e(TAG, "onFailure: " + t.getMessage());
                                        Toast.makeText(
                                                RegisterPelatihActivity.this,
                                                "onFailure: " + String.valueOf(t.getMessage()),
                                                Toast.LENGTH_SHORT
                                        ).show();
                                    }
                                });
                            } else {
                                Toast.makeText(RegisterPelatihActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                                progress.dismiss();
                            }
                        }

                        @Override
                        public void onFailure(Call<loginModel> call, Throwable t) {
                            progress.dismiss();
                            Log.e(TAG, "onFailure: " + t.getMessage());
                            Toast.makeText(
                                    RegisterPelatihActivity.this,
                                    "onFailure: " + String.valueOf(t.getMessage()),
                                    Toast.LENGTH_SHORT
                            ).show();
                        }
                    });
                }
            }
        });
    }

    private void initToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.daftar_pelatih));

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void intentToRegister() {
        Intent intent = new Intent(RegisterPelatihActivity.this, RegisterActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private void intentToBeranda() {
        Intent intent = new Intent(RegisterPelatihActivity.this, BerandaActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    @Override
    public void onBackPressed() {
        intentToRegister();
    }
}
