package com.example.atlit.Activity;

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

import com.example.atlit.Adapter.CooperAdapter;
import com.example.atlit.Model.Cooper;
import com.example.atlit.RetrofitModel.deleteDataModel;
import com.example.atlit.RetrofitModel.getCooperModel;
import com.example.atlit.R;
import com.example.atlit.Utils.ApiClient;
import com.example.atlit.Utils.ApiInterface;
import com.example.atlit.Utils.Loginsharedpreference;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeoutException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CooperDataActivity extends AppCompatActivity {

    private TextView tvHeader;
    private TableRow minggu1, minggu2, minggu3, minggu4;
    private RecyclerView rvminggu1, rvminggu2, rvminggu3, rvminggu4;
    private Button btnDellAll, btnGrafik, btnGrafikPelatih;
    private Loginsharedpreference loginsharedpreference;
    private LinearLayout llPelatih, llAtlit;

    private final static String TAG = BalkeDataActivity.class.getSimpleName();
    Calendar cal = Calendar.getInstance();
    private Toolbar toolbar;
    private List<Cooper> coopers1, coopers2, coopers3, coopers4;
    private CooperAdapter adapter, adapter2, adapter3, adapter4;
    private ProgressBar progressbar;
    private LinearLayout error_layout;
    private TextView error_txt_cause;
    private ScrollView scrollView;
    private Button error_btn_retry;
    private ApiInterface mApiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cooper_data);
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

        llAtlit = findViewById(R.id.llproses);
        llPelatih = findViewById(R.id.llproses_pelatih);

        tvHeader.setText("Bulan ke " + String.valueOf(cal.get(Calendar.MONTH)));
        coopers1 = new ArrayList<>();
        coopers2 = new ArrayList<>();
        coopers3 = new ArrayList<>();
        coopers4 = new ArrayList<>();
        adapter = new CooperAdapter(this, coopers1);
        adapter2 = new CooperAdapter(this, coopers2);
        adapter3 = new CooperAdapter(this, coopers3);
        adapter4 = new CooperAdapter(this, coopers4);
        rvminggu1.setAdapter(adapter);
        rvminggu2.setAdapter(adapter2);
        rvminggu3.setAdapter(adapter3);
        rvminggu4.setAdapter(adapter4);
        loginsharedpreference = new Loginsharedpreference(this);
        mApiInterface = ApiClient.getClient().create(ApiInterface.class);

        initToolbar();

        minggu1.setVisibility(View.GONE);
        minggu2.setVisibility(View.GONE);
        minggu3.setVisibility(View.GONE);
        minggu4.setVisibility(View.GONE);

        refreshData();
        error_btn_retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshData();
            }
        });
        btnGrafik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveToGrafik();
            }
        });

        btnGrafikPelatih.setOnClickListener(v -> {
            moveToGrafik();
        });

        btnDellAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(CooperDataActivity.this)
                        .setTitle("Delete Data")
                        .setMessage("Apakah anda yakin ingin menghapus data pada bulan ke " + cal.get(Calendar.MONTH) + "?")
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                final ProgressDialog pDialog = new ProgressDialog(CooperDataActivity.this);
                                pDialog.setTitle(getString(R.string.information));
                                pDialog.setMessage(getString(R.string.please_wait));
                                pDialog.show();

                                Call<deleteDataModel> cooperDelete = mApiInterface.cooperDelete(cal.get(Calendar.MONTH), loginsharedpreference.getUserLogin().getId());
                                cooperDelete.enqueue(new Callback<deleteDataModel>() {
                                    @Override
                                    public void onResponse(Call<deleteDataModel> call, Response<deleteDataModel> response) {
                                        if (response.isSuccessful()) {
                                            if (response.body().getMessage().equals("success"))
                                                Toast.makeText(CooperDataActivity.this, "Berhasil menghapus data", Toast.LENGTH_SHORT).show();
                                            else
                                                Toast.makeText(CooperDataActivity.this, "Gagal menghapus data", Toast.LENGTH_SHORT).show();

                                            progressbar.setVisibility(View.VISIBLE);
                                            scrollView.setVisibility(View.GONE);
                                            refreshData();
                                        } else
                                            Toast.makeText(CooperDataActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                                        pDialog.dismiss();
                                    }

                                    @Override
                                    public void onFailure(Call<deleteDataModel> call, Throwable t) {
                                        Log.e(TAG, "onError: " + t.getMessage());
                                        Toast.makeText(CooperDataActivity.this, "ANError: " + t.getMessage(), Toast.LENGTH_SHORT).show();
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
        Intent intent = new Intent(CooperDataActivity.this, ChartActivity.class);
        intent.putExtra(ChartActivity.EXTRA_TYPE, "cooper");
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    private void refreshData() {
        hideErrorView();

        Call<getCooperModel> cooperData = loginsharedpreference.getUserLogin().getAkses().toLowerCase().equals("pelatih") ? mApiInterface.cooperDataPelatih(cal.get(Calendar.MONTH)) : mApiInterface.cooperdata(cal.get(Calendar.MONTH), loginsharedpreference.getUserLogin().getId());
        cooperData.enqueue(new Callback<getCooperModel>() {
            @Override
            public void onResponse(Call<getCooperModel> call, Response<getCooperModel> response) {
                if (response.isSuccessful()) {
                    if (response.body().getMessage().equals("success")) {
                        if (response.body().getMinggu1().size() > 0) {
                            coopers1 = response.body().getMinggu1();
                            adapter.setData(coopers1);
                            minggu1.setVisibility(View.VISIBLE);
                        }

                        if (response.body().getMinggu2().size() > 0) {
                            coopers2 = response.body().getMinggu2();
                            adapter2.setData(coopers2);
                            minggu2.setVisibility(View.VISIBLE);
                        }

                        if (response.body().getMinggu3().size() > 0) {
                            coopers3 = response.body().getMinggu3();
                            adapter3.setData(coopers3);
                            minggu3.setVisibility(View.VISIBLE);
                        }

                        if (response.body().getMinggu4().size() > 0) {
                            coopers4 = response.body().getMinggu4();
                            adapter4.setData(coopers4);
                            minggu4.setVisibility(View.VISIBLE);
                        }

                        if (response.body().getMinggu1().size() == 0 && response.body().getMinggu2().size() == 0 &&
                                response.body().getMinggu3().size() == 0 && response.body().getMinggu4().size() == 0) {
                            progressbar.setVisibility(View.GONE);
                            error_txt_cause.setText("Data tidak ada");
                            error_layout.setVisibility(View.VISIBLE);
                        } else {
                            progressbar.setVisibility(View.GONE);
                            scrollView.setVisibility(View.VISIBLE);
                        }
                    } else
                        Toast.makeText(CooperDataActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CooperDataActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "onResponse: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<getCooperModel> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
                Toast.makeText(CooperDataActivity.this, "onFailure: " + t, Toast.LENGTH_SHORT).show();
                showErrorView(t);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (loginsharedpreference.getUserLogin().getAkses().equals("pelatih"))
            intentToCooperPelatih();
        else intentToCooperAtlit();
    }

    private void intentToCooperAtlit() {
        Intent intent = new Intent(CooperDataActivity.this, CooperAtlitActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
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
        if (error_layout.getVisibility() == View.VISIBLE) {
            error_layout.setVisibility(View.GONE);
            progressbar.setVisibility(View.VISIBLE);
            scrollView.setVisibility(View.GONE);
        }

    }

    private void intentToCooperPelatih() {
        Intent intent = new Intent(CooperDataActivity.this, CooperPelatihActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private void initToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.cooper_data));
    }
}
