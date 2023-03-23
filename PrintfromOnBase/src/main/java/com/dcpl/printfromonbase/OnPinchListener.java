package com.dcpl.printfromonbase;

import android.view.ScaleGestureDetector;


/**
 * This is an implementation of the SimpleOnScaleGestureListener class provided by the Android framework,
 * which listens for pinch gestures and scales a ZoomableRelativeLayout accordingly.
 *
 * The OnPinchListener class has four instance variables: startingSpan, endSpan, startFocusX,
 * startFocusY, and mZoomableRelativeLayout.
 *
 * The onScaleBegin method is called when the scale gesture begins. It sets the startingSpan,
 * startFocusX, and startFocusY variables to the current values of the corresponding properties of the ScaleGestureDetector object, which represents the current state of the scale gesture. It returns true to indicate that the gesture is being handled.
 *
 * The onScale method is called when the scale gesture changes. It computes the ratio of the current span of the gesture to the starting span, and passes this ratio, along with the starting focus point, to the scale method of the ZoomableRelativeLayout object to update its scale and position. It returns true to indicate that the gesture is being handled.
 *
 * The onScaleEnd method is called when the scale gesture ends. It calls the restore method of the ZoomableRelativeLayout object to restore its original scale and position.
 *
 * Overall, this implementation provides pinch-to-zoom functionality for a ZoomableRelativeLayout view.
 */
public class OnPinchListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {

    float startingSpan;
    float endSpan;
    float startFocusX;
    float startFocusY;
    private ZoomableRelativeLayout mZoomableRelativeLayout;


    public boolean onScaleBegin(ScaleGestureDetector detector) {
        startingSpan = detector.getCurrentSpan();
        startFocusX = detector.getFocusX();
        startFocusY = detector.getFocusY();
        return true;
    }


    public boolean onScale(ScaleGestureDetector detector) {
        mZoomableRelativeLayout.scale(detector.getCurrentSpan()/startingSpan, startFocusX, startFocusY);
        return true;
    }

    public void onScaleEnd(ScaleGestureDetector detector) {
        mZoomableRelativeLayout.restore();
    }
}