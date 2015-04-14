package com.jamieyang.a2048;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.FontMetricsInt;
import android.graphics.RectF;
import android.util.Log;
import android.view.View;

public class Card extends View{
	Paint paint = new Paint();
	Canvas canvas;

	int value = 0;
	String color_bg;
	String color_value;
	int textSize = 32;
	int row, col;

	public Card(Context context) {
		super(context);
	}
	
	@Override
	public void onDraw(Canvas canvas) {
		this.canvas = canvas;
		this.setAtrribute();
		this.drawBackground();
		this.drawValue();
	}
	
	private void setAtrribute() {
		int[] data_num_color = { -10395295, -10395295, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 };
		int[] data_numbg_color = { -4133, -6214, -29591, -36266, -40121, -47872, -1123198, -1128192, -1135346, -1140224, -23217, -4985286, -4985286 };
		
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

	private void drawBackground() {
		paint.setColor(Color.parseColor(this.color_bg));
		Log.i("draw", getLeft() + "," + getTop() + "," + getRight() + "," + getBottom());
		canvas.drawRoundRect(new RectF(0, 0, getWidth(), getHeight()), 5, 5, paint);
	}

	private void drawValue() {
		paint.setColor(Color.parseColor(this.color_value));
		paint.setTextSize(this.textSize);
		FontMetricsInt fontMetrics = paint.getFontMetricsInt();  
        // 转载请注明出处：http://blog.csdn.net/hursing  
	    float baseline = 0 + ((getBottom() - getTop()) - (fontMetrics.bottom - fontMetrics.top)) / 2 - fontMetrics.top;  
	    // 下面这行是实现水平居中，drawText对应改为传入targetRect.centerX()  
	    paint.setTextAlign(Paint.Align.CENTER);  
		canvas.drawText(Integer.toString(this.value), (getRight() - getLeft()) / 2, baseline, paint);
	}
}
