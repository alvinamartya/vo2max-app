package com.app.atlit.ui.activity;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.app.atlit.R;

public class BalkeMetodeActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private int count = 1;
    private Button btnPrev, btnNext;
    private LinearLayout llbalke1, llbalke2, llbalke3, llbalke4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balke_metode);
        btnPrev = findViewById(R.id.btnPrev);
        btnNext = findViewById(R.id.btnNext);
        llbalke1 = findViewById(R.id.llbalke1);
        llbalke2 = findViewById(R.id.llbalke2);
        llbalke3 = findViewById(R.id.llbalke3);
        llbalke4 = findViewById(R.id.llbalke4);

        initToolbar();
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
        Intent intent = new Intent(BalkeMetodeActivity.this, MetodeActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private void setForm() {
        if (count == 1) {
            llbalke1.setVisibility(View.VISIBLE);
            llbalke2.setVisibility(View.GONE);
            llbalke3.setVisibility(View.GONE);
            llbalke4.setVisibility(View.GONE);
            btnPrev.setVisibility(View.INVISIBLE);
            btnNext.setVisibility(View.VISIBLE);
        } else if(count==2) {
            llbalke1.setVisibility(View.GONE);
            llbalke2.setVisibility(View.VISIBLE);
            llbalke3.setVisibility(View.GONE);
            llbalke4.setVisibility(View.GONE);
            btnPrev.setVisibility(View.VISIBLE);
            btnNext.setVisibility(View.VISIBLE);
        } else if(count==3) {
            llbalke1.setVisibility(View.GONE);
            llbalke2.setVisibility(View.GONE);
            llbalke3.setVisibility(View.VISIBLE);
            llbalke4.setVisibility(View.GONE);
            btnPrev.setVisibility(View.VISIBLE);
            btnNext.setVisibility(View.VISIBLE);
        } else {
            llbalke1.setVisibility(View.GONE);
            llbalke2.setVisibility(View.GONE);
            llbalke3.setVisibility(View.GONE);
            llbalke4.setVisibility(View.VISIBLE);
            btnPrev.setVisibility(View.VISIBLE);
            btnNext.setVisibility(View.INVISIBLE);
        }
    }

    private void initToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Balke");
    }
}
