package com.jamieyang.a2048;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainView extends RelativeLayout implements OnGestureListener{
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
	
	public MainView(Context context) {	
		super(context);
		System.out.println("zzz");
		this.setWillNotDraw(false);
		
		gestureDetector = new GestureDetector(context, this);
		//初始化剩余格子信息
		for(int i = 0; i < cards.length; i++){
			for(int j = 0; j < cards[i].length; j++){
				this.leftcards.add(new Location(i, j));
			}
		}
	}

	public MainView(Context context, AttributeSet attrs, int defStyle){
		super(context, attrs, defStyle);  
		System.out.println("zzz1");
		this.setWillNotDraw(false);
		
		gestureDetector = new GestureDetector(context, this);
		//初始化剩余格子信息
		for(int i = 0; i < cards.length; i++){
			for(int j = 0; j < cards[i].length; j++){
				this.leftcards.add(new Location(i, j));
			}
		}

	}
	
	public MainView(Context context, AttributeSet attrs){
		super(context, attrs, 0);  
		System.out.println("zzz2");
		this.setWillNotDraw(false);
		
		gestureDetector = new GestureDetector(context, this);
		//初始化剩余格子信息
		for(int i = 0; i < cards.length; i++){
			for(int j = 0; j < cards[i].length; j++){
				this.leftcards.add(new Location(i, j));
			}
		}

	}
	
	public void onDraw(Canvas canvas){
		paint.setARGB(255, 206, 182, 154);
		canvas.drawRoundRect(paddingLeft, paddingTop, paddingLeft + lineWidth * 5 + cardWidth * 4, paddingTop + lineWidth * 5 + cardHeight * 4, 5, 5, paint);
		paint.setARGB(255, 205, 199, 187);
		for(int i = 0; i < 4; i++){
			for(int j = 0; j < 4; j++){
				float x = paddingLeft + lineWidth + (cardWidth + lineWidth) * i;
				float y = paddingTop + lineWidth + (cardHeight + lineWidth) * j;
				canvas.drawRoundRect(x, y, x + cardWidth, y + cardHeight, 5, 5, paint);
			}
		}
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
				this.moveRight();
				this.invalidate();
			}else if(offsettingX >= 50){
				Toast.makeText(this.getContext(), "左滑", Toast.LENGTH_SHORT).show();
				this.moveLeft();
				this.invalidate();
			}
		}else{
			if(offsettingY >= 50){
				Toast.makeText(this.getContext(), "上滑", Toast.LENGTH_SHORT).show();
				this.moveUp();
				this.invalidate();
			}else if(offsettingY <= -50){
				Toast.makeText(this.getContext(), "下滑", Toast.LENGTH_SHORT).show();
				this.moveDown();
				this.invalidate();
			}
		}
		return true;
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
	
	public void addCard(){		
		int leftCount = leftcards.size();
		int location = new Random().nextInt(leftCount);
		Location l = leftcards.get(location);
		int value = Math.random() < 0.5 ? 2 : 4;
		Card card = new Card(getContext());
		//card.setX((lineWidth + cardWidth) * l.row);
		//card.setY((lineWidth + cardHeight) * l.col);
		card.width = cardWidth;
		card.height = cardHeight;
		card.value = value;
		this.cards[l.row][l.col] = card;
		System.out.println(getChildCount());
		this.addView(card);
		leftcards.remove(location);
	}
	
	public void refrashLeftcards(){
		this.leftcards.clear();
		for(int i = 0; i < cards.length; i++){
			for(int j = 0; j < cards[i].length; j++){
				if(cards[i][j].value == 0){
					this.leftcards.add(new Location(i, j));
				}				
			}
		}
	}
	
	public void moveLeft(){
		this.hasMoved = false;
		for(int row = 0; row < COUNTX; row++){
			for(int col = 0; col < COUNTY - 1; col++){
				for(int temp = col + 1; temp < COUNTY; temp++){
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
					}else if(cards[row][temp].value != 0 && cards[row][col].value != 0){
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
	
	public void moveUp(){
		this.hasMoved = false;
		for(int row = 0; row < COUNTX - 1; row++){
			for(int col = 0; col < COUNTY; col++){
				for(int temp = row + 1; temp < COUNTX ; temp++){
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
}
