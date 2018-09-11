package com.example.aw.animation.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.util.Log;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.util.HashMap;
import java.util.Map;

public class QRCodeGenerator {
    
    private float pointRadius = 5f;
    
    private int outerRadius = 20;
    
    private int innerRadius = 15;
    
    private int qrCodeSize = 60;
    
    private int canvasSize = 700;
    
    private int pointColor = Color.BLACK;
    
    private int decorationColor = Color.BLACK;
    
    private int bgColor = Color.WHITE;
    
    private boolean drawDecoration = true;
    
    private float x0 = -1f, y0 = -1f;
    
    private float x1, y1;
    
    private static final float SQUARE_SIZE = 7f;
    
    private static final float OUTER_SQUARE_SIZE = SQUARE_SIZE + 1f;
    
    private static final float INNER_SQUARE_SIZE = SQUARE_SIZE - 2f;
    
    private static final float CIRCLE_CENTRE = INNER_SQUARE_SIZE - 2f;
    
    private static final float CIRCLE_RADIUS = CIRCLE_CENTRE / 2f;
    
    private static final float TO_LEFT_OFFSET = -1f;
    
    private static final float TO_RIGHT_OFFSET = -TO_LEFT_OFFSET;
    
    public Bitmap generate(String content) throws WriterException {
        float resize = ((float)canvasSize / (float)qrCodeSize);
        
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content,BarcodeFormat.QR_CODE,qrCodeSize,qrCodeSize,null);
        Bitmap qr = Bitmap.createBitmap(canvasSize,canvasSize,Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(qr);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(pointColor);
        for (int x = 0;x < qrCodeSize;x++) {
            for (int y = 0;y < qrCodeSize;y++) {
                if (bitMatrix.get(x,y)) {
                    if (x0 == -1 && y0 == -1) {
                        x0 = x;
                        y0 = y;
                    }
                    if (y == x0) {
                        x1 = x - SQUARE_SIZE + 1f;
                    }
                    if (x == y0) {
                        y1 = y - SQUARE_SIZE + 1f;
                    }
                    c.drawCircle(x * resize,y * resize,pointRadius,paint);
                }
            }
        }
        if (drawDecoration) {
            drawRect(resize,c,paint);
        }
        return qr;
    }
    
    private Map<EncodeHintType, ErrorCorrectionLevel> getHints() {
        Map<EncodeHintType, ErrorCorrectionLevel> hints = new HashMap<>();
        hints.put(EncodeHintType.ERROR_CORRECTION,ErrorCorrectionLevel.H);
        return hints;
    }
    
