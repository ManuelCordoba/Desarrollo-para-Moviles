package com.example.myapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

public class Rol extends AppCompatActivity {
    ListView lista,listaR;
    String[] elementos,elementosR;
    JSONArray ja = null;
    String docu, miurl="";
    String IP = "10.0.2.2";
    Button btnEdit,btnReg;

    String variable1,variable2,variable3,variable4,variable5;
    public static final String sitio = "webserv";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rol);
        lista=(ListView) findViewById(R.id.LISTA);
        btnReg=findViewById(R.id.btnRegR);
        btnEdit=findViewById(R.id.btnEdR);

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(Rol.this);
                View nView = getLayoutInflater().inflate(R.layout.agregar_rol,null);
                builder.setView(nView);
                final AlertDialog dialog = builder.create();
                dialog.show();
                final EditText id_rol = nView.findViewById(R.id.txt_rol);
                final EditText nombre = nView.findViewById(R.id.txt_nom);
                Button btnRe = nView.findViewById(R.id.btnRealizar);


                    btnRe.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(id_rol.getText().toString().isEmpty() || nombre.getText().toString().isEmpty()) {
                                Toast.makeText(Rol.this, "Todos los campos deben estar llenos", Toast.LENGTH_SHORT).show();
                            }else{
                            new Rol.DeleteInsertar().execute("http://" + IP + "/" + sitio + "/guardar_rol.php?id_rol=" + id_rol.getText().toString() + "&nom=" + nombre.getText().toString());
                            Toast.makeText(Rol.this, "Se ha agregado", Toast.LENGTH_SHORT).show();
                            init2();
                            dialog.dismiss();}
                        }
                    });



            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(Rol.this);
                View nView = getLayoutInflater().inflate(R.layout.editar_rol,null);
                builder.setView(nView);
                final AlertDialog dialog = builder.create();
                dialog.show();
                final EditText id_rol = nView.findViewById(R.id.txt_rol);
                final EditText nombre = nView.findViewById(R.id.txt_nom);
                Button btnRe = nView.findViewById(R.id.btnRealizar);
                btnRe.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(id_rol.getText().toString().isEmpty() || nombre.getText().toString().isEmpty()) {
                            Toast.makeText(Rol.this, "Todos los campos deben estar llenos", Toast.LENGTH_SHORT).show();
                        }else{
                        new Rol.DeleteInsertar().execute("http://"+IP+"/"+sitio+"/editar_rol.php?id_rol="+id_rol.getText().toString()+"&nom="+nombre.getText().toString());
                        Toast.makeText(Rol.this,"Se ha modificado",Toast.LENGTH_SHORT).show();
                        init2();
                        dialog.dismiss();}
                    }
                });

            }
        });

        init2();
    }

    private class DeleteInsertar extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            // params comes from the execute() call: params[0] is the url.
            try {
                return downloadUrl(urls[0]);
            } catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }
        // onPostExecute displays the results of the AsyncTask.
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected void onPostExecute(String result) {




        }
    }
    public void init2(){
        int c=2;
        miurl="http://"+IP+"/"+sitio+"/"+"consultar_roles.php?";
        new Rol.ConsultarDatos().execute(miurl);
    }
    public void mostrar() {
        ArrayAdapter<String> adapter1 = new ArrayAdapter(Rol.this,android.R.layout.simple_list_item_1, elementos);
        lista.setAdapter(adapter1);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override

            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {//Al hacer click en cualquiera de los elementos de la lista
            }
        });
    }

    private class ConsultarDatos extends AsyncTask<String, Void, String> {
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
                elementosR=new String[ja.length()];

                //id.setText(ja.getString(0));// Aqui imprime todo el arreglo asociativo
                if(ja.length()!=0) {
                    for (int i = 0; i < ja.length(); i++) {
                        org.json.JSONObject obj = ja.getJSONObject(i);

                        variable1 = obj.getString("nombre_rol");
                        variable2 = obj.getString("id_rol");

                        elementos[i] = "Rol : "+variable1+" \nid_rol :"+ variable2;


                    }
                    mostrar();
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
    private String downloadUrl(String myurl) throws IOException {
        Log.i("URL", "" + myurl);
        myurl = myurl.replace(" ", "%20");
        InputStream is = null;try {
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
            String contentAsString = readIt(is);
            return contentAsString;
            // Makes sure that the InputStream is closed after the app is
            // finished using it.
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

    public String readIt(InputStream stream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
        StringBuilder buffer = new StringBuilder();
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            buffer.append(inputLine);
        }
        return buffer.toString();
    }

}