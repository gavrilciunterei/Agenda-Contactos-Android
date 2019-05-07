package com.example.contactos;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class Adaptador_Home extends BaseAdapter implements Filterable {

    private Context context;
    private List<Contacto> listaClientes;
    private List<Contacto> orig;

    public Adaptador_Home(Context context, ArrayList<Contacto> listaClientes) {
        this.context = context;
        this.listaClientes= listaClientes;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                final FilterResults oReturn = new FilterResults();
                final ArrayList<Contacto> results = new ArrayList<Contacto>();
                if (orig == null)
                    orig = listaClientes;
                if (constraint != null) {
                    if (orig != null && orig.size() > 0) {
                        for (final Contacto g : orig) {
                            if (g.getNombre().toLowerCase()
                                    .contains(constraint.toString()))
                                results.add(g);
                        }
                    }
                    oReturn.values = results;
                }
                return oReturn;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint,
                                          FilterResults results) {
                listaClientes = (ArrayList<Contacto>) results.values;
                notifyDataSetChanged();
            }
        };

    }

    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
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
            rowView = inflater.inflate(R.layout.lista_datos, parent, false);
        }

        TextView nombre= (TextView) rowView.findViewById(R.id.item);
        ImageView foto= (ImageView) rowView.findViewById(R.id.imageViewContacto);


        Contacto contacto = this.listaClientes.get(position);

        nombre.setText(contacto.getNombre().toLowerCase());
        byte[] bytes = contacto.getImg();
        foto.setImageBitmap(BitmapFactory.decodeByteArray(bytes, 0, bytes.length));

        return rowView;
    }


}