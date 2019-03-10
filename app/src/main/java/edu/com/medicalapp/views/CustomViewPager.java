package edu.com.medicalapp.views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class CustomViewPager extends ViewPager {
    private int leftX, leftY, rightX, rightY;
    private int lastItemIndex = -1;
    private boolean disableSwipe = false;

    public CustomViewPager(@NonNull Context context) {
        super(context);
    }

    public CustomViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        try {
            if (disableSwipe && (getCurrentItem() == lastItemIndex ||
                    ev.getX() >= leftX && ev.getX() <= rightX && ev.getY() >= leftY && ev.getY() <= rightY)) {
                return false;
            } else {
                return super.onInterceptTouchEvent(ev);
            }
        } catch (IllegalArgumentException e) {
            return true;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        try {
            if (disableSwipe && (getCurrentItem() == lastItemIndex || ev.getX() >= leftX && ev.getX() <= rightX &&
                    ev.getY() >= leftY && ev.getY() <= rightY)) {
                return false;
            } else {
                return super.onTouchEvent(ev);
            }
        } catch (IllegalArgumentException e) {
            return true;
        }


    }


    public void setRestrictedArea(int leftX, int leftY, int rightX, int rightY) {
        this.leftX = leftX;
        this.leftY = leftY;
        this.rightX = rightX;
        this.rightY = rightY;
    }

    public void disableSwipe(boolean disableSwipe) {
        this.disableSwipe = disableSwipe;
    }
}