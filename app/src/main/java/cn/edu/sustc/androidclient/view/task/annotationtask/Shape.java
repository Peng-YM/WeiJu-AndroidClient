package cn.edu.sustc.androidclient.view.task.annotationtask;

import android.graphics.Canvas;
import android.graphics.Paint;

public abstract class Shape {
    abstract void draw(Canvas canvas, Paint paint);

    abstract Coordinate getCenter();

    abstract Paint getPaint();

    abstract void setPaint(Paint paint);

    public static class Coordinate {
        public float x, y;

        public Coordinate(float x, float y) {
            this.x = x;
            this.y = y;
        }

        public float distanceTo(Coordinate coordinate) {
            float xd = x - coordinate.x;
            float yd = y - coordinate.y;
            return (float) Math.sqrt(Math.pow(xd, 2) + Math.pow(yd, 2));
        }

        public void set(float x, float y) {
            this.x = x;
            this.y = y;
        }
    }
}
