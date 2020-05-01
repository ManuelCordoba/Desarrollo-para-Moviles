package com.example.primerparcial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //Metodo para el boton home
    public void BotonHome(View view){
        Toast.makeText(this, "Clic en el boton de Home", Toast.LENGTH_SHORT).show();
        Intent home =  new Intent(this, HogarActivity.class);
        startActivity(home);
    }

    //Metodo para el boton imagenes
    public void BotonImagenes(View view){
        Toast.makeText(this, "Clic en el boton de Imagenes", Toast.LENGTH_SHORT).show();
        Intent images =  new Intent(this, ImagesActivity.class);
        startActivity(images);
    }

    //Metodo para el boton agenda
    public void BotonAgenda(View view){
        Toast.makeText(this, "Clic en el boton de Agenda", Toast.LENGTH_SHORT).show();
        Intent scheduler =  new Intent(this, SchedulerActivity.class);
        startActivity(scheduler);
    }
}
