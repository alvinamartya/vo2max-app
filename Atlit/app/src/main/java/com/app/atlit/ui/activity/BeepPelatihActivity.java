package com.app.atlit.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.app.atlit.ui.adapter.BeepListAdapter;
import com.app.atlit.model.pojo.BeepGetPojo;
import com.app.atlit.R;
import com.app.atlit.utils.api.ApiClient;
import com.app.atlit.utils.api.ApiInterface;
import com.app.atlit.utils.LoginSharedPreference;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BeepPelatihActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private LoginSharedPreference loginsharedpreference;
    private final static String TAG = BalkePelatihActivity.class.getSimpleName();

    private ApiInterface mApiInterface;
    private Gson gson;
    private ProgressBar progress;
    private LinearLayout error_layout;
    private TextView error_txt_cause;
    private Button error_btn_retry,btnData;
    private RecyclerView rvBeep;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beep_pelatih);

        initToolbar();

        rvBeep = findViewById(R.id.rv_beep_pelatih);
        progress = findViewById(R.id.progress);
        error_btn_retry = findViewById(R.id.error_btn_retry);
        error_layout = findViewById(R.id.error_layout);
        error_txt_cause = findViewById(R.id.error_txt_cause);
        btnData = findViewById(R.id.btnData);

        mApiInterface = ApiClient.getClient().create(ApiInterface.class);
        gson = new Gson();
        hideProgress(1);

        loginsharedpreference = new LoginSharedPreference(this);
        error_btn_retry.setOnClickListener(view -> hideProgress(1));

        hideProgress(1);
        load();
        error_btn_retry.setOnClickListener(v -> {
            load();
        });

        btnData.setOnClickListener(v-> {
            Intent intent = new Intent(BeepPelatihActivity.this, BeepDataActivity.class);
            intent.putExtra(BeepDataActivity.KEY_FORM, "pelatih");
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });
    }


    public void load() {
        hideProgress(1);
        mApiInterface.getListDataBeep(loginsharedpreference.getUserLogin().getCabor()).enqueue(new Callback<List<BeepGetPojo>>() {
            @Override
            public void onResponse(Call<List<BeepGetPojo>> call, Response<List<BeepGetPojo>> response) {
                if (response.isSuccessful()) {
                    if (response.body().isEmpty()) {
                        hideProgress(2);
                        error_txt_cause.setText("Data balke tidak tersedia");
                    } else {
                        hideProgress(3);
                        BeepListAdapter beepListAdapter = new BeepListAdapter(response.body(), BeepPelatihActivity.this);
                        rvBeep.setAdapter(beepListAdapter);
                    }
                } else {
                    hideProgress(2);
                    error_txt_cause.setText(response.message());
                }
            }

            @Override
            public void onFailure(Call<List<BeepGetPojo>> call, Throwable t) {
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

    public void onBackPressed() {
        intentToBeep();
    }

    private void intentToBeep() {
        Intent intent = new Intent(BeepPelatihActivity.this, BeepActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private void hideProgress(int value) {
        if (value == 1) {
            progress.setVisibility(View.VISIBLE);
            error_layout.setVisibility(View.GONE);
            rvBeep.setVisibility(View.GONE);
        } else if (value == 2) {
            progress.setVisibility(View.GONE);
            error_layout.setVisibility(View.VISIBLE);
            rvBeep.setVisibility(View.GONE);
        } else {
            progress.setVisibility(View.GONE);
            error_layout.setVisibility(View.GONE);
            rvBeep.setVisibility(View.VISIBLE);
        }
    }
}