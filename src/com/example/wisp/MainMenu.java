package com.example.wisp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class MainMenu extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    	Button b1= (Button) findViewById(R.id.mapswitch);
    	Button b2= (Button) findViewById(R.id.record);
    	final MainMenu main=this;
    	b1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(main, MapShow.class);
				startActivity(intent);
			}
		});
    	b2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(main, MainActivity.class);
				startActivity(intent);
				
			}
		});
    }
}
