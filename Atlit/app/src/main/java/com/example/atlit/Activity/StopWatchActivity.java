package com.example.atlit.Activity;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;

import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.atlit.R;
import com.example.atlit.Utils.Constants;
import com.example.atlit.Utils.LocationService;
import com.example.atlit.Utils.MySingleton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class StopWatchActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private boolean isStart = false;

    private int ms = 0;
    private TextView tvTime, tvDistance;
    private Timer timer;
    private Button btnStart;
    private boolean isLocationChanged = false;
    DatabaseReference locDbRef;
    public static String KEY_METHOD = "key_method";
    private String keyMethod = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stop_watch);
        locDbRef = FirebaseDatabase.getInstance().getReference("locations");
        MySingleton.setContext(this);

        keyMethod = getIntent().getStringExtra(KEY_METHOD);
        tvTime = findViewById(R.id.tv_timer_stop_watch);
        tvDistance = findViewById(R.id.tv_distance_stop_watch);
        btnStart = findViewById(R.id.btnProses);
        timer = new Timer();

        if (keyMethod.equals("balke")) {
            ms = 15 * 60;
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
                            if (keyMethod.equals("balke") && ms > 0) {
                                ms -= 1;
                                long s = ms % 60;
                                long m = ms / 60 % 60;
                                long h = ms / 60 / 60;

                                String seconds = String.valueOf(s).length() == 1 ? "0" + s : String.valueOf(s);
                                String minutes = String.valueOf(m).length() == 1 ? "0" + m : String.valueOf(m);
                                String hours = String.valueOf(h).length() == 1 ? "0" + h : String.valueOf(h);
                                String time = hours + ":" + minutes + ":" + seconds;
                                tvTime.setText(time);
                            } else if (keyMethod.equals("cooper")) {
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

                            if(Constants.id != null) {
                                locDbRef.child(Constants.id).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        double d = Double.parseDouble(dataSnapshot.child("distance").getValue().toString());
                                        BigDecimal bd = new BigDecimal(d).setScale(2, RoundingMode.HALF_UP);
                                        double d2 = bd.doubleValue();
                                        if (keyMethod.equals("balke") && ms <= 0) {
                                            Intent intent = new Intent(StopWatchActivity.this, BalkeAtlitActivity.class);
                                            intent.putExtra(BalkeAtlitActivity.Distance_Key, d);
                                            startActivity(intent);
                                            stopLocationService();
                                            timer.cancel();
                                            timer.purge();
                                        } else if (keyMethod.equals("cooper") && d2 >= 2.4) {
                                            Intent intent = new Intent(StopWatchActivity.this, CooperAtlitActivity.class);
                                            intent.putExtra(CooperAtlitActivity.Time_Key, ms);
                                            startActivity(intent);
                                            stopLocationService();
                                            timer.cancel();
                                            timer.purge();
                                        }

                                        if (isLocationChanged) {
                                            tvDistance.setText(d2 + "km");
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                            }
                        }
                    });
                }
            }
        };

        btnStart.setOnClickListener(view -> {
            if (isStart) {
                if(Constants.id != null) {
                    locDbRef.child(Constants.id).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (keyMethod.equals("balke")) {
                                isStart = false;

                                timer.cancel();
                                timer.purge();

                                btnStart.setText("Start");

                                Intent intent = new Intent(StopWatchActivity.this, BalkeAtlitActivity.class);
                                double d = Double.parseDouble(dataSnapshot.child("distance").getValue().toString());
                                BigDecimal bd = new BigDecimal(d).setScale(2, RoundingMode.HALF_UP);
                                double d2 = bd.doubleValue();
                                intent.putExtra(BalkeAtlitActivity.Distance_Key, d2);
                                startActivity(intent);
                                stopLocationService();
                            } else {
                                double d = Double.parseDouble(dataSnapshot.child("distance").getValue().toString());
                                BigDecimal bd = new BigDecimal(d).setScale(2, RoundingMode.HALF_UP);
                                double d2 = bd.doubleValue();
                                if (keyMethod.equals("cooper") && d2 < 2.4) {
                                    Toast.makeText(StopWatchActivity.this, "Jarak tempuh belum mencapai 2,4KM", Toast.LENGTH_SHORT).show();
                                    return;
                                }

                                isStart = false;
                                timer.cancel();
                                timer.purge();

                                btnStart.setText("Start");
                                Intent intent = new Intent(StopWatchActivity.this, CooperAtlitActivity.class);
                                intent.putExtra(CooperAtlitActivity.Time_Key, ms);
                                startActivity(intent);
                                stopLocationService();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                } else {
                    if (keyMethod.equals("balke")) {
                        isStart = false;

                        timer.cancel();
                        timer.purge();

                        btnStart.setText("Start");

                        Intent intent = new Intent(StopWatchActivity.this, BalkeAtlitActivity.class);
                        intent.putExtra(BalkeAtlitActivity.Distance_Key, 0);
                        startActivity(intent);
                        stopLocationService();
                    } else {
                        isStart = false;
                        timer.cancel();
                        timer.purge();

                        btnStart.setText("Start");
                        Intent intent = new Intent(StopWatchActivity.this, CooperAtlitActivity.class);
                        intent.putExtra(CooperAtlitActivity.Time_Key, ms);
                        startActivity(intent);
                        stopLocationService();
                    }
                }
            } else {
                startLocationService();

                isStart = true;

                timer = new Timer();
                timer.scheduleAtFixedRate(task, 0, 1000);
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
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(title);

        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    private void startLocationService() {
        if (!isLocationChanged) {
            Constants.id = null;
            Intent intent = new Intent(getApplicationContext(), LocationService.class);
            startService(intent);
            isLocationChanged = true;
        }
    }

    private void stopLocationService() {
        if (isLocationChanged) {
            isLocationChanged = false;
            Intent intent = new Intent(getApplicationContext(), LocationService.class);
            stopService(intent);
        }
    }
}