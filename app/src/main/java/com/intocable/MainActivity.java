package com.intocable;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class MainActivity extends TabActivity {

	public static TabHost tabHost;
	// Xử lý vuốt màn hình
		private GestureDetector GD;
		private GestureDetector.SimpleOnGestureListener SOGL = new GestureDetector.SimpleOnGestureListener() {

			@Override
			public boolean onFling(MotionEvent e1, MotionEvent e2,
					float veloccityX, float veloccityY) {
				
				// e1 là tọa độ điểm bắt đầu vuốt
				// e2 là tọa độ điểm vuốt đến
				float x1 = e1.getX();
				float y1 = e1.getY();
				float x2 = e2.getX();
				float y2 = e2.getY();
				float distanceY = Math.abs(y1 - y2);// Xác định điều kiện không vuốt
													// quá xiên, chỉ vuốt ngang

				if (x1 > x2 && distanceY < 50) { // Vuốt từ Phai sang trai!
					if (tabHost.getCurrentTab() == 0) {
						tabHost.setCurrentTab(1);
					}else if(tabHost.getCurrentTab()==1){
						tabHost.setCurrentTab(2);
					}
					else if(tabHost.getCurrentTab()==2){
						tabHost.setCurrentTab(3);
					}
				}

				if (x1 < x2 && distanceY < 50) { // Vuốt Trai sang phai

					if (tabHost.getCurrentTab() == 3) {
						tabHost.setCurrentTab(2);
					}else if(tabHost.getCurrentTab()==2){
						tabHost.setCurrentTab(1);
					}
					else if(tabHost.getCurrentTab()==1){
						tabHost.setCurrentTab(0);
					}
				}

				return false;
			}

		};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		GD = new GestureDetector(this, SOGL);
		tabHost = (TabHost) findViewById(android.R.id.tabhost);
		tabHost = getTabHost();
		tabHost.setup();
		// Tab for List
		TabSpec listspec = tabHost.newTabSpec("List");
		View view1 = getLayoutInflater().from(this).inflate(
				R.layout.tab_1, null);
		Intent listIntent = new Intent(this, Example_Activity.class);
		listspec.setIndicator(view1);
		listspec.setContent(listIntent);

		// Tab for favorite
		TabSpec favospec = tabHost.newTabSpec("Favorite");
		View view2 = getLayoutInflater().from(this).inflate(
				R.layout.tab_2, null);
		Intent favoIntent = new Intent(this, Example_Activity.class);
		favospec.setIndicator(view2);
		favospec.setContent(favoIntent);

		// Tab for cate
		TabSpec catespec = tabHost.newTabSpec("Category");
		View view3 = getLayoutInflater().from(this).inflate(
				R.layout.tab_3, null);
		Intent cateIntent = new Intent(this, Example_Activity.class);
		catespec.setIndicator(view3);
		catespec.setContent(cateIntent);

		

		// Adding all TabSpec to TabHost
		tabHost.addTab(listspec); // Adding photos tab
		tabHost.addTab(favospec); // Adding songs tab
		tabHost.addTab(catespec); // Adding videos tab
		tabHost.setCurrentTab(0);
	}
	
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		GD.onTouchEvent(ev);
		// super.dispatchTouchEvent(ev);
		return super.dispatchTouchEvent(ev);
	}
	
	
}