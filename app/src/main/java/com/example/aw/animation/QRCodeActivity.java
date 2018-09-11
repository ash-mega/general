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
import android.widget.ImageView;

import com.example.aw.animation.util.QRCodeGenerator;
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
    
    public static final String URL = "otpauth://totp/Ash:a1312;saldflfdldasasddasdaskldasklasklkas@a.a?secret=krqsus6fzz5evpidmqyjghjghjghnqsgescvmh2eaykqcbxousuq&issuer=Ash";
//    public static final String URL = "d";
    
    public static final int CL = URL.length();
    
    public static final int L = 500;
    
    public static final int QR_L = 50;
    
    public static final float SCALE = ((float)L / (float)QR_L);
    
    public static final float RADIUS = 5f;
    
    public static final float S_L_1 = 7f;
    public static final float S_L_0 = S_L_1 + 1f;
    public static final float S_L_2 = S_L_1 - 2f;
    public static final float S_L_3 = S_L_2 - 2f;
    public static final float S_L_4 = S_L_3 / 2f;
    
    private float x0 = -1, y0 = -1;
    private float x1 = -1, y1 = -1;
    
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
        
        for (int y = 0;y < QR_L;y++) {
            for (int x = 0;x < QR_L;x++) {
                if (bitMatrix.get(x,y)) {
                    if (x0 == -1 && y0 == -1) {
                        x0 = x;
                        y0 = y;
                    }
                    if (y == x0) {
                        x1 = x - S_L_1 + 1f;
                    }
                    if (x == y0) {
                        y1 = y - S_L_1 + 1f;
                    }
                    paint.setColor(BLACK);
                    c.drawCircle(x * resize,y * resize,RADIUS,paint);
                } else {
                    paint.setColor(RED);
                }
            }
        }
        drawRect(resize,c,paint);
        qrImageView1.setImageBitmap(qr);
        log("0 = " + x0 + "/" + y0);
        log("1 = " + x1 + "/" + y1);
        log("gen3:" + (System.currentTimeMillis() - b));
    }
    
    private void drawRect(float resize,Canvas c,Paint paint) {
        paint.setColor(WHITE);
        c.drawRect((x0 - 1f) * resize,(y0 - 1f) * resize,((x0 - 1f) + S_L_0) * resize,((y0 - 1f) + S_L_0) * resize,paint);
        c.drawRect((x1 - 1f) * resize,(y0 - 1f) * resize,((x1 - 1f) + S_L_0) * resize,((y0 - 1f) + S_L_0) * resize,paint);
        c.drawRect((x0 - 1f) * resize,(y1 - 1f) * resize,((x0 - 1f) + S_L_0) * resize,((y1 - 1f) + S_L_0) * resize,paint);
        
        paint.setColor(BLACK);
        if (Build.VERSION.SDK_INT >= 21) {
            c.drawRoundRect((x0 - 0.5f) * resize,(y0 - 0.5f) * resize,((x0 - 0.5f) + S_L_1) * resize,((y0 - 0.5f) + S_L_1) * resize,20,20,paint);
            c.drawRoundRect((x1 - 0.5f) * resize,(y0 - 0.5f) * resize,((x1 - 0.5f) + S_L_1) * resize,((y0 - 0.5f) + S_L_1) * resize,20,20,paint);
            c.drawRoundRect((x0 - 0.5f) * resize,(y1 - 0.5f) * resize,((x0 - 0.5f) + S_L_1) * resize,((y1 - 0.5f) + S_L_1) * resize,20,20,paint);
            
            paint.setColor(WHITE);
            c.drawRoundRect((x0 + 0.5f) * resize,(y0 + 0.5f) * resize,((x0 + 0.5f) + S_L_2) * resize,((y0 + 0.5f) + S_L_2) * resize,15,15,paint);
            c.drawRoundRect((x1 + 0.5f) * resize,(y0 + 0.5f) * resize,((x1 + 0.5f) + S_L_2) * resize,((y0 + 0.5f) + S_L_2) * resize,15,15,paint);
            c.drawRoundRect((x0 + 0.5f) * resize,(y1 + 0.5f) * resize,((x0 + 0.5f) + S_L_2) * resize,((y1 + 0.5f) + S_L_2) * resize,15,15,paint);
        } else {
            c.drawRoundRect(new RectF((x0 - 0.5f) * resize,(y0 - 0.5f) * resize,((x0 - 0.5f) + S_L_1) * resize,((y0 - 0.5f) + S_L_1) * resize),30,30,paint);
            c.drawRoundRect(new RectF((x1 - 0.5f) * resize,(y0 - 0.5f) * resize,((x1 - 0.5f) + S_L_1) * resize,((y0 - 0.5f) + S_L_1) * resize),30,30,paint);
            c.drawRoundRect(new RectF((x0 - 0.5f) * resize,(y1 - 0.5f) * resize,((x0 - 0.5f) + S_L_1) * resize,((y1 - 0.5f) + S_L_1) * resize),30,30,paint);
            
            paint.setColor(WHITE);
            c.drawRoundRect(new RectF((x0 + 0.5f) * resize,(y0 + 0.5f) * resize,((x0 + 0.5f) + S_L_2) * resize,((y0 + 0.5f) + S_L_2) * resize),25,25,paint);
            c.drawRoundRect(new RectF((x1 + 0.5f) * resize,(y0 + 0.5f) * resize,((x1 + 0.5f) + S_L_2) * resize,((y0 + 0.5f) + S_L_2) * resize),25,25,paint);
            c.drawRoundRect(new RectF((x0 + 0.5f) * resize,(y1 + 0.5f) * resize,((x0 + 0.5f) + S_L_2) * resize,((y1 + 0.5f) + S_L_2) * resize),25,25,paint);
        }
        
        paint.setColor(BLACK);
        c.drawCircle((x0 + S_L_3) * resize,(y0 + S_L_3) * resize,S_L_4 * resize,paint);
        c.drawCircle((x1 + S_L_3) * resize,(y0 + S_L_3) * resize,S_L_4 * resize,paint);
        c.drawCircle((x0 + S_L_3) * resize,(y1 + S_L_3) * resize,S_L_4 * resize,paint);
    }
    
    private Map<EncodeHintType, ErrorCorrectionLevel> getHints() {
        Map<EncodeHintType, ErrorCorrectionLevel> hints = new HashMap<>();
        hints.put(EncodeHintType.ERROR_CORRECTION,ErrorCorrectionLevel.H);
        return hints;
    }
    
    private void gen2() throws WriterException {
        QRCodeGenerator generator = new QRCodeGenerator();
        generator.setDecorationColor(Color.RED);
        generator.setCanvasSize(500);
        generator.setQrCodeSize(50);
        generator.setPointRadius(5f);
        qrImageView2.setImageBitmap(generator.generate(URL));
    }
    
    private void gen1() throws WriterException {
        BitMatrix encode = new MultiFormatWriter().encode(URL,BarcodeFormat.QR_CODE,500,500,null);
        int[] pixels = new int[500 * 500];
        for (int i = 0;i < 500;i++) {
            for (int j = 0;j < 500;j++) {
                if (encode.get(i,j)) {
                    pixels[i * 500 + j] = BLACK;
                } else {
                    pixels[i * 500 + j] = WHITE;
                }
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(pixels,0,500,500,500,Bitmap.Config.RGB_565);
        qrImageView3.setImageBitmap(bitmap);
    }
    
    private void log(Object anything) {
        String msg = (anything == null) ? "NULL" : anything.toString();
        Log.e("@#@"," ---> :" + msg);
    }
}
