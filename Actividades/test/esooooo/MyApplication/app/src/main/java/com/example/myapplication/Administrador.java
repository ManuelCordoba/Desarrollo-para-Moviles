package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

public class Administrador extends AppCompatActivity {
    Button btnUser,btnRol,btnPer,btnPerRol;
    Intent intent;

    String variable1,variable2,variable3,variable4,variable5;
    public static final String sitio = "webserv";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrador);
        btnUser=findViewById(R.id.btnUsu);
        btnRol=findViewById(R.id.btnRol);
        btnPer=findViewById(R.id.btnPer);
        btnPerRol=findViewById(R.id.btnRolPer);
        btnUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             intent= new Intent(Administrador.this,Usuario.class);
             startActivity(intent);
            }
        });
        btnRol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                intent= new Intent(Administrador.this,Rol.class);
                startActivity(intent);
            }
        });
        btnPer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                intent= new Intent(Administrador.this,Permiso.class);
                startActivity(intent);
            }
        });

        btnPerRol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                intent= new Intent(Administrador.this,PermisoRol.class);
                startActivity(intent);
            }
        });

    }




}