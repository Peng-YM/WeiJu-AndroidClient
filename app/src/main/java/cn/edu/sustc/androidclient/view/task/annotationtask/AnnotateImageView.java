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
import android.util.DisplayMetrics;
import android.view.MotionEvent;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import cn.edu.sustc.androidclient.view.task.annotationtask.Shape.Coordinate;

public class AnnotateImageView extends AppCompatImageView {
    private Context context;
    private Bitmap srcBitmap; // original picture
    private Bitmap mixedBitmap;

    private Paint paint;
    private List<Shape> shapeList;
    private Shape currentShape;
    private Mode mode;
    private SelectMode selectMode;

    private int touchDownX = 0;
    private int touchDownY = 0;
    private Coordinate startPoint = new Coordinate(0, 0);
    private Matrix currentMatrix, savedMatrix;

    private Canvas myCanvas; // canvas to draw the mixed picture

    private float oldDistance = 1f;
    private Coordinate midPoint;

    private DisplayMetrics dm;

    public AnnotateImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.context = context;

        // init data
        shapeList = new ArrayList<>();
        mode = Mode.EDIT;
        selectMode = SelectMode.NONE;
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

        dm = context.getResources().getDisplayMetrics();
        initMixedBitmap();
        currentMatrix = new Matrix();
        savedMatrix = new Matrix();
    }


    /**
     * reset mixedBitmap to original bitmap
     */
    private void initMixedBitmap() {
        mixedBitmap = null;
        myCanvas = null;

        mixedBitmap = srcBitmap.copy(Bitmap.Config.RGB_565, true);
        myCanvas = new Canvas(mixedBitmap);

        // draw existing rectangles
        for (Shape shape : shapeList) {
            shape.draw(myCanvas, paint);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // draw current rectangle(during motion) on view canvas
        if (currentShape != null) {
            currentShape.draw(canvas, paint);
        }

        // draw image
        setImageBitmap(mixedBitmap);
    }

    /**
     * Center the image
     *
     * @param horizontal center in horizontal
     * @param vertical   center in vertical
     */
    protected Matrix center(boolean horizontal, boolean vertical) {
        Matrix m = new Matrix();
        RectF rect = new RectF(0, 0, srcBitmap.getWidth(), srcBitmap.getHeight());
        m.mapRect(rect);

        float height = rect.height();
        float width = rect.width();

        float deltaX = 0, deltaY = 0;

        if (vertical) {
            // 图片小于屏幕大小，则居中显示。大于屏幕，上方留空则往上移，下方留空则往下移
            int screenHeight = dm.heightPixels;
            if (height < screenHeight) {
                deltaY = (screenHeight - height) / 2 - rect.top;
            } else if (rect.top > 0) {
                deltaY = -rect.top;
            } else if (rect.bottom < screenHeight) {
                deltaY = this.getHeight() - rect.bottom;
            }
        }

        if (horizontal) {
            int screenWidth = dm.widthPixels;
            if (width < screenWidth) {
                deltaX = (screenWidth - width) / 2 - rect.left;
            } else if (rect.left > 0) {
                deltaX = -rect.left;
            } else if (rect.right < screenWidth) {
                deltaX = screenWidth - rect.right;
            }
        }
        m.postTranslate(deltaX, deltaY);
        return m;
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
        shape.draw(myCanvas, paint);
    }


    /**
     * get the shape clicked(center) by user
     *
     * @param clickPoint user's click point
     */
    public Shape getShape(Coordinate clickPoint) {
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
        return shapeList.get(minIndex);
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
        switch (mode) {
            case EDIT:
                handleEdit(event);
                break;
            case SELECT:
                handleSelect(event);
                break;
            default:
        }
        performClick();
        return true;
    }

    /**
     * handle touch event when in select mode
     *
     * @param event
     */
    private void handleSelect(MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN: // single finger
                savedMatrix.set(currentMatrix);
                startPoint.set(event.getX(), event.getY());
                selectMode = SelectMode.DRAG;
                break;

            case MotionEvent.ACTION_POINTER_DOWN: // double finger
                oldDistance = calculateDistance(event);
                // set threshold to avoid jitter
                if (oldDistance > 10f) {
                    savedMatrix.set(currentMatrix);
                    midPoint = calculateMidPoint(event);
                    selectMode = SelectMode.ZOOM;
                }

            case MotionEvent.ACTION_MOVE:
                // single finger drag
                if (selectMode == SelectMode.DRAG) {
                    currentMatrix.set(savedMatrix);
                    float dx = event.getX() - startPoint.x;
                    float dy = event.getY() - startPoint.y;
                    currentMatrix.postTranslate(dx, dy);
                }
                // double fingers zoom
                else if (selectMode == SelectMode.ZOOM && event.getPointerCount() == 2) {
                    float newDistance = calculateDistance(event);
                    currentMatrix.set(savedMatrix);

                    if (newDistance > 10f) {
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
                if (event.getActionIndex() == 0) {
                    startPoint = new Coordinate(event.getX(1), event.getY(1));
                } else if (event.getActionIndex() == 1) {
                    startPoint = new Coordinate(event.getX(0), event.getY(0));
                }
                selectMode = SelectMode.DRAG;
                break;
        }
        setImageMatrix(currentMatrix);
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
     * handle touch event when in edit mode
     *
     * @param event
     */
    private void handleEdit(MotionEvent event) {
        switch (event.getAction()) {
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
                    // need to apply inverse matrix transformation to the shape
                    RectF rect = new RectF(touchDownX, touchDownY, lastTouchX, lastTouchY);
                    Matrix inverseCopy = new Matrix();
                    if (currentMatrix.invert(inverseCopy)) {
                        inverseCopy.mapRect(rect);
                    }

                    Shape transformedShape =
                            new Rectangle((int) rect.left, (int) rect.top, (int) rect.right, (int) rect.bottom);
                    addShape(transformedShape);
                    invalidate();
                }
                break;
            }
        }
    }

    /**
     * Modes
     */
    public enum Mode {
        SELECT,
        EDIT
    }

    /**
     * Select Modes
     */
    public enum SelectMode {
        NONE,
        DRAG,
        ZOOM
    }
}
