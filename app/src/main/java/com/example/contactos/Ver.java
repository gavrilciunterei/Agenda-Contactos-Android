package com.example.contactos;

import android.content.ClipData;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

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
    private NonScrollListView list_telefono;
    private Adaptador_Telefono at;


    //listviw correo
    private NonScrollListView list_correo;
    private ArrayAdapter<String> arrayAdapterCorreo;


    //listviw notas
    private NonScrollListView list_Notas;
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


        list_telefono = (NonScrollListView ) findViewById(R.id.list_telefono2);
        list_telefono.setEnabled(false);


        list_correo = (NonScrollListView) findViewById(R.id.list_correo2);
        list_correo.setEnabled(false);



        list_Notas = (NonScrollListView) findViewById(R.id.list_notas2);
        list_Notas.setEnabled(false);

        buttonEditar = (Button) findViewById(R.id.buttonEditar);
        buttonEditar.setOnClickListener(new openEditarMode());

        buttonLlamar = findViewById(R.id.buttonLlamar);
        buttonLlamar.setOnClickListener(new abrirMenu());
        registerForContextMenu(buttonLlamar);


        buttonMandarCorreo = findViewById(R.id.buttonMandarCorreo);
        buttonMandarCorreo.setOnClickListener(new abrirMenu());
        registerForContextMenu(buttonMandarCorreo);



        //Apartado edicion
        Bundle bundle=getIntent().getExtras();

        if(bundle != null) {
            String dato1 = bundle.getString("ID");
            id = dato1;
            llenarCamposEditar();
        }

    }




    private class abrirMenu implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            openContextMenu(v);
        }

    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        if(v.getId() == R.id.buttonLlamar) {

            int tamaño = telefonos.size();
            for (int i = 0; i < tamaño; i++) {
                menu.add(1, i, i, telefonos.get(i).getTelefono());
            }
            menu.setHeaderTitle("Llamar a:");

        }else if(v.getId() == R.id.buttonMandarCorreo){
            int tamaño = emails.size();
            for (int i = 0; i < tamaño; i++) {
                int a = 3;
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
                llamar(emails.get(1));
                return true;
            case 5:
                sendEmail(emails.get(2));
                return true;
        }
        return super.onOptionsItemSelected(item);

    }
    private void llamar(String telefono){

        startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", telefono, null)));

    }
    private void sendEmail(String email){

        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL  , new String[]{email});
        i.putExtra(Intent.EXTRA_SUBJECT, "Asunto..");
        i.putExtra(Intent.EXTRA_TEXT   , "Ejemplo de cuerpo de texto");
        try {
            startActivity(Intent.createChooser(i, "Elige la app para enviar..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(Ver.this, "No hay clientes de correo instalados.", Toast.LENGTH_SHORT).show();
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
        at = new Adaptador_Telefono(this, telefonos);
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

        }
    }

}
