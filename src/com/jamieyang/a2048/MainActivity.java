package com.jamieyang.a2048;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity{
	MainView mv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		final GameLayout gl = (GameLayout) findViewById(R.id.gamelayout);
		gl.bestScore = (TextView) findViewById(R.id.tv_bestScore);
		gl.currentScore = (TextView) findViewById(R.id.tv_currenScore);
		gl.start();
		
		Button restart = (Button) findViewById(R.id.btn_restart);
		restart.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				gl.restart();
			}
		});
	}
}
