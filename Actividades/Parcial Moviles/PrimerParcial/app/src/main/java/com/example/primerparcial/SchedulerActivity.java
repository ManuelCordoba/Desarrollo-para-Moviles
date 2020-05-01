package com.example.primerparcial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class SchedulerActivity extends AppCompatActivity {

    private EditText schedule_date_edit;
    private EditText schedule_subject_edit;
    private EditText schedule_activity_edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheduler);

        schedule_date_edit = findViewById(R.id.schedule_date_edit);
        schedule_subject_edit = findViewById(R.id.schedule_subject_edit);
        schedule_activity_edit = findViewById(R.id.schedule_activity_edit);
    }

    //Metodo para boton regresar
    public void Enviar(View view){
        Intent i = new Intent(this, SchedulerDataActivity.class);
        i.putExtra("schedule_date_edit", schedule_date_edit.getText().toString());
        i.putExtra("schedule_subject_edit", schedule_subject_edit.getText().toString());
        i.putExtra("schedule_activity_edit", schedule_activity_edit.getText().toString());

        startActivity(i);
    }

    //Metodo para boton regresar
    public void Regresar(View view){
        Intent regresar = new Intent(this, MainActivity.class);
        startActivity(regresar);
    }
}
