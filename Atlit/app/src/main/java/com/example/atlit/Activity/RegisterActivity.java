package com.example.atlit.Activity;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.example.atlit.R;

public class RegisterActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private Button btnDaftarAtlit, btnDaftarPelatih;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initToolbar();
        btnDaftarAtlit = findViewById(R.id.btnAtlit);
        btnDaftarPelatih = findViewById(R.id.btnPelatih);
        btnDaftarPelatih.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentToDaftarPelatih();
            }
        });
        btnDaftarAtlit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentToDaftarAtlit();
            }
        });
    }

    private void initToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.daftar));

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void intentToLogin(){
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private void intentToDaftarAtlit(){
        Intent intent = new Intent(RegisterActivity.this,RegisterAtlitActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    private void intentToDaftarPelatih(){
        Intent intent = new Intent(RegisterActivity.this,RegisterPelatihActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    @Override
    public void onBackPressed() {
        intentToLogin();
    }
}
