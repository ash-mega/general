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
            gen2();
            gen3();
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }
    
//    public static final String URL = "otpauth://totp/Ash:aw+1@ashtray.nz?secret=krqsus6fzz5evpidmqyozxwajfmfnqsgescvmh2eaykqcbxousuq&issuer=Ash";
    public static final String URL = "otpauth://totp/Ash:aw+1@ashtray.nz?secret=krqsus6fzz5evpidmqyozxwajfmfnqsgescvmh2eaykqcbxousuq&issuer=Ashotpauth://totp/Ash:aw+1@ashtray.nz?secret=krqsus6fzz5evpidmqyozxwajfmfnqsgescvmh2eaykqcbxousuq&issuer=Ashotpauth://totp/Ash:aw+1@ashtray.nz?secret=krqsus6fzz5evpidmqyozxwajfmfnqsgescvmh2eaykqcbxousuq&issuer=Ashotpauth://totp/Ash:aw+1@ashtray.nz?secret=krqsus6fzz5evpidmqyozxwajfmfnqsgescvmh2eaykqcbxousuq&issuer=Ash";
    
    public static final int CL = URL.length();
    
    public static final int L = 700;
    
    public static final int QR_L = (CL / 5) < 55 ? 55 : (CL / 5);
    
//    public static final float SCALE = (CL / 60) <= 1 ? 11 : (CL / 60);
    public static final float SCALE = (L / QR_L );
    
    public static final float RADIUS = 5f;
    
    private void gen3() throws WriterException {
        log("content length:" + CL);
        log("qr length:" + QR_L);
        log("scale:" + SCALE);
        float resize = 8.33333f;
        
        BitMatrix bitMatrix = new MultiFormatWriter().encode(URL,BarcodeFormat.QR_CODE,QR_L,QR_L,null);
        Bitmap qr = Bitmap.createBitmap(L,L,Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(qr);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(BLACK);
        c.drawRect(0,0,L,L,paint);
        
        for (int y = 0;y < QR_L;y++) {
            for (int x = 0;x < QR_L;x++) {
                if (bitMatrix.get(x,y)) {
                    paint.setColor(RED);
                } else {
                    paint.setColor(WHITE);
                }
                c.drawCircle(x * resize,y * resize,RADIUS,paint);
            }
        }
//        drawRect(resize,c,paint);
        qrImageView1.setImageBitmap(qr);
    }
    
    private Map<EncodeHintType, ErrorCorrectionLevel> getHints() {
        Map<EncodeHintType, ErrorCorrectionLevel> hints = new HashMap<>();
        hints.put(EncodeHintType.ERROR_CORRECTION,ErrorCorrectionLevel.H);
        return hints;
    }
    
    private void gen2() throws WriterException {
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
    }
    
    private void drawRect(float resize,Canvas c,Paint paint) {
        paint.setColor(WHITE);
        c.drawRect(3 * resize,3 * resize,11.5f * resize,11.5f * resize,paint);
        c.drawRect(36.5f * resize,3 * resize,45 * resize,11.5f * resize,paint);
        c.drawRect(3 * resize,36.5f * resize,11.5f * resize,45 * resize,paint);
        
        paint.setColor(BLACK);
        
        if (Build.VERSION.SDK_INT >= 21) {
            c.drawRoundRect(3.75f * resize,3.75f * resize,10.75f * resize,10.75f * resize,30,30,paint);
            c.drawRoundRect(37.25f * resize,3.75f * resize,44.25f * resize,10.75f * resize,30,30,paint);
            c.drawRoundRect(3.75f * resize,37.25f * resize,10.75f * resize,44.25f * resize,30,30,paint);
            
            paint.setColor(WHITE);
            c.drawRoundRect(4.75f * resize,4.75f * resize,9.75f * resize,9.75f * resize,25,25,paint);
            c.drawRoundRect(38.25f * resize,4.75f * resize,43.25f * resize,9.75f * resize,25,25,paint);
            c.drawRoundRect(4.75f * resize,38.25f * resize,9.75f * resize,43.25f * resize,25,25,paint);
        } else {
            c.drawRoundRect(new RectF(3.75f * resize,3.75f * resize,10.75f * resize,10.75f * resize),30,30,paint);
            c.drawRoundRect(new RectF(37.25f * resize,3.75f * resize,44.25f * resize,10.75f * resize),30,30,paint);
            c.drawRoundRect(new RectF(3.75f * resize,37.25f * resize,10.75f * resize,44.25f * resize),30,30,paint);
            
            paint.setColor(WHITE);
            c.drawRoundRect(new RectF(4.75f * resize,4.75f * resize,9.75f * resize,9.75f * resize),25,25,paint);
            c.drawRoundRect(new RectF(38.25f * resize,4.75f * resize,43.25f * resize,9.75f * resize),25,25,paint);
            c.drawRoundRect(new RectF(4.75f * resize,38.25f * resize,9.75f * resize,43.25f * resize),25,25,paint);
        }
        
        paint.setColor(BLACK);
        c.drawCircle(7.25f * resize,7.25f * resize,17.5f,paint);
        c.drawCircle(40.75f * resize,7.25f * resize,17.5f,paint);
        c.drawCircle(7.25f * resize,40.75f * resize,17.5f,paint);
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
    
    private void log(Object anything) {
        String msg = (anything == null) ? "NULL" : anything.toString();
        Log.e("@#@"," ---> :" + msg);
    }
}
