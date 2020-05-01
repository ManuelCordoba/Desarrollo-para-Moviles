package com.example.primerparcial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class SchedulerDataActivity extends AppCompatActivity {

    private TextView schedule_date_data;
    private TextView schedule_subject_data;
    private TextView schedule_activity_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheduler_data);

        schedule_date_data = findViewById(R.id.schedule_date_data);
        schedule_subject_data = findViewById(R.id.schedule_subject_data);
        schedule_activity_data = findViewById(R.id.schedule_activity_data);


        String schedule_date_edit = getIntent().getStringExtra("schedule_date_edit");
        String schedule_subject_edit = getIntent().getStringExtra("schedule_subject_edit");
        String schedule_activity_edit = getIntent().getStringExtra("schedule_activity_edit");

        schedule_date_data.setText(schedule_date_edit);
        schedule_subject_data.setText(schedule_subject_edit);
        schedule_activity_data.setText(schedule_activity_edit);
    }

    //Metodo para boton regresar
    public void Regresar(View view){
        Intent regresar = new Intent(this, SchedulerActivity.class);
        startActivity(regresar);
    }
}
