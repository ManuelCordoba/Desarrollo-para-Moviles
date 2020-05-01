package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class Imagenes extends AppCompatActivity {
    Intent i;
    String idUs="",idSe="";
    //Arreglo crea los elementos de la lista
    private String gestionServicios[] = new String[]{"corte ", "cepillado","depilacion","tinte"};
    //Arreglo Asigna imagenes a la lista
    private Integer[] imgid = {
            R.drawable.corte,
            R.drawable.cepillado,
            R.drawable.depilacion,
            R.drawable.tinte,};

    private ListView lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.imagenes);


        i= getIntent();
        idUs=i.getStringExtra("idU");
        System.out.println(idUs);


        AdaptadorImage adapter = new AdaptadorImage(this, gestionServicios, imgid);

        lista = (ListView) findViewById(R.id.mi_listaaa);
        lista.setAdapter(adapter);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String Slecteditem = gestionServicios[+position];
                Toast.makeText(getApplicationContext(), Slecteditem, Toast.LENGTH_SHORT).show
                        ();
                if (position == 0) {
                    Toast.makeText(getApplicationContext(), "Ha seleccionado " +
                            Slecteditem, Toast.LENGTH_SHORT).show();
                    i = new Intent(Imagenes.this, Agenda.class);
//                    i.putExtra("id", Integer.toString(l));
                    idSe="1";
                    i.putExtra("idU",idUs);
                    i.putExtra("idS",idSe);
                    startActivity(i);
                    //i.putExtra("docu",docu);
                }



                else if (position == 1) {
                    Toast.makeText(getApplicationContext(), "Ha seleccionado " +
                            Slecteditem, Toast.LENGTH_SHORT).show();

                    i = new Intent(Imagenes.this, Agenda.class);
                    //i.putExtra("nombre",nombre);
                    /// i.putExtra("docu",docu);
                    idSe="2";
                    i.putExtra("idU",idUs);
                    i.putExtra("idS",idSe);
                    startActivity(i);
                }

                else if (position == 2) {
                    Toast.makeText(getApplicationContext(), "Ha seleccionado " +
                            Slecteditem, Toast.LENGTH_SHORT).show();
                    i = new Intent(Imagenes.this, Agenda.class);
                    //i.putExtra("nombre",nombre);
                    /// i.putExtra("docu",docu);
                    idSe="3";
                    i.putExtra("idU",idUs);
                    i.putExtra("idS",idSe);
                    startActivity(i);
                }



                else if (position == 3) {
                    Toast.makeText(getApplicationContext(), "Ha seleccionado " +
                            Slecteditem, Toast.LENGTH_SHORT).show();
                    i = new Intent(Imagenes.this, Agenda.class);
                    //i.putExtra("nombre",nombre);
                    /// i.putExtra("docu",docu);
                    idSe="4";
                    i.putExtra("idU",idUs);
                    i.putExtra("idS",idSe);
                    startActivity(i);
                }



            }

        }  );








    }
}
