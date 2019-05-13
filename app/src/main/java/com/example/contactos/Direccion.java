package com.example.contactos;

import java.io.Serializable;

public class Direccion implements Serializable, Comparable<Direccion> {

    private int id;
    private String calle;
    private String numero;
    private String piso;
    private String puerta;
    private String codigoPostal;
    private String ciudad;
    private String provincia;

    public Direccion(int id, String calle, String numero, String piso, String puerta, String codigoPostal, String ciudad, String provincia) {
        this.id = id;
        this.calle = calle;
        this.numero = numero;
        this.piso = piso;
        this.puerta = puerta;
        this.codigoPostal = codigoPostal;
        this.ciudad = ciudad;
        this.provincia = provincia;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getPiso() {
        return piso;
    }

    public void setPiso(String piso) {
        this.piso = piso;
    }

    public String getPuerta() {
        return puerta;
    }

    public void setPuerta(String puerta) {
        this.puerta = puerta;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    @Override
    public int compareTo(Direccion o) {

        int comparacion = calle.compareTo(o.calle);
        if (comparacion == 0) {
            comparacion = numero.compareTo(o.numero);

        }
        if (comparacion == 0) {
            comparacion = piso.compareTo(o.piso);

        }
        if (comparacion == 0) {
            comparacion = puerta.compareTo(o.puerta);
        }
        if (comparacion == 0) {
            comparacion = codigoPostal.compareTo(o.codigoPostal);
        }
        if (comparacion == 0) {
            comparacion = ciudad.compareTo(o.ciudad);
        }
        if (comparacion == 0) {
            comparacion = provincia.compareTo(o.provincia);
        }


        return comparacion;
    }
}

