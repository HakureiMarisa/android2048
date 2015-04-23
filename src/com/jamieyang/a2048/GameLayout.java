package com.jamieyang.a2048;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.Transformation;
import android.view.animation.TranslateAnimation;
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
	
	Card[][] cards = new Card[COUNTX][COUNTY];
	
	List<Location> leftcards = new ArrayList<Location>();
	boolean hasMoved = false;
	Paint p = new Paint();
	ScaleAnimation sa = new ScaleAnimation(0f, 1.0f, 0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
	private Map<Card, Animation> animations = new HashMap<Card, Animation>();

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
		
		System.out.println("---" + animations.size());
		for(Map.Entry<Card, Animation> entry: animations.entrySet()){
			entry.getKey().startAnimation(entry.getValue());
			//entry.getKey().clearAnimation();
		}
		animations.clear();
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
				this.moveRight();
				//this.invalidate();
			}else if(offsettingX >= 50){
				Toast.makeText(this.getContext(), "左滑", Toast.LENGTH_SHORT).show();
				this.moveLeft();
				//this.invalidate();
			}
		}else{
			if(offsettingY >= 50){
				Toast.makeText(this.getContext(), "上滑", Toast.LENGTH_SHORT).show();
				this.moveUp();
				//this.invalidate();
			}else if(offsettingY <= -50){
				Toast.makeText(this.getContext(), "下滑", Toast.LENGTH_SHORT).show();
				this.moveDown();
				/*
				TranslateAnimation ta = new TranslateAnimation(1, 0, 1, 0, 1, 150, 1, 200);
				ta.setDuration(2000);

				Card c = (Card) getChildAt(0);
				c.startAnimation(ta);
				
				this.hasMoved = true;
				//this.invalidate();*/
			}
		}
		return true;
	}
	
	public void moveLeft(){
		this.hasMoved = false;
		for(int col = 0; col < COUNTX; col++){
			for(int row = 0; row < COUNTY - 1; row++){
				for(int temp = row + 1; temp < COUNTX; temp++){
					if(this._move(temp, col, row, col)){
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
		for(int col = 0; col < COUNTX; col++){
			for(int row = COUNTY - 1; row > 0; row--){
				for(int temp = row - 1; temp >= 0 ; temp--){
					if(this._move(temp, col, row, col)){
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
	
	private float getRelateDistanceX(int row1, int row2){
		return (row1 - row2) * (cardWidth + lineWidth);
	}
	
	private float getRelateDistanceY(int col1, int col2){
		return (col1 - col2) * (cardHeight + lineWidth);
	}
		
	public void moveUp(){
		this.hasMoved = false;
		for(int row = 0; row < COUNTX; row++){
			for(int col = 0; col < COUNTY - 1; col++){
				for(int temp = col + 1; temp < COUNTY; temp++){
					if(this._move(row, temp, row, col)){
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
		this.hasMoved = false;
		for(int row = 0; row < COUNTX; row++){
			for(int col = COUNTY - 1; col > 0; col--){
				for(int temp = col - 1; temp >= 0 ; temp--){
					if(this._move(row, temp, row, col)){
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
	
	public boolean _move(int row1, int col1, int row2, int col2){
		if(!this.isEmptyCard(row1, col1) && isEmptyCard(row2, col2)){
			
			TranslateAnimation ta = new TranslateAnimation(0, getRelateDistanceX(row2, row1), 0, getRelateDistanceY(col2, col1));
			ta.setDuration(2000);
			ta.setFillAfter(true);
			
			//animations.put(cards[row1][col1], ta);
			cards[row2][col2] = cards[row1][col1];
			cards[row2][col2].row = row2;
			cards[row2][col2].col = col2;
			cards[row1][col1] = null;
			this.hasMoved = true;
		}else if(!this.isEmptyCard(row1, col1) && cards[row1][col1].value == cards[row2][col2].value){
			/*
			AnimationSet as = new AnimationSet(true);
			TranslateAnimation ta = new TranslateAnimation(0, 0, 0, getRelateDistanceY(col, temp));
			ta.setDuration(2000);
			as.addAnimation(ta);
			
			ScaleAnimation sa = new ScaleAnimation(1.5f, 1.0f, 1.5f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
			sa.setDuration(2000);
			sa.setStartOffset(3000);
			//as.addAnimation(sa);
			as.setFillAfter(true);
			animations.put(cards[row][temp], as);
			*/
			this.removeView(cards[row1][col1]);
			cards[row2][col2].value *= 2;
			cards[row1][col1] = null;
			this.hasMoved = true;
			return true;
		}else if(!this.isEmptyCard(row1, col1) && !this.isEmptyCard(row1, col2)){
			return true;
		}
		return false;
	}
	
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

		sa.setStartOffset(200);
		this.animations.put(card, sa);
		
		this.cards[l.row][l.col] = card;
		leftcards.remove(index);
	}
	
	public void refrashLeftcards(){
		this.leftcards.clear();
		for(int i = 0; i < cards.length; i++){
			for(int j = 0; j < cards[i].length; j++){
				if(this.isEmptyCard(i, j)){
					this.leftcards.add(new Location(i, j));
				}				
			}
		}
	}
	
	private boolean isEmptyCard(int row, int col){
		if(cards[row][col] == null || cards[row][col].value == 0){
			return true;
		}
		return false;
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
	
	class CardAnimationManager{
		private float mFromXDelta;
	    private float mToXDelta;
	    private float mFromYDelta;
	    private float mToYDelta;
	    
	    private List<Animation> animations = new ArrayList<Animation>();
	    
		public CardAnimationManager() {

		}

		public void addAnimation(float fromRow, float toRow, float fromCol, float toCol, float index, float space){
			
		}	
	}
	
	class CardAnimationListener implements Animation.AnimationListener{
		GameLayout gl;
		Card card;
		
		CardAnimationListener(GameLayout gl, Card card){
			this.gl = gl;
			this.card = card;
		}
		
		@Override
		public void onAnimationStart(Animation animation) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onAnimationEnd(Animation animation) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onAnimationRepeat(Animation animation) {
			Card c = (Card) gl.getChildAt(gl.indexOfChild(card));
			//animation.
		}
		
	}
}
