package com.app.atlit.ui.activity;

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

import com.app.atlit.model.pojo.AtlitPojo;
import com.app.atlit.model.pojo.PelariPojo;
import com.app.atlit.R;
import com.app.atlit.utils.api.ApiClient;
import com.app.atlit.utils.api.ApiInterface;
import com.app.atlit.utils.LoginSharedPreference;
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
    private LoginSharedPreference loginsharedpreference;
    private Button btnSave;
    private List<AtlitPojo> atlitPojos = new ArrayList<>();
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
        loginsharedpreference = new LoginSharedPreference(this);
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
        btnSave.setOnClickListener(view -> {
            final ProgressDialog progress = new ProgressDialog(PelariActivity.this);
            progress.setMessage(getString(R.string.information));
            progress.setTitle(getString(R.string.please_wait));
            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progress.show();
            if(!loginsharedpreference.getUserLogin().getAkses().equals("atlit")){
                try {
                    AtlitPojo atlitPojo = atlitPojos.get(spNama.getSelectedItemPosition());
                    Date date = dateFormat.parse(atlitPojo.getTanggal_lahir());
                    Calendar cal = Calendar.getInstance();
                    Calendar cal2 = Calendar.getInstance();
                    cal2.setTime(date);
                    Log.e(TAG, "onClick: " + gson.toJson(atlitPojo) );
                    int umur = cal.get(Calendar.YEAR) - cal2.get(Calendar.YEAR);
                    loginsharedpreference.setPelari(new PelariPojo(atlitPojo.getRefid(), atlitPojo.getNama(), atlitPojo.getJenis_kelamin(),umur));
                    intentToBack();
                } catch (ParseException e) {
                    Toast.makeText(PelariActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                progress.dismiss();
            } else {
                intentToBack();
                progress.dismiss();
            }
        });

        error_btn_retry.setOnClickListener(view -> {
            hideProgress(1);
            fillSP();
        });

        spNama.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    selectAtlit(atlitPojos.get(i));
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

    private int index(List<AtlitPojo> data, String find) {
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

    private void selectAtlit(AtlitPojo atlitPojo) throws ParseException {
        Date date = dateFormat.parse(atlitPojo.getTanggal_lahir());
        Calendar cal = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date);

        int umur = cal.get(Calendar.YEAR) - cal2.get(Calendar.YEAR);
        txtUmur.setText(String.valueOf(umur));

        if(atlitPojo.getJenis_kelamin().equals("Laki-Laki")) rbLaki.setChecked(true);
        else rbPerempuan.setChecked(true);

        txtUmur.setEnabled(false);
        rgGender.setEnabled(false);
        rbLaki.setEnabled(false);
        rbPerempuan.setEnabled(false);
    }

    private void fillSP() {
        atlitPojos = new ArrayList<>();
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
            Call<com.app.atlit.model.pojo.retrofit.PelatihRetrofit> getPelatih = mApiInterface.getPelatih(loginsharedpreference.getUserLogin().getId());
            Log.e(TAG, "fillSP: " + loginsharedpreference.getUserLogin().getId() );
            getPelatih.enqueue(new Callback<com.app.atlit.model.pojo.retrofit.PelatihRetrofit>() {
                @Override
                public void onResponse(Call<com.app.atlit.model.pojo.retrofit.PelatihRetrofit> call, Response<com.app.atlit.model.pojo.retrofit.PelatihRetrofit> response) {
                    if(response.isSuccessful()) {
                        if(response.body().getMessage().equals("data berhasil ditemukan")) {
                            Call<com.app.atlit.model.pojo.retrofit.ListAtlitRetrofit> dataAtlit = mApiInterface.atlitgets(response.body().getData().getCabang_olahraga());
                            dataAtlit.enqueue(new Callback<com.app.atlit.model.pojo.retrofit.ListAtlitRetrofit>() {
                                @Override
                                public void onResponse(Call<com.app.atlit.model.pojo.retrofit.ListAtlitRetrofit> call, Response<com.app.atlit.model.pojo.retrofit.ListAtlitRetrofit> response) {
                                    if(response.isSuccessful()) {
                                        Log.e(TAG, "onResponse2: " + gson.toJson(response.body()) );
                                        if(response.body().isStatus()) {
                                            atlitPojos = response.body().getData();
                                            if(atlitPojos.size() > 0) {
                                                String[] valueAtlit = new String[atlitPojos.size()];
                                                for (int i = 0; i < atlitPojos.size(); i++)
                                                    valueAtlit[i] = atlitPojos.get(i).getNama();

                                                ArrayAdapter<String> adapter =
                                                        new ArrayAdapter<String>(PelariActivity.this,
                                                                android.R.layout.simple_spinner_dropdown_item, valueAtlit);
                                                spNama.setAdapter(adapter);

                                                if(loginsharedpreference.getPelari() != null) {
                                                    int index = index(atlitPojos, loginsharedpreference.getPelari().getName());
                                                    if(index>-1) spNama.setSelection(index);
                                                }

                                                try{
                                                    selectAtlit(atlitPojos.get(spNama.getSelectedItemPosition()));
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
                                public void onFailure(Call<com.app.atlit.model.pojo.retrofit.ListAtlitRetrofit> call, Throwable t) {
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
                public void onFailure(Call<com.app.atlit.model.pojo.retrofit.PelatihRetrofit> call, Throwable t) {
                    error_txt_cause.setText(t.getMessage());
                    hideProgress(2);
                }
            });
        }
    }
}
