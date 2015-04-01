package com.jamieyang.a2048;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.FontMetricsInt;
import android.view.View;

public class CellView extends View {
	static int CELL_WIDTH = 50;
	static int CELL_HEIGHT = 50;

	Paint paint = new Paint();

	float x = 0;
	float y = 0;
	int value = 0;
	String color_bg;
	String color_value;
	int textSize = 32;
	
	public CellView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public void onDraw(Canvas canvas) {
		this.setAtrribute();
		this.drawBackground(canvas);
		this.drawValue(canvas);
	}

	private void setAtrribute() {
		switch (this.value) {
		case 0:
			color_bg = "#CCC0B3";
			color_value = "black";
			break;
		case 2:
			color_bg = "#EEE4DA";
			color_value = "black";
			break;
		case 4:
			color_bg = "#EDE0C8";
			color_value = "black";
			break;
		case 8:
			color_bg = "#F2B179";// #F2B179
			color_value = "black";
			break;
		case 16:
			color_bg = "#F49563";
			color_value = "black";
			break;
		case 32:
			color_bg = "#F5794D";
			color_value = "black";
			break;
		case 64:
			color_bg = "#F55D37";
			color_value = "black";
			break;
		case 128:
			color_bg = "#EEE863";
			color_value = "black";
			break;
		case 256:
			color_bg = "#EDB04D";
			color_value = "black";
			break;
		case 512:
			color_bg = "#ECB04D";
			color_value = "black";
			break;
		case 1024:
			color_bg = "#EB9437";
			color_value = "black";
			break;
		case 2048:
			color_bg = "#EA7821";
			color_value = "black";
			break;
		default:
			color_bg = "#EA7821";
			color_value = "black";
			break;
		}
	}

	private void drawBackground(Canvas canvas) {
		paint.setColor(Color.parseColor(this.color_bg));
		canvas.drawRoundRect(x, y, x + CELL_WIDTH, y + CELL_HEIGHT, 5, 5, paint);
	}

	private void drawValue(Canvas canvas) {
		paint.setColor(Color.parseColor(this.color_value));
		paint.setTextSize(this.textSize);
		FontMetricsInt fontMetrics = paint.getFontMetricsInt();  
        // 转载请注明出处：http://blog.csdn.net/hursing  
	    float baseline = y + (CELL_HEIGHT - (fontMetrics.bottom - fontMetrics.top)) / 2 - fontMetrics.top;  
	    // 下面这行是实现水平居中，drawText对应改为传入targetRect.centerX()  
	    paint.setTextAlign(Paint.Align.CENTER);  
		canvas.drawText(Integer.toString(this.value), x + CELL_WIDTH / 2, baseline, paint);
	}
}
