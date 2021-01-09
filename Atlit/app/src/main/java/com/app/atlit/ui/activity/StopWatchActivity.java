package com.app.atlit.ui.activity;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;

import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.app.atlit.R;
import com.app.atlit.utils.Constants;
import com.app.atlit.utils.LocationService;
import com.app.atlit.utils.MySingleton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
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
    private final DecimalFormat decimalFormat = new DecimalFormat("#.##");

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
            ms = 15 * 60 * 60 * 100;
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
                                setTime(ms);
                            } else if (keyMethod.equals("cooper")) {
                                ms += 1;
                                setTime(ms);
                            }

                            if(Constants.id != null) {
                                locDbRef.child(Constants.id).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if (keyMethod.equals("balke") && ms <= 0) {
                                            sendBalkeResult(getDistance(dataSnapshot));
                                        } else if (keyMethod.equals("cooper") && getDistance(dataSnapshot) >= 2400) {
                                            sendCooperResult(ms);
                                        }

                                        if (isLocationChanged) {
                                            tvDistance.setText(getDistance(dataSnapshot) + "m");
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
                                stopStartButton(getDistance(dataSnapshot), "balke");
                            } else {
                                if (keyMethod.equals("cooper") && getDistance(dataSnapshot) < 2400) {
                                    Toast.makeText(StopWatchActivity.this, "Jarak tempuh belum mencapai 2,4KM", Toast.LENGTH_SHORT).show();
                                    return;
                                }

                                stopStartButton(ms, "cooper");
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                } else {
                    if (keyMethod.equals("balke")) {
                        stopStartButton(0, "balke");
                    } else {
                        stopStartButton(ms, "cooper");
                    }
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

    private void stopStartButton(double x, String mode) {
        isStart = false;
        btnStart.setText("Start");

        if(mode.equals("balke")) {
            sendBalkeResult(x);
        } else {
            sendCooperResult(x);
        }
    }

    private void setTime(int msec) {
        long sec = msec / 100;
        long s = sec % 60;
        long m = sec / 60 % 60;
        long h = sec / 60 / 60;

        String seconds = String.valueOf(s).length() == 1 ? "0" + s : String.valueOf(s);
        String minutes = String.valueOf(m).length() == 1 ? "0" + m : String.valueOf(m);
        String hours = String.valueOf(h).length() == 1 ? "0" + h : String.valueOf(h);
        String time = hours + ":" + minutes + ":" + seconds;
        tvTime.setText(time);
    }

    private double getDistance(DataSnapshot dataSnapshot) {
        double d = Double.parseDouble(dataSnapshot.child("distance").getValue().toString()) * 1000;
        BigDecimal bigDecimal = new BigDecimal(d).setScale(0, RoundingMode.CEILING);
        return  bigDecimal.doubleValue();
    }

    private void sendBalkeResult(double d) {
        Intent intent = new Intent(StopWatchActivity.this, BalkeAtlitActivity.class);
        intent.putExtra(BalkeAtlitActivity.Distance_Key, d);
        startActivity(intent);
        stopLocationService();
        timer.cancel();
        timer.purge();
    }

    private void sendCooperResult(double msec) {
        Intent intent = new Intent(StopWatchActivity.this, CooperAtlitActivity.class);
        intent.putExtra(CooperAtlitActivity.Time_Key, msec);
        startActivity(intent);
        stopLocationService();
        timer.cancel();
        timer.purge();
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