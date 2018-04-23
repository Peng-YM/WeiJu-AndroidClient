package cn.edu.sustc.androidclient.view.task.annotationtask;

import android.graphics.Rect;

public class Rectangle {
    public int startX, startY, endX, endY;
    public int color;

    public Rectangle(int startX, int startY, int endX, int endY, int color) {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.color = color;
    }

    public Rect getRectangle(){
        return new Rect(startX, startY, endX, endY);
    }
}
