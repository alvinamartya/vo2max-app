package com.example.atlit.Activity;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.atlit.Model.Balke;
import com.example.atlit.Model.Beep;
import com.example.atlit.Model.Cooper;
import com.example.atlit.Model.getBalkeModel;
import com.example.atlit.R;
import com.example.atlit.RetrofitModel.getBeepModel;
import com.example.atlit.RetrofitModel.getCooperModel;
import com.example.atlit.Utils.ApiClient;
import com.example.atlit.Utils.ApiInterface;
import com.example.atlit.Utils.Loginsharedpreference;
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
    private Loginsharedpreference loginsharedpreference;
    private ApiInterface mApiInterface;
    private List<Balke> balkes1, balkes2, balkes3, balkes4;
    private List<Cooper> coopers1, coopers2, coopers3, coopers4;
    private List<Beep> beeps1, beeps2, beeps3, beeps4;
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
        loginsharedpreference = new Loginsharedpreference(this);
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
        Call<getBeepModel> beepData = loginsharedpreference.getUserLogin().getAkses().toLowerCase().equals("pelatih") ? mApiInterface.beepDataPelatih(cal.get(Calendar.MONTH)) : mApiInterface.beepdata(cal.get(Calendar.MONTH), loginsharedpreference.getUserLogin().getId());
        beepData.enqueue(new Callback<getBeepModel>() {
            @Override
            public void onResponse(Call<getBeepModel> call, Response<getBeepModel> response) {
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
            public void onFailure(Call<getBeepModel> call, Throwable t) {
                hideProgress(2);
                error_txt_cause.setText(t.getMessage());
            }
        });
    }

    private void fillCoopers() {
        Call<getCooperModel> cooperData = loginsharedpreference.getUserLogin().getAkses().toLowerCase().equals("pelatih") ? mApiInterface.cooperDataPelatih(cal.get(Calendar.MONTH)) : mApiInterface.cooperdata(cal.get(Calendar.MONTH), loginsharedpreference.getUserLogin().getId());
        cooperData.enqueue(new Callback<getCooperModel>() {
            @Override
            public void onResponse(Call<getCooperModel> call, Response<getCooperModel> response) {
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
            public void onFailure(Call<getCooperModel> call, Throwable t) {
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

    private float valueChartBalke(List<Balke> balkes) {
        float result = 0;
        if (balkes.size() > 0) {
            for (Balke x : balkes) result += x.getVo2max();
            return result / balkes.size();
        }
        return 0;
    }

    private float valueChartBeep(List<Beep> beeps) {
        float result = 0;
        if (beeps.size() > 0) {
            for (Beep x : beeps) result += x.getVo2max();
            return result / beeps.size();
        }
        return 0;
    }

    private float valueChartCooper(List<Cooper> coopers) {
        float result = 0;
        if (coopers.size() > 0) {
            for (Cooper x : coopers) result += x.getVo2max();
            return result / coopers.size();
        }
        return 0;
    }

    private void fillBalkes() {
        Call<getBalkeModel> balkeData = loginsharedpreference.getUserLogin().getAkses().toLowerCase().equals("pelatih") ? mApiInterface.balkeDataPelatih(cal.get(Calendar.MONTH)) : mApiInterface.balkedata(cal.get(Calendar.MONTH), loginsharedpreference.getUserLogin().getId());
        balkeData.enqueue(new Callback<getBalkeModel>() {
            @Override
            public void onResponse(Call<getBalkeModel> call, Response<getBalkeModel> response) {
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
            public void onFailure(Call<getBalkeModel> call, Throwable t) {
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
