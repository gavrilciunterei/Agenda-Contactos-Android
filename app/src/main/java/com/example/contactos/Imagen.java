package com.example.contactos;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.DisplayMetrics;

class Imagen {


     private static Bitmap getBitmapEscalado(String ruta) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        int tamañoNuevoAncho = 120;
        int tamañoNuevoAlto = 120;
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(ruta, options);
        float srcWidth = options.outWidth;
        float srcHeight = options.outHeight;
        int inSampleSize = 1;
        //en este apartado comprobamls si el tamño que tiene la foto es más grande que el que yo le doy, si es así hace un reescalado
        if (srcHeight > tamañoNuevoAlto || srcWidth > tamañoNuevoAncho) {
            //si es mas ancho que alto
            if (srcWidth > srcHeight) {
                inSampleSize = Math.round(srcHeight / tamañoNuevoAlto);
            } else {
                inSampleSize = Math.round(srcWidth / tamañoNuevoAncho);
            }
        }
        options = new BitmapFactory.Options();
        options.inSampleSize = inSampleSize;
        return BitmapFactory.decodeFile(ruta, options);
    }


     static Bitmap getBitmapCirculrFoto(String ruta){

        Bitmap bitmap = getBitmapEscalado(ruta);

        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        float diameterPixels = 30 * (metrics.densityDpi / 100f);

        Bitmap output = Bitmap.createBitmap((int) diameterPixels, (int) diameterPixels, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(output);

        final Rect rect = new Rect(0, 0, (int) diameterPixels, (int) diameterPixels);
        RectF rectF = new RectF(rect);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(color);

        canvas.drawOval(rectF, paint);

        float left = (diameterPixels-bitmap.getWidth())/2;
        float top = (diameterPixels-bitmap.getHeight())/2;

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, left, top, paint);
        bitmap.recycle();

        return output;
    }



     static Bitmap getBitmapCircularString(String letra){
        final int textColor = 0xffffffff;

        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        float diameterPixels = 30 * (metrics.densityDpi / 130f);
        float radiusPixels = (float) (diameterPixels/2.3);

        Bitmap output = Bitmap.createBitmap((int) diameterPixels, (int) diameterPixels,
                Bitmap.Config.ARGB_8888);

        // Creamos un canvas para dibujar
        Canvas canvas = new Canvas(output);
        canvas.drawARGB(0, 0, 0, 0);

        // Dibuja el circulo
        final Paint paintC = new Paint();
        paintC.setAntiAlias(true);
        paintC.setColor(Color.GRAY);
        canvas.drawCircle(radiusPixels, radiusPixels, radiusPixels, paintC);

        // Dibuja el texto
        if (letra != null && letra.length() > 0) {
            final Paint paintT = new Paint();
            paintT.setColor(textColor);
            paintT.setAntiAlias(true);
            paintT.setTextSize(radiusPixels*2);
            Typeface typeFace = Typeface.defaultFromStyle(Typeface.BOLD);
            paintT.setTypeface(typeFace);
            final Rect textBounds = new Rect();
            paintT.getTextBounds(letra, 0, letra.length(), textBounds);
            canvas.drawText(letra, radiusPixels - textBounds.exactCenterX(), radiusPixels - textBounds.exactCenterY(), paintT);
        }

        return output;
    }





}