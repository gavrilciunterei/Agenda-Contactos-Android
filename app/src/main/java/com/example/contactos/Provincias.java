package com.example.contactos;

import java.util.ArrayList;

public class Provincias{


    private ArrayList<String> provincias;

    public Provincias() {
        provincias = new ArrayList<String>();
        setProvincias();
    }

    private void setProvincias() {
        provincias.add("A Coruña");
        provincias.add("Álava");
        provincias.add("Albacete");
        provincias.add("Alicante");
        provincias.add("Almería");
        provincias.add("Asturias");
        provincias.add("Ávila");
        provincias.add("Badajoz");
        provincias.add("Islas Baleares");
        provincias.add("Barcelona");
        provincias.add("Burgos");
        provincias.add("Cáceres");
        provincias.add("Cádiz");
        provincias.add("Cantabria");
        provincias.add("Castellón");
        provincias.add("Ciudad Real");
        provincias.add("Córdoba");
        provincias.add("Cuenca");
        provincias.add("Girona");
        provincias.add("Granada");
        provincias.add("Guadalajara");
        provincias.add("Guipúzcoa");
        provincias.add("Huelva");
        provincias.add("Huesca");
        provincias.add("Jaén");
        provincias.add("La Rioja");
        provincias.add("Las Palmas");
        provincias.add("León");
        provincias.add("Lleida");
        provincias.add("Lugo");
        provincias.add("Madrid");
        provincias.add("Málaga");
        provincias.add("Murcia");
        provincias.add("Navarra");
        provincias.add("Orense");
        provincias.add("Palencia");
        provincias.add("Pontevedra");
        provincias.add("Salamanca");
        provincias.add("Segovia");
        provincias.add("Sevilla");
        provincias.add("Soria");
        provincias.add("Tarragona");
        provincias.add("Santa Cruz de Tenerife");
        provincias.add("Teruel");
        provincias.add("Toledo");
        provincias.add("Valencia");
        provincias.add("Valladolid");
        provincias.add("Vizcaya");
        provincias.add("Zamora");
        provincias.add("Zaragoza");

    }

    public ArrayList<String> getProvincias() {
        return provincias;
    }

    public int returnPositionProvincia(String provincia){
        for(int i = 0; i < provincias.size(); i++){
            if(provincias.get(i).equals(provincia)){
                return i;
            }
        }

        return 0;
    }
}


