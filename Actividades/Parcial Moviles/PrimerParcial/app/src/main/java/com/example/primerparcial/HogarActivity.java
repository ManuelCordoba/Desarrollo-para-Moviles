package com.example.primerparcial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class HogarActivity extends AppCompatActivity {

    private EditText parent_name_edit;
    private EditText parent_address_edit;
    private EditText parent_location_edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hogar);

        parent_name_edit = findViewById(R.id.parent_name_edit);
        parent_address_edit = findViewById(R.id.parent_address_edit);
        parent_location_edit = findViewById(R.id.parent_location_edit);
    }

    //Metodo para boton regresar
    public void Enviar(View view){
        Intent i = new Intent(this, HomeDataActivity.class);
        i.putExtra("parent_name_edit", parent_name_edit.getText().toString());
        i.putExtra("parent_address_edit", parent_address_edit.getText().toString());
        i.putExtra("parent_location_edit", parent_location_edit.getText().toString());

        startActivity(i);
    }

    //Metodo para boton regresar
    public void Regresar(View view){
        Intent regresar = new Intent(this, MainActivity.class);
        startActivity(regresar);
    }
}
