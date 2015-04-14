package com.jamieyang.a2048;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity{
	MainView mv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		GameLayout gl = (GameLayout) findViewById(R.id.gamelayout);
		gl.addCard();gl.addCard();
	}
}
