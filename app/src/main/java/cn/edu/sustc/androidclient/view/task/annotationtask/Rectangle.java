package cn.edu.sustc.androidclient.view.task.annotationtask;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.List;

import io.reactivex.annotations.Nullable;

public class Rectangle extends Shape{
    private int startX, startY, endX, endY;
    private Paint myPaint;

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
        if (paint != null){
            canvas.drawRect(rect, paint);
        }else if (myPaint != null){
            canvas.drawRect(rect, myPaint);
        }
    }

    @Override
    Coordinate getCenter() {
        float x = (endX + startX) / 2;
        float y = (endY + startY) / 2;
        return new Coordinate(x, y);
    }

    @Override
    Paint getPaint() {
        return myPaint;
    }

    @Override
    void setPaint(Paint paint) {
        myPaint = paint;
    }

    @Override
    public String toString() {
        return String.format("Rectangle: [%d %d %d %d]", startX, startY, endX, endY);
    }
}
