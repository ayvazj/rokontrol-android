package com.github.ayvazj.rokontrol;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;


public class RokuButton extends Button {
    private RokuButtonActionListener rokuButtonActionListener;

    public interface RokuButtonActionListener {
        public void onRokuKeyDown(View v);

        public void onRokuKeyUp(View v);

        public void onRokuKeyPress(View v);
    }

    public RokuButton(Context context) {
        super(context, null);
    }

    public RokuButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RokuButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setRokuButtonActionListener(RokuButtonActionListener listener) {
        this.rokuButtonActionListener = listener;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (rokuButtonActionListener != null) {
                    rokuButtonActionListener.onRokuKeyDown(RokuButton.this);
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_OUTSIDE:
                if (rokuButtonActionListener != null) {
                    rokuButtonActionListener.onRokuKeyUp(RokuButton.this);
                }
                break;
        }
        return true;
    }
}
