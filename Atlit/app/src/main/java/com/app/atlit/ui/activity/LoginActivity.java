package com.app.atlit.ui.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;

import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.app.atlit.model.pojo.PelariPojo;
import com.app.atlit.R;
import com.app.atlit.utils.api.ApiClient;
import com.app.atlit.utils.api.ApiInterface;
import com.app.atlit.utils.Encrypts;
import com.app.atlit.utils.LoginSharedPreference;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private Button btnLogin, btnDaftar;
    private EditText txtUsername, txtPassword;
    private Toolbar toolbar;
    private LoginSharedPreference loginsharedpreference;
    private final static String TAG = LoginActivity.class.getSimpleName();
    private ApiInterface apiInterface;
    private Gson gson;
    private SimpleDateFormat dateFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initToolbar();
        loginsharedpreference = new LoginSharedPreference(this);
        if (loginsharedpreference.getHasLogin()) {
            intentToBeranda();
        }

        ArrayList<String> perms = new ArrayList<>();
        perms.add(Manifest.permission.ACCESS_FINE_LOCATION);
        perms.add(Manifest.permission.ACCESS_COARSE_LOCATION);

        ArrayList<String> reqPermission = permissionToRequest(perms);
        if(reqPermission.size() > 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(reqPermission.toArray(new String[reqPermission.size()]), 100);
            }
        }


        gson = new Gson();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        btnLogin = findViewById(R.id.btnLogin);
        btnDaftar = findViewById(R.id.btnRegister);
        txtUsername = findViewById(R.id.txtUserId);
        txtPassword = findViewById(R.id.txtPassword);
        btnDaftar.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });

        btnLogin.setOnClickListener(v -> {
            if (TextUtils.isEmpty(txtUsername.getText().toString())) {
                txtUsername.setError("Username harus diisi");
            } else if (TextUtils.isEmpty(txtPassword.getText().toString())) {
                txtPassword.setError("Password harus diisi");
            } else {
                Log.e(TAG, "onClick: " + Encrypts.encrypt(txtPassword.getText().toString()));
                final ProgressDialog progress = new ProgressDialog(LoginActivity.this);
                progress.setMessage(getString(R.string.information));
                progress.setTitle(getString(R.string.please_wait));
                progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progress.show();

                final Call<com.app.atlit.model.pojo.retrofit.LoginRetrofit> login = apiInterface.login(
                        txtUsername.getText().toString(), Encrypts.encrypt(txtPassword.getText().toString()));
                login.enqueue(new Callback<com.app.atlit.model.pojo.retrofit.LoginRetrofit>() {
                    @Override
                    public void onResponse(Call<com.app.atlit.model.pojo.retrofit.LoginRetrofit> call, Response<com.app.atlit.model.pojo.retrofit.LoginRetrofit> response) {
                        if (response.isSuccessful()) {
                            if (response.body().getMessage().equals("anda berhasil login")) {
                                loginsharedpreference.setUserLogin(response.body().getData());

                                final int loginid = response.body().getData().getId();
                                if (response.body().getData().getAkses().equals("atlit")) {
                                    Call<com.app.atlit.model.pojo.retrofit.AtlitRetrofit> getSingleDataAtlit = apiInterface.atlitGets(response.body().getData().getId());
                                    getSingleDataAtlit.enqueue(new Callback<com.app.atlit.model.pojo.retrofit.AtlitRetrofit>() {
                                        @Override
                                        public void onResponse(Call<com.app.atlit.model.pojo.retrofit.AtlitRetrofit> call, Response<com.app.atlit.model.pojo.retrofit.AtlitRetrofit> response) {
                                            if (response.isSuccessful()) {
                                                if (response.body().getMessage().equals("data berhasil ditemukan")) {
                                                    try {
                                                        Date date = dateFormat.parse(response.body().getData().getTanggal_lahir());
                                                        Calendar cal = Calendar.getInstance();
                                                        Calendar cal2 = Calendar.getInstance();
                                                        cal2.setTime(date);
                                                        int umur = cal.get(Calendar.YEAR) - cal2.get(Calendar.YEAR);
                                                        loginsharedpreference.setHasLogin(true);
                                                        loginsharedpreference.setPelari(new PelariPojo(
                                                                loginid,
                                                                response.body().getData().getNama(),
                                                                response.body().getData().getJenis_kelamin(),
                                                                umur
                                                        ));
                                                        intentToBeranda();
                                                    } catch (ParseException e) {
                                                        Log.e(TAG, "onResponse: " + e.getMessage());
                                                        Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            } else {
                                                Toast.makeText(LoginActivity.this, "Anda tidak dapat login ke sistem", Toast.LENGTH_SHORT).show();
                                            }
                                            progress.dismiss();
                                        }

                                        @Override
                                        public void onFailure(Call<com.app.atlit.model.pojo.retrofit.AtlitRetrofit> call, Throwable t) {
                                            Toast.makeText(
                                                    LoginActivity.this,
                                                    "onFailure: " + t.getMessage(),
                                                    Toast.LENGTH_SHORT
                                            ).show();
                                            Log.e(TAG, "onFailure: " + t.getMessage());
                                            progress.dismiss();
                                        }
                                    });
                                } else {
                                    loginsharedpreference.setHasLogin(true);
                                    intentToBeranda();
                                    progress.dismiss();
                                }
                            } else {
                                Toast.makeText(LoginActivity.this, "Anda tidak dapat login ke sistem", Toast.LENGTH_SHORT).show();
                                progress.dismiss();
                            }
                        } else {
                            Toast.makeText(LoginActivity.this, "Respone not success: " + response.message(), Toast.LENGTH_SHORT).show();
                            Log.e(TAG, "Respone not success: " + response.message());
                            progress.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<com.app.atlit.model.pojo.retrofit.LoginRetrofit> call, Throwable t) {
                        Toast.makeText(
                                LoginActivity.this,
                                "onFailure: " + t.getMessage(),
                                Toast.LENGTH_SHORT
                        ).show();
                        Log.e(TAG, "onFailure: " + t.getMessage());
                        progress.dismiss();
                    }
                });
            }
        });

    }

    private ArrayList<String> permissionToRequest(ArrayList<String> wantedPermissions) {
        ArrayList<String> result = new ArrayList<>();

        for (String perm : wantedPermissions) {
            if (!hasPermission(perm))
                result.add(perm);
        }

        return result;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission has been granted", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean hasPermission(String permission) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
        }

        return true;
    }

    private void initToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.login));
    }

    private void intentToBeranda() {
        Intent intent = new Intent(LoginActivity.this, BerandaActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}
