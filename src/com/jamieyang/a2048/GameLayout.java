package com.jamieyang.a2048;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.jamieyang.a2048.MainView.Location;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;

public class GameLayout extends ViewGroup implements OnGestureListener{
	GestureDetector gestureDetector;
	Paint paint = new Paint();
	float paddingLeft = 20;
	float paddingTop = 20;
	float lineWidth = 10;
	float cardWidth = 70;
	float cardHeight = 70;

	final static int COUNTX = 4;
	final static int COUNTY = 4;
	
	//存储坐标与index的对应关系，负数表示当前位置无卡片
	int[][] cards = {
		{-1, -1, -1, -1},
		{-1, -1, -1, -1},
		{-1, -1, -1, -1},
		{-1, -1, -1, -1}
	};
	
	List<Location> leftcards = new ArrayList<Location>();
	boolean hasMoved = false;
	Paint p = new Paint();
	ScaleAnimation sa = new ScaleAnimation(0.1f, 1.0f, 0.1f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);

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
		gestureDetector = new GestureDetector(context, this);
		this.setWillNotDraw(false);
		int mScreenWidth = context.getResources().getDisplayMetrics().widthPixels;
		int mScreenHeight = context.getResources().getDisplayMetrics().heightPixels;
		// 线、格比例1/5
		lineWidth = (mScreenWidth - 2 * paddingLeft) / (5 + 4 * 6);
		cardWidth = 6 * lineWidth;
		cardHeight = cardWidth;
		this.refrashLeftcards();
		
		sa.setDuration(200);
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
			Card childView = (Card) getChildAt(i);
			int childLeft = (int) (left + childView.row * (cardWidth + lineWidth));
			int childTop = (int) (top + childView.col * (cardHeight + lineWidth));
			System.out.println(childView.row + ", " + childView.col);
			childView.layout(childLeft, childTop, childLeft + (int) cardWidth, childTop + (int) cardHeight);
		}
	}

	@Override
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
				//this.moveRight();
				this.invalidate();
			}else if(offsettingX >= 50){
				Toast.makeText(this.getContext(), "左滑", Toast.LENGTH_SHORT).show();
				//this.moveLeft();
				this.invalidate();
			}
		}else{
			if(offsettingY >= 50){
				Toast.makeText(this.getContext(), "上滑", Toast.LENGTH_SHORT).show();
				this.moveUp();
				this.invalidate();
			}else if(offsettingY <= -50){
				Toast.makeText(this.getContext(), "下滑", Toast.LENGTH_SHORT).show();
				//this.moveDown();
				this.invalidate();
			}
		}
		return true;
	}
	
	public void moveLeft(){
		this.hasMoved = false;
		for(int row = 0; row < COUNTX; row++){
			for(int col = 0; col < COUNTY - 1; col++){
				for(int temp = col + 1; temp < COUNTY; temp++){
					if(cards[row][temp] >= 0){
						Card c = (Card) this.getChildAt(cards[row][temp]);
						if(cards[row][col] < 0){							
							c.row = row;
							c.col = col;						
							this.cards[row][col] = cards[row][temp];
							this.cards[row][temp] = -1;
							this.hasMoved = true;
							continue;
						}
						Card c1 = (Card) this.getChildAt(cards[row][col]);
						if(c.value == c1.value){
							c1.value *= 2;							
							this.removeView(c);
							this.cards[row][temp] = -1;
							this.hasMoved = true;
						}
						break;
					}
				}				
			}
		}
		if(this.hasMoved){
			this.refrashLeftcards();
			this.addCard();
		}
	}
	/*
	public void moveRight(){
		this.hasMoved = false;
		for(int row = 0; row < COUNTX; row++){
			for(int col = COUNTY - 1; col > 0; col--){
				for(int temp = col - 1; temp >= 0 ; temp--){
					if(cards[row][temp].value != 0 && cards[row][col].value == 0){
						int value = cards[row][temp].value;
						cards[row][col].value = value;
						cards[row][temp].value = 0;
						this.hasMoved = true;
					}else if(cards[row][temp].value != 0 && cards[row][col].value == cards[row][temp].value){
						cards[row][col].value *= 2;
						cards[row][temp].value = 0;
						this.hasMoved = true;
						break;
					}else if(cards[row][col].value != 0 && cards[row][temp].value != 0){
						break;
					}
				}				
			}
		}
		if(this.hasMoved){
			this.refrashLeftcards();
			this.addCard();
		}
	}
	*/
	public void moveUp(){
		this.hasMoved = false;
		for(int row = 0; row < COUNTX; row++){
			for(int col = 0; col < COUNTY - 1; col++){
				for(int temp = col + 1; temp < COUNTY; temp++){
					if(cards[row][temp] >= 0){
						Card c = (Card) this.getChildAt(cards[row][temp]);
						if(cards[row][col] < 0){							
							c.row = row;
							c.col = col;						
							this.cards[row][col] = cards[row][temp];
							this.cards[row][temp] = -1;
							this.hasMoved = true;
							continue;
						}
						Card c1 = (Card) this.getChildAt(cards[row][col]);
						if(c.value == c1.value){
							c1.value *= 2;							
							this.removeView(c);
							this.cards[row][temp] = -1;
							this.hasMoved = true;
						}
						break;
					}
				}				
			}
		}
		if(this.hasMoved){
			this.refrashLeftcards();
			this.addCard();
		}
	}
	/*
	public void moveDown(){
		System.out.println("movedown");
		this.hasMoved = false;
		for(int row = COUNTX - 1; row > 0; row--){
			for(int col = 0; col < COUNTY; col++){
				for(int temp = row - 1; temp >= 0 ; temp--){
					if(cards[temp][col].value != 0 && cards[row][col].value == 0){
						int value = cards[temp][col].value;
						cards[row][col].value = value;
						cards[temp][col].value = 0;
						this.hasMoved = true;
					}else if(cards[temp][col].value != 0 && cards[row][col].value == cards[temp][col].value){
						cards[row][col].value *= 2;
						cards[temp][col].value = 0;
						this.hasMoved = true;
						break;
					}else if(cards[temp][col].value != 0 && cards[row][col].value != 0){
						break;
					}
				}				
			}
		}
		if(this.hasMoved){
			this.refrashLeftcards();
			this.addCard();
		}
	}
	*/
	public void addCard(){		
		int leftCount = leftcards.size();
		int index = new Random().nextInt(leftCount);
		Location l = leftcards.get(index);		
		int value = Math.random() < 0.7 ? 2 : 4;
		Card card = new Card(getContext());		
		card.value = value;
		card.row = l.row;
		card.col = l.col;
		this.addView(card);
		card.startAnimation(sa);		
		this.cards[l.row][l.col] = indexOfChild(card);
		leftcards.remove(index);
	}
	
	public void refrashLeftcards(){
		this.leftcards.clear();
		for(int i = 0; i < cards.length; i++){
			for(int j = 0; j < cards[i].length; j++){
				if(cards[i][j] < 0){
					this.leftcards.add(new Location(i, j));
				}				
			}
		}
	}
	
	/*
	 * 格子所在行列位置存储类
	 */
	class Location{
		int row = 0;
		int col = 0;
		public Location(int row, int col) {
			this.row = row;
			this.col = col;
		}
	}
}
