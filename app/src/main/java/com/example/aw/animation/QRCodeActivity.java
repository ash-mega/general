package com.example.aw.animation;


import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.WHITE;

public class QRCodeActivity extends Activity {
    
    private ImageView qrImageView1, qrImageView2, qrImageView3;
    
    public static final int L = 50;
    
    public static final int QR_L = 500;
    
    public static final float SCALE = 1f;
    
    public static final float RADIUS = 1f;
    
    public static final String URL = "otpauth://totp/MEGA:aw+1@mega.nz?secret=krqsus6fzz5evpidmqyozxwajfmfnqsgescvmh2eaykqcbxousuq&issuer=MEGA";
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);
        qrImageView1 = findViewById(R.id.qr_2fa_1);
        qrImageView2 = findViewById(R.id.qr_2fa_2);
        qrImageView3 = findViewById(R.id.qr_2fa_3);
        try {
            gen1();
            gen2();
            gen3();
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }
    
    private void gen1() throws WriterException {
        BitMatrix encode = new MultiFormatWriter().encode(URL,BarcodeFormat.QR_CODE,QR_L,QR_L,null);
        int[] pixels = new int[QR_L * QR_L];
        for (int i = 0;i < QR_L;i++) {
            for (int j = 0;j < QR_L;j++) {
                if (encode.get(i,j)) {
                    pixels[i * QR_L + j] = BLACK;
                } else {
                    pixels[i * QR_L + j] = WHITE;
                }
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(pixels,0,QR_L,QR_L,QR_L,Bitmap.Config.RGB_565);
        qrImageView1.setImageBitmap(bitmap);
    }
    
    private void gen2() throws WriterException {
        BitMatrix bitMatrix = new MultiFormatWriter().encode(URL,BarcodeFormat.QR_CODE,40,40,null);
        int w = bitMatrix.getWidth();
        int h = bitMatrix.getHeight();
        int[] pixels = new int[w * h];
        float resize = 15;
        Bitmap qr = Bitmap.createBitmap(700,700,Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(qr);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(WHITE);
        c.drawRect(0,0,700,700,paint);
        paint.setColor(BLACK);
        
        for (int y = 0;y < h;y++) {
            int offset = y * w;
            for (int x = 0;x < w;x++) {
                pixels[offset + x] = bitMatrix.get(x,y) ? BLACK : WHITE;
                if (pixels[offset + x] == BLACK) {
                    c.drawCircle(x * resize,y * resize,7,paint);
                }
            }
        }
//        paint.setColor(WHITE);
//        c.drawRect(3 * resize,3 * resize,11.5f * resize,11.5f * resize,paint);
//        c.drawRect(36.5f * resize,3 * resize,45 * resize,11.5f * resize,paint);
//        c.drawRect(3 * resize,36.5f * resize,11.5f * resize,45 * resize,paint);

//        paint.setColor(BLACK);
//
//        if (Build.VERSION.SDK_INT >= 21) {
//            c.drawRoundRect(3.75f * resize,3.75f * resize,10.75f * resize,10.75f * resize,30,30,paint);
//            c.drawRoundRect(37.25f * resize,3.75f * resize,44.25f * resize,10.75f * resize,30,30,paint);
//            c.drawRoundRect(3.75f * resize,37.25f * resize,10.75f * resize,44.25f * resize,30,30,paint);
//
//            paint.setColor(WHITE);
//            c.drawRoundRect(4.75f * resize,4.75f * resize,9.75f * resize,9.75f * resize,25,25,paint);
//            c.drawRoundRect(38.25f * resize,4.75f * resize,43.25f * resize,9.75f * resize,25,25,paint);
//            c.drawRoundRect(4.75f * resize,38.25f * resize,9.75f * resize,43.25f * resize,25,25,paint);
//        } else {
//            c.drawRoundRect(new RectF(3.75f * resize,3.75f * resize,10.75f * resize,10.75f * resize),30,30,paint);
//            c.drawRoundRect(new RectF(37.25f * resize,3.75f * resize,44.25f * resize,10.75f * resize),30,30,paint);
//            c.drawRoundRect(new RectF(3.75f * resize,37.25f * resize,10.75f * resize,44.25f * resize),30,30,paint);
//
//            paint.setColor(WHITE);
//            c.drawRoundRect(new RectF(4.75f * resize,4.75f * resize,9.75f * resize,9.75f * resize),25,25,paint);
//            c.drawRoundRect(new RectF(38.25f * resize,4.75f * resize,43.25f * resize,9.75f * resize),25,25,paint);
//            c.drawRoundRect(new RectF(4.75f * resize,38.25f * resize,9.75f * resize,43.25f * resize),25,25,paint);
//        }

//        paint.setColor(BLACK);
//        c.drawCircle(7.25f * resize,7.25f * resize,17.5f,paint);
//        c.drawCircle(40.75f * resize,7.25f * resize,17.5f,paint);
//        c.drawCircle(7.25f * resize,40.75f * resize,17.5f,paint);
        qrImageView2.setImageBitmap(qr);
    }
    
    private void gen3() throws WriterException {
        BitMatrix bitMatrix = new MultiFormatWriter().encode(URL,BarcodeFormat.QR_CODE,40,40,null);
        int w = bitMatrix.getWidth();
        int h = bitMatrix.getHeight();
        int[] pixels = new int[w * h];
        float resize = 12.2f;
        Bitmap qr = Bitmap.createBitmap(500,500,Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(qr);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(WHITE);
        c.drawRect(0,0,500,500,paint);
        paint.setColor(BLACK);
        
        for (int y = 0;y < h;y++) {
            int offset = y * w;
            for (int x = 0;x < w;x++) {
                pixels[offset + x] = bitMatrix.get(x,y) ? BLACK : WHITE;
                if (pixels[offset + x] == BLACK) {
                    c.drawCircle(x * resize,y * resize,5,paint);
                }
            }
        }
        paint.setColor(WHITE);
        c.drawRect(3*resize, 3*resize, 11.5f*resize, 11.5f*resize, paint);
        c.drawRect(28.5f*resize, 3*resize, 37*resize, 11.5f*resize, paint);
        c.drawRect(3*resize, 28.5f*resize, 11.5f*resize, 37*resize, paint);
    
        paint.setColor(BLACK);
    
        if (Build.VERSION.SDK_INT >= 21) {
            c.drawRoundRect(3.75f * resize, 3.75f * resize, 10.75f * resize, 10.75f * resize, 30, 30, paint);
            c.drawRoundRect(29.25f * resize, 3.75f * resize, 36.25f * resize, 10.75f * resize, 30, 30, paint);
            c.drawRoundRect(3.75f * resize, 29.25f * resize, 10.75f * resize, 36.25f * resize, 30, 30, paint);
        
            paint.setColor(WHITE);
            c.drawRoundRect(4.75f * resize, 4.75f * resize, 9.75f * resize, 9.75f * resize, 25, 25, paint);
            c.drawRoundRect(30.25f * resize, 4.75f * resize, 35.25f * resize, 9.75f * resize, 25, 25, paint);
            c.drawRoundRect(4.75f * resize, 30.25f * resize, 9.75f * resize, 35.25f * resize, 25, 25, paint);
        }
        else {
            c.drawRoundRect(new RectF(3.75f * resize, 3.75f * resize, 10.75f * resize, 10.75f * resize), 30, 30, paint);
            c.drawRoundRect(new RectF(29.25f * resize, 3.75f * resize, 36.25f * resize, 10.75f * resize), 30, 30, paint);
            c.drawRoundRect(new RectF(3.75f * resize, 29.25f * resize, 10.75f * resize, 36.25f * resize), 30, 30, paint);
        
            paint.setColor(WHITE);
            c.drawRoundRect(new RectF(4.75f * resize, 4.75f * resize, 9.75f * resize, 9.75f * resize), 25, 25, paint);
            c.drawRoundRect(new RectF(30.25f * resize, 4.75f * resize, 35.25f * resize, 9.75f * resize), 25, 25, paint);
            c.drawRoundRect(new RectF(4.75f * resize, 30.25f * resize, 9.75f * resize, 35.25f * resize), 25, 25, paint);
        }
    
        paint.setColor(BLACK);
        c.drawCircle(7.25f*resize, 7.25f*resize, 17.5f, paint);
        c.drawCircle(32.75f*resize, 7.25f*resize, 17.5f, paint);
        c.drawCircle(7.25f*resize, 32.75f*resize, 17.5f, paint);
        qrImageView3.setImageBitmap(qr);
    }
    
    private void log(Object anything) {
        String msg = (anything == null) ? "NULL" : anything.toString();
        Log.e("@#@"," ---> :" + msg);
    }
}
