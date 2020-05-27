package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Registro extends AppCompatActivity {

    String IP = "10.0.2.2";
    public static final String sitio = "webservice";
    int rol=1;
    EditText txtNom,txtApe,txtUser,txtEmail,txtPass;
    Button btnReg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        txtNom=findViewById(R.id.editNom);
        txtApe=findViewById(R.id.editApe);
        txtUser=findViewById(R.id.editUser);
        txtEmail=findViewById(R.id.editEma);
        txtPass=findViewById(R.id.editCon);
        btnReg=findViewById(R.id.buttonReg);

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    byte[] passPlana= txtPass.getText().toString().getBytes();
                    BigInteger md5Data=null;

                    try{
                        md5Data= new BigInteger(1, md5.encryptMD5(passPlana));
                    }catch (Exception e){
                        e.printStackTrace();

                    }

                    String passEncryp = md5Data.toString();


                new Registro.CargarDatos().execute("http://"+IP+"/"+sitio+"/guardar.php?username="
                        +txtUser.getText().toString()
                        +"&nomusu="+txtNom.getText().toString()
                        +"&apeusu="+txtApe.getText().toString()
                        +"&emausu="+txtEmail.getText().toString()
                        +"&rol="+rol
                        +"&password="+passEncryp);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private class CargarDatos extends AsyncTask<String, Void, String> {
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
            Toast.makeText(getApplicationContext(), "Los datos se guardaron Exitosamente", Toast.LENGTH_LONG).show();
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
            URL url = new URL(myurl);
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
    }public String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
        Reader reader = null;
        reader = new InputStreamReader(stream, "UTF-8");
        char[] buffer = new char[len];
        reader.read(buffer);
        return new String(buffer);
    }

    private String encriptar(String datos, String password) throws Exception{
        SecretKeySpec secretKey = generateKey(password);
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] datosEncriptadosBytes = cipher.doFinal(datos.getBytes());
        String datosEncriptadosString = Base64.encodeToString(datosEncriptadosBytes, Base64.DEFAULT);
        return datosEncriptadosString;
    }

    private SecretKeySpec generateKey(String password) throws Exception{
        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        byte[] key = password.getBytes("UTF-8");
        key = sha.digest(key);
        SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
        return secretKey;
    }
}
