package com.example.aw.animation;


import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
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

public class QRCodeActivity extends Activity {
    
    private ImageView qrImageView;
    
    public static final String URL = "otpauth://totp/MEGA:aw+1@mega.nz?secret=krqsus6fzz5evpidmqyozxwajfmfnqsgescvmh2eaykqcbxousuq&issuer=MEGA";
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);
        qrImageView = findViewById(R.id.qr_2fa);
        try {
            generate2FAQR(URL);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }
    
    private void generate2FAQR(String url) throws WriterException {
        Bitmap bitmap = Bitmap.createBitmap(L,L,Bitmap.Config.ARGB_8888);
        BitMatrix bitMatrix = new MultiFormatWriter().encode(url,BarcodeFormat.QR_CODE,QR_L,QR_L,null);
        
        Canvas c = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.LTGRAY);
        c.drawRect(0,0,L,L,paint);
        int t = 0, f = 0;
        for (int y = 0;y < QR_L;y++) {
            for (int x = 0;x < QR_L;x++) {
                if (bitMatrix.get(x,y)) {
                    t++;
                    paint.setColor(BLACK);
                } else {
                    f++;
                    paint.setColor(Color.CYAN);
                }
                c.drawCircle(x * SCALE ,y * SCALE,RADIUS ,paint);
            }
        }
        log("t: " + t + " | f: " + f);
        //TODO draw rect
        qrImageView.setImageBitmap(bitmap);
    }
    
    public static final int L = 1000;
    
    public static final int QR_L = 70;
    
    public static final float SCALE = 20.2f;
    
    public static final float RADIUS = 10f;
    
    private void log(Object anything) {
        String msg = (anything == null) ? "NULL" : anything.toString();
        Log.e("@#@"," ---> :" + msg);
    }
    
    static {
        //            paint.setColor(WHITE);
//            c.drawRect(3*resize, 3*resize, 11.5f*resize, 11.5f*resize, paint);
//            c.drawRect(36.5f*resize, 3*resize, 45*resize, 11.5f*resize, paint);
//            c.drawRect(3*resize, 36.5f*resize, 11.5f*resize, 45*resize, paint);
//
//            paint.setColor(BLACK);
//
//            if (Build.VERSION.SDK_INT >= 21) {
//                c.drawRoundRect(3.75f * resize, 3.75f * resize, 10.75f * resize, 10.75f * resize, 30, 30, paint);
//                c.drawRoundRect(37.25f * resize, 3.75f * resize, 44.25f * resize, 10.75f * resize, 30, 30, paint);
//                c.drawRoundRect(3.75f * resize, 37.25f * resize, 10.75f * resize, 44.25f * resize, 30, 30, paint);
//
//                paint.setColor(WHITE);
//                c.drawRoundRect(4.75f * resize, 4.75f * resize, 9.75f * resize, 9.75f * resize, 25, 25, paint);
//                c.drawRoundRect(38.25f * resize, 4.75f * resize, 43.25f * resize, 9.75f * resize, 25, 25, paint);
//                c.drawRoundRect(4.75f * resize, 38.25f * resize, 9.75f * resize, 43.25f * resize, 25, 25, paint);
//            }
//            else {
//                c.drawRoundRect(new RectF(3.75f * resize, 3.75f * resize, 10.75f * resize, 10.75f * resize), 30, 30, paint);
//                c.drawRoundRect(new RectF(37.25f * resize, 3.75f * resize, 44.25f * resize, 10.75f * resize), 30, 30, paint);
//                c.drawRoundRect(new RectF(3.75f * resize, 37.25f * resize, 10.75f * resize, 44.25f * resize), 30, 30, paint);
//
//                paint.setColor(WHITE);
//                c.drawRoundRect(new RectF(4.75f * resize, 4.75f * resize, 9.75f * resize, 9.75f * resize), 25, 25, paint);
//                c.drawRoundRect(new RectF(38.25f * resize, 4.75f * resize, 43.25f * resize, 9.75f * resize), 25, 25, paint);
//                c.drawRoundRect(new RectF(4.75f * resize, 38.25f * resize, 9.75f * resize, 43.25f * resize), 25, 25, paint);
//            }
//
//            paint.setColor(BLACK);
//            c.drawCircle(7.25f*resize, 7.25f*resize, 17.5f, paint);
//            c.drawCircle(40.75f*resize, 7.25f*resize, 17.5f, paint);
//            c.drawCircle(7.25f*resize, 40.75f*resize, 17.5f, paint);
    }
}
