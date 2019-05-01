package com.example.contactos;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static android.view.ViewGroup.*;
import static com.example.contactos.R.id.editTextNotas;
import static com.example.contactos.R.id.edit_query;

public class Anadir extends AppCompatActivity {

    private SQLiteDatabase db=null;
    private DataBaseHelper usdbh;
    private final String []opciones={"FIJO","MOVIL"};


    private EditText editTextNombre, editTextApodo, editTextEmpresa, editTextTelefono, editTextEmail, editTextNotas;
    private Button buttonAnadir, buttonAbrirGaleria;
    private Button buttonAddNotas, buttonAddTelefonos;
    private ImageView imageViewimg;
    private Spinner spinnerTipo;
    private long backPressedTime;
    private ArrayAdapter<String> adapterSpinner;

    private EditText editTextNotas2;

    private List<EditText> listaNotas, listaTelefonos;
    private List<Spinner> spinner_listaTelefonos;


    private static final int REQUEST_SELECT_PHOTO = 1;
    private Bitmap bmp;

    private LinearLayout parentLinearLayout;
    private EditText edittext_notas, edittext_telefonos;
    private Spinner spinner_telefonos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anadir);
        checkPermission();

        usdbh =  new DataBaseHelper(this, "DBcontactos", null, 1);


        editTextNombre = (EditText) findViewById(R.id.editTextNombre);
        editTextApodo = (EditText) findViewById(R.id.editTextApodo);
        editTextEmpresa = (EditText) findViewById(R.id.editTextEmpresa);
        editTextTelefono = (EditText) findViewById(R.id.editTextTelefono);
        editTextEmail =(EditText) findViewById(R.id.editTextCorreo);
        editTextNotas = (EditText)findViewById(R.id.editTextNotas);

        imageViewimg = (ImageView) findViewById(R.id.imageViewimg);
        buttonAbrirGaleria = (Button) findViewById(R.id.buttonAbrirGaleria);
        buttonAbrirGaleria.setOnClickListener(new abrirGaleria());

        buttonAnadir = (Button) findViewById(R.id.buttonAnadir);
        buttonAnadir.setOnClickListener(new anadirContacto());

        spinnerTipo = (Spinner) findViewById(R.id.spinnerTipo);
        adapterSpinner = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, opciones);
        spinnerTipo.setAdapter(adapterSpinner);

        parentLinearLayout = (LinearLayout) findViewById(R.id.linear_anadir);


        buttonAddNotas = (Button) findViewById(R.id.buttonAddNotas);
        buttonAddNotas.setOnClickListener(new onAddFieldNotas());

        buttonAddTelefonos = (Button) findViewById(R.id.buttonAddTelefono);
        buttonAddTelefonos.setOnClickListener(new onAddFieldTelefonos());


        listaNotas = new ArrayList<>();
        listaTelefonos = new ArrayList<>();
        spinner_listaTelefonos = new ArrayList<>();



        listaTelefonos.add(editTextNotas);
        listaNotas.add(editTextNotas);
        spinner_listaTelefonos.add(spinner_telefonos);

    }

    private class abrirGaleria implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            Intent ii = new Intent(Intent.ACTION_PICK) ;
            ii.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    MediaStore.Images.Media.CONTENT_TYPE) ;
            startActivityForResult(ii,REQUEST_SELECT_PHOTO) ;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == REQUEST_SELECT_PHOTO && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            imageViewimg.setImageBitmap(BitmapFactory.decodeFile(picturePath));

        }
    }


    private class anadirContacto implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            db= usdbh.getWritableDatabase();

            int maxid = getLastId();


            BitmapDrawable drawable = (BitmapDrawable) imageViewimg.getDrawable();
            bmp = drawable.getBitmap();

            ByteArrayOutputStream baos = new ByteArrayOutputStream(20480);
            bmp.compress(Bitmap.CompressFormat.PNG, 0 , baos);
            byte[] blob = baos.toByteArray();

            ContentValues nuevoRegistroContacto = new ContentValues();
            Contacto contacto = new Contacto(maxid, editTextNombre.getText().toString(), editTextApodo.getText().toString(), editTextEmpresa.getText().toString(), blob);
            nuevoRegistroContacto.put("ID", contacto.getId());
            nuevoRegistroContacto.put("NOMBRE", contacto.getNombre());
            nuevoRegistroContacto.put("APODO", contacto.getApodo());
            nuevoRegistroContacto.put("EMPRESA", contacto.getEmpresa());
            nuevoRegistroContacto.put("IMG", contacto.getImg());

            ContentValues  nuevoRegistroEmail = new ContentValues();
            Email email = new Email(maxid, editTextEmail.getText().toString());
            nuevoRegistroEmail.put("ID", email.getId());
            nuevoRegistroEmail.put("EMAIL",email.getEmail());

            ContentValues nuevoRegistroNotas = new ContentValues();
            Notas notas = new Notas(maxid, editTextNotas.getText().toString());
            nuevoRegistroNotas.put("ID", notas.getId());
            nuevoRegistroNotas.put("NOTA",notas.getNota());


            ContentValues nuevoRegistroTelefono = new ContentValues();


            Telefono telefono = new Telefono(maxid, spinnerTipo.getSelectedItem().toString(), editTextTelefono.getText().toString());
            nuevoRegistroTelefono.put("ID", telefono.getId());
            nuevoRegistroTelefono.put("TIPO", telefono.getTipo());
            nuevoRegistroTelefono.put("TELEFONO", telefono.getTelefono());
            db.insert("TELEFONO", null, nuevoRegistroTelefono);

            for(int i = 0; i < listaTelefonos.size(); i ++) {

                Telefono telefono2 = new Telefono(maxid, spinner_listaTelefonos.get(i).getSelectedItem().toString(), listaTelefonos.get(i).getText().toString());
                nuevoRegistroTelefono.put("ID", telefono2.getId());
                nuevoRegistroTelefono.put("TIPO", telefono2.getTipo());
                nuevoRegistroTelefono.put("TELEFONO", telefono2.getTelefono());
                db.insert("TELEFONO", null, nuevoRegistroTelefono);
            }
            try{
                db.insert("CONTACTO", null, nuevoRegistroContacto);
                db.insert("EMAIL", null, nuevoRegistroEmail);
                db.insert("NOTAS", null, nuevoRegistroNotas);
                mensajeNormalContacto();

            }catch (Exception e){
                //Añadir error aqui
            }


        }
    }




    public int getLastId() {

        String sql = "SELECT MAX(ID) FROM CONTACTO";
        db = usdbh.getReadableDatabase();
        Cursor c = db.rawQuery(sql, null);

        if (c.moveToFirst()) {
            return c.getInt(0)+1;
        }
        return 0;
    }





    private class onAddFieldNotas implements View.OnClickListener {
        @Override
        public void onClick(View view) {

            if(listaNotas.size()<3) {
                edittext_notas = new EditText(getApplicationContext());
                edittext_notas.setId(listaNotas.size() + 1);

                int indice = listaNotas.size();

                for (int i = 0; i < listaNotas.size(); i++) {
                    System.out.println("--------------Notas:" + listaNotas.get(i).getText().toString());
                }

                listaNotas.add(edittext_notas);
                parentLinearLayout.addView(listaNotas.get(indice));
            }else{

                maxCamp();

            }

        }
    }

    private class onAddFieldTelefonos implements View.OnClickListener {
        @Override
        public void onClick(View view) {

            if(listaTelefonos.size()<3) {


                int indice = listaTelefonos.size();
                System.out.println("---"+ indice);

                edittext_telefonos = new EditText(editTextTelefono.getContext());

                edittext_telefonos.setId(listaTelefonos.size() + 1);
                edittext_telefonos.setHint("Telefono " + (listaTelefonos.size()));

                spinner_telefonos = new Spinner(getApplicationContext());
                spinner_telefonos.setId(indice);
                spinner_telefonos.setAdapter(adapterSpinner);


                for (int i = 0; i < listaTelefonos.size(); i++) {
                    System.out.println("--------------Tel:" + listaTelefonos.get(i).getText().toString());
                }

                listaTelefonos.add(edittext_telefonos);
                spinner_listaTelefonos.add(spinner_telefonos);


                parentLinearLayout.addView(listaTelefonos.get(indice));
                parentLinearLayout.addView(spinner_listaTelefonos.get(indice));


            }else{


                maxCamp();


            }

        }
    }



    private void checkPermission() {

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {

            } else {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        1);
            }
        }
    }

    public void mensajeNormalContacto()
    {
        Toast mensaje =
                Toast.makeText(getApplicationContext(),
                        "Contacto añadido correctamente", Toast.LENGTH_SHORT);
        mensaje.show();
    }


    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            finish();
        } else {
            Toast.makeText(this, "Pulsa otra vez para salir.", Toast.LENGTH_SHORT).show();
        }

        backPressedTime = System.currentTimeMillis();
    }

    public void maxCamp() {

            Toast.makeText(this, "Solo se pueden añadir 3 campos", Toast.LENGTH_SHORT).show();

    }


}