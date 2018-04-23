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
    private List<Shape> shapeList;
    private Shape currentShape;
    private int currentColor;
    private int startX=0, startY=0, endX=0, endY=0;
    private EditMode mode;

    public AnnotateImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        paint = new Paint();
        shapeList = new ArrayList<>();
        currentColor = Color.BLUE;

        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.cover);
        mode = EditMode.EDIT;
        paint.setStrokeWidth(10);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLUE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        this.canvas = canvas;
        super.onDraw(canvas);
        // draw image
        canvas.drawBitmap(bitmap, 0, 0, null);

        // draw current rectangle(during motion)
        if (currentShape != null){
            currentShape.draw(canvas, paint);
        }

        // draw existing rectangles
        for (Shape shape: shapeList){
            shape.draw(canvas, paint);
        }
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
            currentShape = new Rectangle(startX, startY, endX, endY);
            invalidate();
            return true;
        }
        else if (event.getAction() == MotionEvent.ACTION_UP){
            endX = (int) event.getX();
            endY = (int) event.getY();
            // ignore the rectangle that is too small(created from user's click, not drag)
            if (endX - startX > 10){
                shapeList.add(new Rectangle(startX, startY, endX, endY));
                currentShape = null;
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
        shapeList.clear();
        invalidate();
    }

    /**
     * undo the previous step
     * */
    public boolean undo(){
        if (shapeList.size() > 0){
            shapeList.remove(shapeList.size() - 1);
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
