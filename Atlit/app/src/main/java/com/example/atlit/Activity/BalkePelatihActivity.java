package com.example.atlit.Activity;

import androidx.recyclerview.widget.RecyclerView;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.atlit.Adapter.BalkeListAdapter;
import com.example.atlit.Model.BalkeGet;
import com.example.atlit.R;
import com.example.atlit.Utils.ApiClient;
import com.example.atlit.Utils.ApiInterface;
import com.example.atlit.Utils.Constants;
import com.example.atlit.Utils.LocationService;
import com.example.atlit.Utils.Loginsharedpreference;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BalkePelatihActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private Loginsharedpreference loginsharedpreference;
    private final static String TAG = BalkePelatihActivity.class.getSimpleName();
    private Button btnData;

    private ApiInterface mApiInterface;
    private Gson gson;
    private ProgressBar progress;
    private LinearLayout error_layout;
    private TextView error_txt_cause;
    private Button error_btn_retry;
    private RecyclerView rvBalke;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balke_pelatih);
        initToolbar();

        rvBalke = findViewById(R.id.rv_balke_pelatih);
        btnData = findViewById(R.id.btnData);
        progress = findViewById(R.id.progress);
        error_btn_retry = findViewById(R.id.error_btn_retry);
        error_layout = findViewById(R.id.error_layout);
        error_txt_cause = findViewById(R.id.error_txt_cause);

        mApiInterface = ApiClient.getClient().create(ApiInterface.class);
        gson = new Gson();
        hideProgress(1);

        loginsharedpreference = new Loginsharedpreference(this);
        error_btn_retry.setOnClickListener(view -> hideProgress(1));


        btnData.setOnClickListener(v -> intentToBalkeData());

        hideProgress(1);
        load();
        error_btn_retry.setOnClickListener(v -> {
            load();
        });
    }

    public void load() {
        hideProgress(1);
        mApiInterface.getListData(loginsharedpreference.getUserLogin().getCabor()).enqueue(new Callback<List<BalkeGet>>() {
            @Override
            public void onResponse(Call<List<BalkeGet>> call, Response<List<BalkeGet>> response) {
                if (response.isSuccessful()) {
                    if (response.body().isEmpty()) {
                        hideProgress(2);
                        error_txt_cause.setText("Data balke tidak tersedia");
                    } else {
                        hideProgress(3);
                        BalkeListAdapter balkeListAdapter = new BalkeListAdapter(response.body(), BalkePelatihActivity.this);
                        rvBalke.setAdapter(balkeListAdapter);
                    }
                } else {
                    hideProgress(2);
                    error_txt_cause.setText(response.message());
                }
            }

            @Override
            public void onFailure(Call<List<BalkeGet>> call, Throwable t) {
                hideProgress(2);
                error_txt_cause.setText(t.getMessage());
            }
        });
    }

    private void initToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.balke_pelatih));
    }

    private void intentToBalkeData() {
        Intent intent = new Intent(BalkePelatihActivity.this, BalkeDataActivity.class);
        intent.putExtra("form", "pelatih");
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    @Override
    public void onBackPressed() {
        intentToProgramTest();
    }

    private void intentToProgramTest() {
        Intent intent = new Intent(BalkePelatihActivity.this, ProgramTestActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private void hideProgress(int value) {
        if (value == 1) {
            progress.setVisibility(View.VISIBLE);
            error_layout.setVisibility(View.GONE);
            rvBalke.setVisibility(View.GONE);
        } else if (value == 2) {
            progress.setVisibility(View.GONE);
            error_layout.setVisibility(View.VISIBLE);
            rvBalke.setVisibility(View.GONE);
        } else {
            progress.setVisibility(View.GONE);
            error_layout.setVisibility(View.GONE);
            rvBalke.setVisibility(View.VISIBLE);
        }
    }
}