package cn.edu.sustc.androidclient.view.task.annotationtask;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import cn.edu.sustc.androidclient.R;

public class AnnotateImageView extends View {
    private Paint paint;
    private Bitmap bitmap;
    private List<Shape> shapeList;
    private Shape currentShape;
    private EditMode mode;

    public AnnotateImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        paint = new Paint();
        shapeList = new ArrayList<>();

        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.cover);
        mode = EditMode.EDIT;
        paint.setStrokeWidth(10);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLUE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
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

    public void addDraftShape(Shape shape){
        currentShape = shape;
    }

    public void addShape(Shape shape){
        shapeList.add(shape);
        currentShape = null;
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

    public EditMode getMode() {
        return mode;
    }

    public void setMode(EditMode mode) {
        this.mode = mode;
    }

    public static enum EditMode{
        SELECT,
        EDIT;
    }
}
