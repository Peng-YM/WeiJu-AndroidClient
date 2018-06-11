package cn.edu.sustc.androidclient.view.task.annotationtask;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import cn.edu.sustc.androidclient.view.task.annotationtask.Shape.Coordinate;

public class AnnotateImageView extends AppCompatImageView {
    private Bitmap srcBitmap; // original picture
    private Bitmap mixedBitmap;

    private Paint paint;
    private List<Shape> shapeList;
    private Shape currentShape;
    private Mode mode;

    private int touchDownX = 0;
    private int touchDownY = 0;
    private Coordinate startPoint = new Coordinate(0, 0);
    private int edit_index = -1;
    private Matrix currentMatrix, savedMatrix;

    private Canvas mixCanvas; // canvas to draw the mixed picture

    private float oldDistance = 1f;
    private Coordinate midPoint;

    public AnnotateImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        // init data
        shapeList = new ArrayList<>();
        mode = Mode.DRAW;
    }

    /**
     * @param bitmap: source bitmap
     */
    public void init(Bitmap bitmap) {
        srcBitmap = bitmap;
        paint = new Paint();
        paint.setStrokeWidth(10);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLUE);

        currentMatrix = new Matrix();
        savedMatrix = new Matrix();
        setImageMatrix(currentMatrix);
        initMixedBitmap();
    }


    /**
     * reset mixedBitmap to original bitmap
     */
    private void initMixedBitmap() {
        mixedBitmap = null;
        mixCanvas = null;

        mixedBitmap = srcBitmap.copy(Bitmap.Config.RGB_565, true);
        mixCanvas = new Canvas(mixedBitmap);

        // draw existing rectangles
        for (Shape shape : shapeList) {
            shape.draw(mixCanvas, paint);
        }
        // draw image
        setImageBitmap(mixedBitmap);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // draw current rectangle(during motion) on view canvas
        if (currentShape != null) {
            currentShape.draw(canvas, paint);
        }
    }

    /**
     * show current drawing shape on view canvas
     *
     * @param shape current drawing shape
     */
    public void addDraftShape(Shape shape) {
        currentShape = shape;
    }


    /**
     * draw shape to mixedBitmap
     *
     * @param shape shape to be added
     */
    public void addShape(Shape shape) {
        shapeList.add(shape);
        currentShape = null;
        // draw current shape on mixed canvas
        shape.draw(mixCanvas, paint);
        // redraw mixed image
        setImageBitmap(mixedBitmap);
    }


    /**
     * get the shape index clicked(center) by user
     *
     * @param clickPoint user's click point
     */
    public int getShape(Coordinate clickPoint) {
        double minDistance = Integer.MAX_VALUE;
        int minIndex = -1, cnt = 0;

        for (Shape shape : shapeList) {
            Coordinate center = shape.getCenter();
            double newDistance = center.distanceTo(clickPoint);
            if (minDistance > newDistance) {
                minIndex = cnt;
                minDistance = newDistance;
                Logger.d("Update Distance: %f", minDistance);
            }
            cnt++;
        }
        return minIndex;
    }

    /**
     * clear the screen
     */
    public void clear() {
        shapeList.clear();
        initMixedBitmap();
        invalidate();
    }


    /**
     * undo the previous step
     */
    public boolean undo() {
        if (shapeList.size() > 0) {
            shapeList.remove(shapeList.size() - 1);
            initMixedBitmap();
            invalidate();
            return true;
        }
        return false;
    }

    public Mode getMode() {
        return mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    /**
     * handle touch event
     * Operations:
     * 1. Select mode:
     * a. Select one shape
     * b. Double touch to scale and move the picture
     * 2. Edit mode:
     * single touch to draw shapes
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getPointerCount()){
            case 1:
                handleDraw(event);
                break;
            case 2:
                handleZoom(event);
                break;
        }
        return true;
    }

    /**
     * handle touch event when in zoom mode
     *
     * @param event
     */
    private void handleZoom(MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_POINTER_DOWN: // double finger
                oldDistance = calculateDistance(event);
                // set threshold to avoid jitter
                if (oldDistance > 10f) {
                    savedMatrix.set(currentMatrix);
                    startPoint = calculateMidPoint(event);
                }

            case MotionEvent.ACTION_MOVE:
                // double fingers drag
                currentMatrix.set(savedMatrix);
                midPoint = calculateMidPoint(event);
                float dx = midPoint.x - startPoint.x;
                float dy = midPoint.y - startPoint.y;
                currentMatrix.postTranslate(dx, dy);
                // double fingers zoom
                float newDistance = calculateDistance(event);
                if (newDistance > 10f) {
                    // calculate scale percentage from the movement
                    float scale = newDistance / oldDistance;
                    currentMatrix.postScale(scale, scale, midPoint.x, midPoint.y);
                }
                break;
        }
        setImageMatrix(currentMatrix);
        invalidate();
    }

    /**
     * handle touch event when in draw mode
     *
     * @param event motion event
     */
    private void handleDraw(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                touchDownX = (int) event.getX();
                touchDownY = (int) event.getY();
                break;
            }
            case MotionEvent.ACTION_MOVE:
                int lastTouchX;
                int lastTouchY;
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
                if ((lastTouchX - touchDownX > 10) && (lastTouchY - touchDownY > 10)) {
                    // need to apply inverse matrix transformation to the shape
                    RectF rect = new RectF(touchDownX, touchDownY, lastTouchX, lastTouchY);
                    Matrix inverseCopy = new Matrix();
                    if (currentMatrix.invert(inverseCopy)) {
                        inverseCopy.mapRect(rect);
                    }
                    // TODO: universal implementation for any shapes
                    Shape transformedShape =
                            new Rectangle((int) rect.left, (int) rect.top, (int) rect.right, (int) rect.bottom);

                    // TODO: test if the shape is out of image bound
                    addShape(transformedShape);
                    invalidate();
                }
                break;
            }
        }
    }

    private float calculateDistance(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(x * x + y * y);
    }

    private Coordinate calculateMidPoint(MotionEvent event) {
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        return new Coordinate(x / 2, y / 2);
    }

    /**
     * Modes
     */
    public enum Mode {
        ZOOM,
        DRAW
    }
}
