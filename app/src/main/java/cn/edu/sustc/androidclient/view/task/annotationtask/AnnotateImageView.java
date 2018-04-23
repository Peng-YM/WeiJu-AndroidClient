package cn.edu.sustc.androidclient.view.task.annotationtask;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import cn.edu.sustc.androidclient.R;

public class AnnotateImageView extends View {
    private Paint paint;
    private Bitmap bitmap;
    private Canvas canvas;
    private List<Rectangle> rectangleList;
    private Rectangle currentRectangle;
    private int currentColor;
    private int startX=0, startY=0, endX=0, endY=0;
    private EditMode mode;

    public AnnotateImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        paint = new Paint();
        rectangleList = new ArrayList<>();
        currentColor = Color.BLUE;

        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.cover);
        mode = EditMode.EDIT;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        this.canvas = canvas;
        super.onDraw(canvas);
        // draw image
        canvas.drawBitmap(bitmap, 0, 0, null);

        // draw current rectangle(during motion)
        if (currentRectangle != null){
            drawBBox(currentRectangle);
        }

        // draw existing rectangles
        for (Rectangle rectangle: rectangleList){
            drawBBox(rectangle);
        }
    }

    private void drawBBox(Rectangle rectangle){
        paint.setStrokeWidth(10);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(rectangle.color);
        canvas.drawRect(rectangle.getRectangle(), paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        switch (mode){
            case EDIT:
                return onTouchEventEditMode(event);
            default:
                return onTouchEventSelectMode(event);
        }
    }

    private boolean onTouchEventEditMode(MotionEvent event){
        if (event.getAction() == MotionEvent.ACTION_DOWN){
            startX = (int) event.getX();
            startY = (int) event.getY();
            invalidate();
            return true;
        }
        else if(event.getAction() == MotionEvent.ACTION_MOVE){
            endX = (int) event.getX();
            endY = (int) event.getY();
            currentRectangle = new Rectangle(startX, startY, endX, endY, getCurrentColor());
            invalidate();
            return true;
        }
        else if (event.getAction() == MotionEvent.ACTION_UP){
            endX = (int) event.getX();
            endY = (int) event.getY();
            // ignore the rectangle that is too small(created from user's click, not drag)
            if (endX - startX > 10){
                rectangleList.add(new Rectangle(startX, startY, endX, endY, getCurrentColor()));
                currentRectangle = null;
                invalidate();
            }
            return true;
        }
        return super.onTouchEvent(event);
    }

    private boolean onTouchEventSelectMode(MotionEvent event){
        // TODO
        return super.onTouchEvent(event);
    }

    public void setCurrentColor(int currentColor) {
        this.currentColor = currentColor;
    }

    public int getCurrentColor() {
        return currentColor;
    }

    /**
     * clear the screen
     * */
    public void clear(){
        rectangleList.clear();
        invalidate();
    }

    /**
     * undo the previous step
     * */
    public boolean undo(){
        if (rectangleList.size() > 0){
            rectangleList.remove(rectangleList.size() - 1);
            invalidate();
            return true;
        }
        return false;
    }

    public static enum EditMode{
        SELECT,
        EDIT;
    }


    public EditMode getMode() {
        return mode;
    }

    public void setMode(EditMode mode) {
        this.mode = mode;
    }
}
