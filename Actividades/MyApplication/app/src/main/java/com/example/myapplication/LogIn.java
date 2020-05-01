package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;

public class LogIn extends AppCompatActivity {
Button btnIngre;
EditText txtUser,txtPass;
    String IP = "10.0.2.2";
    public static final String sitio = "webservice";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_in);
        btnIngre=findViewById(R.id.btnIng);
        txtUser=findViewById(R.id.txtId);
        txtPass=findViewById(R.id.txtPas);
       // getSupportActionBar().setDisplayShowHomeEnabled(true);

        btnIngre.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

            new LogIn.ConsultarDatos().execute("http://"+IP+"/"+sitio+"/"+"consultar_perfil.php?idcli="+txtUser.getText().toString());
         }
        }
        );
    }

    private class ConsultarDatos extends AsyncTask<String, Void, String> {
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
        @Override
        protected void onPostExecute(String result) {
//Estrae la información de la BD
            JSONArray ja = null;
            try {
                ja = new JSONArray(result);
                //Validación de contraseña

                byte[] passPlana= txtPass.getText().toString().getBytes();
                BigInteger md5Data=null;

                try{
                    md5Data= new BigInteger(1, md5.encryptMD5(passPlana));
                }catch (Exception e){
                    e.printStackTrace();

                }

                String passEncryp = md5Data.toString();

                System.out.println(passEncryp);
                if(txtUser.getText().toString().equals(ja.getString(1)) &&
                        passEncryp.equals(ja.getString(2)) ){
//Validación de Usuario, contraseña y perfil del Usuario lo que recibe de la IGU contra la BD

                        //Pasa los datos Consultados de la BD a la otra Activity
                    Intent i=new Intent(LogIn.this, Menu.class);
                        i.putExtra("id",ja.getString(0));
                        i.putExtra("nombre",ja.getString(3));
                        i.putExtra("apellido",ja.getString(4));
                        i.putExtra("rol",ja.getString(6));
                        startActivity(i);




                }} catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    private String downloadUrl(String myurl) throws IOException {
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
}

