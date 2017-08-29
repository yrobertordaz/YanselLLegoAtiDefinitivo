package com.llegoati.llegoati.explorer;

import android.app.Activity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.Toast;

/**
 * Created by Richard on 02/09/2016.
 */
class GestureListener extends GestureDetector.SimpleOnGestureListener {
    private static final int SWIPE_MIN_DISTANCE = 50;
    private static final int SWIPE_MAX_OFF_PATH = 100;
    private static final int SWIPE_THRESHOLD_VELOCITY = 25;

    private MotionEvent mLastOnDownEvent = null;
    private Activity mActivity;

    public  GestureListener(Activity mActivity){
        this.mActivity = mActivity;

    }

    @Override
    public boolean onDown(MotionEvent e) {
        mLastOnDownEvent = e;
        return super.onDown(e);
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        // TODO Auto-generated method stub
        Toast.makeText(mActivity.getApplicationContext(), "On Single TAP up ", Toast.LENGTH_SHORT).show();
        return super.onSingleTapUp(e);
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                           float velocityY) {
        if (e1 == null) {
            e1 = mLastOnDownEvent;
        }
        if (e1 == null || e2 == null) {
            return false;
        }

        float dX = e2.getX() - e1.getX();
        float dY = e1.getY() - e2.getY();

        if (Math.abs(dY) < SWIPE_MAX_OFF_PATH
                && Math.abs(velocityX) >= SWIPE_THRESHOLD_VELOCITY
                && Math.abs(dX) >= SWIPE_MIN_DISTANCE) {
            /*if (dX > 0) {
                int position = tasks.pointToPosition((int) e1.getX(),
                        (int) e1.getY());

                int _id = (int) tasks.getItemIdAtPosition(position);
                databaseConnector.deleteContact(_id);

                new DeleteRow(_id, contactAdapter, getApplicationContext());
                contactAdapter.notifyDataSetChanged();


                Toast.makeText(getApplicationContext(),
                        "Right Swipe" + _id, Toast.LENGTH_SHORT).show();
            } else {

                Toast.makeText(getApplicationContext(), "Left Swipe",
                        Toast.LENGTH_SHORT).show();
            }*/
            return true;
        }

        return false;
    }
}
