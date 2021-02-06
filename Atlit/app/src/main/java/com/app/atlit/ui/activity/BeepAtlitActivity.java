package com.app.atlit.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.app.atlit.model.pojo.BeepTablePojo;
import com.app.atlit.ui.dialog.SolusiDialog;
import com.app.atlit.R;
import com.app.atlit.utils.api.ApiClient;
import com.app.atlit.utils.api.ApiInterface;
import com.app.atlit.utils.LoginSharedPreference;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BeepAtlitActivity extends AppCompatActivity {

    private EditText edtBulan, edtMinggu, edtnama, edtLevel, edtShuttle, edtVo2Max, edtTingkatKebugaran, edtUmur;
    private RadioGroup radiogroup;
    private RadioButton rbL, rbP;
    private boolean isProcess = false;
    private LoginSharedPreference loginsharedpreference;
    public final static String EXTRA_LEVEL = "extra_level";
    public final static String EXTRA_SHUTTLE = "extra_shuttle";
    private final static String TAG = BeepActivity.class.getSimpleName();
    private Button btnProses, btnSimpan, btnHapus, btnData, btnSolusi;
    private ProgressBar progress;
    private TextView error_txt_cause;
    private LinearLayout error_layout, data_beep;
    private ApiInterface apiInterface;
    private Toolbar toolbar;
    private final ArrayList<BeepTablePojo> beepTablePojo = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beep_atlit);
        initToolbar();
        edtBulan = findViewById(R.id.edtBulan);
        edtMinggu = findViewById(R.id.edtMinggu);
        edtnama = findViewById(R.id.edtnama);
        edtLevel = findViewById(R.id.edtLevel);
        edtShuttle = findViewById(R.id.edtShuttle);
        edtVo2Max = findViewById(R.id.edtVo2Max);
        edtTingkatKebugaran = findViewById(R.id.edtTingkatKebugaran);
        radiogroup = findViewById(R.id.radiogroup);
        rbL = findViewById(R.id.rbL);
        rbP = findViewById(R.id.rbP);
        edtUmur = findViewById(R.id.edtUmur);
        btnProses = findViewById(R.id.btnProses);
        btnSimpan = findViewById(R.id.btnSimpan);
        btnHapus = findViewById(R.id.btnHapus);
        btnData = findViewById(R.id.btnData);
        btnSolusi = findViewById(R.id.btnSolusi);
        loginsharedpreference = new LoginSharedPreference(this);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        progress = findViewById(R.id.progress);
        error_layout = findViewById(R.id.error_layout);
        data_beep = findViewById(R.id.ll_data_beep);
        error_txt_cause = findViewById(R.id.error_txt_cause);

        if(loginsharedpreference.getUserLogin().getAkses().toLowerCase().equals("pelatih")) {
            edtnama.setEnabled(false);
            rbL.setEnabled(false);
            rbP.setEnabled(false);
            edtUmur.setEnabled(false);
            edtnama.setText(loginsharedpreference.getPelari().getName());
            edtUmur.setText(String.valueOf(loginsharedpreference.getPelari().getUmur()));
            if (loginsharedpreference.getPelari().getJk().equals("Laki-Laki")) rbL.setChecked(true);
            else rbP.setChecked(true);
            hideProgress(3);
        } else {
            edtnama.setEnabled(false);
            rbL.setEnabled(false);
            rbP.setEnabled(false);
            edtUmur.setEnabled(false);
            fillData();
        }
        edtLevel.setText(String.valueOf(getIntent().getIntExtra(EXTRA_LEVEL, 0)));
        edtShuttle.setText(String.valueOf(getIntent().getLongExtra(EXTRA_SHUTTLE, 0)));
        edtLevel.setEnabled(false);
        edtShuttle.setEnabled(false);
        final Calendar cal = Calendar.getInstance();
        edtBulan.setText(String.valueOf(cal.get(Calendar.MONTH)));
        edtMinggu.setText(String.valueOf(cal.get(Calendar.WEEK_OF_MONTH)));

        btnSolusi.setOnClickListener(v -> {
            SolusiDialog dialog = new SolusiDialog();
            dialog.show(getSupportFragmentManager(), "SolusiDialog");
        });

        btnProses.setOnClickListener(view -> {
            if(Integer.parseInt(edtUmur.getText().toString()) < 13) {
                Toast.makeText(BeepAtlitActivity.this, "Umur harus lebih besar atau sama dengan 13", Toast.LENGTH_SHORT).show();
                return;
            }

            isProcess = true;
            int Tb = getShuttle(getIntent().getIntExtra(EXTRA_LEVEL, 0));
            Log.e("shuttle", String.valueOf(Tb));
            double vo2max = 15 + (0.3689295 * Tb) + (-0.000349 * Tb * Tb);
            edtVo2Max.setText(String.valueOf((float)vo2max));
            Log.e(TAG, "onClick: " + loginsharedpreference.getPelari().getJk());
            String tingkat = loginsharedpreference.getPelari().getJk().equals("Laki-Laki") ? tableVo2MaxL(loginsharedpreference.getPelari().getUmur(), vo2max) : tableVo2MaxP(loginsharedpreference.getPelari().getUmur(), vo2max);
            edtTingkatKebugaran.setText(tingkat);
        });

        btnData.setOnClickListener(view -> {
            Intent intent = new Intent(BeepAtlitActivity.this, BeepDataActivity.class);
            intent.putExtra(BeepDataActivity.KEY_FORM, "atlit");
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });

        btnSimpan.setOnClickListener(view -> {
            final ProgressDialog pDialog = new ProgressDialog(BeepAtlitActivity.this);
            pDialog.setTitle(getString(R.string.information));
            pDialog.setMessage(getString(R.string.please_wait));
            pDialog.show();

            if(TextUtils.isEmpty(edtMinggu.getText())) {
                Toast.makeText(BeepAtlitActivity.this, "Pastikan minggu telah diisi", Toast.LENGTH_SHORT).show();
                pDialog.dismiss();
            } else if(TextUtils.isEmpty(edtBulan.getText())) {
                Toast.makeText(BeepAtlitActivity.this, "Pastikan bulan telah diisi", Toast.LENGTH_SHORT).show();
                pDialog.dismiss();
            } else {
                if(isProcess) {
                    String jk = rbL.isChecked() ? "L" : "P";
                    Log.e(TAG, "onClick: " + loginsharedpreference.getPelari().getId());
                    Call<com.app.atlit.model.pojo.retrofit.BeepRetrofit> beepModel = apiInterface.beepPost(
                            Integer.parseInt(edtBulan.getText().toString()),
                            Integer.parseInt(edtMinggu.getText().toString()),
                            getIntent().getIntExtra(EXTRA_LEVEL, 0),
                            getIntent().getLongExtra(EXTRA_SHUTTLE, 0),
                            Float.parseFloat(edtVo2Max.getText().toString()),
                            edtTingkatKebugaran.getText().toString(),
                            loginsharedpreference.getPelari().getId()
                    );

                    beepModel.enqueue(new Callback<com.app.atlit.model.pojo.retrofit.BeepRetrofit>() {
                        @Override
                        public void onResponse(Call<com.app.atlit.model.pojo.retrofit.BeepRetrofit> call, Response<com.app.atlit.model.pojo.retrofit.BeepRetrofit> response) {
                            if(response.isSuccessful()) {
                                if(response.body().getMessage().equals("berhasil menambahkan data")) {
                                    Toast.makeText(BeepAtlitActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                    edtVo2Max.setText("");
                                    edtTingkatKebugaran.setText("");
                                }
                                else
                                    Toast.makeText(BeepAtlitActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                pDialog.dismiss();
                            } else {
                                Toast.makeText(BeepAtlitActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                                pDialog.dismiss();
                            }
                        }

                        @Override
                        public void onFailure(Call<com.app.atlit.model.pojo.retrofit.BeepRetrofit> call, Throwable t) {
                            Log.e(TAG, "onFailure: " + t.getMessage());
                            Toast.makeText(BeepAtlitActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                            pDialog.dismiss();
                        }
                    });

                    if(!loginsharedpreference.getUserLogin().getAkses().equals("atlit")) {
                        Call<com.app.atlit.model.pojo.retrofit.BeepRetrofit> beepModel2 = apiInterface.beepPost(
                                Integer.parseInt(edtBulan.getText().toString()),
                                Integer.parseInt(edtMinggu.getText().toString()),
                                getIntent().getIntExtra(EXTRA_LEVEL, 0),
                                getIntent().getIntExtra(EXTRA_SHUTTLE, 0),
                                Float.parseFloat(edtVo2Max.getText().toString()),
                                edtTingkatKebugaran.getText().toString(),
                                loginsharedpreference.getUserLogin().getId()
                        );

                        beepModel2.enqueue(new Callback<com.app.atlit.model.pojo.retrofit.BeepRetrofit>() {
                            @Override
                            public void onResponse(Call<com.app.atlit.model.pojo.retrofit.BeepRetrofit> call, Response<com.app.atlit.model.pojo.retrofit.BeepRetrofit> response) {
                                if(response.isSuccessful()) {
                                    if(response.body().getMessage().equals("berhasil menambahkan data")) {
                                        Toast.makeText(BeepAtlitActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                        edtVo2Max.setText("");
                                        edtTingkatKebugaran.setText("");
                                    }
                                    else
                                        Toast.makeText(BeepAtlitActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                    pDialog.dismiss();
                                } else {
                                    Toast.makeText(BeepAtlitActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                                    pDialog.dismiss();
                                }
                            }

                            @Override
                            public void onFailure(Call<com.app.atlit.model.pojo.retrofit.BeepRetrofit> call, Throwable t) {
                                Log.e(TAG, "onFailure: " + t.getMessage());
                                Toast.makeText(BeepAtlitActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                                pDialog.dismiss();
                            }
                        });
                    }


                } else {
                    Toast.makeText(BeepAtlitActivity.this, "Pastikan anda telah menginput data", Toast.LENGTH_SHORT).show();
                    pDialog.dismiss();
                }
            }
        });

        btnHapus.setOnClickListener(view -> {
            isProcess= false;
            edtVo2Max.setText("");
            edtTingkatKebugaran.setText("");
        });
    }


    private void fillData() {
        hideProgress(1);
        Call<com.app.atlit.model.pojo.retrofit.AtlitRetrofit> getData = apiInterface.atlitGets(loginsharedpreference.getUserLogin().getId());
        getData.enqueue(new Callback<com.app.atlit.model.pojo.retrofit.AtlitRetrofit>() {
            @Override
            public void onResponse(Call<com.app.atlit.model.pojo.retrofit.AtlitRetrofit> call, Response<com.app.atlit.model.pojo.retrofit.AtlitRetrofit> response) {
                if (response.isSuccessful()) {
                    if (response.body().getMessage().equals("data berhasil ditemukan")) {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        edtnama.setText(response.body().getData().getNama());

                        Calendar cal = Calendar.getInstance();
                        int umur = 0;
                        try {
                            Date birthDate = dateFormat.parse(response.body().getData().getTanggal_lahir());
                            Calendar cal2 = Calendar.getInstance();
                            cal2.setTime(birthDate);
                            umur = cal.get(Calendar.YEAR) - cal2.get(Calendar.YEAR);
                        } catch (ParseException e) {
                            Log.e("Balke Atlit", e.getMessage());
                        }

                        edtUmur.setText(String.valueOf(umur));
                        if (response.body().getData().getJenis_kelamin().equals("Laki-Laki")) rbL.setChecked(true);
                        else rbP.setChecked(true);
                        hideProgress(3);
                    } else {
                        error_txt_cause.setText(response.body().getMessage());
                        hideProgress(2);
                    }
                } else {
                    error_txt_cause.setText(response.message());
                    hideProgress(2);
                }
            }

            @Override
            public void onFailure(Call<com.app.atlit.model.pojo.retrofit.AtlitRetrofit> call, Throwable t) {
                error_txt_cause.setText(t.getMessage());
                hideProgress(2);
            }
        });

    }

    private void hideProgress(int value) {
        if (value == 1) {
            progress.setVisibility(View.VISIBLE);
            error_layout.setVisibility(View.GONE);
            data_beep.setVisibility(View.GONE);
        } else if (value == 2) {
            progress.setVisibility(View.GONE);
            error_layout.setVisibility(View.VISIBLE);
            data_beep.setVisibility(View.GONE);
        } else {
            progress.setVisibility(View.GONE);
            error_layout.setVisibility(View.GONE);
            data_beep.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        intentToBack();
    }

    private void intentToBack() {
        Intent intent = new Intent(BeepAtlitActivity.this, BeepStartActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private String tableVo2MaxP(int umur, double vo2max) {
        if (
                ((umur >= 13 && umur <= 19) && vo2max < 25) ||
                        ((umur >= 20 && umur <= 29) && vo2max < 23.6) ||
                        ((umur >= 30 && umur <= 39) && vo2max < 22.8) ||
                        ((umur >= 40 && umur <= 49) && vo2max < 21.0) ||
                        ((umur >= 50 && umur <= 59) && vo2max < 20.2) ||
                        (umur > 60 && vo2max < 17.5)
        ) return "Very Poor";
        else if (
                ((umur >= 13 && umur <= 19) && (vo2max >= 25 && vo2max <= 30.9)) ||
                        ((umur >= 20 && umur <= 29) && (vo2max >= 23.6 && vo2max <= 28.9)) ||
                        ((umur >= 30 && umur <= 39) && (vo2max >= 22.8  && vo2max <= 26.9)) ||
                        ((umur >= 40 && umur <= 49) && (vo2max >= 20.2 && vo2max <= 21)) ||
                        ((umur >= 50 && umur <= 59) && (vo2max >= 20.2 && vo2max <= 22.7)) ||
                        (umur > 60 && (vo2max >= 17.5 && vo2max <= 20.2))
        ) return "Poor";
        else if (
                ((umur >= 13 && umur <= 19) && (vo2max >= 31 && vo2max <= 34.9)) ||
                        ((umur >= 20 && umur <= 29) && (vo2max >= 29 && vo2max <= 32.9)) ||
                        ((umur >= 30 && umur <= 39) && (vo2max >= 27 && vo2max <= 31.4)) ||
                        ((umur >= 40 && umur <= 49) && (vo2max >= 24.5 && vo2max <= 28.9)) ||
                        ((umur >= 50 && umur <= 59) && (vo2max >= 22.8 && vo2max <= 26.9)) ||
                        (umur > 60 && (vo2max >= 20.2 && vo2max <= 24.4))
        ) return "Fair";
        else if (
                ((umur >= 13 && umur <= 19) && (vo2max >= 35 && vo2max <= 38.9)) ||
                        ((umur >= 20 && umur <= 29) && (vo2max >= 33 && vo2max <= 36.9)) ||
                        ((umur >= 30 && umur <= 39) && (vo2max >= 31.5 && vo2max <= 35.6)) ||
                        ((umur >= 40 && umur <= 49) && (vo2max >= 29 && vo2max <= 32.8)) ||
                        ((umur >= 50 && umur <= 59) && (vo2max >= 27 && vo2max <= 31.4)) ||
                        (umur > 60 && (vo2max >= 24.5 && vo2max <= 30.2))
        ) return "Good";
        else if (
                ((umur >= 13 && umur <= 19) && (vo2max >= 39 && vo2max <= 41.9)) ||
                        ((umur >= 20 && umur <= 29) && (vo2max >= 37 && vo2max <= 41)) ||
                        ((umur >= 30 && umur <= 39) && (vo2max >= 35.7 && vo2max <= 40)) ||
                        ((umur >= 40 && umur <= 49) && (vo2max >= 32.9 && vo2max <= 36.9)) ||
                        ((umur >= 50 && umur <= 59) && (vo2max >= 31.5 && vo2max <= 35.7)) ||
                        (umur > 60 && (vo2max >= 30.3 && vo2max <= 31.4))
        ) return "Excelent";
        else if (
                ((umur >= 13 && umur <= 19) && vo2max > 41.9) ||
                        ((umur >= 20 && umur <= 29) && vo2max > 41) ||
                        ((umur >= 30 && umur <= 39) && vo2max > 40) ||
                        ((umur >= 40 && umur <= 49) && vo2max > 36.9) ||
                        ((umur >= 50 && umur <= 59) && vo2max > 35.7) ||
                        (umur > 60 && vo2max > 31.4)
        ) return "Superior";
        return "";
    }

    private String tableVo2MaxL(int umur, double vo2max) {
        if (
                ((umur >= 13 && umur <= 19) && vo2max < 35) ||
                        ((umur >= 20 && umur <= 29) && vo2max < 33) ||
                        ((umur >= 30 && umur <= 39) && vo2max < 31.5) ||
                        ((umur >= 40 && umur <= 49) && vo2max < 30.2) ||
                        ((umur >= 50 && umur <= 59) && vo2max < 26.1) ||
                        (umur > 60 && vo2max < 20.5)
        ) return "Very Poor";
        else if (
                ((umur >= 13 && umur <= 19) && (vo2max >= 35 && vo2max <= 38.3)) ||
                        ((umur >= 20 && umur <= 29) && (vo2max >= 33 && vo2max <= 36.4)) ||
                        ((umur >= 30 && umur <= 39) && (vo2max >= 31.5  && vo2max <= 35.4)) ||
                        ((umur >= 40 && umur <= 49) && (vo2max >= 30.2 && vo2max <= 33.5)) ||
                        ((umur >= 50 && umur <= 59) && (vo2max >= 26.1 && vo2max <= 30.9)) ||
                        (umur > 60 && (vo2max >= 20.5 && vo2max <= 26))
        ) return "Poor";
        else if (
                ((umur >= 13 && umur <= 19) && (vo2max >= 38.4 && vo2max <= 45.1)) ||
                        ((umur >= 20 && umur <= 29) && (vo2max >= 36.5 && vo2max <= 42.4)) ||
                        ((umur >= 30 && umur <= 39) && (vo2max >= 35.5 && vo2max <= 40.9)) ||
                        ((umur >= 40 && umur <= 49) && (vo2max >= 33.6 && vo2max <= 38.9)) ||
                        ((umur >= 50 && umur <= 59) && (vo2max >= 31 && vo2max <= 35.7)) ||
                        (umur > 60 && (vo2max >= 26.1 && vo2max <= 32.2))
        ) return "Fair";
        else if (
                ((umur >= 13 && umur <= 19) && (vo2max >= 45.2 && vo2max <= 50.9)) ||
                        ((umur >= 20 && umur <= 29) && (vo2max >= 42.5 && vo2max <= 46.4)) ||
                        ((umur >= 30 && umur <= 39) && (vo2max >= 41 && vo2max <= 44.9)) ||
                        ((umur >= 40 && umur <= 49) && (vo2max >= 39 && vo2max <= 43.7)) ||
                        ((umur >= 50 && umur <= 59) && (vo2max >= 35.8 && vo2max <= 40.9)) ||
                        (umur > 60 && (vo2max >= 32.3 && vo2max <= 36.4))
        ) return "Good";
        else if (
                ((umur >= 13 && umur <= 19) && (vo2max >= 51 && vo2max <= 55.9)) ||
                        ((umur >= 20 && umur <= 29) && (vo2max >= 46.5 && vo2max <= 52.4)) ||
                        ((umur >= 30 && umur <= 39) && (vo2max >= 45 && vo2max <= 49.4)) ||
                        ((umur >= 40 && umur <= 49) && (vo2max >= 43.8 && vo2max <= 48)) ||
                        ((umur >= 50 && umur <= 59) && (vo2max >= 41 && vo2max <= 45.3)) ||
                        (umur > 60 && (vo2max >= 36.5 && vo2max <= 44.2))
        ) return "Excelent";
        else if (
                ((umur >= 13 && umur <= 19) && vo2max > 55.9) ||
                        ((umur >= 20 && umur <= 29) && vo2max > 52.4) ||
                        ((umur >= 30 && umur <= 39) && vo2max > 49.4) ||
                        ((umur >= 40 && umur <= 49) && vo2max > 48) ||
                        ((umur >= 50 && umur <= 59) && vo2max > 45.3) ||
                        (umur > 60 && vo2max > 44.2)
        ) return "Superior";
        return "";
    }

    private void initToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Beep Test");
    }

    private int getShuttle(int level) {
        switch (level) {
            case 1:
                return 7;
            case 2:
            case 3:
                return 8;
            case 4:
                return 9;
            case 5:
            case 8:
                return 10;
            case 6:
                return 10;
            case 7:
                return 10;
            case 9:
            case 11:
                return 11;
            case 10:
                return 11;
            case 12:
            case 13:
                return 12;
            case 14:
            case 16:
                return 13;
            case 15:
                return 13;
            case 17:
            case 18:
                return 14;
            case 19:
            case 20:
                return 15;
            case 21:
                return 16;
            default:
                return 0;
        }
    }
}
