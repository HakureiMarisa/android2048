package com.jamieyang.a2048;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class MainView extends View implements OnGestureListener{
	CellsView cv;
	GestureDetector gestureDetector;	
	Paint paint = new Paint();
	float paddingLeft = 20;
	float paddingTop = 20;
	float lineWidth = 10;
	
	public MainView(Context context) {
		super(context);
		gestureDetector = new GestureDetector(context, this);
		cv = new CellsView(context);
	}

	public void onDraw(Canvas canvas){
		paint.setARGB(255, 206, 182, 154);
		canvas.drawRoundRect(paddingLeft, paddingTop, paddingLeft + lineWidth * 5 + Cell.CELL_WIDTH * 4, paddingTop + lineWidth * 5 + Cell.CELL_HEIGHT * 4, 5, 5, paint);
		paint.setARGB(255, 205, 199, 187);
		for(int i = 0; i < 4; i++){
			for(int j = 0; j < 4; j++){
				float x = paddingLeft + lineWidth * (i + 1) + Cell.CELL_WIDTH * i;
				float y = paddingTop + lineWidth * (j + 1) + Cell.CELL_HEIGHT * j;
				canvas.drawRoundRect(x, y, x + Cell.CELL_WIDTH, y + Cell.CELL_HEIGHT, 5, 5, paint);
			}
		}
		cv.init(paddingLeft + lineWidth, paddingTop + lineWidth, lineWidth);
		cv.draw(canvas);
	}

	public boolean onTouchEvent(MotionEvent event) {
		gestureDetector.onTouchEvent(event);
		return true;
	}

	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		float offsettingX = e1.getX() - e2.getX();
		float offsettingY = e1.getY() - e2.getY();
		
		if(Math.abs(offsettingX) > Math.abs(offsettingY)){
			if(offsettingX <= -50){
				Toast.makeText(this.getContext(), "右滑", Toast.LENGTH_SHORT).show();
			}else if(offsettingX >= 50){
				Toast.makeText(this.getContext(), "左滑", Toast.LENGTH_SHORT).show();
			}
		}else{
			if(offsettingY >= 50){
				Toast.makeText(this.getContext(), "上滑", Toast.LENGTH_SHORT).show();
			}else if(offsettingY <= -50){
				Toast.makeText(this.getContext(), "下滑", Toast.LENGTH_SHORT).show();
			}
		}
		return true;
	}
}
