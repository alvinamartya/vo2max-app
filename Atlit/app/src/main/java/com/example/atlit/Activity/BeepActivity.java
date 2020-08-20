package com.example.atlit.Activity;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.example.atlit.R;
import com.example.atlit.Utils.Loginsharedpreference;

public class BeepActivity extends AppCompatActivity {

    private Button btnMulaiTest, btnPelari, btnParameter, btnPengaturan, btnResult;
    private Toolbar toolbar;
    private Loginsharedpreference loginsharedpreference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beep);
        btnMulaiTest = findViewById(R.id.btnMulaiTest);
        btnPelari = findViewById(R.id.btnPelari);
        btnParameter = findViewById(R.id.btnParameter);
        btnPengaturan = findViewById(R.id.btnPengaturan);
        btnResult = findViewById(R.id.btnResult);

        loginsharedpreference = new Loginsharedpreference(BeepActivity.this);

        initToolbar();
        btnMulaiTest.setOnClickListener(v -> intentToStart());
        btnPelari.setOnClickListener(v -> intentToPelari());
        btnParameter.setOnClickListener(v -> intentToParameter());
        btnPengaturan.setOnClickListener(v -> intentToPengaturan());
        btnResult.setOnClickListener(v -> intentToPelatih());
        if(!loginsharedpreference.getUserLogin().getAkses().toLowerCase().equals("pelatih")) btnResult.setVisibility(View.GONE);
    }

    private void intentToPelatih() {
        Intent intent = new Intent(BeepActivity.this, BeepPelatihActivity.class);
        startActivity(intent);
        finish();

        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    private void intentToStart() {
        Intent intent = new Intent(BeepActivity.this, BeepStartActivity.class);
        startActivity(intent);
        finish();

        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    private void intentToPelari() {
        Intent intent = new Intent(BeepActivity.this, PelariActivity.class);
        startActivity(intent);
        finish();

        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    private void initToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.beep_test));
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(BeepActivity.this, ProgramTestActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private void intentToParameter() {
        Intent intent = new Intent(BeepActivity.this, ParameterActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    private void intentToPengaturan() {
        Intent intent = new Intent(BeepActivity.this, PengaturanActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}
