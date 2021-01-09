package com.app.atlit.ui.activity;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.app.atlit.R;

public class KebugaranActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private LinearLayout llKebugaran1, llKebugaran2;
    private Button btnPrev, btnNext;
    private int count = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kebugaran);
        initToolbar();
        llKebugaran1 = findViewById(R.id.llKebugaran1);
        llKebugaran2 = findViewById(R.id.llKebugaran2);
        btnPrev = findViewById(R.id.btnPrev);
        btnNext = findViewById(R.id.btnNext);
        setForm();
        btnPrev.setOnClickListener(view -> {
            count -= 1;
            setForm();
        });
        btnNext.setOnClickListener(view -> {
            count += 1;
            setForm();
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(KebugaranActivity.this, BerandaActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private void setForm(){
        if(count==1) {
            llKebugaran1.setVisibility(View.VISIBLE);
            llKebugaran2.setVisibility(View.GONE);
            btnPrev.setVisibility(View.INVISIBLE);
            btnNext.setVisibility(View.VISIBLE);
        } else {
            llKebugaran1.setVisibility(View.GONE);
            llKebugaran2.setVisibility(View.VISIBLE);
            btnPrev.setVisibility(View.VISIBLE);
            btnNext.setVisibility(View.INVISIBLE);
        }
    }

    private void initToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Kebugaran");
    }
}
