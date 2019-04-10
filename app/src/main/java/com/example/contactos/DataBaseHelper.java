package com.example.contactos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {


    public DataBaseHelper(Context contexto, String nombre, SQLiteDatabase.CursorFactory factory, int version) {
        super(contexto, nombre, factory, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {


        String sqlCreateContacto  = "CREATE TABLE IF NOT EXISTS CONTACTO(" +
                "ID INTEGER PRIMARY KEY NOT NULL," +
                "NOMBRE VARCHAR," +
                "APODO VARCHAR," +
                "EMPRESA VARCHAR," +
                "IMG BLOB" +
                ")";
        String sqlCreateDireccion  = "CREATE TABLE IF NOT EXISTS DIRECCION(" +
                "ID INTEGER PRIMARY KEY NOT NULL," +
                "CALLE VARCHAR," +
                "NUMERO INTEGER," +
                "PISO INTEGER," +
                "PUERTA CHAR," +
                "CODIGO VARCHAR," +
                "CIUDAD VARCHAR," +
                "PROCINVIA VARCHAR" +
                ")";
        String sqlCreateEmail = "CREATE TABLE IF NOT EXISTS EMAIL(" +
                "ID INTEGER PRIMARY KEY NOT NULL," +
                "EMAIL VARCHAR" +
                ")";
        String sqlCreateNotas = "CREATE TABLE IF NOT EXISTS NOTAS(" +
                "ID INTEGER PRIMARY KEY NOT NULL," +
                "NOTA VARCHAR" +
                ")";
        String sqlCreateTelefono = "CREATE TABLE IF NOT EXISTS TELEFONO(" +
                "ID INTEGER PRIMARY KEY NOT NULL," +
                "TIPO VARCHAR," +
                "TELEFONO CARCHAR" +
                ")";

        db.execSQL(sqlCreateContacto);
        db.execSQL(sqlCreateDireccion);
        db.execSQL(sqlCreateEmail);
        db.execSQL(sqlCreateNotas);
        db.execSQL(sqlCreateTelefono);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
