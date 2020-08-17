package com.example.atlit.Activity;

import android.content.Intent;
import android.media.MediaPlayer;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.atlit.Model.BeepTable;
import com.example.atlit.R;
import com.example.atlit.Utils.GameView;
import com.example.atlit.Utils.Loginsharedpreference;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class BeepStartActivity extends AppCompatActivity {

    private final static String TAG = BeepStartActivity.class.getSimpleName();
    private Toolbar toolbar;
    private Loginsharedpreference loginsharedpreference;
    private TextView tvJarak, tvTingkatan, tvKecepatan, tvWaktu;
    private Button btnStart;
    private ImageView iv;
    private boolean isStart = false;
    private Timer timer;
    private long second = 0, secondUnit = 0;
    private String time = "00:00:00";
    private MediaPlayer mediaPlayer;
    private GameView gameView;
    private FrameLayout frameLayout;
    private boolean isStart2 = false;
    private long sec = 0, l = 0, d = 0;
    private int level = 0;
    private ArrayList<Float> seconds = new ArrayList<>();
    private ArrayList<BeepTable> beepTable = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beep_start);
        loginsharedpreference = new Loginsharedpreference(this);
        initToolbar();

        btnStart = findViewById(R.id.btnStart);
        tvJarak = findViewById(R.id.tvJarak);
        tvTingkatan = findViewById(R.id.tvTingkatan);
        tvKecepatan = findViewById(R.id.tvKecepatan);
        tvWaktu = findViewById(R.id.tvWaktu);
        iv = findViewById(R.id.iv);
        timer = new Timer();
        frameLayout = findViewById(R.id.frameLayout);

        // init data to beep table
        if (loginsharedpreference.getAcvieAudio()) {
            beepTable.add(new BeepTable(1, 7, 9f));
            beepTable.add(new BeepTable(2, 8, 8f));
            beepTable.add(new BeepTable(3, 8, 7.5f));
            beepTable.add(new BeepTable(4, 9, 7.33f));
            beepTable.add(new BeepTable(5, 10, 6f));
            beepTable.add(new BeepTable(6, 10, 6.6f));
            beepTable.add(new BeepTable(7, 10, 6.3f));
            beepTable.add(new BeepTable(8, 10, 6.6f));
            beepTable.add(new BeepTable(9, 11, 5.82f));
            beepTable.add(new BeepTable(10, 11, 5.55f));
            beepTable.add(new BeepTable(11, 11, 5.73f));
            beepTable.add(new BeepTable(12, 12, 5.17f));
            beepTable.add(new BeepTable(13, 12, 5.42f));
            beepTable.add(new BeepTable(14, 13, 4.77f));
            beepTable.add(new BeepTable(15, 13, 4.69f));
            beepTable.add(new BeepTable(16, 13, 4.85f));
            beepTable.add(new BeepTable(17, 14, 4.36f));
            beepTable.add(new BeepTable(18, 14, 4.57f));
            beepTable.add(new BeepTable(19, 15, 4.13f));
            beepTable.add(new BeepTable(20, 15, 4.27f));
            beepTable.add(new BeepTable(21, 16, 3.94f));

            for (BeepTable x : beepTable) {
                for (int i = 0; i < x.getShuttles(); i++) {
                    seconds.add(x.getSecondsPerShuttle());
                }
            }
        } else {
            beepTable.add(new BeepTable(1, 7, 9f));
            beepTable.add(new BeepTable(2, 8, 8.47f));
            beepTable.add(new BeepTable(3, 8, 8f));
            beepTable.add(new BeepTable(4, 9, 7.58f));
            beepTable.add(new BeepTable(5, 10, 7.20f));
            beepTable.add(new BeepTable(6, 10, 6.86f));
            beepTable.add(new BeepTable(7, 10, 6.55f));
            beepTable.add(new BeepTable(8, 10, 6.26f));
            beepTable.add(new BeepTable(9, 11, 6f));
            beepTable.add(new BeepTable(10, 11, 5.76f));
            beepTable.add(new BeepTable(11, 11, 5.54f));
            beepTable.add(new BeepTable(12, 12, 5.33f));
            beepTable.add(new BeepTable(13, 12, 5.14f));
            beepTable.add(new BeepTable(14, 13, 4.97f));
            beepTable.add(new BeepTable(15, 13, 4.80f));
            beepTable.add(new BeepTable(16, 13, 4.65f));
            beepTable.add(new BeepTable(17, 14, 4.50f));
            beepTable.add(new BeepTable(18, 14, 4.36f));
            beepTable.add(new BeepTable(19, 15, 4.24f));
            beepTable.add(new BeepTable(20, 15, 4.11f));
            beepTable.add(new BeepTable(21, 16, 4f));
            for (BeepTable x : beepTable) {
                for (int i = 0; i < x.getShuttles(); i++) {
                    seconds.add(x.getSecondsPerShuttle());
                }
            }
        }

        gameView = new GameView(this, seconds);
        frameLayout.addView(gameView);
        mediaPlayer = MediaPlayer.create(this, R.raw.beeptest);
        btnStart.setOnClickListener(view -> {

            if (loginsharedpreference.getPelari() == null) {
                Toast.makeText(BeepStartActivity.this, "Pastikan anda telah memilih pelari", Toast.LENGTH_SHORT).show();
                return;
            }

            if (isStart) {
                stop();
            } else {
                isStart = true;
                btnStart.setText("Berhenti");
                if (loginsharedpreference.getAcvieAudio())
                    mediaPlayer.start();

                TimerTask task = new TimerTask() {
                    @Override
                    public void run() {
                        if (isStart) {
                            runOnUiThread(() -> {
                                second += 1;
                                if (loginsharedpreference.getAcvieAudio()) {
                                    if (second >= 1300) {
                                        secondUnit += 1;
                                        sec += 1;
                                        if (!isStart2) {
                                            gameView.isRunning();
                                            isStart2 = true;
                                        }
                                    }
                                } else {
                                    secondUnit += 1;
                                    sec += 1;
                                    if (!isStart2) {
                                        gameView.isRunning();
                                        isStart2 = true;
                                    }
                                }

                                if (secondUnit == 100) {
                                    tvTingkatan.setText((level + 1) + "-" + (l + 1));
                                    long z = Math.round((((double) (20 * 3600) / (1000 * beepTable.get(level).getSecondsPerShuttle())) * 100));
                                    double z2 = (double) z / (double) 100;
                                    tvKecepatan.setText(String.valueOf(z2));
                                    d = 20;
                                    tvJarak.setText(String.valueOf(d));
                                }


                                if (secondUnit % 100 == 0) {
                                    long s = (secondUnit / 100) % 60;
                                    long m = (secondUnit / 100) / 60 % 60;
                                    long h = (secondUnit / 100) / 60 / 60;

                                    String seconds = String.valueOf(s).length() == 1 ? "0" + s : String.valueOf(s);
                                    String minutes = String.valueOf(m).length() == 1 ? "0" + m : String.valueOf(m);
                                    String hours = String.valueOf(h).length() == 1 ? "0" + h : String.valueOf(h);
                                    time = hours + ":" + minutes + ":" + seconds;

                                    tvWaktu.setText(time);
                                }

                                if (sec > (int) (beepTable.get(level).getSecondsPerShuttle() * 100)) {
                                    l += 1;
                                    sec = 0;

                                    tvTingkatan.setText((level + 1) + "-" + (l + 1));
                                    d += 20;
                                    tvJarak.setText(String.valueOf(d));
                                }

                                if (l >= beepTable.get(level).getShuttles()) {
                                    l = 0;
                                    sec = 0;
                                    level += 1;

                                    tvTingkatan.setText((level + 1) + "-" + (l + 1));
                                    long z = Math.round((((double) (20 * 3600) / (1000 * beepTable.get(level).getSecondsPerShuttle())) * 100));
                                    double z2 = (double) z / (double) 100;
                                    tvKecepatan.setText(String.valueOf(z2));
                                    d += 20;
                                    tvJarak.setText(String.valueOf(d));
                                }

                                if (level >= beepTable.size()) {
                                    stop();
                                }
                            });
                        } else {
                            timer.cancel();
                            timer.purge();
                        }
                    }
                };

                Timer timer = new Timer();
                timer.scheduleAtFixedRate(task, 0, 10);
            }
        });
    }


    private void stop() {
        gameView.isRunning();
        isStart2 = false;
        isStart = false;
        btnStart.setText("Mulai");
        timer.cancel();
        if (loginsharedpreference.getAcvieAudio())
            mediaPlayer.stop();

        if (loginsharedpreference.getActiveSave()) {
            Intent intent = new Intent(BeepStartActivity.this, BeepAtlitActivity.class);
            if (secondUnit >= 1) {
                intent.putExtra(BeepAtlitActivity.EXTRA_LEVEL, (level + 1));
                intent.putExtra(BeepAtlitActivity.EXTRA_SHUTTLE, (l + 1));
            } else {
                intent.putExtra(BeepAtlitActivity.EXTRA_LEVEL, 0);
                intent.putExtra(BeepAtlitActivity.EXTRA_SHUTTLE, l);
            }
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(BeepStartActivity.this, BeepActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private void initToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Beep Test");
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameView.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        gameView.pause();
    }


}