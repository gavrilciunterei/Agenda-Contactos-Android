package com.example.contactos;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

public class Permisos {

    //Request Permisson


    public static void Request_FINE_LOCATION(Activity act,int code)
    {
        ActivityCompat.requestPermissions(act, new
                String[]{Manifest.permission.ACCESS_FINE_LOCATION},code);
    }
    public static void Request_CALL_PHONE(Activity act,int code)
    {
        ActivityCompat.requestPermissions(act, new
                String[]{Manifest.permission.CALL_PHONE},code);
    }
    public static void Request_READ_EXTERNAL_STORAGE(Activity act,int code)
    {
        ActivityCompat.requestPermissions(act, new
                String[]{Manifest.permission.READ_EXTERNAL_STORAGE},code);
    }
    public static void Request_INTERNET(Activity act,int code)
    {
        ActivityCompat.requestPermissions(act, new
                String[]{Manifest.permission.INTERNET},code);
    }

    //Check Permisson

    public static boolean Check_FINE_LOCATION(Activity act)
    {
        int result = ContextCompat.checkSelfPermission(act, Manifest.permission.ACCESS_FINE_LOCATION);
        return result == PackageManager.PERMISSION_GRANTED;
    }
    public static boolean Check_CALL_PHONE(Activity act)
    {
        int result = ContextCompat.checkSelfPermission(act, Manifest.permission.CALL_PHONE);
        return result == PackageManager.PERMISSION_GRANTED;
    }
    public static boolean Check_READ_EXTERNAL_STORAGE(Activity act)
    {
        int result = ContextCompat.checkSelfPermission(act, Manifest.permission.READ_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }
    public static boolean Check_INTERNET(Activity act)
    {
        int result = ContextCompat.checkSelfPermission(act, Manifest.permission.INTERNET);
        return result == PackageManager.PERMISSION_GRANTED;
    }

}