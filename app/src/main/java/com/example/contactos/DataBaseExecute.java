package com.example.contactos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

class DataBaseExecute {

    private SQLiteDatabase db;
    private DataBaseHelper usdbh;



    DataBaseExecute(Context con){
        usdbh = new DataBaseHelper(con);
        db = null;

    }

     void anadirContacto(ContentValues nuevoRegistro, String tabla){
        db= usdbh.getWritableDatabase();
        db.insert(tabla, null, nuevoRegistro);
     }



    int getLastId() {

        String sql = "SELECT MAX(ID) FROM CONTACTO";
        db = usdbh.getReadableDatabase();
        Cursor c = db.rawQuery(sql, null);

        if (c.moveToFirst()) {
            return c.getInt(0)+1;
        }
        return 0;
    }


    //EDITAR

     Contacto getContactoWithID(String id) {

        String sql = "SELECT * FROM CONTACTO WHERE ID = ?";
        Contacto con = null;
        try {
            String[] argss = new String[]{id};

            db = usdbh.getReadableDatabase();

            Cursor c = db.rawQuery(sql, argss);
            if (c.moveToFirst()) {
                do {
                    con = new Contacto(c.getInt(0), c.getString(1), c.getString(2), c.getString(3), c.getBlob(4));
                } while (c.moveToNext());
            }


        }catch(Exception e){
            System.out.println(e.toString());

        }
        return con;

    }


     Direccion getDireccionWithID(String id) {

        String sql = "SELECT * FROM DIRECCION WHERE ID = ?";
        Direccion dir = null;
        try {
            String[] argss = new String[]{id};

            db = usdbh.getReadableDatabase();

            Cursor c = db.rawQuery(sql, argss);
            if (c.moveToFirst()) {
                do {
                    dir = new Direccion(c.getInt(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4), c.getString(5), c.getString(6), c.getString(7));
                } while (c.moveToNext());
            }


        }catch(Exception e){
            System.out.println(e.toString());

        }
        return dir;

    }


     ArrayList<Telefono> getTelWithID(String id) {
        ArrayList<Telefono> telefonos = new ArrayList<>();
        Telefono telefono;

        String sql = "SELECT * FROM TELEFONO WHERE ID = ?";
        try {
            String[] argss = new String[]{id};

            db = usdbh.getReadableDatabase();

            Cursor c = db.rawQuery(sql, argss);
            if (c.moveToFirst()) {
                do {
                    telefono = new Telefono(c.getInt(0),c.getString(1), c.getString(2));
                    telefonos.add(telefono);
                } while (c.moveToNext());
            }


        }catch(Exception e){
            System.out.println(e.toString());

        }
        return telefonos;

    }


     ArrayList<String> getCorreoWithID(String id) {
        ArrayList<String> emails = new ArrayList<>();

        String sql = "SELECT * FROM EMAIL WHERE ID = ?";
        try {
            String[] argss = new String[]{id};

            db = usdbh.getReadableDatabase();

            Cursor c = db.rawQuery(sql, argss);
            if (c.moveToFirst()) {
                do {
                    emails.add(c.getString(1));
                } while (c.moveToNext());
            }


        }catch(Exception e){
            System.out.println(e.toString());

        }
        return emails;

    }

     ArrayList<String> getNotasWithID(String id) {
        ArrayList<String> notas = new ArrayList<>();

        String sql = "SELECT * FROM NOTAS WHERE ID = ?";
        try {
            String[] argss = new String[]{id};

            db = usdbh.getReadableDatabase();

            Cursor c = db.rawQuery(sql, argss);
            if (c.moveToFirst()) {
                do {
                    notas.add(c.getString(1));
                } while (c.moveToNext());
            }


        }catch(Exception e){
            System.out.println(e.toString());

        }
        return notas;

    }


     ArrayList<Contacto> getContactoByName(String name) {
        ArrayList<Contacto> contacto = new ArrayList<>();


        db = usdbh.getReadableDatabase();
        Cursor c;
        c = db.query("CONTACTO", new String[] {"*"},"NOMBRE"+" LIKE '"+name+"%'", null, null, null, null);

        while (c.moveToNext()){
            Contacto con = new Contacto(c.getInt(0), c.getString(1), c.getString(2), c.getString(3), c.getBlob(4));
            contacto.add(con);
        }

        return contacto;
    }

     ArrayList<Contacto> getContacto() {
        ArrayList<Contacto> contacto = new ArrayList<>();

        String sql = "SELECT * FROM CONTACTO ORDER BY NOMBRE ASC";
        db = usdbh.getReadableDatabase();
        Cursor c;
        c= db.rawQuery(sql, null);

        while (c.moveToNext()){
            Contacto con = new Contacto(c.getInt(0), c.getString(1), c.getString(2), c.getString(3), c.getBlob(4));
            contacto.add(con);
        }

        return contacto;
    }

     void editarContacto(ContentValues nuevoRegistro, String tabla, String id) {

        db= usdbh.getWritableDatabase();
        db.update(tabla, nuevoRegistro, "ID=?", new String[]{id});
     }

     void borrarPorID(String id, String tabla){

        db= usdbh.getWritableDatabase();
        db.delete(tabla, "ID=?",new String[] {id });

     }


     void close(){

        db.close();
        usdbh.close();
    }


}
