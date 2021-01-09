package com.app.atlit.ui.activity;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.app.atlit.R;
import com.app.atlit.utils.LoginSharedPreference;

public class PengaturanActivity extends AppCompatActivity {

    private ToggleButton tbAudio, tbSaveData;
    private LoginSharedPreference loginsharedpreference;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengaturan);
        tbAudio = findViewById(R.id.tbAudio);
        tbSaveData = findViewById(R.id.tbSaveData);
        loginsharedpreference = new LoginSharedPreference(this);
        initToolbar();

        tbAudio.setChecked(loginsharedpreference.getAcvieAudio());
        tbSaveData.setChecked(loginsharedpreference.getActiveSave());

        tbAudio.setOnCheckedChangeListener((buttonView, isChecked) -> loginsharedpreference.setActiveAudio(isChecked));

        tbSaveData.setOnCheckedChangeListener((buttonView, isChecked) -> loginsharedpreference.setActiveSave(isChecked));
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
