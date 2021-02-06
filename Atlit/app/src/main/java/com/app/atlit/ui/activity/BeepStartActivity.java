package com.app.atlit.ui.activity;

import android.content.Intent;
import android.media.MediaPlayer;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.atlit.model.pojo.BeepTablePojo;
import com.app.atlit.R;
import com.app.atlit.utils.GameView;
import com.app.atlit.utils.LoginSharedPreference;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class BeepStartActivity extends AppCompatActivity {

    private final static String TAG = BeepStartActivity.class.getSimpleName();
    private Toolbar toolbar;
    private LoginSharedPreference loginsharedpreference;
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
    private final ArrayList<Float> seconds = new ArrayList<>();
    private final ArrayList<Integer> changeShuttles = new ArrayList<>();
    private final ArrayList<BeepTablePojo> beepTablePojo = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beep_start);
        loginsharedpreference = new LoginSharedPreference(this);
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
            beepTablePojo.add(new BeepTablePojo(1, 7, 9f));
            beepTablePojo.add(new BeepTablePojo(2, 8, 8f));
            beepTablePojo.add(new BeepTablePojo(3, 8, 7.5f));
            beepTablePojo.add(new BeepTablePojo(4, 9, 7.33f));
            beepTablePojo.add(new BeepTablePojo(5, 10, 6f));
            beepTablePojo.add(new BeepTablePojo(6, 10, 6.6f));
            beepTablePojo.add(new BeepTablePojo(7, 10, 6.3f));
            beepTablePojo.add(new BeepTablePojo(8, 10, 6.6f));
            beepTablePojo.add(new BeepTablePojo(9, 11, 5.82f));
            beepTablePojo.add(new BeepTablePojo(10, 11, 5.55f));
            beepTablePojo.add(new BeepTablePojo(11, 11, 5.73f));
            beepTablePojo.add(new BeepTablePojo(12, 12, 5.17f));
            beepTablePojo.add(new BeepTablePojo(13, 12, 5.42f));
            beepTablePojo.add(new BeepTablePojo(14, 13, 4.77f));
            beepTablePojo.add(new BeepTablePojo(15, 13, 4.69f));
            beepTablePojo.add(new BeepTablePojo(16, 13, 4.85f));
            beepTablePojo.add(new BeepTablePojo(17, 14, 4.36f));
            beepTablePojo.add(new BeepTablePojo(18, 14, 4.57f));
            beepTablePojo.add(new BeepTablePojo(19, 15, 4.13f));
            beepTablePojo.add(new BeepTablePojo(20, 15, 4.27f));
            beepTablePojo.add(new BeepTablePojo(21, 16, 3.94f));

            int shut = 0;
            for (BeepTablePojo x : beepTablePojo) {
                for (int i = 0; i < x.getShuttles(); i++) {
                    shut++;
                    seconds.add(x.getSecondsPerShuttle());
                    if(x.getLevel() > 1 && i == 0) {
                        changeShuttles.add(shut - 1);
                    }
                }
            }
        } else {
            beepTablePojo.add(new BeepTablePojo(1, 7, 9f));
            beepTablePojo.add(new BeepTablePojo(2, 8, 8.47f));
            beepTablePojo.add(new BeepTablePojo(3, 8, 8f));
            beepTablePojo.add(new BeepTablePojo(4, 9, 7.58f));
            beepTablePojo.add(new BeepTablePojo(5, 10, 7.20f));
            beepTablePojo.add(new BeepTablePojo(6, 10, 6.86f));
            beepTablePojo.add(new BeepTablePojo(7, 10, 6.55f));
            beepTablePojo.add(new BeepTablePojo(8, 10, 6.26f));
            beepTablePojo.add(new BeepTablePojo(9, 11, 6f));
            beepTablePojo.add(new BeepTablePojo(10, 11, 5.76f));
            beepTablePojo.add(new BeepTablePojo(11, 11, 5.54f));
            beepTablePojo.add(new BeepTablePojo(12, 12, 5.33f));
            beepTablePojo.add(new BeepTablePojo(13, 12, 5.14f));
            beepTablePojo.add(new BeepTablePojo(14, 13, 4.97f));
            beepTablePojo.add(new BeepTablePojo(15, 13, 4.80f));
            beepTablePojo.add(new BeepTablePojo(16, 13, 4.65f));
            beepTablePojo.add(new BeepTablePojo(17, 14, 4.50f));
            beepTablePojo.add(new BeepTablePojo(18, 14, 4.36f));
            beepTablePojo.add(new BeepTablePojo(19, 15, 4.24f));
            beepTablePojo.add(new BeepTablePojo(20, 15, 4.11f));
            beepTablePojo.add(new BeepTablePojo(21, 16, 4f));

            int shut = 0;
            for (BeepTablePojo x : beepTablePojo) {
                for (int i = 0; i < x.getShuttles(); i++) {
                    shut++;
                    seconds.add(x.getSecondsPerShuttle());
                    if(x.getLevel() > 1 && i == 0) {
                        changeShuttles.add(shut - 1);
                    }
                }
            }
        }

        gameView = new GameView(this, seconds,changeShuttles);
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
                                    long z = Math.round((((double) (20 * 3600) / (1000 * beepTablePojo.get(level).getSecondsPerShuttle())) * 100));
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

                                if (sec > (int) (beepTablePojo.get(level).getSecondsPerShuttle() * 100)) {
                                    l += 1;
                                    sec = 0;

                                    tvTingkatan.setText((level + 1) + "-" + (l + 1));
                                    d += 20;
                                    tvJarak.setText(String.valueOf(d));
                                }

                                if (l >= beepTablePojo.get(level).getShuttles()) {
                                    l = 0;
                                    sec = 0;
                                    level += 1;

                                    tvTingkatan.setText((level + 1) + "-" + (l + 1));
                                    long z = Math.round((((double) (20 * 3600) / (1000 * beepTablePojo.get(level).getSecondsPerShuttle())) * 100));
                                    double z2 = (double) z / (double) 100;
                                    tvKecepatan.setText(String.valueOf(z2));
                                    d += 20;
                                    tvJarak.setText(String.valueOf(d));
                                }

                                if (level >= beepTablePojo.size()) {
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