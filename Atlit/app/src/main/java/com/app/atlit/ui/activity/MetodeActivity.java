package com.app.atlit.ui.activity;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.app.atlit.R;

public class MetodeActivity extends AppCompatActivity {

    private LinearLayout llvo2max1,llvo2max2;
    private Button btnCooper, btnBalke, btnBeep, btnPrev, btnNext;
    private Toolbar toolbar;
    private int count = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metode);
        initToolbar();
        llvo2max1 = findViewById(R.id.llvo2max1);
        llvo2max2 = findViewById(R.id.llvo2max2);
        btnCooper = findViewById(R.id.btnCooper);
        btnBeep = findViewById(R.id.btnBeep);
        btnBalke = findViewById(R.id.btnBalke);
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

        btnBeep.setOnClickListener(view -> {
            Intent intent = new Intent(MetodeActivity.this,BeepMetodeActivity.class );
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });

        btnBalke.setOnClickListener(view -> {
            Intent intent = new Intent(MetodeActivity.this, BalkeMetodeActivity.class);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });

        btnCooper.setOnClickListener(view -> {
            Intent intent = new Intent(MetodeActivity.this, CooperActivity.class);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });
    }

    private void setForm(){
        if(count==1) {
            llvo2max1.setVisibility(View.VISIBLE);
            llvo2max2.setVisibility(View.GONE);
            btnPrev.setVisibility(View.INVISIBLE);
            btnNext.setVisibility(View.VISIBLE);
        } else {
            llvo2max1.setVisibility(View.GONE);
            llvo2max2.setVisibility(View.VISIBLE);
            btnPrev.setVisibility(View.VISIBLE);
            btnNext.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(MetodeActivity.this, BerandaActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private void initToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Metode");
    }
}
