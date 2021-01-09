package com.app.atlit.ui.activity;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.app.atlit.ui.adapter.BalkeListAdapter;
import com.app.atlit.model.pojo.BalkeGetPojo;
import com.app.atlit.R;
import com.app.atlit.utils.api.ApiClient;
import com.app.atlit.utils.api.ApiInterface;
import com.app.atlit.utils.LoginSharedPreference;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BalkePelatihActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private LoginSharedPreference loginsharedpreference;
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

        loginsharedpreference = new LoginSharedPreference(this);
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
        mApiInterface.getListData(loginsharedpreference.getUserLogin().getCabor()).enqueue(new Callback<List<BalkeGetPojo>>() {
            @Override
            public void onResponse(Call<List<BalkeGetPojo>> call, Response<List<BalkeGetPojo>> response) {
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
            public void onFailure(Call<List<BalkeGetPojo>> call, Throwable t) {
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