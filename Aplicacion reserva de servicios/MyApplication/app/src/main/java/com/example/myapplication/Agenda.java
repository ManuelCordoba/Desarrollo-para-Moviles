package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.util.Calendar;

public class Agenda extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
Button btnRes;
ImageButton btnFecha,btnHora;
EditText txtFecha,txtHora;
String currentTimeString, currentDateString;
    TextView n;
    String ssesion,idSer,idUsu;
    String IP = "10.0.2.2";
    public static final String sitio = "webservice";
int dia,mes,ano,minuto,hora;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda);

        btnFecha=findViewById(R.id.btnFecha);
        btnHora=findViewById(R.id.btnHora);
        btnRes=findViewById(R.id.btnRes);

        txtFecha=findViewById(R.id.txtFecha);
        txtHora=findViewById(R.id.txtHora);
        Intent u =getIntent();

        idUsu=u.getStringExtra("idU");
        System.out.println("usuario:  "+idUsu);
        idSer=u.getStringExtra("idS");
        System.out.println("servicio: "+idSer);




        btnFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DialogFragment datePicker = new FechaPicker();
                datePicker.show(getSupportFragmentManager(),"date picker");

                }
        });


        btnHora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TiempoPiker();
                timePicker.show(getSupportFragmentManager(), "time picker");
            }
        });

        btnRes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int idSer=1;

                new Agenda.RegistrarReserva().execute("http://"+IP+"/"+sitio+"/"+"registrar_reserva.php?usuario="+idUsu+"&servicio="+idSer+"&fecha="+currentDateString+"&tiempo="+currentTimeString);
            }
        }
        );
    }



    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        String amPm;
        boolean st=false;
        if(hourOfDay<=11){
            if(hourOfDay==0){
                hourOfDay=12;}
            amPm="AM";
        }else{
            amPm="PM";
            if(hourOfDay!=12){
                hourOfDay=hourOfDay-12;
            }}
        currentTimeString=(String.format("%02d:%02d", hourOfDay, minute) + amPm);
        txtHora.setText("     "+currentTimeString);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        month++;
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR,year);
        c.set(Calendar.MONTH,month);
        c.set(Calendar.DAY_OF_MONTH,dayOfMonth);
        //currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        currentDateString =(String.format("%02d/%02d/%4d",dayOfMonth, month, year));
                txtFecha.setText("   "+currentDateString);

    }
    private class RegistrarReserva extends AsyncTask<String, Void, String> {
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
        }}
    public String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
        Reader reader = null;
        reader = new InputStreamReader(stream, "UTF-8");
        char[] buffer = new char[len];
        reader.read(buffer);
        return new String(buffer);
    }}

