package com.innovationtechnology.iordanis.stampalapasta.ui.widget;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Button;

import com.innovationtechnology.iordanis.stampalapasta.R;

/**
 * Created by iordanis on 19/2/2015.
 */
public class CompoundDrawableStateButton extends Button {
    private final static byte DRAWABLE_TOP_INDEX = 1;

    public CompoundDrawableStateButton(Context context) {
        super(context);
    }

    public CompoundDrawableStateButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CompoundDrawableStateButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        final Drawable drawable = getCompoundDrawables()[DRAWABLE_TOP_INDEX];
        if (drawable != null)
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    drawable.setColorFilter(getResources().getColor(R.color.dashboard_button_touch), PorterDuff.Mode.MULTIPLY);
                    invalidate();
                    break;
                case MotionEvent.ACTION_CANCEL:
                    drawable.clearColorFilter();
                    invalidate();
                    break;
                case MotionEvent.ACTION_UP:
                    drawable.clearColorFilter();
                    invalidate();
                    break;
            }
        return super.onTouchEvent(event);
    }
}
