package com.dcpl.printfromonbase;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.RelativeLayout;


/**
 *
 This is a custom class named ZoomableRelativeLayout that extends RelativeLayout.
 It provides functionality to zoom in and out on its child views by scaling the canvas during drawing.

 The class has three constructors which take different arguments such as Context, AttributeSet, and defStyle.

 The dispatchDraw method is overridden to scale the canvas according to the current
 scale factor and pivot point, and then call the super.dispatchDraw() method to draw its child views.

 The scale method takes three arguments, scaleFactor, pivotX, and pivotY, and sets the class
 variables mScaleFactor, mPivotX, and mPivotY respectively. It then invalidates the view to redraw it with the new scale factor.

 The restore method resets the scale factor to 1 and invalidates the view to redraw it with the original scale.

 The relativeScale and release methods are empty and do nothing, they might be intended
 for future functionality or left for customization by the user.
 */
public class ZoomableRelativeLayout extends RelativeLayout {
    float mScaleFactor = 1;
    float mPivotX;
    float mPivotY;

    public ZoomableRelativeLayout(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public ZoomableRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    public ZoomableRelativeLayout(Context context, AttributeSet attrs,
                                  int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
    }

    protected void dispatchDraw(Canvas canvas) {
        //canvas.save(Canvas.);
        canvas.scale(mScaleFactor, mScaleFactor, mPivotX, mPivotY);
        super.dispatchDraw(canvas);
        canvas.restore();
    }

    public void scale(float scaleFactor, float pivotX, float pivotY) {
        mScaleFactor = scaleFactor;
        mPivotX = pivotX;
        mPivotY = pivotY;
        this.invalidate();
    }

    public void restore() {
        mScaleFactor = 1;
        this.invalidate();
    }

    public void relativeScale(float v, float startFocusX, float startFocusY) {

    }

    public void release() {

    }
}
