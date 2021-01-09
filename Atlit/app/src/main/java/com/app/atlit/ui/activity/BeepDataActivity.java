package com.app.atlit.ui.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.app.atlit.ui.activity.adapter.BeepAdapter;
import com.app.atlit.model.pojo.BeepPojo;
import com.app.atlit.R;
import com.app.atlit.utils.api.ApiClient;
import com.app.atlit.utils.api.ApiInterface;
import com.app.atlit.utils.LoginSharedPreference;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeoutException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BeepDataActivity extends AppCompatActivity {

    public final static String KEY_FORM = "form";
    private TextView tvHeader;
    private TableRow minggu1, minggu2, minggu3, minggu4;
    private RecyclerView rvminggu1, rvminggu2, rvminggu3, rvminggu4;
    private Button btnDellAll, btnGrafik, btnGrafikPelatih;
    private LinearLayout llAtlit, llPelatih;
    private LoginSharedPreference loginsharedpreference;
    private final static String TAG = BeepDataActivity.class.getSimpleName();
    Calendar cal = Calendar.getInstance();
    private Toolbar toolbar;
    private List<BeepPojo> beepPojo1, beepPojo2, beepPojo3, beepPojo4;
    private BeepAdapter adapter, adapter2, adapter3, adapter4;
    private ProgressBar progressbar;
    private LinearLayout error_layout;
    private TextView error_txt_cause;
    private ScrollView scrollView;
    private Button error_btn_retry;
    private ApiInterface mApiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beep_data);
        tvHeader = findViewById(R.id.tvHeader);
        minggu1 = findViewById(R.id.minggu1);
        minggu2 = findViewById(R.id.minggu2);
        minggu3 = findViewById(R.id.minggu3);
        minggu4 = findViewById(R.id.minggu4);
        rvminggu1 = findViewById(R.id.rvminggu1);
        rvminggu2 = findViewById(R.id.rvminggu2);
        rvminggu3 = findViewById(R.id.rvminggu3);
        rvminggu4 = findViewById(R.id.rvminggu4);
        btnDellAll = findViewById(R.id.btnDellAll);
        btnGrafik = findViewById(R.id.btnGrafik);
        btnGrafikPelatih = findViewById(R.id.btnGrafik_pelatih);
        progressbar = findViewById(R.id.progressbar);
        error_layout = findViewById(R.id.error_layout);
        error_txt_cause = findViewById(R.id.error_txt_cause);
        scrollView = findViewById(R.id.scrollView);
        error_btn_retry = findViewById(R.id.error_btn_retry);
        mApiInterface = ApiClient.getClient().create(ApiInterface.class);
        tvHeader.setText("Bulan ke " + cal.get(Calendar.MONTH));
        beepPojo1 = new ArrayList<>();
        beepPojo2 = new ArrayList<>();
        beepPojo3 = new ArrayList<>();
        beepPojo4 = new ArrayList<>();
        adapter = new BeepAdapter(beepPojo1, this);
        adapter2 = new BeepAdapter(beepPojo2, this);
        adapter3 = new BeepAdapter(beepPojo3, this);
        adapter4 = new BeepAdapter(beepPojo4, this);
        rvminggu1.setAdapter(adapter);
        rvminggu2.setAdapter(adapter2);
        rvminggu3.setAdapter(adapter3);
        rvminggu4.setAdapter(adapter4);
        loginsharedpreference = new LoginSharedPreference(this);

        llAtlit = findViewById(R.id.llproses);
        llPelatih = findViewById(R.id.llproses_pelatih);

        initToolbar();

        minggu1.setVisibility(View.GONE);
        minggu2.setVisibility(View.GONE);
        minggu3.setVisibility(View.GONE);
        minggu4.setVisibility(View.GONE);

        refreshData();
        error_btn_retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refreshData();
            }
        });

        btnGrafik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               moveToGrafik();
            }
        });

        btnGrafikPelatih.setOnClickListener(v-> {
            moveToGrafik();
        });

        btnDellAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(BeepDataActivity.this)
                        .setTitle("Delete Data")
                        .setMessage("Apakah anda yakin ingin menghapus data pada bulan ke " + cal.get(Calendar.MONTH) + "?")
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                final ProgressDialog pDialog = new ProgressDialog(BeepDataActivity.this);
                                pDialog.setTitle(getString(R.string.information));
                                pDialog.setMessage(getString(R.string.please_wait));
                                pDialog.show();
                                Call<com.app.atlit.model.pojo.retrofit.DeleteDataRetrofit> beepDelete = mApiInterface.beepDelete(cal.get(Calendar.MONTH), loginsharedpreference.getUserLogin().getId());
                                beepDelete.enqueue(new Callback<com.app.atlit.model.pojo.retrofit.DeleteDataRetrofit>() {
                                    @Override
                                    public void onResponse(Call<com.app.atlit.model.pojo.retrofit.DeleteDataRetrofit> call, Response<com.app.atlit.model.pojo.retrofit.DeleteDataRetrofit> response) {
                                        if (response.isSuccessful()) {
                                            if (response.body().getMessage().equals("success"))
                                                Toast.makeText(BeepDataActivity.this, "Berhasil menghapus data", Toast.LENGTH_SHORT).show();
                                            else
                                                Toast.makeText(BeepDataActivity.this, "Gagal menghapus data", Toast.LENGTH_SHORT).show();

                                            progressbar.setVisibility(View.VISIBLE);
                                            scrollView.setVisibility(View.GONE);
                                            refreshData();
                                        } else
                                            Toast.makeText(BeepDataActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                                        pDialog.dismiss();
                                    }

                                    @Override
                                    public void onFailure(Call<com.app.atlit.model.pojo.retrofit.DeleteDataRetrofit> call, Throwable t) {
                                        Log.e(TAG, "onError: " + t.getMessage());
                                        Toast.makeText(BeepDataActivity.this, "ANError: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                                        pDialog.dismiss();
                                    }
                                });
                            }
                        })
                        .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //
                            }
                        });
                dialog.show();
            }
        });

        if(loginsharedpreference.getUserLogin().getAkses().toLowerCase().equals("pelatih")) {
            llPelatih.setVisibility(View.VISIBLE);
            llAtlit.setVisibility(View.GONE);
        } else {
            llPelatih.setVisibility(View.GONE);
            llAtlit.setVisibility(View.VISIBLE);
        }
    }

    private void moveToGrafik() {
        Intent intent = new Intent(BeepDataActivity.this, ChartActivity.class);
        intent.putExtra(ChartActivity.EXTRA_TYPE,"beep");
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    @Override
    public void onBackPressed() {
        if(getIntent().getStringExtra(KEY_FORM).equals("atlit")) {
            Intent intent = new Intent(BeepDataActivity.this, BeepStartActivity.class);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        } else {
            Intent intent = new Intent(BeepDataActivity.this, BeepPelatihActivity.class);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }
    }

    private void refreshData() {
        hideErrorView();

        Call<com.app.atlit.model.pojo.retrofit.GetBeepRetrofit> beepData = loginsharedpreference.getUserLogin().getAkses().toLowerCase().equals("pelatih") ? mApiInterface.beepDataPelatih(cal.get(Calendar.MONTH)) : mApiInterface.beepdata(cal.get(Calendar.MONTH), loginsharedpreference.getUserLogin().getId());
        beepData.enqueue(new Callback<com.app.atlit.model.pojo.retrofit.GetBeepRetrofit>() {
            @Override
            public void onResponse(Call<com.app.atlit.model.pojo.retrofit.GetBeepRetrofit> call, Response<com.app.atlit.model.pojo.retrofit.GetBeepRetrofit> response) {
                if (response.isSuccessful()) {
                    if (response.body().getMessage().equals("success")) {
                        if (response.body().getMinggu1().size() > 0) {
                            beepPojo1 = response.body().getMinggu1();
                            adapter.setData(beepPojo1);
                            minggu1.setVisibility(View.VISIBLE);
                        }

                        if (response.body().getMinggu2().size() > 0) {
                            beepPojo2 = response.body().getMinggu2();
                            adapter2.setData(beepPojo2);
                            minggu2.setVisibility(View.VISIBLE);
                        }

                        if (response.body().getMinggu3().size() > 0) {
                            beepPojo3 = response.body().getMinggu3();
                            adapter3.setData(beepPojo3);
                            minggu3.setVisibility(View.VISIBLE);
                        }

                        if (response.body().getMinggu4().size() > 0) {
                            beepPojo4 = response.body().getMinggu4();
                            adapter4.setData(beepPojo4);
                            minggu4.setVisibility(View.VISIBLE);
                        }

                        if (response.body().getMinggu1().size() == 0 && response.body().getMinggu2().size() == 0 &&
                                response.body().getMinggu3().size() == 0 && response.body().getMinggu4().size() == 0) {
                            progressbar.setVisibility(View.GONE);
                            error_txt_cause.setText("Data tidak ada");
                            error_layout.setVisibility(View.VISIBLE);
                            scrollView.setVisibility(View.GONE);
                        } else {
                            progressbar.setVisibility(View.GONE);
                            scrollView.setVisibility(View.VISIBLE);
                        }
                    } else {
                        Toast.makeText(BeepDataActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        error(response.body().getMessage());
                    }
                } else {
                    Toast.makeText(BeepDataActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "onResponse: " + response.message());
                    error(response.message());
                }
            }

            @Override
            public void onFailure(Call<com.app.atlit.model.pojo.retrofit.GetBeepRetrofit> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
                Toast.makeText(BeepDataActivity.this, "onFailure: " + t, Toast.LENGTH_SHORT).show();
                showErrorView(t);
            }
        });
    }

    private void error(String message) {
        progressbar.setVisibility(View.GONE);
        error_layout.setVisibility(View.VISIBLE);
        error_txt_cause.setText(message);
        scrollView.setVisibility(View.GONE);
    }

    private void showErrorView(Throwable throwable) {
        if (error_layout.getVisibility() == View.GONE) {
            error_layout.setVisibility(View.VISIBLE);
            progressbar.setVisibility(View.GONE);
            error_txt_cause.setText(fetchErrorMessage(throwable));
        }
    }

    private String fetchErrorMessage(Throwable throwable) {
        String errorMsg = getResources().getString(R.string.error_msg_unknown);

        if (!isNetworkConnected()) {
            errorMsg = getResources().getString(R.string.error_msg_no_internet);
        } else if (throwable instanceof TimeoutException) {
            errorMsg = getResources().getString(R.string.error_msg_timeout);
        } else {
            errorMsg = throwable.getMessage();
        }

        return errorMsg;
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;

    }

    private void hideErrorView() {
        error_layout.setVisibility(View.GONE);
        progressbar.setVisibility(View.VISIBLE);
        scrollView.setVisibility(View.GONE);
    }

    private void initToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Beep Data");
    }
}
