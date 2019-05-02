package com.example.contactos;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Adaptador_Telefono extends BaseAdapter {

    private Context context;
    private List<Telefono> listaClientes;

    public Adaptador_Telefono(Context context, ArrayList<Telefono> listaClientes) {
        this.context = context;
        this.listaClientes = listaClientes;
    }

    @Override
    public int getCount() {
        return this.listaClientes.size();
    }

    @Override
    public Object getItem(int position) {
        return this.listaClientes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View rowView = convertView;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.lista_telefono, parent, false);
        }

        TextView nombre = (TextView) rowView.findViewById(R.id.itemTelefono);
        TextView nombre2 = (TextView) rowView.findViewById(R.id.subitemTelefono);

        Telefono tel = this.listaClientes.get(position);


            nombre.setText(tel.getTelefono().toString());
            nombre2.setText(tel.getTipo().toString());

        return rowView;
    }
}