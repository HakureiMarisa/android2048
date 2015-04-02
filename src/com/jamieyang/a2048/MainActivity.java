package com.jamieyang.a2048;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MainView cv = new MainView(this);
		setContentView(cv);
	}
	
	
}
