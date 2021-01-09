package com.app.atlit.ui.activity;

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

import com.app.atlit.model.pojo.ParameterPojo;
import com.app.atlit.R;

import java.util.ArrayList;
import java.util.List;

public class ParameterActivity extends AppCompatActivity {

    private RecyclerView rvParameter;
    private List<ParameterPojo> parameterPojoList;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parameter);
        initToolbar();
        rvParameter = findViewById(R.id.rvParameter);
        fillParameterList();
        ParameterAdapeter adapter = new ParameterAdapeter(parameterPojoList,this);
        rvParameter.setAdapter(adapter);
    }

    private void fillParameterList() {
        parameterPojoList = new ArrayList<>();
        parameterPojoList.add(new ParameterPojo(1,8.5,7));
        parameterPojoList.add(new ParameterPojo(2,9.0,8));
        parameterPojoList.add(new ParameterPojo(3,9.5,8));
        parameterPojoList.add(new ParameterPojo(4,10.0,8));
        parameterPojoList.add(new ParameterPojo(5,10.5,9));
        parameterPojoList.add(new ParameterPojo(6,11.0,9));
        parameterPojoList.add(new ParameterPojo(7,11.5,10));
        parameterPojoList.add(new ParameterPojo(8,12.0,10));
        parameterPojoList.add(new ParameterPojo(9,12.5,10));
        parameterPojoList.add(new ParameterPojo(10,13.0,11));
        parameterPojoList.add(new ParameterPojo(11,13.5,11));
        parameterPojoList.add(new ParameterPojo(12,14.0,12));
        parameterPojoList.add(new ParameterPojo(13,14.5,12));
        parameterPojoList.add(new ParameterPojo(14,15.0,13));
        parameterPojoList.add(new ParameterPojo(15,15.5,13));
        parameterPojoList.add(new ParameterPojo(16,16.0,13));
        parameterPojoList.add(new ParameterPojo(17,16.5,14));
        parameterPojoList.add(new ParameterPojo(18,17.0,14));
        parameterPojoList.add(new ParameterPojo(19,17.5,15));
        parameterPojoList.add(new ParameterPojo(20,18.0,15));
        parameterPojoList.add(new ParameterPojo(21,18.5,15));

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

        private final List<ParameterPojo> parameterPojos;
        private final Context context;

        public ParameterAdapeter(List<ParameterPojo> parameterPojos, Context context) {
            this.parameterPojos = parameterPojos;
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
            ParameterPojo item = parameterPojos.get(i);
            viewHolder.tvStage.setText("Stage : " + item.getStage());
            viewHolder.tvSpeed.setText("Speed : " + item.getSpeed());
            viewHolder.tvLaps.setText("Laps : " + item.getLaps());
        }


        @Override
        public int getItemCount() {
            if(parameterPojos == null) return 0;
            return parameterPojos.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            private final TextView tvStage;
            private final TextView tvSpeed;
            private final TextView tvLaps;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                tvStage = itemView.findViewById(R.id.tvStage);
                tvSpeed = itemView.findViewById(R.id.tvSpeed);
                tvLaps = itemView.findViewById(R.id.tvLaps);
            }
        }
    }
}
