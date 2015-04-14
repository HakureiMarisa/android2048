package com.jamieyang.a2048;

import java.util.ArrayList;
import java.util.List;

import com.jamieyang.a2048.MainView.Location;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.View;
import android.view.ViewGroup;

public class GameLayout extends ViewGroup {
	GestureDetector gestureDetector;
	Paint paint = new Paint();
	float paddingLeft = 20;
	float paddingTop = 20;
	float lineWidth = 10;
	float cardWidth = 70;
	float cardHeight = 70;

	final static int COUNTX = 4;
	final static int COUNTY = 4;

	Card[][] cards = new Card[COUNTX][COUNTY];
	List<Location> leftcards = new ArrayList<Location>();
	boolean hasMoved = false;
	Paint p = new Paint();

	@Override
	public void onDraw(Canvas canvas) {
		paint.setARGB(255, 206, 182, 154);
		canvas.drawRoundRect(new RectF(paddingLeft, paddingTop, paddingLeft
				+ lineWidth * 5 + cardWidth * 4, paddingTop + lineWidth * 5
				+ cardHeight * 4), 5, 5, paint);
		paint.setARGB(255, 205, 199, 187);
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				float x = paddingLeft + lineWidth + (cardWidth + lineWidth) * i;
				float y = paddingTop + lineWidth + (cardHeight + lineWidth) * j;
				canvas.drawRoundRect(new RectF(x, y, x + cardWidth, y
						+ cardHeight), 5, 5, paint);
			}
		}
	}

	public GameLayout(Context context, AttributeSet attrs) {
		super(context, attrs, 0);
		this.setWillNotDraw(false);
		int mScreenWidth = context.getResources().getDisplayMetrics().widthPixels;
		int mScreenHeight = context.getResources().getDisplayMetrics().heightPixels;
		// 线格比例1/5
		lineWidth = (mScreenWidth - 2 * paddingLeft) / (5 + 4 * 6);
		cardWidth = 6 * lineWidth;
		cardHeight = cardWidth;

		Card card = new Card(getContext());
		card.value = 1024;
		this.addView(card);

		Card card1 = new Card(getContext());
		card1.value = 64;
		this.addView(card1);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int count = getChildCount();
		for (int i = 0; i < count; i++) {
			// mesure child
			getChildAt(i).measure(MeasureSpec.UNSPECIFIED,
					MeasureSpec.UNSPECIFIED);
		}
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO Auto-generated method stub
		int left = (int) (paddingLeft + lineWidth);
		int top = (int) (paddingTop + lineWidth);
		int childCount = getChildCount();
		for (int i = 0; i < childCount; i++) {
			View childView = getChildAt(i);
			childView.layout(left, top, left + (int) cardWidth, top
					+ (int) cardHeight);
			left += cardWidth + lineWidth;
			top += cardHeight + lineWidth;
		}
	}
}
