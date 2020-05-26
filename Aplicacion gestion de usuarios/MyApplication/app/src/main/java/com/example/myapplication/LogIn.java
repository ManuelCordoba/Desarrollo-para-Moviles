package com.example.myapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.util.Calendar;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
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
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class LogIn extends AppCompatActivity {
    Button btnIngre, btnReg;
    EditText txtUser, txtPass;
    String passw, usua;
    String currentTimeString, currentDateString;
    String IP = "10.0.2.2";
    public static final String sitio = "webserv";
    String passwordEncriptacion = "gdsawr";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_in);
        btnIngre = findViewById(R.id.btnIng);
        btnReg = findViewById(R.id.btnReg);
        txtUser = findViewById(R.id.txtId);
        txtPass = findViewById(R.id.txtPas);

        // getSupportActionBar().setDisplayShowHomeEnabled(true);
        btnReg.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {

                                          Intent intent = new Intent(LogIn.this, Registro.class);
                                          startActivity(intent);

                                      }
                                  }
        );

        btnIngre.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            usua = txtUser.getText().toString();
                                            passw = txtPass.getText().toString();

                        if (usua.isEmpty()||passw.isEmpty()) {
                            Toast.makeText(LogIn.this, "Todos los campos deben estar llenos", Toast.LENGTH_SHORT).show();

                        }else{

                                            System.out.println("envio" + txtUser.getText().toString());
                                            new LogIn.ConsultarDatos().execute("http://" + IP + "/" + sitio + "/" + "consulta.php?user=" + txtUser.getText().toString());
                                        }
                                    }}
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
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected void onPostExecute(String result) {
//Estrae la información de la BD
            JSONArray ja = null;
            try {
                ja = new JSONArray(result);

                String pass = desencriptar(ja.getString(2), passwordEncriptacion);
                //Validación de contraseña
                if (usua.equals(ja.getString(1)) && passw.equals(pass)) {

                    if (ja.getString(7).equals("1")) {


                        int id = Integer.parseInt(ja.getString(6));

//Validación de Usuario, contraseña y perfil del Usuario lo que recibe de la IGU contra la BD

                        final Calendar calendar = Calendar.getInstance();
                        calendar.get(Calendar.DATE);
                        currentTimeString = (String.format("%02d:%02d", calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE)));
                        currentDateString = (String.format("%02d/%02d/%4d", calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.MONTH), calendar.get(Calendar.YEAR)));
                        new LogIn.RegistrarInSesion().execute("http://" + IP + "/" + sitio + "/" + "registrar_in_sesion.php?usuario=" + ja.getString(0) + "&fecha=" + currentDateString + "&tiempo=" + currentTimeString);
                        //Pasa los datos Consultados de la BD a la otra Activity
                        Intent i = new Intent(LogIn.this, Menu.class);
                        i.putExtra("fecha", currentDateString);
                        i.putExtra("hora", currentTimeString);
                        i.putExtra("nombre", ja.getString(3));
                        i.putExtra("apellido", ja.getString(4));
                        i.putExtra("id", ja.getString(0));
                        i.putExtra("rol", ja.getString(6));
                        startActivity(i);

                    } else {

                        Toast.makeText(getApplicationContext(), "La cuenta se encuentra desactivada", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Usuario o contraseña incorrecta", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                Toast.makeText(getApplicationContext(), "Usuario o contraseña incorrecta", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Usuario o contraseña incorrecta", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }

        }
    }

    private class RegistrarInSesion extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            // params comes from the execute() call: params[0] is the url.
            try {
                return downloadUrl(urls[0]);
            } catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }


    }

    private String downloadUrl(String myurl) throws IOException {
        Log.i("URL", "" + myurl);
        myurl = myurl.replace(" ", "%20");
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
    }

    public String readIt(InputStream stream, int len) throws IOException,
            UnsupportedEncodingException {
        Reader reader = null;
        reader = new InputStreamReader(stream, "UTF-8");
        char[] buffer = new char[len];
        reader.read(buffer);
        return new String(buffer);
    }

    private String encriptar(String datos, String password) throws Exception {
        SecretKeySpec secretKey = generateKey(password);
        Cipher cipher = Cipher.getInstance(secretKey.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] datosEncriptadosBytes = cipher.doFinal(datos.getBytes());
        String datosEncriptadosString = Base64.encodeToString(datosEncriptadosBytes, Base64.DEFAULT);
        System.out.println(datosEncriptadosString);
        return datosEncriptadosString;
    }

    private SecretKeySpec generateKey(String password) throws Exception {
        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        byte[] key = password.getBytes("UTF-8");
        key = sha.digest(key);
        SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
        return secretKey;
    }

    private String desencriptar(String datos, String password) throws Exception {
        SecretKeySpec secretKey = generateKey(password);
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] datosDescoficados = Base64.decode(datos, Base64.DEFAULT);
        byte[] datosDesencriptadosByte = cipher.doFinal(datosDescoficados);
        String datosDesencriptadosString = new String(datosDesencriptadosByte);
        return datosDesencriptadosString;
    }

}

