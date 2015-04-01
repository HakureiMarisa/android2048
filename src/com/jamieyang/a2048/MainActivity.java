package com.jamieyang.a2048;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		final CellView cv = new CellView(this);
		cv.value = 2;
		setContentView(cv);
		
		cv.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				cv.value *= 2;
				cv.invalidate();
			}
		});
	}
}
