package com.example.atlit.Activity;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.atlit.Model.Parameter;
import com.example.atlit.R;

import java.util.ArrayList;
import java.util.List;

public class ParameterActivity extends AppCompatActivity {

    private RecyclerView rvParameter;
    private List<Parameter> parameterList;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parameter);
        initToolbar();
        rvParameter = findViewById(R.id.rvParameter);
        fillParameterList();
        ParameterAdapeter adapter = new ParameterAdapeter(parameterList,this);
        rvParameter.setAdapter(adapter);
    }

    private void fillParameterList() {
        parameterList = new ArrayList<>();
        parameterList.add(new Parameter(1,8.5,7));
        parameterList.add(new Parameter(2,9.0,8));
        parameterList.add(new Parameter(3,9.5,8));
        parameterList.add(new Parameter(4,10.0,8));
        parameterList.add(new Parameter(5,10.5,9));
        parameterList.add(new Parameter(6,11.0,9));
        parameterList.add(new Parameter(7,11.5,10));
        parameterList.add(new Parameter(8,12.0,10));
        parameterList.add(new Parameter(9,12.5,10));
        parameterList.add(new Parameter(10,13.0,11));
        parameterList.add(new Parameter(11,13.5,11));
        parameterList.add(new Parameter(12,14.0,12));
        parameterList.add(new Parameter(13,14.5,12));
        parameterList.add(new Parameter(14,15.0,13));
        parameterList.add(new Parameter(15,15.5,13));
        parameterList.add(new Parameter(16,16.0,13));
        parameterList.add(new Parameter(17,16.5,14));
        parameterList.add(new Parameter(18,17.0,14));
        parameterList.add(new Parameter(19,17.5,15));
        parameterList.add(new Parameter(20,18.0,15));
        parameterList.add(new Parameter(21,18.5,15));

    }

    private void initToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.parameter));
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ParameterActivity.this, BeepActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private class ParameterAdapeter extends RecyclerView.Adapter<ParameterAdapeter.ViewHolder> {

        private List<Parameter> parameters;
        private Context context;

        public ParameterAdapeter(List<Parameter> parameters, Context context) {
            this.parameters = parameters;
            this.context = context;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(context).inflate(R.layout.row_parameter,viewGroup,false);
            ViewHolder holder = new ViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
            Parameter item = parameters.get(i);
            viewHolder.tvStage.setText("Stage : " + item.getStage());
            viewHolder.tvSpeed.setText("Speed : " + item.getSpeed());
            viewHolder.tvLaps.setText("Laps : " + item.getLaps());
        }


        @Override
        public int getItemCount() {
            if(parameters == null) return 0;
            return parameters.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            private TextView tvStage, tvSpeed, tvLaps;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                tvStage = itemView.findViewById(R.id.tvStage);
                tvSpeed = itemView.findViewById(R.id.tvSpeed);
                tvLaps = itemView.findViewById(R.id.tvLaps);
            }
        }
    }
}
