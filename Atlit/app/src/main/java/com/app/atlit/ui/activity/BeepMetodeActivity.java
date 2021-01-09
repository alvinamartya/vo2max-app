package com.app.atlit.ui.activity;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.app.atlit.R;

public class BeepMetodeActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private int count = 1;
    private Button btnPrev, btnNext;
    private LinearLayout llbeep1, llbeep2, llbeep3, llbeep4,llbeep5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beep_metode);
        btnPrev = findViewById(R.id.btnPrev);
        btnNext = findViewById(R.id.btnNext);
        llbeep1 = findViewById(R.id.llbeep1);
        llbeep2 = findViewById(R.id.llbeep2);
        llbeep3 = findViewById(R.id.llbeep3);
        llbeep4 = findViewById(R.id.llbeep4);
        llbeep5 = findViewById(R.id.llbeep5);
        initToolbar();
        setForm();
        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count -= 1;
                setForm();
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count += 1;
                setForm();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(BeepMetodeActivity.this, MetodeActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private void setForm() {
        if (count == 1) {
            llbeep1.setVisibility(View.VISIBLE);
            llbeep2.setVisibility(View.GONE);
            llbeep3.setVisibility(View.GONE);
            llbeep4.setVisibility(View.GONE);
            llbeep5.setVisibility(View.GONE);
            btnPrev.setVisibility(View.INVISIBLE);
            btnNext.setVisibility(View.VISIBLE);
        } else if(count==2) {
            llbeep1.setVisibility(View.GONE);
            llbeep2.setVisibility(View.VISIBLE);
            llbeep3.setVisibility(View.GONE);
            llbeep4.setVisibility(View.GONE);
            llbeep5.setVisibility(View.GONE);
            btnPrev.setVisibility(View.VISIBLE);
            btnNext.setVisibility(View.VISIBLE);
        } else if(count==3) {
            llbeep1.setVisibility(View.GONE);
            llbeep2.setVisibility(View.GONE);
            llbeep3.setVisibility(View.VISIBLE);
            llbeep4.setVisibility(View.GONE);
            llbeep5.setVisibility(View.GONE);
            btnPrev.setVisibility(View.VISIBLE);
            btnNext.setVisibility(View.VISIBLE);
        } else if(count==4) {
            llbeep1.setVisibility(View.GONE);
            llbeep2.setVisibility(View.GONE);
            llbeep3.setVisibility(View.GONE);
            llbeep4.setVisibility(View.VISIBLE);
            llbeep5.setVisibility(View.GONE);
            btnPrev.setVisibility(View.VISIBLE);
            btnNext.setVisibility(View.VISIBLE);
        } else {
            llbeep1.setVisibility(View.GONE);
            llbeep2.setVisibility(View.GONE);
            llbeep3.setVisibility(View.GONE);
            llbeep4.setVisibility(View.GONE);
            llbeep5.setVisibility(View.VISIBLE);
            btnPrev.setVisibility(View.VISIBLE);
            btnNext.setVisibility(View.INVISIBLE);
        }
    }

    private void initToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Beep Test");
    }
}
