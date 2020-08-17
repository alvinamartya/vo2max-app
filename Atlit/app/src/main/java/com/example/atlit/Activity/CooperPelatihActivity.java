package com.example.atlit.Activity;

import android.app.ProgressDialog;

import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.atlit.Adapter.BalkeListAdapter;
import com.example.atlit.Adapter.CooperListAdapter;
import com.example.atlit.Model.Atlit;
import com.example.atlit.Model.BalkeGet;
import com.example.atlit.Model.CooperGet;
import com.example.atlit.R;
import com.example.atlit.RetrofitModel.CooperModel;
import com.example.atlit.RetrofitModel.atlitModels;
import com.example.atlit.RetrofitModel.pelatihModel;
import com.example.atlit.Utils.ApiClient;
import com.example.atlit.Utils.ApiInterface;
import com.example.atlit.Utils.Loginsharedpreference;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CooperPelatihActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private Loginsharedpreference loginsharedpreference;
    private final static String TAG = CooperPelatihActivity.class.getSimpleName();
    private Button btnData;
    private RecyclerView rvCooper;

    private ApiInterface mApiInterface;

    private Gson gson;
    private ProgressBar progress;
    private LinearLayout error_layout;
    private TextView error_txt_cause;
    private Button error_btn_retry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cooper_pelatih);
        initToolbar();

        gson = new Gson();
        rvCooper = findViewById(R.id.rv_cooper_pelatih);
        progress = findViewById(R.id.progress);
        error_btn_retry = findViewById(R.id.error_btn_retry);
        error_layout = findViewById(R.id.error_layout);
        error_txt_cause = findViewById(R.id.error_txt_cause);
        btnData = findViewById(R.id.btnData);
        mApiInterface = ApiClient.getClient().create(ApiInterface.class);

        hideProgress(1);

        Calendar cal = Calendar.getInstance();
        loginsharedpreference = new Loginsharedpreference(this);

        btnData.setOnClickListener(v -> intentToCooperData());
        load();
        error_btn_retry.setOnClickListener(v -> {
            load();
        });
    }

    public void load() {
        mApiInterface.getListDataCooper(loginsharedpreference.getUserLogin().getCabor()).enqueue(new Callback<List<CooperGet>>() {
            @Override
            public void onResponse(Call<List<CooperGet>> call, Response<List<CooperGet>> response) {
                if (response.isSuccessful()) {
                    if (response.body().isEmpty()) {
                        hideProgress(2);
                        error_txt_cause.setText("Data balke tidak tersedia");
                    } else {
                        hideProgress(3);
                        CooperListAdapter cooperListAdapter = new CooperListAdapter(response.body(), CooperPelatihActivity.this);
                        rvCooper.setAdapter(cooperListAdapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<CooperGet>> call, Throwable t) {
                hideProgress(2);
                error_txt_cause.setText(t.getMessage());
            }
        });
    }

    private void initToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.cooper_pelatih));
    }

    @Override
    public void onBackPressed() {
        intetToProgramTest();
    }

    private void intentToCooperData() {
        Intent intent = new Intent(CooperPelatihActivity.this, CooperDataActivity.class);
        intent.putExtra("form", "pelatih");
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    private void hideProgress(int value) {
        if (value == 1) {
            progress.setVisibility(View.VISIBLE);
            error_layout.setVisibility(View.GONE);
            rvCooper.setVisibility(View.GONE);
        } else if (value == 2) {
            progress.setVisibility(View.GONE);
            error_layout.setVisibility(View.VISIBLE);
            rvCooper.setVisibility(View.GONE);
        } else {
            progress.setVisibility(View.GONE);
            error_layout.setVisibility(View.GONE);
            rvCooper.setVisibility(View.VISIBLE);
        }
    }

    private void intetToProgramTest() {
        Intent intent = new Intent(CooperPelatihActivity.this, ProgramTestActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
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
}
