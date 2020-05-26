package com.example.myapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.util.Calendar;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;

public class Menu extends AppCompatActivity {
    String id,rol;
    Intent intent,servicio;
    String currentTimeString, currentDateString;
    Button btnSalir,btnEncu,btnMiEncu,btnAdmin,btnPer;
    JSONArray ja = null;
    String[] elementos;
    String IP = "10.0.2.2";
    public static final String sitio = "webserv";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        servicio =getIntent();
        String rol=servicio.getStringExtra("rol");
        String nom=servicio.getStringExtra("nombre");
        String ape=servicio.getStringExtra("apellido");
        id= servicio.getStringExtra("id");
        TextView txtUsu=findViewById(R.id.txvUsu);
        txtUsu.setText(nom+" "+ape);
        btnEncu=findViewById(R.id.btnCu);
        btnMiEncu= findViewById(R.id.btnResul);
        btnSalir= findViewById(R.id.btnSal);
        btnAdmin= findViewById(R.id.btnAdmin);
        btnPer= findViewById(R.id.btnPer);
        btnSalir= findViewById(R.id.btnSal);

        btnAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                servicio= new Intent(Menu.this,Administrador.class);
                startActivity(servicio);
            }
        });
        new Menu.ConsultarRol().execute("http://"+IP+"/"+sitio+"/"+"consultar_rol.php?id_rol="+rol);


        btnSalir.setOnClickListener(new View.OnClickListener() {
                                        @RequiresApi(api = Build.VERSION_CODES.N)
                                        @Override
                                        public void onClick(View v) {
            final Calendar calendar= Calendar.getInstance();
            calendar.get(Calendar.DATE);
            currentTimeString=(String.format("%02d:%02d", calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE)));
            currentDateString =(String.format("%02d/%02d/%4d",calendar.get(Calendar.DAY_OF_MONTH),calendar.get(Calendar.MONTH),calendar.get(Calendar.YEAR)));
            new Menu.RegistrarOutSesion().execute("http://"+IP+"/"+sitio+"/"+"registrar_out_sesion.php?usuario="+
                    id+"&fechaO="+currentDateString+"&fechaI="+servicio.getStringExtra("fecha")+"&tiempoI="+
                    servicio.getStringExtra("hora")+"&tiempoO="+currentTimeString);

            servicio = new Intent(Menu.this, LogIn.class);
            startActivity(servicio);

    }
    }
        );
    }


    private class ConsultarRol extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            // params comes from the execute() call: params[0] is the url.
            try {
                //String cadena=urls[0];
                return downloadUrl(urls[0]);
            } catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            try {
                ja = new JSONArray(result);
                elementos=new String[ja.length()];

                //id.setText(ja.getString(0));// Aqui imprime todo el arreglo asociativo
                if(ja.length()!=0) {
                    for (int i = 0; i < ja.length(); i++) {
                        org.json.JSONObject obj = ja.getJSONObject(i);


                        if(obj.getString("id_permiso").equals("1")){

                            btnEncu.setVisibility(View.VISIBLE);}
                        if(obj.getString("id_permiso").equals("2")){

                            btnMiEncu.setVisibility(View.VISIBLE);}
                        if(obj.getString("id_permiso").equals("3")){

                            btnAdmin.setVisibility(View.VISIBLE); }
                        if(obj.getString("id_permiso").equals("4")){

                            btnPer.setVisibility(View.VISIBLE);}






                    }
                   }
                else{
                    Toast.makeText(getApplicationContext(), "No se ha creado ningún asignatura",
                            Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    private class RegistrarOutSesion extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            // params comes from the execute() call: params[0] is the url.
            try {
                return downloadUrl(urls[0]);
            } catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }


    }private String downloadUrl(String myurl) throws IOException {
        Log.i("URL",""+myurl);
        myurl = myurl.replace(" ","%20");
        InputStream is = null;
        // Only display the first 500 characters of the retrieved
        // web page content.
        int len = 500;
        try {
            java.net.URL url = new java.net.URL(myurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            // Starts the query
            conn.connect();
            int response = conn.getResponseCode();
            Log.d("respuesta", "The response is: " + response);
            is = conn.getInputStream();
            // Convert the InputStream into a string
            String contentAsString = readIt(is, len);
            return contentAsString;
            // Makes sure that the InputStream is closed after the app is
            // finished using it.
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }public String readIt(InputStream stream, int len) throws IOException,
            UnsupportedEncodingException {
        Reader reader = null;
        reader = new InputStreamReader(stream, "UTF-8");
        char[] buffer = new char[len];
        reader.read(buffer);
        return new String(buffer);
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
    @Override
    public void onBackPressed (){
        Toast.makeText(getApplicationContext(), "Debe cerrar sesion", Toast.LENGTH_LONG).show();
    }

}
