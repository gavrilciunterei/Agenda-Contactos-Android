package com.example.contactos;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class Adaptador extends BaseAdapter {

    private Context context;
    private List<Contacto> listaClientes;

    public Adaptador(Context context, ArrayList<Contacto> listaClientes) {
        this.context = context;
        this.listaClientes= listaClientes;
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
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.lista_datos, parent, false);
        }

        TextView nombre= (TextView) rowView.findViewById(R.id.item);
        ImageView foto= (ImageView) rowView.findViewById(R.id.imageViewContacto);

        Contacto contacto = this.listaClientes.get(position);
        nombre.setText(contacto.getNombre());

        byte[] bytes = contacto.getImg();
        foto.setImageBitmap(BitmapFactory.decodeByteArray(bytes, 0, bytes.length));

        return rowView;
    }

}