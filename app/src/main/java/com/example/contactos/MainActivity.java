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

public class MainActivity extends AppCompatActivity {


    private Button buttonAnadir;
    private ListView listView;
    private DataBaseExecute dbe;
    private EditText editTextBuscarContacto;
    private Adaptador_Home adaptador_home;
    private SearchView searchView;
    private String mQuery;

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
        contacto = dbe.getContacto();
        adaptador_home = new Adaptador_Home(this, contacto);
        this.listView.setAdapter(adaptador_home);
        listView.setTextFilterEnabled(true);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String idd = Integer.toString(contacto.get(position).getId());

                Intent i = new Intent(MainActivity.this, Ver.class );
                i.putExtra("ID", idd);

                startActivity(i);

            }
        });

        searchView =(SearchView) findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                contacto = dbe.getContactoByName(query);
                listView.setAdapter(new Adaptador_Home(MainActivity.this, contacto));
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(TextUtils.isEmpty(newText)){
                    contacto = dbe.getContacto();
                    listView.setAdapter(new Adaptador_Home(MainActivity.this, contacto));
                }else {
                    contacto = dbe.getContactoByName(newText);
                    listView.setAdapter(new Adaptador_Home(MainActivity.this, contacto));
                }
                return false;
            }
        });
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setQuery(mQuery, false);
            }
        });

        setupSearchView();


    }



    private void setupSearchView()
    {
        searchView.setIconifiedByDefault(false);
        searchView.setSubmitButtonEnabled(true);
        searchView.setQueryHint("Buscar...");
    }



    @Override
    public void onRestart() {

        super.onRestart();
        contacto = dbe.getContacto();
        this.listView.setAdapter(new Adaptador_Home(this, contacto));
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