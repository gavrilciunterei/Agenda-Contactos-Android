package com.example.contactos;

import java.io.Serializable;

public class Telefono implements Serializable {

    private int  id;
    private String tipo;
    private String telefono;

    public Telefono(int id, String tipo, String telefono) {
        this.id = id;
        this.tipo = tipo;
        this.telefono = telefono;
    }

    public Telefono( String telefono, String tipo) {
        this.tipo = tipo;
        this.telefono = telefono;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }



}
