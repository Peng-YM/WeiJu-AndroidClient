package cn.edu.sustc.androidclient.view.task.annotationtask;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class Rectangle implements Shape{
    private int startX, startY, endX, endY;

    public Rectangle(int startX, int startY, int endX, int endY) {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
    }

    public Rect getRectangle(){
        return new Rect(startX, startY, endX, endY);
    }

    @Override
    public void draw(Canvas canvas, Paint paint){
        canvas.drawRect(new Rect(startX, startY, endX, endY), paint);
    }
}
