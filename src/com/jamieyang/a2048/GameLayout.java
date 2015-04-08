package com.jamieyang.a2048;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

public class GameLayout extends RelativeLayout{

	public GameLayout(Context context) {
		super(context);
	}
	
	public GameLayout(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public GameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
    	super(context, attrs, defStyleAttr, 0);
    }

    public GameLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
