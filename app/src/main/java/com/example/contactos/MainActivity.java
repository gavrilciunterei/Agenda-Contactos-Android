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
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private Button buttonAnadir;
    private ListView listView;
    private DataBaseExecute dbe;


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
        this.listView.setAdapter(new Adaptador_Home(this, dbe.getContacto()));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String idd = Integer.toString(dbe.getContacto().get(position).getId());

                Intent i = new Intent(MainActivity.this, Ver.class );
                    i.putExtra("ID", idd);

                startActivity(i);

            }
        });

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