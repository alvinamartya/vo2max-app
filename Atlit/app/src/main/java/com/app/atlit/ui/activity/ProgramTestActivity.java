package com.app.atlit.ui.activity;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.app.atlit.R;
import com.app.atlit.utils.LoginSharedPreference;

public class ProgramTestActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private Button btnBalke, btnBeep, btnCooper;
    private LoginSharedPreference loginsharedpreference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program_test);
        initToolbar();
        loginsharedpreference = new LoginSharedPreference(this);
        btnBalke = findViewById(R.id.btnBalke);
        btnBeep = findViewById(R.id.btnBeep);
        btnCooper = findViewById(R.id.btnCooper);

        btnCooper.setOnClickListener(v -> {
            if(loginsharedpreference.getUserLogin().getAkses().equals("pelatih")) {
                intentToCooperPelatih();
            } else {
                intentToCooperAtlit();
            }
        });
        btnBalke.setOnClickListener(v -> {
            if(loginsharedpreference.getUserLogin().getAkses().equals("pelatih")) {
                intentToBalkePelatih();
            } else {
                intentToBalkeAtlit();
            }
        });
        btnBeep.setOnClickListener(v -> intentToBeep());
    }

    private void initToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.program_test));
    }

    @Override
    public void onBackPressed() {
        backTOBeranda();
    }

    private void backTOBeranda() {
        Intent intent = new Intent(ProgramTestActivity.this,BerandaActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private void intentToBeep() {
        Intent intent = new Intent(ProgramTestActivity.this,BeepActivity.class);
        startActivity(intent);
        finish();

        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    private void intentToBalkeAtlit() {
        Intent intent = new Intent(ProgramTestActivity.this,StopWatchActivity.class);
        intent.putExtra(StopWatchActivity.KEY_METHOD, "balke");
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    private void intentToBalkePelatih() {
        Intent intent = new Intent(ProgramTestActivity.this,BalkePelatihActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    private void intentToCooperAtlit() {
        Intent intent = new Intent(ProgramTestActivity.this,StopWatchActivity.class);
        intent.putExtra(StopWatchActivity.KEY_METHOD, "cooper");
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    private void intentToCooperPelatih() {
        Intent intent = new Intent(ProgramTestActivity.this,CooperPelatihActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}
