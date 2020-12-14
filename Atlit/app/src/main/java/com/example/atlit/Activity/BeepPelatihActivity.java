package com.example.atlit.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.atlit.Adapter.BalkeListAdapter;
import com.example.atlit.Adapter.BeepListAdapter;
import com.example.atlit.Model.BalkeGet;
import com.example.atlit.Model.Beep;
import com.example.atlit.Model.BeepGet;
import com.example.atlit.R;
import com.example.atlit.Utils.ApiClient;
import com.example.atlit.Utils.ApiInterface;
import com.example.atlit.Utils.Loginsharedpreference;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BeepPelatihActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private Loginsharedpreference loginsharedpreference;
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

        loginsharedpreference = new Loginsharedpreference(this);
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
        mApiInterface.getListDataBeep(loginsharedpreference.getUserLogin().getCabor()).enqueue(new Callback<List<BeepGet>>() {
            @Override
            public void onResponse(Call<List<BeepGet>> call, Response<List<BeepGet>> response) {
                if (response.isSuccessful()) {
                    if (response.body().isEmpty()) {
                        hideProgress(2);
                        error_txt_cause.setText("Data balke tidak tersedia");
                    } else {
                        hideProgress(3);
                        Log.e("test", new Gson().toJson(response.body()));
                        BeepListAdapter beepListAdapter = new BeepListAdapter(response.body(), BeepPelatihActivity.this);
                        rvBeep.setAdapter(beepListAdapter);
                    }
                } else {
                    hideProgress(2);
                    error_txt_cause.setText(response.message());
                }
            }

            @Override
            public void onFailure(Call<List<BeepGet>> call, Throwable t) {
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