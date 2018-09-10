package com.example.aw.animation;


import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.LTGRAY;
import static android.graphics.Color.RED;
import static android.graphics.Color.WHITE;

public class QRCodeActivity extends Activity {
    
    private ImageView qrImageView1, qrImageView2, qrImageView3;
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);
        qrImageView1 = findViewById(R.id.qr_2fa_1);
        qrImageView2 = findViewById(R.id.qr_2fa_2);
        qrImageView3 = findViewById(R.id.qr_2fa_3);
        try {
//            gen1();
//            gen2();
            gen3();
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }
    
    public static final String URL = "otpauth://totp/Ash:aghghhhkkjkjkkl;lkklkl+1@ashtray.nz?secret=krqsus6fzz5evpidmqyozxwajfmfnqsgescvmh2eaykqcbxousuq&issuer=Ash";
    
    public static final int CL = URL.length();
    
    public static final int L = 700;
    
    public static final int QR_L = 55;
    
    public static final float SCALE = ((float)L / (float)QR_L);
    
    public static final float RADIUS = 5f;
    
    private int x0 = -1, y0 = -1;
    
    private void gen3() throws WriterException {
        long b = System.currentTimeMillis();
        log("content length:" + CL);
        log("qr length:" + QR_L);
        log("scale:" + SCALE);
        float resize = SCALE;
        
        BitMatrix bitMatrix = new MultiFormatWriter().encode(URL,BarcodeFormat.QR_CODE,QR_L,QR_L,null);
        Bitmap qr = Bitmap.createBitmap(L,L,Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(qr);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        
        paint.setColor(BLACK);
        for (int y = 0;y < QR_L;y++) {
            for (int x = 0;x < QR_L;x++) {
                if (bitMatrix.get(x,y)) {
//                    log(x +"/" + y);
                    if (x0 == -1 && y0 == -1) {
                        x0 = x;
                        y0 = y;
                    }
                    c.drawCircle(x * resize,y * resize,RADIUS,paint);
                }
            }
        }
        drawRect(resize,c,paint);
        qrImageView1.setImageBitmap(qr);
        log(x0 + "/" + y0);
        log("gen3:" + (System.currentTimeMillis() - b));
    }
    
    private void drawRect(float resize,Canvas c,Paint paint) {
        paint.setColor(BLACK);
        if (Build.VERSION.SDK_INT >= 21) {
            c.drawRoundRect((x0 - 1) * resize,(y0 - 1) * resize,(x0 + 7) * resize,(y0 + 7) * resize,30,30,paint);
            c.drawRoundRect(40f * resize,(y0 - 1) * resize,48f * resize,(y0 + 7) * resize,30,30,paint);
            c.drawRoundRect((x0 - 1) * resize,(40f) * resize,(x0 + 7) * resize,48f * resize,30,30,paint);
            
            paint.setColor(WHITE);
            c.drawRoundRect((x0) * resize,(y0) * resize,(x0 + 6) * resize,(y0 + 6) * resize,25,25,paint);
            c.drawRoundRect(41f * resize,(y0) * resize,47f * resize,(y0 + 6) * resize,25,25,paint);
            c.drawRoundRect((x0) * resize,(41f) * resize,(x0 + 6) * resize,(47f) * resize,25,25,paint);
        } else {
            c.drawRoundRect(new RectF((x0 - 1) * resize,(y0 - 1) * resize,(x0 + 7) * resize,(y0 + 7) * resize),30,30,paint);
            c.drawRoundRect(new RectF(40f * resize,6f * resize,48f * resize,14f * resize),30,30,paint);
            c.drawRoundRect(new RectF((x0 - 1) * resize,(40f) * resize,(x0 + 7) * resize,48f * resize),30,30,paint);
            
            paint.setColor(WHITE);
            c.drawRoundRect(new RectF((x0) * resize,(y0) * resize,(x0 + 6) * resize,(y0 + 6) * resize),25,25,paint);
            c.drawRoundRect(new RectF(41f * resize,(y0) * resize,47f * resize,(y0 + 6) * resize),25,25,paint);
            c.drawRoundRect(new RectF((x0) * resize,(41f) * resize,(x0 + 6) * resize,(47f) * resize),25,25,paint);
        }
        
        paint.setColor(BLACK);
        c.drawCircle((x0 + 3) * resize,(y0 + 3) * resize,1.5f * resize,paint);
        c.drawCircle(44f * resize,(y0 + 3) * resize,1.5f * resize,paint);
        c.drawCircle((x0 + 3) * resize,44f * resize,1.5f * resize,paint);
    }
    
    private Map<EncodeHintType, ErrorCorrectionLevel> getHints() {
        Map<EncodeHintType, ErrorCorrectionLevel> hints = new HashMap<>();
        hints.put(EncodeHintType.ERROR_CORRECTION,ErrorCorrectionLevel.H);
        return hints;
    }
    
    private void gen2() throws WriterException {
        long b = System.currentTimeMillis();
        BitMatrix bitMatrix = new MultiFormatWriter().encode(URL,BarcodeFormat.QR_CODE,QR_L,QR_L,null);
        int w = bitMatrix.getWidth();
        int h = bitMatrix.getHeight();
        int[] pixels = new int[w * h];
        float resize = SCALE;
        Bitmap qr = Bitmap.createBitmap(L,L,Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(qr);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(WHITE);
        c.drawRect(0,0,L,L,paint);
        paint.setColor(BLACK);
        
        for (int y = 0;y < h;y++) {
            int offset = y * w;
            for (int x = 0;x < w;x++) {
                pixels[offset + x] = bitMatrix.get(x,y) ? BLACK : WHITE;
                if (pixels[offset + x] == BLACK) {
                    c.drawCircle(x * resize,y * resize,RADIUS,paint);
                }
            }
        }
//        drawRect(resize,c,paint);
        qrImageView2.setImageBitmap(qr);
        log("gen2:" + (System.currentTimeMillis() - b));
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
        qrImageView3.setImageBitmap(bitmap);
    }
    
    private void log(Object anything) {
        String msg = (anything == null) ? "NULL" : anything.toString();
        Log.e("@#@"," ---> :" + msg);
    }
}
