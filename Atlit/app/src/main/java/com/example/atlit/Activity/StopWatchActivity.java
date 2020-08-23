package com.example.atlit.Activity;

import android.Manifest;
import android.app.ActivityManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.atlit.R;
import com.example.atlit.Utils.Constants;
import com.example.atlit.Utils.LocationService;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.util.Timer;
import java.util.TimerTask;

public class StopWatchActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private boolean isStart = false;

    private int ms = 0;
    private TextView tvTime, tvDistance;
    private Timer timer;
    private Button btnStart;
    private double distance = 0;
    private boolean isLocationChanged = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stop_watch);

        tvTime = findViewById(R.id.tv_timer_stop_watch);
        tvDistance = findViewById(R.id.tv_distance_stop_watch);
        btnStart = findViewById(R.id.btnProses);
        timer = new Timer();

        if (Constants.Key_Method.equals("balke")) {
            ms = 15 * 60 * 60;
            initToolbar("Balke");
        } else {
            initToolbar("Cooper");
        }

        final TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if (isStart) {
                    runOnUiThread(() -> {
                        if (isStart) {
                            if (Constants.Key_Method.equals("balke") && ms > 0) {
                                ms -= 1;
                                long s = ms % 60;
                                long m = ms / 60 % 60;
                                long h = ms / 60 / 60;

                                String seconds = String.valueOf(s).length() == 1 ? "0" + s : String.valueOf(s);
                                String minutes = String.valueOf(m).length() == 1 ? "0" + m : String.valueOf(m);
                                String hours = String.valueOf(h).length() == 1 ? "0" + h : String.valueOf(h);
                                String time = hours + ":" + minutes + ":" + seconds;
                                tvTime.setText(time);
                            } else if (Constants.Key_Method.equals("cooper")) {
                                ms += 1;
                                long s = ms % 60;
                                long m = ms / 60 % 60;
                                long h = ms / 60 / 60;

                                String seconds = String.valueOf(s).length() == 1 ? "0" + s : String.valueOf(s);
                                String minutes = String.valueOf(m).length() == 1 ? "0" + m : String.valueOf(m);
                                String hours = String.valueOf(h).length() == 1 ? "0" + h : String.valueOf(h);
                                String time = hours + ":" + minutes + ":" + seconds;
                                tvTime.setText(time);
                            }
                            double d = Constants.distance * (double) 1000;
                            double d2 = (double) Math.round(d * 100) / 100;
                            if (Constants.Key_Method.equals("balke") && ms <= 0) {
                                Intent intent = new Intent(StopWatchActivity.this, BalkeAtlitActivity.class);
                                intent.putExtra(BalkeAtlitActivity.Distance_Key, distance);
                                startActivity(intent);
                                stopLocationService();
                                timer.cancel();
                                timer.purge();
                            } else if (Constants.Key_Method.equals("cooper") && d2 >= 2400) {
                                Intent intent = new Intent(StopWatchActivity.this, CooperAtlitActivity.class);
                                intent.putExtra(CooperAtlitActivity.Time_Key, ms);
                                startActivity(intent);
                                stopLocationService();
                                timer.cancel();
                                timer.purge();
                            }
                        }

//                        if (isLocationServiceRunning()) {
                        if (isLocationChanged) {
                            double d = Constants.distance * (double) 1000;
                            double d2 = (double) Math.round(d * 100) / 100;
                            tvDistance.setText(d2 + "m");
                        }
                    });
                }
            }
        };

        btnStart.setOnClickListener(view -> {
            if (isStart) {
                if (Constants.Key_Method.equals("balke")) {
                    isStart = false;

                    timer.cancel();
                    timer.purge();

                    btnStart.setText("Start");

                    Intent intent = new Intent(StopWatchActivity.this, BalkeAtlitActivity.class);
                    double d = Constants.distance * (double) 1000;
                    distance = (double) Math.round(d * 100) / 100;
                    intent.putExtra(BalkeAtlitActivity.Distance_Key, distance);
                    startActivity(intent);
                    stopLocationService();
                } else {
                    double d = Constants.distance * (double) 1000;
                    double d2 = (double) Math.round(d * 100) / 100;
                    if (Constants.Key_Method.equals("cooper") && d2 < 2400) {
                        Toast.makeText(this, "Jarak tempuh belum mencapai 2,4KM", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    isStart = false;

                    timer.cancel();
                    timer.purge();

                    btnStart.setText("Start");

                    Intent intent = new Intent(StopWatchActivity.this, CooperAtlitActivity.class);
                    intent.putExtra(CooperAtlitActivity.Time_Key, ms);
                    startActivity(intent);
                }
            } else {
                startLocationService();

                isStart = true;

                timer = new Timer();
                timer.scheduleAtFixedRate(task, 0, 10);
                btnStart.setText("Finish");
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopLocationService();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(this, ProgramTestActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private void initToolbar(String title) {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(title);

        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    private void startLocationService() {
        if (!isLocationChanged) {
            Intent intent = new Intent(getApplicationContext(), LocationService.class);
            startService(intent);
            isLocationChanged = true;
        }
    }

    private void stopLocationService() {
        if (isLocationChanged) {
            Constants.distance = 0;
            isLocationChanged = false;

            Intent intent = new Intent(getApplicationContext(), LocationService.class);
            stopService(intent);
        }
    }
}