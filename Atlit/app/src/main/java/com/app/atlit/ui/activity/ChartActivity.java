package com.app.atlit.ui.activity;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.app.atlit.model.pojo.BalkePojo;
import com.app.atlit.model.pojo.BeepPojo;
import com.app.atlit.model.pojo.CooperPojo;
import com.app.atlit.model.pojo.GetBalkePojo;
import com.app.atlit.R;
import com.app.atlit.utils.api.ApiClient;
import com.app.atlit.utils.api.ApiInterface;
import com.app.atlit.utils.LoginSharedPreference;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChartActivity extends AppCompatActivity {

    private ProgressBar progress;
    private BarChart barChart;
    private TextView error_txt_cause;
    private Button error_btn_retry;
    private LinearLayout error_layout;
    public final static String TAG = ChartActivity.class.getSimpleName();
    public final static String EXTRA_TYPE = "extra_type";
    Calendar cal = Calendar.getInstance();
    private LoginSharedPreference loginsharedpreference;
    private ApiInterface mApiInterface;
    private List<BalkePojo> balkes1, balkes2, balkes3, balkes4;
    private List<CooperPojo> coopers1, coopers2, coopers3, coopers4;
    private List<BeepPojo> beeps1, beeps2, beeps3, beeps4;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        progress = findViewById(R.id.progress);
        barChart = findViewById(R.id.chart);
        error_txt_cause = findViewById(R.id.error_txt_cause);
        error_btn_retry = findViewById(R.id.error_btn_retry);
        error_layout = findViewById(R.id.error_layout);
        loginsharedpreference = new LoginSharedPreference(this);
        mApiInterface = ApiClient.getClient().create(ApiInterface.class);
        initToolbar();

        hideProgress(1);

        error_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideProgress(1);
                if (getIntent().getStringExtra(EXTRA_TYPE).equals("balke")) {
                    balkes1 = new ArrayList<>();
                    balkes2 = new ArrayList<>();
                    balkes3 = new ArrayList<>();
                    balkes4 = new ArrayList<>();
                    fillBalkes();
                } else if (getIntent().getStringExtra(EXTRA_TYPE).equals("beep")) {
                    beeps1 = new ArrayList<>();
                    beeps2 = new ArrayList<>();
                    beeps3 = new ArrayList<>();
                    beeps4 = new ArrayList<>();
                    fillBeeps();
                } else {
                    coopers1 = new ArrayList<>();
                    coopers2 = new ArrayList<>();
                    coopers3 = new ArrayList<>();
                    coopers4 = new ArrayList<>();
                    fillCoopers();
                }
            }
        });

        if (getIntent().getStringExtra(EXTRA_TYPE).equals("balke")) {
            balkes1 = new ArrayList<>();
            balkes2 = new ArrayList<>();
            balkes3 = new ArrayList<>();
            balkes4 = new ArrayList<>();
            fillBalkes();
        } else if (getIntent().getStringExtra(EXTRA_TYPE).equals("beep")) {
            beeps1 = new ArrayList<>();
            beeps2 = new ArrayList<>();
            beeps3 = new ArrayList<>();
            beeps4 = new ArrayList<>();
            fillBeeps();
        } else {
            coopers1 = new ArrayList<>();
            coopers2 = new ArrayList<>();
            coopers3 = new ArrayList<>();
            coopers4 = new ArrayList<>();
            fillCoopers();
        }
    }

    private void initToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Chart");
    }

    private void fillBeeps() {
        Call<com.app.atlit.model.pojo.retrofit.GetBeepRetrofit> beepData = loginsharedpreference.getUserLogin().getAkses().toLowerCase().equals("pelatih") ? mApiInterface.beepDataPelatih(cal.get(Calendar.MONTH)) : mApiInterface.beepdata(cal.get(Calendar.MONTH), loginsharedpreference.getUserLogin().getId());
        beepData.enqueue(new Callback<com.app.atlit.model.pojo.retrofit.GetBeepRetrofit>() {
            @Override
            public void onResponse(Call<com.app.atlit.model.pojo.retrofit.GetBeepRetrofit> call, Response<com.app.atlit.model.pojo.retrofit.GetBeepRetrofit> response) {
                if (response.isSuccessful()) {
                    if (response.body().getMessage().equals("success")) {
                        beeps1 = response.body().getMinggu1();
                        beeps2 = response.body().getMinggu2();
                        beeps3 = response.body().getMinggu3();
                        beeps4 = response.body().getMinggu4();
                        hideProgress(3);

                        List<BarEntry> yVals = new ArrayList<>();
                        yVals.add(new BarEntry(0.5f, valueChartBeep(beeps1)));
                        yVals.add(new BarEntry(1.5f, valueChartBeep(beeps2)));
                        yVals.add(new BarEntry(2.5f, valueChartBeep(beeps3)));
                        yVals.add(new BarEntry(3.5f, valueChartBeep(beeps4)));

                        final ArrayList<String> xVals = new ArrayList<String>();
                        xVals.add("minggu 1");
                        xVals.add("minggu 2");
                        xVals.add("minggu 3");
                        xVals.add("minggu 4");

                        BarDataSet dataset = new BarDataSet(yVals, "Rata-Rata");
                        BarData data = new BarData(dataset);

                        // Pengaturan sumbu X
                        XAxis xAxis = barChart.getXAxis();
                        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                        xAxis.setCenterAxisLabels(true);
                        xAxis.setValueFormatter(new IndexAxisValueFormatter(xVals));

                        // Agar ketika di zoom tidak menjadi pecahan
                        xAxis.setGranularity(1f);

                        // Diubah menjadi integer, kemudian dijadikan String
                        // Ini berfungsi untuk menghilankan koma, dan tanda ribuah pada tahun
                        xAxis.setValueFormatter(new IAxisValueFormatter() {
                            @Override
                            public String getFormattedValue(float value, AxisBase axis) {
                                int x = (int) value;
                                if (x > -1 && x < xVals.size()) return xVals.get(x);
                                else return "";
                            }
                        });
                        xAxis.setTextSize(8);

                        //Menghilangkan sumbu Y yang ada di sebelah kanan
                        barChart.getAxisRight().setEnabled(false);

                        // Menghilankan deskripsi pada Chart
                        barChart.getDescription().setEnabled(false);

                        // Set data ke Chart
                        // Tambahkan invalidate setiap kali mengubah data chart
                        barChart.setData(data);
                        barChart.setDragEnabled(true);
                        barChart.invalidate();
                    } else {
                        hideProgress(2);
                        error_txt_cause.setText(response.body().getMessage());
                    }
                } else {
                    hideProgress(2);
                    error_txt_cause.setText(response.message());
                }
            }

            @Override
            public void onFailure(Call<com.app.atlit.model.pojo.retrofit.GetBeepRetrofit> call, Throwable t) {
                hideProgress(2);
                error_txt_cause.setText(t.getMessage());
            }
        });
    }

    private void fillCoopers() {
        Call<com.app.atlit.model.pojo.retrofit.GetCooperRetrofit> cooperData = loginsharedpreference.getUserLogin().getAkses().toLowerCase().equals("pelatih") ? mApiInterface.cooperDataPelatih(cal.get(Calendar.MONTH)) : mApiInterface.cooperdata(cal.get(Calendar.MONTH), loginsharedpreference.getUserLogin().getId());
        cooperData.enqueue(new Callback<com.app.atlit.model.pojo.retrofit.GetCooperRetrofit>() {
            @Override
            public void onResponse(Call<com.app.atlit.model.pojo.retrofit.GetCooperRetrofit> call, Response<com.app.atlit.model.pojo.retrofit.GetCooperRetrofit> response) {
                if (response.isSuccessful()) {
                    if (response.body().getMessage().equals("success")) {
                        coopers1 = response.body().getMinggu1();
                        coopers2 = response.body().getMinggu2();
                        coopers3 = response.body().getMinggu3();
                        coopers4 = response.body().getMinggu4();
                        hideProgress(3);

                        List<BarEntry> yVals = new ArrayList<>();
                        yVals.add(new BarEntry(0.5f, valueChartCooper(coopers1)));
                        yVals.add(new BarEntry(1.5f, valueChartCooper(coopers2)));
                        yVals.add(new BarEntry(2.5f, valueChartCooper(coopers3)));
                        yVals.add(new BarEntry(3.5f, valueChartCooper(coopers4)));

                        final ArrayList<String> xVals = new ArrayList<String>();
                        xVals.add("minggu 1");
                        xVals.add("minggu 2");
                        xVals.add("minggu 3");
                        xVals.add("minggu 4");

                        BarDataSet dataset = new BarDataSet(yVals, "Rata-Rata");
                        BarData data = new BarData(dataset);

                        // Pengaturan sumbu X
                        XAxis xAxis = barChart.getXAxis();
                        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                        xAxis.setCenterAxisLabels(true);
                        xAxis.setValueFormatter(new IndexAxisValueFormatter(xVals));

                        // Agar ketika di zoom tidak menjadi pecahan
                        xAxis.setGranularity(1f);

                        // Diubah menjadi integer, kemudian dijadikan String
                        // Ini berfungsi untuk menghilankan koma, dan tanda ribuah pada tahun
                        xAxis.setValueFormatter(new IAxisValueFormatter() {
                            @Override
                            public String getFormattedValue(float value, AxisBase axis) {
                                int x = (int) value;
                                if (x > -1 && x < xVals.size()) return xVals.get(x);
                                else return "";
                            }
                        });
                        xAxis.setTextSize(8);

                        //Menghilangkan sumbu Y yang ada di sebelah kanan
                        barChart.getAxisRight().setEnabled(false);

                        // Menghilankan deskripsi pada Chart
                        barChart.getDescription().setEnabled(false);

                        // Set data ke Chart
                        // Tambahkan invalidate setiap kali mengubah data chart
                        barChart.setData(data);
                        barChart.setDragEnabled(true);
                        barChart.invalidate();
                    } else {
                        hideProgress(2);
                        error_txt_cause.setText(response.body().getMessage());
                    }
                } else {
                    hideProgress(2);
                    error_txt_cause.setText(response.message());
                }
            }

            @Override
            public void onFailure(Call<com.app.atlit.model.pojo.retrofit.GetCooperRetrofit> call, Throwable t) {
                hideProgress(2);
                error_txt_cause.setText(t.getMessage());
            }
        });
    }

    @Override
    public void onBackPressed() {
        intentToBack();
    }

    private void intentToBack() {
        if (getIntent().getStringExtra(EXTRA_TYPE).equals("balke")) {
            Intent intent = new Intent(ChartActivity.this, BalkeDataActivity.class);
            startActivity(intent);
            finish();
        } else if (getIntent().getStringExtra(EXTRA_TYPE).equals("beep")) {
            Intent intent = new Intent(ChartActivity.this, BeepStartActivity.class);
            startActivity(intent);
            finish();
        } else {
            Intent intent = new Intent(ChartActivity.this, CooperDataActivity.class);
            startActivity(intent);
            finish();
        }
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private float valueChartBalke(List<BalkePojo> balkePojos) {
        float result = 0;
        if (balkePojos.size() > 0) {
            for (BalkePojo x : balkePojos) result += x.getVo2max();
            return result / balkePojos.size();
        }
        return 0;
    }

    private float valueChartBeep(List<BeepPojo> beepPojos) {
        float result = 0;
        if (beepPojos.size() > 0) {
            for (BeepPojo x : beepPojos) result += x.getVo2max();
            return result / beepPojos.size();
        }
        return 0;
    }

    private float valueChartCooper(List<CooperPojo> cooperPojos) {
        float result = 0;
        if (cooperPojos.size() > 0) {
            for (CooperPojo x : cooperPojos) result += x.getVo2max();
            return result / cooperPojos.size();
        }
        return 0;
    }

    private void fillBalkes() {
        Call<GetBalkePojo> balkeData = loginsharedpreference.getUserLogin().getAkses().toLowerCase().equals("pelatih") ? mApiInterface.balkeDataPelatih(cal.get(Calendar.MONTH)) : mApiInterface.balkedata(cal.get(Calendar.MONTH), loginsharedpreference.getUserLogin().getId());
        balkeData.enqueue(new Callback<GetBalkePojo>() {
            @Override
            public void onResponse(Call<GetBalkePojo> call, Response<GetBalkePojo> response) {
                if (response.isSuccessful()) {
                    if (response.body().getMessage().equals("success")) {
                        balkes1 = response.body().getMinggu1();
                        balkes2 = response.body().getMinggu2();
                        balkes3 = response.body().getMinggu3();
                        balkes4 = response.body().getMinggu4();
                        hideProgress(3);

                        List<BarEntry> yVals = new ArrayList<>();
                        yVals.add(new BarEntry(0.5f, valueChartBalke(balkes1)));
                        yVals.add(new BarEntry(1.5f, valueChartBalke(balkes2)));
                        yVals.add(new BarEntry(2.5f, valueChartBalke(balkes3)));
                        yVals.add(new BarEntry(3.5f, valueChartBalke(balkes4)));

                        final ArrayList<String> xVals = new ArrayList<String>();
                        xVals.add("minggu 1");
                        xVals.add("minggu 2");
                        xVals.add("minggu 3");
                        xVals.add("minggu 4");

                        BarDataSet dataset = new BarDataSet(yVals, "Rata-Rata");
                        BarData data = new BarData(dataset);

                        // Pengaturan sumbu X
                        XAxis xAxis = barChart.getXAxis();
                        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                        xAxis.setCenterAxisLabels(true);
                        xAxis.setValueFormatter(new IndexAxisValueFormatter(xVals));

                        // Agar ketika di zoom tidak menjadi pecahan
                        xAxis.setGranularity(1f);

                        // Diubah menjadi integer, kemudian dijadikan String
                        // Ini berfungsi untuk menghilankan koma, dan tanda ribuah pada tahun
                        xAxis.setValueFormatter(new IAxisValueFormatter() {
                            @Override
                            public String getFormattedValue(float value, AxisBase axis) {
                                int x = (int) value;
                                if (x > -1 && x < xVals.size()) return xVals.get(x);
                                else return "";
                            }
                        });
                        xAxis.setTextSize(8);

                        //Menghilangkan sumbu Y yang ada di sebelah kanan
                        barChart.getAxisRight().setEnabled(false);

                        // Menghilankan deskripsi pada Chart
                        barChart.getDescription().setEnabled(false);

                        // Set data ke Chart
                        // Tambahkan invalidate setiap kali mengubah data chart
                        barChart.setData(data);
                        barChart.setDragEnabled(true);
                        barChart.invalidate();
                    } else {
                        hideProgress(2);
                        error_txt_cause.setText(response.body().getMessage());
                    }
                } else {
                    hideProgress(2);
                    error_txt_cause.setText(response.message());
                }
            }

            @Override
            public void onFailure(Call<GetBalkePojo> call, Throwable t) {
                hideProgress(2);
                error_txt_cause.setText(t.getMessage());
            }
        });
    }

    private void hideProgress(int value) {
        if (value == 1) {
            progress.setVisibility(View.VISIBLE);
            error_layout.setVisibility(View.GONE);
            barChart.setVisibility(View.GONE);
        } else if (value == 2) {
            progress.setVisibility(View.GONE);
            error_layout.setVisibility(View.VISIBLE);
            barChart.setVisibility(View.GONE);
        } else {
            progress.setVisibility(View.GONE);
            error_layout.setVisibility(View.GONE);
            barChart.setVisibility(View.VISIBLE);
        }
    }
}
