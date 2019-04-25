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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;

public class Anadir extends AppCompatActivity {

    private SQLiteDatabase db=null;
    private DataBaseHelper usdbh;

    private EditText editTextNombre, editTextApodo, editTextEmpresa, editTextTelefono, editTextEmail, editTextNotas;
    private Button buttonAnadir, buttonAbrirGaleria;
    private Button buttonAddNotas;
    private ImageView imageViewimg;
    private Spinner spinnerTipo;
    private long backPressedTime;


    private static final int REQUEST_SELECT_PHOTO = 1;
    private Bitmap bmp;

    private LinearLayout parentLinearLayout;



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

        String []opciones={"FIJO","MOVIL"};
        spinnerTipo = (Spinner) findViewById(R.id.spinnerTipo);
        ArrayAdapter<String> adapterSpinner = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, opciones);
        spinnerTipo.setAdapter(adapterSpinner);

        parentLinearLayout = (LinearLayout) findViewById(R.id.linear_anadir);

        buttonAddNotas = (Button) findViewById(R.id.buttonAddNotas);
        buttonAddNotas.setOnClickListener(new onAddField());



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
            Telefono telefono  = new Telefono(maxid, spinnerTipo.getSelectedItem().toString(), editTextTelefono.getText().toString());
            nuevoRegistroTelefono.put("ID",telefono.getId());
            nuevoRegistroTelefono.put("TIPO",telefono.getTipo());
            nuevoRegistroTelefono.put("TELEFONO",telefono.getTelefono());

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
    public void mensajeNormalContacto()
    {
        Toast mensaje =
                Toast.makeText(getApplicationContext(),
                        "Contacto añadido correctamente", Toast.LENGTH_SHORT);
        mensaje.show();
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





    private class onAddField implements View.OnClickListener  {
        @Override
        public void onClick(View view) {
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View rowView = inflater.inflate(R.layout.diseno_notas, null);
            // Add the new row before the add field button.
            parentLinearLayout.addView(rowView, parentLinearLayout.getChildCount() - 1);
        }
    }



    public void onDelete(View v) {

        parentLinearLayout.removeView((View) v.getParent());


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


    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            finish();
        } else {
            Toast.makeText(this, "Pulsa otra vez para salir.", Toast.LENGTH_SHORT).show();
        }

        backPressedTime = System.currentTimeMillis();
    }




}
