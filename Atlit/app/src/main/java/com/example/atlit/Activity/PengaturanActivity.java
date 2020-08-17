package com.example.atlit.Activity;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.example.atlit.R;
import com.example.atlit.Utils.Loginsharedpreference;

public class PengaturanActivity extends AppCompatActivity {

    private ToggleButton tbAudio, tbSaveData;
    private Loginsharedpreference loginsharedpreference;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengaturan);
        tbAudio = findViewById(R.id.tbAudio);
        tbSaveData = findViewById(R.id.tbSaveData);
        loginsharedpreference = new Loginsharedpreference(this);
        initToolbar();

        tbAudio.setChecked(loginsharedpreference.getAcvieAudio());
        tbSaveData.setChecked(loginsharedpreference.getActiveSave());

        tbAudio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                loginsharedpreference.setActiveAudio(isChecked);
            }
        });

        tbSaveData.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                loginsharedpreference.setActiveSave(isChecked);
            }
        });
    }

    private void initToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Pengaturan");
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(PengaturanActivity.this, BeepActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
