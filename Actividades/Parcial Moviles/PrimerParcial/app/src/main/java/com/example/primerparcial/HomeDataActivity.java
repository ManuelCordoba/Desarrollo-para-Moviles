package com.example.primerparcial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class HomeDataActivity extends AppCompatActivity {

    private TextView parent_name_data;
    private TextView parent_address_data;
    private TextView parent_location_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_data);

        parent_name_data = findViewById(R.id.parent_name_data);
        parent_address_data = findViewById(R.id.parent_address_data);
        parent_location_data = findViewById(R.id.parent_location_data);


        String parent_name_edit = getIntent().getStringExtra("parent_name_edit");
        String parent_address_edit = getIntent().getStringExtra("parent_address_edit");
        String parent_location_edit = getIntent().getStringExtra("parent_location_edit");

        parent_name_data.setText(parent_name_edit);
        parent_address_data.setText(parent_address_edit);
        parent_location_data.setText(parent_location_edit);
    }

    //Metodo para boton regresar
    public void Regresar(View view){
        Intent regresar = new Intent(this, HogarActivity.class);
        startActivity(regresar);
    }
}
