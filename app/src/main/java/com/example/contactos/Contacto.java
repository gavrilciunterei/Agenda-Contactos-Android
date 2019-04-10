package com.example.contactos;

import java.sql.Blob;

public class Contacto {

    private int id;
    private String nombre;
    private String apodo;
    private String empresa;
    private  byte[] img;

    public Contacto(int id, String nombre, String apodo, String empresa, byte[] img) {
        this.id = id;
        this.nombre = nombre;
        this.apodo = apodo;
        this.empresa = empresa;
        this.img = img;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApodo() {
        return apodo;
    }

    public void setApodo(String apodo) {
        this.apodo = apodo;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }
}
