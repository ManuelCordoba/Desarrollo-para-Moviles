package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class Menu extends AppCompatActivity {
    String id;
    Intent servicio;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Intent var2 =getIntent();
        String nom=var2.getStringExtra("nombre");
        String ape=var2.getStringExtra("apellido");
        id= var2.getStringExtra("id");

        TextView txvb=findViewById(R.id.txvBien);
        txvb.setText(nom+" "+ape);
    }
    public void Agenda(View view){
        servicio =  new Intent(this, Encuesta.class);
        servicio.putExtra("idU",id);
        startActivity(servicio);
    }



    public void mi(View view){
        servicio =  new Intent(this, Resultados.class);
        servicio.putExtra("idU",id);
        startActivity(servicio);
    }

}
