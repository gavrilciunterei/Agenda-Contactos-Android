package com.example.contactos;

import java.io.Serializable;

public class Notas implements Serializable {

    private int id;
    private String nota;

    public Notas(int id, String nota) {
        this.id = id;
        this.nota = nota;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }
}
