package com.example.contactos;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
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

    //Expxortar para Editar
    private Contacto con;
    private Direccion dir;
    private ArrayList<String> emails;
    private ArrayList<String> notas;
    private ArrayList<Telefono> telefonos;

    private Button buttonLlamar, buttonMandarCorreo;




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

        buttonLlamar = findViewById(R.id.buttonLlamar);
     //   buttonLlamar.setOnClickListener(new );


        //Apartado edicion
        Bundle bundle=getIntent().getExtras();

        if(bundle != null) {
            String dato1 = bundle.getString("ID");
            id = dato1;
            llenarCamposEditar();
        }





    }


    private void llenarCamposEditar(){

        con = dbe.getContactoWithID(id);
        editTextNombre2.setText(con.getNombre());
        editTextApodo2.setText(con.getApodo());
        editTextEmpresa2.setText(con.getEmpresa());
        byte[] bytes = con.getImg();
        imageViewimg2.setImageBitmap(BitmapFactory.decodeByteArray(bytes, 0, bytes.length));

        dir = dbe.getDireccionWithID(id);
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


        telefonos = dbe.getTelefonoWithID(id);
        at = new Adaptador_Telefono(this, dbe.getTelefonoWithID(id));
        list_telefono.setAdapter(at);

        emails = dbe.getCorreoWithID(id);
        arrayAdapterCorreo = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, emails);
        list_correo.setAdapter(arrayAdapterCorreo);

        notas = dbe.getNotasWithID(id);
        arrayAdapterNotas = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, notas);
        list_Notas.setAdapter(arrayAdapterNotas);
    }



    private class openEditarMode implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            //Paso los objetos a la clase añadir y ahi relleno los campos con ellos
            Intent i = new Intent(Ver.this, Anadir.class );
            i.putExtra("CONTACTO", con);
            i.putExtra("DIRECCION", dir);
            i.putExtra("NOTA", notas);
            i.putExtra("EMAIL", emails);
            i.putExtra("TELEFONO", telefonos);
            startActivity(i);


            //Enciar los objetos(Contacto, telefono, email..) a la siguiente vista y ahi lo relleno con esos los campos
            //Al dar click en guardar en la siguiente vista, comparo el objeto enviado con lo que traen los cambios(con.getNombre() != editTextNombre.getText())

        }
    }

}
