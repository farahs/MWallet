package com.example.mwallet;

import android.os.Bundle;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainFragment extends Fragment implements OnClickListener {

	DrawerActivity activity;
	View rootView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		this.rootView = inflater.inflate(R.layout.activity_main, container, false);
		this.activity = (DrawerActivity) this.getActivity();
		
		this.setupView();
		this.setupEvent();
		return this.rootView;
	}
	
	private void setupView() {
	}

	private void setupEvent() {
	}

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.main, menu);
//		
//		return true;
//	}
//	
//	public void openSetting(MenuItem item) {
//		Intent i = new Intent(this.activity, SettingActivity.class);
//		this.startActivity(i);
//	}

	@Override
	public void onClick(View v) {

	}

}