    private void drawRect(float resize,Canvas c,Paint paint) {
        paint.setColor(bgColor);
        c.drawRect((x0 + TO_LEFT_OFFSET) * resize,(y0 + TO_LEFT_OFFSET) * resize,((x0 + TO_LEFT_OFFSET) + OUTER_SQUARE_SIZE) * resize,((y0 + TO_LEFT_OFFSET) + OUTER_SQUARE_SIZE) * resize,paint);
        c.drawRect((x1 + TO_LEFT_OFFSET) * resize,(y0 + TO_LEFT_OFFSET) * resize,((x1 + TO_LEFT_OFFSET) + OUTER_SQUARE_SIZE) * resize,((y0 + TO_LEFT_OFFSET) + OUTER_SQUARE_SIZE) * resize,paint);
        c.drawRect((x0 + TO_LEFT_OFFSET) * resize,(y1 + TO_LEFT_OFFSET) * resize,((x0 + TO_LEFT_OFFSET) + OUTER_SQUARE_SIZE) * resize,((y1 + TO_LEFT_OFFSET) + OUTER_SQUARE_SIZE) * resize,paint);
        
        paint.setColor(decorationColor);
        if (Build.VERSION.SDK_INT >= 21) {
            c.drawRoundRect((x0 + TO_LEFT_OFFSET / 2) * resize,(y0 + TO_LEFT_OFFSET / 2) * resize,((x0 + TO_LEFT_OFFSET / 2) + SQUARE_SIZE) * resize,((y0 + TO_LEFT_OFFSET / 2) + SQUARE_SIZE) * resize,outerRadius,outerRadius,paint);
            c.drawRoundRect((x1 + TO_LEFT_OFFSET / 2) * resize,(y0 + TO_LEFT_OFFSET / 2) * resize,((x1 + TO_LEFT_OFFSET / 2) + SQUARE_SIZE) * resize,((y0 + TO_LEFT_OFFSET / 2) + SQUARE_SIZE) * resize,outerRadius,outerRadius,paint);
            c.drawRoundRect((x0 + TO_LEFT_OFFSET / 2) * resize,(y1 + TO_LEFT_OFFSET / 2) * resize,((x0 + TO_LEFT_OFFSET / 2) + SQUARE_SIZE) * resize,((y1 + TO_LEFT_OFFSET / 2) + SQUARE_SIZE) * resize,outerRadius,outerRadius,paint);
            
            paint.setColor(bgColor);
            c.drawRoundRect((x0 + TO_RIGHT_OFFSET / 2) * resize,(y0 + TO_RIGHT_OFFSET / 2) * resize,((x0 + TO_RIGHT_OFFSET / 2) + INNER_SQUARE_SIZE) * resize,((y0 + TO_RIGHT_OFFSET / 2) + INNER_SQUARE_SIZE) * resize,innerRadius,innerRadius,paint);
            c.drawRoundRect((x1 + TO_RIGHT_OFFSET / 2) * resize,(y0 + TO_RIGHT_OFFSET / 2) * resize,((x1 + TO_RIGHT_OFFSET / 2) + INNER_SQUARE_SIZE) * resize,((y0 + TO_RIGHT_OFFSET / 2) + INNER_SQUARE_SIZE) * resize,innerRadius,innerRadius,paint);
            c.drawRoundRect((x0 + TO_RIGHT_OFFSET / 2) * resize,(y1 + TO_RIGHT_OFFSET / 2) * resize,((x0 + TO_RIGHT_OFFSET / 2) + INNER_SQUARE_SIZE) * resize,((y1 + TO_RIGHT_OFFSET / 2) + INNER_SQUARE_SIZE) * resize,innerRadius,innerRadius,paint);
        } else {
            c.drawRoundRect(new RectF((x0 + TO_LEFT_OFFSET / 2) * resize,(y0 + TO_LEFT_OFFSET / 2) * resize,((x0 + TO_LEFT_OFFSET / 2) + SQUARE_SIZE) * resize,((y0 + TO_LEFT_OFFSET / 2) + SQUARE_SIZE) * resize),outerRadius,outerRadius,paint);
            c.drawRoundRect(new RectF((x1 + TO_LEFT_OFFSET / 2) * resize,(y0 + TO_LEFT_OFFSET / 2) * resize,((x1 + TO_LEFT_OFFSET / 2) + SQUARE_SIZE) * resize,((y0 + TO_LEFT_OFFSET / 2) + SQUARE_SIZE) * resize),outerRadius,outerRadius,paint);
            c.drawRoundRect(new RectF((x0 + TO_LEFT_OFFSET / 2) * resize,(y1 + TO_LEFT_OFFSET / 2) * resize,((x0 + TO_LEFT_OFFSET / 2) + SQUARE_SIZE) * resize,((y1 + TO_LEFT_OFFSET / 2) + SQUARE_SIZE) * resize),outerRadius,outerRadius,paint);
            
            paint.setColor(bgColor);
            c.drawRoundRect(new RectF((x0 + TO_RIGHT_OFFSET / 2) * resize,(y0 + TO_RIGHT_OFFSET / 2) * resize,((x0 + TO_RIGHT_OFFSET / 2) + INNER_SQUARE_SIZE) * resize,((y0 + TO_RIGHT_OFFSET / 2) + INNER_SQUARE_SIZE) * resize),innerRadius,innerRadius,paint);
            c.drawRoundRect(new RectF((x1 + TO_RIGHT_OFFSET / 2) * resize,(y0 + TO_RIGHT_OFFSET / 2) * resize,((x1 + TO_RIGHT_OFFSET / 2) + INNER_SQUARE_SIZE) * resize,((y0 + TO_RIGHT_OFFSET / 2) + INNER_SQUARE_SIZE) * resize),innerRadius,innerRadius,paint);
            c.drawRoundRect(new RectF((x0 + TO_RIGHT_OFFSET / 2) * resize,(y1 + TO_RIGHT_OFFSET / 2) * resize,((x0 + TO_RIGHT_OFFSET / 2) + INNER_SQUARE_SIZE) * resize,((y1 + TO_RIGHT_OFFSET / 2) + INNER_SQUARE_SIZE) * resize),innerRadius,innerRadius,paint);
        }
        
        paint.setColor(decorationColor);
        c.drawCircle((x0 + CIRCLE_CENTRE) * resize,(y0 + CIRCLE_CENTRE) * resize,CIRCLE_RADIUS * resize,paint);
        c.drawCircle((x1 + CIRCLE_CENTRE) * resize,(y0 + CIRCLE_CENTRE) * resize,CIRCLE_RADIUS * resize,paint);
        c.drawCircle((x0 + CIRCLE_CENTRE) * resize,(y1 + CIRCLE_CENTRE) * resize,CIRCLE_RADIUS * resize,paint);
    }
    
    public void setPointRadius(float pointRadius) {
        this.pointRadius = pointRadius;
    }
    
    public void setOuterRadius(int outerRadius) {
        this.outerRadius = outerRadius;
    }
    
    public void setInnerRadius(int innerRadius) {
        this.innerRadius = innerRadius;
    }
    
    public void setQrCodeSize(int qrCodeSize) {
        this.qrCodeSize = qrCodeSize;
    }
    
    public void setCanvasSize(int canvasSize) {
        this.canvasSize = canvasSize;
    }
    
    public void setPointColor(int pointColor) {
        this.pointColor = pointColor;
    }
    
    public void setDecorationColor(int decorationColor) {
        this.decorationColor = decorationColor;
    }
    
    public void setBgColor(int bgColor) {
        this.bgColor = bgColor;
    }
    
    public void setDrawDecoration(boolean drawDecoration) {
        this.drawDecoration = drawDecoration;
    }
    
    private void log(Object anything) {
        String msg = (anything == null) ? "NULL" : anything.toString();
        Log.e("@#@"," ---> :" + msg);
    }
}
