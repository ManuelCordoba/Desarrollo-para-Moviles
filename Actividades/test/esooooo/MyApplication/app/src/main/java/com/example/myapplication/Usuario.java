package com.example.myapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
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
import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Usuario extends AppCompatActivity {
    ListView lista;
    String[] elementos, elementosR;
    JSONArray ja = null;
    EditText txtNom, txtApe, txtUser, txtEmail, txtPass, txtRol;
    String  miurl = "";
    String apiKeyEncriptada ="0SPrEK0JntQ2qCm9cPEabw?man=",pass;
    String passwordEncriptacion = "gdsawr";
    String IP = "10.0.2.2";
    Intent intent;
    int estado;

    String variable1, variable2, variable3, variable4, variable5, usu;
    public static final String sitio = "webserv";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario);
        lista = (ListView) findViewById(R.id.LISTA);

        init2();
    }

    public void init2() {
        int c = 2;
        miurl = "http://" + IP + "/" + sitio + "/" + "consultar_usuarios.php?";
        new Usuario.ConsultarDatos().execute(miurl);
    }

    public void mostrar() {
        ArrayAdapter<String> adapter1 = new ArrayAdapter(Usuario.this, android.R.layout.simple_list_item_1, elementos);
        lista.setAdapter(adapter1);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override

            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                final AlertDialog.Builder builder = new AlertDialog.Builder(Usuario.this);
                final View nView = getLayoutInflater().inflate(R.layout.activity_gestionar_usuario, null);
                builder.setView(nView);
                final AlertDialog dialog = builder.create();
                dialog.show();
                txtNom = nView.findViewById(R.id.editNom);
                txtApe = nView.findViewById(R.id.editApe);
                txtUser = nView.findViewById(R.id.editUser);
                txtEmail = nView.findViewById(R.id.editEma);
                txtPass = nView.findViewById(R.id.editCon);
                txtRol = nView.findViewById(R.id.editRol);
                final Button btnElim = nView.findViewById(R.id.btnElim);
                final Button btnGuardar = nView.findViewById(R.id.btnGuard);
                try {
                    final org.json.JSONObject obj = ja.getJSONObject(i);
                    txtNom.setText(obj.getString("nombre_usu"));
                    txtApe.setText(obj.getString("apellido_usu"));
                    txtUser.setText(obj.getString("username"));
                    txtPass.setText(obj.getString("password"));
                    txtEmail.setText(obj.getString("email_usu"));
                    txtRol.setText(obj.getString("id_rol"));
                    usu = obj.getString("id_usu");

                    if (obj.getString("estado").equals("1")) {

                        btnElim.setBackgroundColor(Color.rgb(228, 9, 9));
                        btnElim.setText("Desactivar");
                        estado = 1;

                    } else {

                        btnElim.setBackgroundColor(Color.rgb(32, 146, 28));
                        btnElim.setText("Activar");
                        estado = 0;

                    }

                    btnElim.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (estado == 1) {
                                btnElim.setBackgroundColor(Color.rgb(32, 146, 28));
                                btnElim.setText("Activar");
                                estado = 0;

                                new Usuario.DeleteInsertar().execute("http://" + IP + "/" + sitio + "/" + "estado_usuario.php?estado=" + estado + "&id_usuario=" + usu);
                                dialog.dismiss();
                                init2();

                            } else {
                                btnElim.setBackgroundColor(Color.rgb(228, 9, 9));
                                btnElim.setText("Desactivar");
                                estado = 1;
                                new Usuario.DeleteInsertar().execute("http://" + IP + "/" + sitio + "/" + "estado_usuario.php?estado=" + estado + "&id_usuario=" + usu);
                                dialog.dismiss();
                                init2();
                            }
                        }
                    });

                    btnGuardar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if (txtNom.getText().toString().isEmpty() || txtApe.getText().toString().isEmpty() || txtEmail.getText().toString().isEmpty()
                                || txtRol.getText().toString().isEmpty() || txtApe.getText().toString().isEmpty() || txtPass.getText().toString().isEmpty()) {
                                Toast.makeText(Usuario.this, "Todos los campos deben estar llenos", Toast.LENGTH_SHORT).show();
                            } else {

                                try {
                                    pass= encriptar(txtPass.getText().toString(),passwordEncriptacion);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                new Usuario.DeleteInsertar().execute("http://" + IP + "/" + sitio + "/update_usuario.php?username="
                                        + txtUser.getText().toString()
                                        + "&nomusu=" + txtNom.getText().toString()
                                        + "&apeusu=" + txtApe.getText().toString()
                                        + "&emausu=" + txtEmail.getText().toString()
                                        + "&rol=" + txtRol.getText().toString()
                                        + "&id_usuario=" + usu
                                        + "&password=" + pass);
                                dialog.dismiss();
                                init2();
                            }

                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }
    private SecretKeySpec generateKey(String password) throws Exception{
        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        byte[] key = password.getBytes("UTF-8");
        key = sha.digest(key);
        SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
        return secretKey;
    }
    private String encriptar(String datos, String password) throws Exception{
        SecretKeySpec secretKey = generateKey(password);
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] datosEncriptadosBytes = cipher.doFinal(datos.getBytes());
        String datosEncriptadosString = Base64.encodeToString(datosEncriptadosBytes, Base64.DEFAULT);
        return datosEncriptadosString;
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
                elementos = new String[ja.length()];

                //id.setText(ja.getString(0));// Aqui imprime todo el arreglo asociativo
                if (ja.length() != 0) {
                    for (int i = 0; i < ja.length(); i++) {
                        org.json.JSONObject obj = ja.getJSONObject(i);
                        System.out.println("HSDFKJSFAHASFKJASFKJ");

                        variable1 = obj.getString("nombre_usu");
                        variable2 = obj.getString("apellido_usu");
                        variable3 = obj.getString("username");
                        variable4 = obj.getString("password");
                        variable5 = obj.getString("email_usu");

                        System.out.println(variable1);

                        elementos[i] = "Nombre : " + variable1 + "      Apellido : " + variable2 + "    Username : " + variable3 + " \nPassword : " + variable4 + " \nEmail : " + variable5;

                    }
                    mostrar();
                } else {
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