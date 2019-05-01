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
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

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



    //listviews dinamicos
    private Button buttonAddTelefono;
    private ArrayList<String> arrayListTelefonos;
    private ListView list_telefono;

    private ArrayAdapter<String> arrayAdapter;

    private static final int REQUEST_SELECT_PHOTO = 1;
    private Bitmap bmp;


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

        buttonAddTelefono = (Button) findViewById(R.id.buttonAddTelefono);
        buttonAddTelefono.setOnClickListener(new addViewTelefono());

        arrayListTelefonos = new ArrayList<>();
        list_telefono = (ListView) findViewById(R.id.list_telefono);
        arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arrayListTelefonos);
        list_telefono.setAdapter(arrayAdapter);

        list_telefono.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                arrayAdapter.remove(arrayListTelefonos.get(position));
                return true;

            }
        });

    }
    private class addViewTelefono implements View.OnClickListener {

        @Override
        public void onClick(View view) {

            arrayListTelefonos.add(editTextTelefono.getText().toString());
            arrayAdapter.notifyDataSetChanged();

        }
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


            try{
                db.insert("CONTACTO", null, nuevoRegistroContacto);
                db.insert("EMAIL", null, nuevoRegistroEmail);
                db.insert("NOTAS", null, nuevoRegistroNotas);
                db.insert("TELEFONO", null, nuevoRegistroTelefono);
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