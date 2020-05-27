package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AdaptadorImage extends ArrayAdapter<String> {
    private final Activity context;
    private final String[] itemname;
    private final Integer[] integers;

    public AdaptadorImage(Activity context, String[] itemname, Integer[] integers) {
        super(context, R.layout.recurso, itemname);

        this.context = context;
        this.itemname = itemname;
        this.integers = integers;
    }



    public View getView(int posicion, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.recurso,null,true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.nombre_item);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        txtTitle.setText(itemname[posicion]);
        imageView.setImageResource(integers[posicion]);
        return rowView;

    }



}
