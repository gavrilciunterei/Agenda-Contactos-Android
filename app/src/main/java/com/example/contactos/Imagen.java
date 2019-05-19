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


     private static Bitmap getScaledBitmap(String path, int destWidth, int destHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        float srcWidth = options.outWidth;
        float srcHeight = options.outHeight;
        int inSampleSize = 1;
        if (srcHeight > destHeight || srcWidth > destWidth) {
            if (srcWidth > srcHeight) {
                inSampleSize = Math.round(srcHeight / destHeight);
            } else {
                inSampleSize = Math.round(srcWidth / destWidth);
            }
        }
        options = new BitmapFactory.Options();
        options.inSampleSize = inSampleSize;
        return BitmapFactory.decodeFile(path, options);
    }


     static Bitmap getCircularBitmap(String filePath){

        Bitmap bitmap = getScaledBitmap(filePath, (int) (150*0.7), (int) (160*0.7));

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



     static Bitmap generateCircleBitmap(String text){
        final int textColor = 0xffffffff;

        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        float diameterPixels = 30 * (metrics.densityDpi / 130f);
        float radiusPixels = (float) (diameterPixels/2.3);

        Bitmap output = Bitmap.createBitmap((int) diameterPixels, (int) diameterPixels,
                Bitmap.Config.ARGB_8888);

        // Create the canvas to draw on
        Canvas canvas = new Canvas(output);
        canvas.drawARGB(0, 0, 0, 0);

        // Draw the circle
        final Paint paintC = new Paint();
        paintC.setAntiAlias(true);
        paintC.setColor(Color.GRAY);
        canvas.drawCircle(radiusPixels, radiusPixels, radiusPixels, paintC);

        // Draw the text
        if (text != null && text.length() > 0) {
            final Paint paintT = new Paint();
            paintT.setColor(textColor);
            paintT.setAntiAlias(true);
            paintT.setTextSize(radiusPixels*2);
            Typeface typeFace = Typeface.defaultFromStyle(Typeface.BOLD);
            paintT.setTypeface(typeFace);
            final Rect textBounds = new Rect();
            paintT.getTextBounds(text, 0, text.length(), textBounds);
            canvas.drawText(text, radiusPixels - textBounds.exactCenterX(), radiusPixels - textBounds.exactCenterY(), paintT);
        }

        return output;
    }





}