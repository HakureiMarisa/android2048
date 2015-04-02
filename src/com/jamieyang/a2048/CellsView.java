package com.jamieyang.a2048;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

public class CellsView extends View{
	Cell[][] cells = new Cell[4][4];
	List<Location> leftCells = new ArrayList<Location>();
	boolean hasMoved = false;
	Paint p = new Paint();
	public CellsView(Context context) {
		super(context);
		//初始化剩余格子信息
		for(int i = 0; i < cells.length; i++){
			for(int j = 0; j < cells[i].length; j++){
				this.leftCells.add(new Location(i, j));
			}
		}
	}
	
	public void init(float offestX, float offestY, float lineWidth){
		//初始化格子位置
		for(int i = 0; i < cells.length; i++){
			for(int j = 0; j < cells[i].length; j++){
				Cell c = new Cell();
				c.x = offestX + (Cell.CELL_WIDTH + lineWidth) * i;
				c.y = offestY + (Cell.CELL_HEIGHT + lineWidth) * j;
				cells[i][j] = c;
			}
		}
		
		this.addCell();
		this.addCell();
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
	
	public void onDraw(Canvas canvas){
		//super.onDraw(canvas);
		
		for(int i = 0; i < cells.length; i++){
			for(int j = 0; j < cells[i].length; j++){
				cells[i][j].draw(canvas, p);
			}
		}
	}
	
	public void addCell(){		
		int leftCount = leftCells.size();
		int location = new Random().nextInt(leftCount);
		Location l = leftCells.get(location);
		int value = Math.random() < 0.5 ? 2 : 4;
		this.cells[l.row][l.col].value = value;
		leftCells.remove(location);
	}
	
	public void moveLeft(){
		for(int i = 0; i < cells.length; i++){
			for(int j = 0; j < cells[i].length - 1; j++){
				for(int k = j + 1; k < cells[i].length; k++){
					if(cells[i][k].value != 0 && cells[i][j].value == 0){
						int value = cells[i][k].value;
						cells[i][j].value = value;
						cells[i][k].value = 0;
						this.hasMoved = true;
					}else if(cells[i][k].value != 0 && cells[i][j].value != cells[i][k].value){
						cells[i][j].value *= 2;
						cells[i][k].value = 0;
						this.hasMoved = true;
					}
				}				
			}
		}
		if(this.hasMoved){
			this.addCell();
		}
	}
}
