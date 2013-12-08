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
	Button historyBtn;
	Button paymentBtn;
	Button topUpBtn;
	
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
		this.historyBtn = (Button) this.rootView.findViewById(R.id.main_historyBtn);
		this.paymentBtn = (Button) this.rootView.findViewById(R.id.main_paymentBtn);
		this.topUpBtn = (Button) this.rootView.findViewById(R.id.main_top_upBtn);
	}

	private void setupEvent() {
		this.historyBtn.setOnClickListener(this);
		this.paymentBtn.setOnClickListener(this);
		this.topUpBtn.setOnClickListener(this);
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
		switch (v.getId()) {
		case R.id.main_historyBtn:
			Intent hisIntent = new Intent(this.activity, HistoryFragment.class);
			this.startActivity(hisIntent);
//			 Toast.makeText(this.getApplicationContext(), "History", Toast.LENGTH_SHORT).show();
			break;
		case R.id.main_paymentBtn:
			Intent payIntent = new Intent(this.activity, PaymentFragment.class);
			this.startActivity(payIntent);
//			Toast.makeText(this.getApplicationContext(), "Payment", Toast.LENGTH_SHORT).show();
			break;
		case R.id.main_top_upBtn:
			Intent topIntent = new Intent(this.activity, TopUpFragment.class);
			this.startActivity(topIntent);
//			 Toast.makeText(this.getApplicationContext(), "Top Up", Toast.LENGTH_SHORT).show();
			break;
		default:
			break;
		}

	}

}
