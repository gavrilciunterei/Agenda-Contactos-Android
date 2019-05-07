package com.example.contactos;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {


    private Button buttonAnadir;
    private ListView listView;
    private DataBaseExecute dbe;
    private EditText editTextBuscarContacto;
    private Adaptador_Home adaptador_home;
    private SearchView searchView;
    private ArrayList<Contacto> contacto;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        checkPermission();

        dbe = new DataBaseExecute(this);

        buttonAnadir = (Button)findViewById(R.id.buttonAnadir);
        buttonAnadir.setOnClickListener(new newAnadir());


        this.listView = (ListView) findViewById(R.id.lsvContactos);
        adaptador_home = new Adaptador_Home(this, dbe.getContacto());
        this.listView.setAdapter(adaptador_home);
        listView.setTextFilterEnabled(true);
        contacto = dbe.getContacto();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("--------"+ position);

                String idd = Integer.toString(contacto.get(position).getId());


                Intent i = new Intent(MainActivity.this, Ver.class );
                    i.putExtra("ID", idd);

                startActivity(i);

            }
        });

        searchView =(SearchView) findViewById(R.id.searchView);

        setupSearchView();

    }
    private void setupSearchView()
    {
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(this);
        searchView.setSubmitButtonEnabled(true);
        searchView.setQueryHint("Buscar...");
    }


    public boolean onQueryTextChange(String newText)
    {

        if (TextUtils.isEmpty(newText)) {
            listView.clearTextFilter();
        } else {
            listView.setFilterText(newText);
        }
        return true;
    }

    public boolean onQueryTextSubmit(String query)
    {
        return false;
    }


    @Override
    public void onRestart() {

        super.onRestart();
        this.listView.setAdapter(new Adaptador_Home(this, dbe.getContacto()));
    }


    private class newAnadir implements View.OnClickListener {

        @Override
        public void onClick(View view) {

            Intent i = new Intent(MainActivity.this, Anadir.class );
            startActivity(i);

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


}