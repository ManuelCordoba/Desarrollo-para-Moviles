package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;



public class Lista_consulta extends AppCompatActivity {
    String variable1,variable2,variable3,variable4,variable5;
    ListView list;
    //Button consulta;
    String docu, miurl="";
    String[] elementos;
    String[] dni;
    String[] nombres;
    String[] telefono;
    String[] email;
    String IP = "10.0.2.2";
    public static final String sitio = "webservice";
    JSONArray ja = null;
    int contador=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_consulta);

        // consulta=(Button)findViewById(R.id.consultar);
        list=(ListView) findViewById(R.id.lista);



        init2();




    }



    public void init2(){
        new ConsultarDatos().execute("http://"+IP+"/"+sitio+"/consulta_general.php?");
        new ConsultarDatos().execute(miurl);
    }

    /*
        public void mostrar() {
            ArrayAdapter<String> adapter1 = new ArrayAdapter(Lista_consulta.this,
                    android.R.layout.simple_list_item_1, elementos);
            list.setAdapter(adapter1);
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override

                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
                {//Al hacer click en cualquiera de los elementos de la lista
                }
            });
        }*/
    public void mostrar() {

        ArrayAdapter<String> adapter1 = new ArrayAdapter(Lista_consulta.this, android.R.layout.simple_list_item_checked, elementos);
        list.setAdapter(adapter1);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {//Obtiene la posisción de la lista

                SparseBooleanArray sparseBooleanArray = list.getCheckedItemPositions();
            }
        });
    }

    @SuppressLint("StaticFieldLeak")
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

                System.out.println("ANTES DEL DEL JSON");
                ja = new JSONArray(result);
                elementos=new String[ja.length()];
                System.out.println("CREAMOS EL STRING");
                //id.setText(ja.getString(0));// Aqui imprimetodo el arreglo asociativo
                if(ja.length()!=0) {
                    System.out.println("CREAMOS EL CONDICIONAL");
                    for (int i = 0; i < ja.length(); i++) {

                        JSONObject obj = ja.getJSONObject(i);
                        System.out.println("LO OBTENGO AQUI ");
                        variable1 = obj.getString("id_res");
                        variable2 = obj.getString("id_usu");
                        variable3 = obj.getString("id_ser");
                        variable4 = obj.getString("time");
                        variable5 = obj.getString("date");
                        System.out.println("YA ESTAN");
                        elementos[i] = variable1+" "+ variable2+" "+ variable3+" "+ variable4+" "+variable5+ ";";

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
        InputStream is = null;
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
            String contentAsString = readIt(is);
            return contentAsString;
            // Makes sure that the InputStream is closed after the app is
            // finished using it.
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                is.close();
            }
        }
        return myurl;
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
