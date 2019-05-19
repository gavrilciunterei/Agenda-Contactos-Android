package com.example.contactos;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class Ver extends AppCompatActivity implements OnMapReadyCallback {

    private DataBaseExecute dbe;
    private String id;

    private TextView editTextNombre2, editTextApodo2, editTextEmpresa2;
    private TextView editTextCalle, editTextPiso, editTextNumero, editTextPuerta, editTextCodigo, editTextCiudad;
    private ImageView imageViewimg2;
    private Spinner spinnerProvincia;


    //listviews dinamicos telefonos
    private NonScrollListView list_telefono;


    //listviw correo
    private NonScrollListView list_correo;


    //listviw notas
    private NonScrollListView list_Notas;

    //Expxortar para Editar
    private Contacto con;
    private Direccion dir;
    private ArrayList<String> emails;
    private ArrayList<String> notas;
    private ArrayList<Telefono> telefonos;

    private GoogleMap mapa;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver);
        getSupportActionBar().hide();
        dbe = new DataBaseExecute(this);

        editTextNombre2 = findViewById(R.id.editTextNombre2);
        editTextApodo2 = findViewById(R.id.editTextApodo2);
        editTextEmpresa2 = findViewById(R.id.editTextEmpresa2);
        editTextCalle = findViewById(R.id.editTextCalle2);
        editTextPiso = findViewById(R.id.editTextPiso2);
        editTextNumero = findViewById(R.id.editTextNumero2);
        editTextPuerta = findViewById(R.id.editTextPuerta2);
        editTextCodigo = findViewById(R.id.editTextCodigo2);
        editTextCiudad = findViewById(R.id.editTextCiudad2);
        imageViewimg2 =  findViewById(R.id.imageViewimg2);
        imageViewimg2.setEnabled(false);
        spinnerProvincia = findViewById(R.id.spinnerProvincia3);
        spinnerProvincia.setEnabled(false);

        list_telefono = findViewById(R.id.list_telefono2);
        list_telefono.setEnabled(false);


        list_correo = findViewById(R.id.list_correo2);
        list_correo.setEnabled(false);


        list_Notas = findViewById(R.id.list_notas2);
        list_Notas.setEnabled(false);

        Button buttonEditar = findViewById(R.id.buttonEditar);
        buttonEditar.setOnClickListener(new openEditarMode());


        Button buttonLlamar = findViewById(R.id.buttonLlamar);
        buttonLlamar.setOnClickListener(new abrirMenu());
        registerForContextMenu(buttonLlamar);


        Button buttonMandarCorreo = findViewById(R.id.buttonMandarCorreo);
        buttonMandarCorreo.setOnClickListener(new abrirMenu());
        registerForContextMenu(buttonMandarCorreo);


        Button buttonEliminar = findViewById(R.id.buttonEliminar);
        buttonEliminar.setOnClickListener(new eliminarContacto());



        //Apartado edicion
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {

            id = bundle.getString("ID");
            llenarCamposEditar();
        }

    }

    @Override
    public void onRestart() {

        super.onRestart();
        llenarCamposEditar();
    }



    private class abrirMenu implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            openContextMenu(v);
        }

    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if (v.getId() == R.id.buttonLlamar) {

            int tamano = telefonos.size();
            for (int i = 0; i < tamano; i++) {
                menu.add(1, i, i, telefonos.get(i).getTelefono());
            }
            menu.setHeaderTitle("Llamar a:");

        } else if (v.getId() == R.id.buttonMandarCorreo) {
            int tamano = emails.size();
            int a = 3;
            for (int i = 0; i < tamano; i++) {
                menu.add(1, a, a, emails.get(i));
                a++;
            }
            menu.setHeaderTitle("Enviar email a:");
        }
    }

    public boolean onContextItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case 0:
                llamar(telefonos.get(0).getTelefono());
                return true;
            case 1:
                llamar(telefonos.get(1).getTelefono());
                return true;
            case 2:
                llamar(telefonos.get(2).getTelefono());
                return true;
            case 3:
                sendEmail(emails.get(0));
                return true;
            case 4:
                sendEmail(emails.get(1));
                return true;
            case 5:
                sendEmail(emails.get(2));
                return true;
        }

        return super.onOptionsItemSelected(item);

    }

    private void llamar(String telefono) {

        startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", telefono, null)));

    }

    private void sendEmail(String email) {

        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
        i.putExtra(Intent.EXTRA_SUBJECT, "Asunto..");
        i.putExtra(Intent.EXTRA_TEXT, "Ejemplo de cuerpo de texto");
        try {
            startActivity(Intent.createChooser(i, "Elige la app para enviar..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(Ver.this, "No hay clientes de correo instalados.", Toast.LENGTH_SHORT).show();
        }

    }


    private void llenarCamposEditar() {

        con = dbe.getContactoWithID(id);
        if(!con.getNombre().isEmpty()) {
            editTextNombre2.setText(con.getNombre());
            editTextNombre2.setVisibility(View.VISIBLE);
        }else{
            editTextNombre2.setVisibility(View.INVISIBLE);
        }
        if(!con.getApodo().isEmpty()) {
             editTextApodo2.setText(con.getApodo());
            editTextApodo2.setVisibility(View.VISIBLE);
        }else{
            editTextApodo2.setVisibility(View.INVISIBLE);
        }
        if(!con.getEmpresa().isEmpty()) {
            editTextEmpresa2.setText(con.getEmpresa());
            editTextEmpresa2.setVisibility(View.VISIBLE);
        }else{
            editTextEmpresa2.setVisibility(View.INVISIBLE);
        }
        byte[] bytes = con.getImg();
        imageViewimg2.setImageBitmap(BitmapFactory.decodeByteArray(bytes, 0, bytes.length));

        dir = dbe.getDireccionWithID(id);
        editTextCiudad.setText(dir.getCiudad());
        editTextCalle.setText(dir.getCalle());
        editTextCodigo.setText(dir.getCodigoPostal());
        editTextPiso.setText(dir.getPiso());
        editTextPuerta.setText(dir.getPuerta());
        editTextNumero.setText(dir.getNumero());
        List<String> list = new ArrayList<>();
        list.add(dir.getProvincia());
        ArrayAdapter<String> adapterSpinnerPronvincia;
        adapterSpinnerPronvincia = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, list);
        spinnerProvincia.setAdapter(adapterSpinnerPronvincia);


        telefonos = dbe.getTelWithID(id);
        Adaptador_Telefono at = new Adaptador_Telefono(this, telefonos);
        list_telefono.setAdapter(at);

        emails = dbe.getCorreoWithID(id);
        ArrayAdapter<String> arrayAdapterCorreo;
        arrayAdapterCorreo = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, emails);
        list_correo.setAdapter(arrayAdapterCorreo);

        notas = dbe.getNotasWithID(id);
        ArrayAdapter<String> arrayAdapterNotas;
        arrayAdapterNotas = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, notas);
        list_Notas.setAdapter(arrayAdapterNotas);

        cargarMapa();

    }


    private class openEditarMode implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            //Paso los objetos a la clase añadir y ahi relleno los campos con ellos
            Intent i = new Intent(Ver.this, Anadir.class);
            i.putExtra("CONTACTO", con);
            i.putExtra("DIRECCION", dir);
            i.putExtra("NOTA", notas);
            i.putExtra("EMAIL", emails);
            i.putExtra("TELEFONO", telefonos);
            startActivity(i);

        }
    }

    private class eliminarContacto implements View.OnClickListener {

        @Override
        public void onClick(View view) {


            AlertDialog.Builder dialogo1 = new AlertDialog.Builder(Ver.this);
            dialogo1.setTitle("Importante");
            dialogo1.setIcon(R.drawable.ic_error_black_24dp);
            dialogo1.setMessage("¿ Está seguro que desea eliminar el contacto ?");
            dialogo1.setCancelable(false);
            dialogo1.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogo1, int id) {
                    String idEmilinar = Integer.toString(con.getId());
                    dbe.borrarPorID(idEmilinar, "CONTACTO");
                    dbe.borrarPorID(idEmilinar, "DIRECCION");
                    dbe.borrarPorID(idEmilinar, "EMAIL");
                    dbe.borrarPorID(idEmilinar, "NOTAS");
                    dbe.borrarPorID(idEmilinar, "TELEFONO");
                    finish();
                }
            });
            dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogo1, int id) {
                }
            });
            dialogo1.show();

        }
    }

    private void cargarMapa() {
        SupportMapFragment mapaFrag = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapa);
        // Le decimos que el mapa se cargue con esta actividad
        assert mapaFrag != null;
        mapaFrag.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapa = googleMap;
        personalizarPropiedades(); //Modificamos alguna propiedad del mapa
    }


    public void personalizarPropiedades() {
        // Mostramos controles zoom sobre el mapa
        mapa.getUiSettings().setZoomControlsEnabled(true);


        String address = dir.getNumero() + " " + dir.getCalle()+ " " + dir.getCiudad() + " " + dir.getProvincia()+ " "  + dir.getCodigoPostal();

        Geocoder geoCoder = new Geocoder(this);
        double lat=0;
        double lng = 0;
        try {
            List<Address> addressList = geoCoder.getFromLocationName(address, 1);
            if (addressList != null && addressList.size() > 0) {
                 lat = addressList.get(0).getLatitude();
                 lng = addressList.get(0).getLongitude();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        LatLng dir = new LatLng(lat, lng);
        mapa.setMinZoomPreference(16);

        mapa.moveCamera(CameraUpdateFactory.newLatLng(dir));
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(dir);
        mapa.addMarker(markerOptions);


    }


}
