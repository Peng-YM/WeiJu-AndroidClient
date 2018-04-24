package cn.edu.sustc.androidclient.view.task.annotationtask;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import cn.edu.sustc.androidclient.R;
import cn.edu.sustc.androidclient.view.task.annotationtask.Shape.Coordinate;

public class AnnotateImageView extends AppCompatImageView {
    private Context context;
    private Bitmap srcBitmap; // original picture

    private Paint paint;
    private List<Shape> shapeList;
    private Shape currentShape;
    private EditMode editMode;
    private SelectMode selectMode;

    private int touchDownX = 0;
    private int touchDownY = 0;
    private Coordinate startPoint = new Coordinate(0, 0), endPoint;
    private Matrix currentMatrix, savedMatrix;
    private int viewWidth, viewHeight;
    private Canvas myCanvas;

    private float oldDistance = 1f;
    private Coordinate midPoint;

    public AnnotateImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.context = context;

        // init data
        shapeList = new ArrayList<>();
        editMode = EditMode.EDIT;
        selectMode = SelectMode.NONE;

        init(BitmapFactory.decodeResource(getResources(), R.drawable.cover));
    }

    private void init(Bitmap bitmap){
        srcBitmap = bitmap;
        currentMatrix = new Matrix();
        savedMatrix = new Matrix();

        paint = new Paint();
        paint.setStrokeWidth(10);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLUE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        viewWidth = getWidth();
        viewHeight = getHeight();
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // draw image
        setImageBitmap(srcBitmap);

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
     *  get the shape clicked(center) by user
     * */
    public Shape getShape(Coordinate clickPoint){
        double minDistance = Integer.MAX_VALUE;
        int minIndex = -1, cnt = 0;

        for(Shape shape: shapeList){
            Coordinate center = shape.getCenter();
            double newDistance = center.distanceTo(clickPoint);
            if(minDistance > newDistance){
                minIndex = cnt;
                minDistance = newDistance;
                Logger.d("Update Distance: %f", minDistance);
            }
            cnt++;
        }
        return shapeList.get(minIndex);
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

    public EditMode getEditMode() {
        return editMode;
    }

    public void setEditMode(EditMode editMode) {
        this.editMode = editMode;
    }

    /**
     * handle touch event
     * Operations:
     * 1. Select editMode:
     *  a. Select one shape
     *  b. Double touch to scale and move the picture
     * 2. Edit editMode:
     *  single touch to draw shapes
     * */
    @Override
    public boolean onTouchEvent(MotionEvent event){
        switch (editMode){
            case EDIT:
                return handleEdit(event);
            case SELECT:
                return handleSelect(event);
            default:
                return true;
        }
    }

    private boolean handleSelect(MotionEvent event){
        switch (event.getAction() & MotionEvent.ACTION_MASK){
            case MotionEvent.ACTION_DOWN: // single finger
                savedMatrix.set(currentMatrix);
                startPoint.set(event.getX(), event.getY());
                selectMode = SelectMode.DRAG;
                break;

            case MotionEvent.ACTION_POINTER_DOWN: // double finger
                oldDistance = calculateDistance(event);
                // set threshold to avoid jitter
                if (oldDistance > 10f){
                    savedMatrix.set(currentMatrix);
                    midPoint = calculateMidPoint(event);
                    selectMode = SelectMode.ZOOM;
                }

            case MotionEvent.ACTION_MOVE:
                // single finger drag
                if(selectMode == SelectMode.DRAG){
                    currentMatrix.set(savedMatrix);
                    float dx = event.getX() - startPoint.x;
                    float dy = event.getY() - startPoint.y;
                    currentMatrix.postTranslate(dx, dy);
                }
                // double fingers zoom
                else if (selectMode == SelectMode.ZOOM && event.getPointerCount() == 2){
                    float newDistance = calculateDistance(event);
                    currentMatrix.set(savedMatrix);

                    if (newDistance > 10f){
                        // calculate scale percentage from the movement
                        float scale = newDistance / oldDistance;
                        currentMatrix.postScale(scale, scale, midPoint.x, midPoint.y);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                selectMode = SelectMode.NONE;
                break;
            case MotionEvent.ACTION_POINTER_UP:
                savedMatrix.set(currentMatrix);
                if (event.getActionIndex() == 0){
                    startPoint = new Coordinate(event.getX(1), event.getY(1));
                }else if (event.getActionIndex() == 1){
                    startPoint = new Coordinate(event.getX(0), event.getY(0));
                }
                selectMode = SelectMode.DRAG;
                break;
        }
        setImageMatrix(currentMatrix);
        return true;
    }

    private float calculateDistance(MotionEvent event){
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(x * x + y * y);
    }

    private Coordinate calculateMidPoint(MotionEvent event){
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        return new Coordinate(x / 2, y / 2);
    }

    private boolean handleEdit(MotionEvent event){
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN: {
                touchDownX = (int) event.getX();
                touchDownY = (int) event.getY();
                break;
            }
            case MotionEvent.ACTION_MOVE:
                int lastTouchX = 0;
                int lastTouchY = 0;
            {
                lastTouchX = (int) event.getX();
                lastTouchY = (int) event.getY();
                Shape currentShape = new Rectangle(touchDownX, touchDownY, lastTouchX, lastTouchY);
                addDraftShape(currentShape);
                invalidate();
                break;
            }
            case MotionEvent.ACTION_UP: {
                lastTouchX = (int) event.getX();
                lastTouchY = (int) event.getY();
                // ignore the rectangle that is too small(created from user's click, not drag)
                if (lastTouchX - touchDownX > 10) {
                    Shape currentShape = new Rectangle(touchDownX, touchDownY, lastTouchX, lastTouchY);
                    addShape(currentShape);
                    invalidate();
                }
                break;
            }
        }
        return true;
    }

    public static enum EditMode{
        SELECT,
        EDIT;
    }

    public static enum SelectMode{
        NONE,
        DRAG,
        ZOOM;
    }
}
