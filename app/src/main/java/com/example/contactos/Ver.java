package com.example.contactos;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class Ver extends AppCompatActivity {

    private DataBaseExecute dbe;
    private String id;

    private EditText editTextNombre2, editTextApodo2, editTextEmpresa2;
    private EditText editTextCalle, editTextPiso, editTextNumero, editTextPuerta, editTextCodigo, editTextCiudad;
    private ImageView imageViewimg2;
    private Spinner spinnerTipo, spinnerProvincia;
    private long backPressedTime;
    private ArrayAdapter<String> adapterSpinner, adapterSpinnerPronvincia;


    private Button buttonEditar;

    //listviews dinamicos telefonos
    private ListView list_telefono;
    private Adaptador_Telefono at;


    //listviw correo
    private ListView list_correo;
    private ArrayAdapter<String> arrayAdapterCorreo;


    //listviw notas
    private ListView list_Notas;
    private ArrayAdapter<String> arrayAdapterNotas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver);
        getSupportActionBar().hide();
        dbe = new DataBaseExecute(this);

        editTextNombre2 = (EditText) findViewById(R.id.editTextNombre2);
        editTextNombre2.setEnabled(false);
        editTextApodo2 = (EditText) findViewById(R.id.editTextApodo2);
        editTextApodo2.setEnabled(false);
        editTextEmpresa2 = (EditText) findViewById(R.id.editTextEmpresa2);
        editTextEmpresa2.setEnabled(false);
        editTextCalle = (EditText) findViewById(R.id.editTextCalle);
        editTextCalle.setEnabled(false);
        editTextPiso = (EditText) findViewById(R.id.editTextPiso);
        editTextPiso.setEnabled(false);
        editTextNumero = (EditText) findViewById(R.id.editTextNumero);
        editTextNumero.setEnabled(false);
        editTextPuerta = (EditText) findViewById(R.id.editTextPuerta);
        editTextPuerta.setEnabled(false);
        editTextCodigo = (EditText) findViewById(R.id.editTextCodigo);
        editTextCodigo.setEnabled(false);
        editTextCiudad = (EditText) findViewById(R.id.editTextCiudad);
        editTextCiudad.setEnabled(false);
        imageViewimg2 = (ImageView) findViewById(R.id.imageViewimg2);
        imageViewimg2.setEnabled(false);
        spinnerProvincia = (Spinner) findViewById(R.id.spinnerProvincia);
        spinnerProvincia.setEnabled(false);



        list_telefono = (ListView) findViewById(R.id.list_telefono2);
        list_telefono.setEnabled(false);



        list_correo = (ListView) findViewById(R.id.list_correo2);
        list_correo.setEnabled(false);



        list_Notas = (ListView) findViewById(R.id.list_notas2);
        list_Notas.setEnabled(false);

        buttonEditar = (Button) findViewById(R.id.buttonEditar);
        buttonEditar.setOnClickListener(new openEditarMode());


        //Apartado edicion
        Bundle bundle=getIntent().getExtras();

        if(bundle != null) {
            String dato1 = bundle.getString("ID");
            id = dato1;
            llenarCamposEditar();
        }


    }


    private void llenarCamposEditar(){

        Contacto con = dbe.getContactoWithID(id);
        editTextNombre2.setText(con.getNombre());
        editTextApodo2.setText(con.getNombre());
        editTextEmpresa2.setText(con.getEmpresa());
        byte[] bytes = con.getImg();
        imageViewimg2.setImageBitmap(BitmapFactory.decodeByteArray(bytes, 0, bytes.length));
        Direccion dir = dbe.getDireccionWithID(id);
        editTextCiudad.setText(dir.getCiudad());
        editTextCalle.setText(dir.getCalle());
        editTextCodigo.setText(dir.getCodigoPostal());
        editTextPiso.setText(dir.getPiso());
        editTextPuerta.setText(dir.getPuerta());
        editTextNumero.setText(dir.getNumero());

        List<String> list = new ArrayList<String>();
        list.add(dir.getProvincia());
        adapterSpinnerPronvincia = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,list);
        spinnerProvincia.setAdapter(adapterSpinnerPronvincia);

        at = new Adaptador_Telefono(this, dbe.getTelefonoWithID(id));
        list_telefono.setAdapter(at);

        arrayAdapterCorreo = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, dbe.getCorreoWithID(id));
        list_correo.setAdapter(arrayAdapterCorreo);

        arrayAdapterNotas = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, dbe.getNotasWithID(id));
        list_Notas.setAdapter(arrayAdapterNotas);
    }



    private class openEditarMode implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            Intent i = new Intent(Ver.this, Anadir.class );
            i.putExtra("ID", id);
            startActivity(i);



        }
    }

}
