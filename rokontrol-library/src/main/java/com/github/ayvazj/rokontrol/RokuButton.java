package com.github.ayvazj.rokontrol;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;


public class RokuButton extends Button {
    private boolean isHolding = false;
    private Handler holdingHandler;
    private RokuButtonActionListener rokuButtonActionListener;

    public interface RokuButtonActionListener {
        public void onRokuKeyDown(View v);

        public void onRokuKeyUp(View v);

        public void onRokuKeyPress(View v);
    }

    private Runnable pressedKeyRunnable = new Runnable() {
        @Override
        public void run() {
            if (rokuButtonActionListener != null) {
                rokuButtonActionListener.onRokuKeyDown(RokuButton.this);
            }
        }
    };

    private Runnable downKeyRunnable = new Runnable() {
        @Override
        public void run() {
            if (isHolding) {
                if (rokuButtonActionListener != null) {
                    rokuButtonActionListener.onRokuKeyDown(RokuButton.this);
                }
                holdingHandler.postDelayed(this, 100);
            }
        }
    };


    public RokuButton(Context context) {
        super(context, null);
        holdingHandler = new Handler();
    }

    public RokuButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        holdingHandler = new Handler();
    }

    public RokuButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        holdingHandler = new Handler();
    }

    public void setRokuButtonActionListener(RokuButtonActionListener listener) {
        this.rokuButtonActionListener = listener;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (!isHolding) {
                    if (rokuButtonActionListener != null) {
                        rokuButtonActionListener.onRokuKeyDown(RokuButton.this);
                    }
                    isHolding = true;
                }
                holdingHandler.postDelayed(downKeyRunnable, 1000);
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_OUTSIDE:
                isHolding = false;
                holdingHandler.removeCallbacks(downKeyRunnable);
                if (rokuButtonActionListener != null) {
                    rokuButtonActionListener.onRokuKeyUp(RokuButton.this);
                }
                break;
        }
        return true;
    }
}
