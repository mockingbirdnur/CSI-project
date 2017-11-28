package com.example.aidana.project;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class PaintSecondActivity extends View {

    private Path drawPath;
    private static Paint drawPaint;
    private Paint canvasPaint;
    private static int paintColor=0xFFFF0000;
    private Canvas drawCanvas;
    private Bitmap canvasBitmap;


    static float TP;
    private static boolean bor=false;

    public PaintSecondActivity(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupDrawing();
    }

    private void setupDrawing(){
        drawPath = new Path();
        drawPaint=new Paint();
        drawPaint.setColor(paintColor);
        drawPaint.setAntiAlias(true);

        setTP(20);

        drawPaint.setStrokeWidth(TP);
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);
        canvasPaint = new Paint(Paint.DITHER_FLAG);

    }

    protected void onSizeChanged(int w, int t, int oldw, int oldh){
        super.onSizeChanged(w,t,oldw,oldh);
        canvasBitmap= Bitmap.createBitmap(w, t, Bitmap.Config.ARGB_8888);
        drawCanvas=new Canvas(canvasBitmap);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(canvasBitmap, 0, 0, canvasPaint);
        canvas.drawPath(drawPath, drawPaint);
    }
    public boolean onTouchEvent(MotionEvent event){
        float touchX=event.getX();
        float touchY=event.getY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                drawPath.moveTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_MOVE:
                drawPath.lineTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_UP:
                drawPath.lineTo(touchX, touchY);
                drawCanvas.drawPath(drawPath, drawPaint);
                drawPath.reset();
                break;
            default:
                return false;
        }
        invalidate();
        return true;
    }
    public void setColor(String newColor){
        invalidate();
        paintColor= Color.parseColor(newColor);
        drawPaint.setColor(paintColor);
    }

    public static void setTP(float n){

        drawPaint.setStrokeWidth(n);}

    public static void setB(boolean esta){
        bor=esta;
        if(bor){
            drawPaint.setColor(Color.WHITE);
        }
        else{
            drawPaint.setColor(paintColor);
        }
    }
    public void ND(){
        drawCanvas.drawColor(0, PorterDuff.Mode.CLEAR);
        invalidate();
    }
}