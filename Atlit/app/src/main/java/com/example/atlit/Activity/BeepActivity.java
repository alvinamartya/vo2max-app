package com.example.atlit.Activity;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.example.atlit.R;

public class BeepActivity extends AppCompatActivity {

    private Button btnMulaiTest, btnPelari, btnParameter, btnPengaturan;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beep);
        btnMulaiTest = findViewById(R.id.btnMulaiTest);
        btnPelari = findViewById(R.id.btnPelari);
        btnParameter = findViewById(R.id.btnParameter);
        btnPengaturan = findViewById(R.id.btnPengaturan);
        initToolbar();
        btnMulaiTest.setOnClickListener(v -> IntentToStart());
        btnPelari.setOnClickListener(v -> intentToPelari());
        btnParameter.setOnClickListener(v -> intentToParameter());
        btnPengaturan.setOnClickListener(v -> intentToPengaturan());
    }

    private void IntentToStart() {
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
