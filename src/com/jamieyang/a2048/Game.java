package com.jamieyang.a2048;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.widget.TextView;

public class Game implements OnGestureListener{
	private int[][] cards = new int[4][4];
	List<Location> leftcards = new ArrayList<Location>();
	
	private String bestScoreKey = "bestScore";
	Context context;
	SharedPreferences sp;
	
	TextView bestScore, currentScore;
	GameLayout gl;
	
	public Game(Context context, GameLayout gamelayout){
		this.context = context;
		sp = PreferenceManager.getDefaultSharedPreferences(context);
		this.gl = gamelayout;
	}
	
	public void start(){
		this.bestScore.setText(this.getBestScore() + "");
		this.addCard();
		this.addCard();
	}
	
	private void addCard(){
		int leftCount = leftcards.size();
		int index = new Random().nextInt(leftCount);
		Location l = leftcards.get(index);		
		int value = Math.random() < 0.7 ? 2 : 4;
		Card card = new Card(this.context);	
		card.setId(l.row * 10 + l.col);
		card.value = value;
		card.row = l.row;
		card.col = l.col;
		gl.addView(card);
		
		this.cards[l.row][l.col] = value;
		leftcards.remove(index);
	}
	
	public void restart(){
		this.cards = new int[4][4];
		this.currentScore.setText("0");
		this.start();
	}
	
	public int getBestScore(){
		return sp.getInt(this.bestScoreKey, 0);
	}
	
	public void setBestScore(int score){
		sp.edit().putInt(this.bestScoreKey, score).commit();
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
		// TODO Auto-generated method stub
		return false;
	}
}
