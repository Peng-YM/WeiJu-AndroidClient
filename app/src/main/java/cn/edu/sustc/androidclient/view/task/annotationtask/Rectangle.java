package cn.edu.sustc.androidclient.view.task.annotationtask;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class Rectangle extends Shape{
    private int startX, startY, endX, endY;

    public Rectangle(int startX, int startY, int endX, int endY) {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
    }

    public Rectangle(Rectangle rectangle){
        this.startX = rectangle.startX;
        this.startY = rectangle.startY;
        this.endX = rectangle.endX;
        this.endY = rectangle.endY;
    }

    @Override
    public void draw(Canvas canvas, Paint paint){
        Rect rect = new Rect(startX, startY, endX, endY);
        canvas.drawRect(rect, paint);
    }
}
