package com.example.atlit.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.atlit.Model.Atlit;
import com.example.atlit.Model.Pelari;
import com.example.atlit.R;
import com.example.atlit.RetrofitModel.atlitModels;
import com.example.atlit.RetrofitModel.pelatihModel;
import com.example.atlit.Utils.ApiClient;
import com.example.atlit.Utils.ApiInterface;
import com.example.atlit.Utils.Loginsharedpreference;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PelariActivity extends AppCompatActivity {

    private Spinner spNama;
    private RadioGroup rgGender;
    private RadioButton rbLaki, rbPerempuan;
    private EditText txtUmur;
    private Loginsharedpreference loginsharedpreference;
    private Button btnSave;
    private List<Atlit> atlits = new ArrayList<>();
    private ApiInterface mApiInterface;
    private final static String TAG = PelariActivity.class.getSimpleName();
    private Gson gson;
    private SimpleDateFormat dateFormat;
    private Toolbar toolbar;
    private ProgressBar progress;
    private LinearLayout error_layout, llData;
    private TextView error_txt_cause, tvName;
    private Button error_btn_retry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pelari);
        tvName = findViewById(R.id.tvName);
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        spNama = findViewById(R.id.spNama);
        rgGender = findViewById(R.id.rgGender);
        rbLaki = findViewById(R.id.rbLaki);
        rbPerempuan = findViewById(R.id.rbPerempuan);
        txtUmur = findViewById(R.id.txtUmur);
        loginsharedpreference = new Loginsharedpreference(this);
        btnSave = findViewById(R.id.btnSave);
        error_layout = findViewById(R.id.error_layout);
        progress = findViewById(R.id.progress);
        error_txt_cause = findViewById(R.id.error_txt_cause);
        error_btn_retry = findViewById(R.id.error_btn_retry);
        llData = findViewById(R.id.llData);
        mApiInterface = ApiClient.getClient().create(ApiInterface.class);
        gson = new Gson();
        hideProgress(1);
        fillSP();
        initToolbar();
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog progress = new ProgressDialog(PelariActivity.this);
                progress.setMessage(getString(R.string.information));
                progress.setTitle(getString(R.string.please_wait));
                progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progress.show();
                if(!loginsharedpreference.getUserLogin().getAkses().equals("atlit")){
                    try {
                        Atlit atlit = atlits.get(spNama.getSelectedItemPosition());
                        Date date = dateFormat.parse(atlit.getTanggal_lahir());
                        Calendar cal = Calendar.getInstance();
                        Calendar cal2 = Calendar.getInstance();
                        cal2.setTime(date);
                        Log.e(TAG, "onClick: " + gson.toJson(atlit) );
                        int umur = cal.get(Calendar.YEAR) - cal2.get(Calendar.YEAR);
                        loginsharedpreference.setPelari(new Pelari(atlit.getRefid(),atlit.getNama(),atlit.getJenis_kelamin(),umur));
                        intentToBack();
                    } catch (ParseException e) {
                        Toast.makeText(PelariActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    progress.dismiss();
                } else {
                    intentToBack();
                    progress.dismiss();
                }
            }
        });

        error_btn_retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideProgress(1);
                fillSP();
            }
        });

        spNama.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    selectAtlit(atlits.get(i));
                } catch (Exception e) {
                    error_txt_cause.setText(e.getMessage());
                    hideProgress(2);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void hideProgress(int value) {
        if (value == 1) {
            progress.setVisibility(View.VISIBLE);
            error_layout.setVisibility(View.GONE);
            llData.setVisibility(View.GONE);
        } else if (value == 2) {
            progress.setVisibility(View.GONE);
            error_layout.setVisibility(View.VISIBLE);
            llData.setVisibility(View.GONE);
        } else {
            progress.setVisibility(View.GONE);
            error_layout.setVisibility(View.GONE);
            llData.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        intentToBack();
    }

    private void intentToBack() {
        Intent intent = new Intent(PelariActivity.this, BeepActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private int index(List<Atlit> data, String find) {
        for(int i = 0; i < data.size(); i++) {
            if(data.get(i).getNama().equals(find)) return i;
        }
        return -1;
    }

    private void initToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Data Pelari");
    }

    private void selectAtlit(Atlit atlit) throws ParseException {
        Date date = dateFormat.parse(atlit.getTanggal_lahir());
        Calendar cal = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date);

        int umur = cal.get(Calendar.YEAR) - cal2.get(Calendar.YEAR);
        txtUmur.setText(String.valueOf(umur));

        if(atlit.getJenis_kelamin().equals("Laki-Laki")) rbLaki.setChecked(true);
        else rbPerempuan.setChecked(true);

        txtUmur.setEnabled(false);
        rgGender.setEnabled(false);
        rbLaki.setEnabled(false);
        rbPerempuan.setEnabled(false);
    }

    private void fillSP() {
        atlits = new ArrayList<>();
        if(loginsharedpreference.getUserLogin().getAkses().equals("atlit")) {
            spNama.setVisibility(View.GONE);
            tvName.setVisibility(View.VISIBLE);
            tvName.setText(loginsharedpreference.getPelari().getName());
            txtUmur.setText(String.valueOf(loginsharedpreference.getPelari().getUmur()));
            txtUmur.setEnabled(false);
            rgGender.setEnabled(false);
            rbLaki.setEnabled(false);
            rbPerempuan.setEnabled(false);
            if(loginsharedpreference.getPelari().getJk().equals("Laki-Laki")) rbLaki.setChecked(true);
            else rbPerempuan.setChecked(true);
            hideProgress(3);
        } else {
            spNama.setVisibility(View.VISIBLE);
            tvName.setVisibility(View.GONE);
            Call<pelatihModel> getPelatih = mApiInterface.getPelatih(loginsharedpreference.getUserLogin().getId());
            Log.e(TAG, "fillSP: " + loginsharedpreference.getUserLogin().getId() );
            getPelatih.enqueue(new Callback<pelatihModel>() {
                @Override
                public void onResponse(Call<pelatihModel> call, Response<pelatihModel> response) {
                    if(response.isSuccessful()) {
                        if(response.body().getMessage().equals("data berhasil ditemukan")) {
                            Call<atlitModels> dataAtlit = mApiInterface.atlitgets(response.body().getData().getCabang_olahraga());
                            dataAtlit.enqueue(new Callback<atlitModels>() {
                                @Override
                                public void onResponse(Call<atlitModels> call, Response<atlitModels> response) {
                                    if(response.isSuccessful()) {
                                        Log.e(TAG, "onResponse2: " + gson.toJson(response.body()) );
                                        if(response.body().isStatus()) {
                                            atlits = response.body().getData();
                                            if(atlits.size() > 0) {
                                                String[] valueAtlit = new String[atlits.size()];
                                                for (int i = 0; i < atlits.size(); i++)
                                                    valueAtlit[i] = atlits.get(i).getNama();

                                                ArrayAdapter<String> adapter =
                                                        new ArrayAdapter<String>(PelariActivity.this,
                                                                android.R.layout.simple_spinner_dropdown_item, valueAtlit);
                                                spNama.setAdapter(adapter);

                                                if(loginsharedpreference.getPelari() != null) {
                                                    int index = index(atlits, loginsharedpreference.getPelari().getName());
                                                    if(index>-1) spNama.setSelection(index);
                                                }

                                                try{
                                                    selectAtlit(atlits.get(spNama.getSelectedItemPosition()));
                                                    hideProgress(3);
                                                } catch (ParseException e) {
                                                    error_txt_cause.setText(e.getMessage());
                                                    hideProgress(2);
                                                }
                                            } else {
                                                error_txt_cause.setText("Data atlit tidak tersedia");
                                                hideProgress(2);
                                            }
                                        } else {
                                            error_txt_cause.setText("Data atlit tidak tersedia");
                                            hideProgress(2);
                                        }
                                    } else {
                                        error_txt_cause.setText(response.message());
                                        hideProgress(2);
                                    }
                                }

                                @Override
                                public void onFailure(Call<atlitModels> call, Throwable t) {
                                    error_txt_cause.setText(t.getMessage());
                                    hideProgress(2);
                                }
                            });
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
                public void onFailure(Call<pelatihModel> call, Throwable t) {
                    error_txt_cause.setText(t.getMessage());
                    hideProgress(2);
                }
            });
        }
    }
}
