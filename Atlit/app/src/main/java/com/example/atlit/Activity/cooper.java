package com.example.atlit.Activity;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.atlit.R;

public class cooper extends AppCompatActivity {

    private Toolbar toolbar;
    private LinearLayout llcooper1, llcooper2, llcooper3, llcooper4;
    private Button btnPrev, btnNext;
    private int count = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cooper);
        btnPrev = findViewById(R.id.btnPrev);
        btnNext = findViewById(R.id.btnNext);
        llcooper1 = findViewById(R.id.llcooper1);
        llcooper2 = findViewById(R.id.llcooper2);
        llcooper3 = findViewById(R.id.llcooper3);
        llcooper4 = findViewById(R.id.llcooper4);
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
        Intent intent = new Intent(cooper.this, MetodeActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private void setForm() {
        if (count == 1) {
            llcooper1.setVisibility(View.VISIBLE);
            llcooper2.setVisibility(View.GONE);
            llcooper3.setVisibility(View.GONE);
            llcooper4.setVisibility(View.GONE);
            btnPrev.setVisibility(View.INVISIBLE);
            btnNext.setVisibility(View.VISIBLE);
        } else if(count==2) {
            llcooper1.setVisibility(View.GONE);
            llcooper2.setVisibility(View.VISIBLE);
            llcooper3.setVisibility(View.GONE);
            llcooper4.setVisibility(View.GONE);
            btnPrev.setVisibility(View.VISIBLE);
            btnNext.setVisibility(View.VISIBLE);
        } else if(count==3) {
            llcooper1.setVisibility(View.GONE);
            llcooper2.setVisibility(View.GONE);
            llcooper3.setVisibility(View.VISIBLE);
            llcooper4.setVisibility(View.GONE);
            btnPrev.setVisibility(View.VISIBLE);
            btnNext.setVisibility(View.VISIBLE);
        } else {
            llcooper1.setVisibility(View.GONE);
            llcooper2.setVisibility(View.GONE);
            llcooper3.setVisibility(View.GONE);
            llcooper4.setVisibility(View.VISIBLE);
            btnPrev.setVisibility(View.VISIBLE);
            btnNext.setVisibility(View.INVISIBLE);
        }
    }

    private void initToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Cooper");
    }
}
