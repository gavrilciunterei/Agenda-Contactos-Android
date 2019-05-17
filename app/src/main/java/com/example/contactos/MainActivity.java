package com.example.contactos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private ListView listView;
    private DataBaseExecute dbe;
    private SearchView searchView;
    private ArrayList<Contacto> contacto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        checkPermission();

        dbe = new DataBaseExecute(this);

        Button buttonAnadir = findViewById(R.id.buttonAnadir);
        buttonAnadir.setOnClickListener(new newAnadir());


        this.listView = findViewById(R.id.lsvContactos);
        contacto = dbe.getContacto();
        Adaptador_Home adaptador_home = new Adaptador_Home(this, contacto);
        this.listView.setAdapter(adaptador_home);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String idd = Integer.toString(contacto.get(position).getId());

                Intent i = new Intent(MainActivity.this, Ver.class );
                i.putExtra("ID", idd);
                startActivity(i);

            }
        });

        searchView =findViewById(R.id.searchView);
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


        setupSearchView();
        dbe.close();
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

        if(!Permisos.Check_FINE_LOCATION(MainActivity.this)){
            Permisos.Request_FINE_LOCATION(MainActivity.this,1);
        }
        if(!Permisos.Check_CALL_PHONE(MainActivity.this)){
            Permisos.Request_CALL_PHONE(MainActivity.this,2);
        }
        if(!Permisos.Check_READ_EXTERNAL_STORAGE(MainActivity.this)) {
            Permisos.Request_READ_EXTERNAL_STORAGE(MainActivity.this,3);
        }
        if(!Permisos.Check_INTERNET(MainActivity.this)) {
            Permisos.Request_INTERNET(MainActivity.this,4);
        }
    }


}