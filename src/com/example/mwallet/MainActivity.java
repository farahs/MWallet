package com.example.mwallet;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {

	Button historyBtn;
	Button paymentBtn;
	Button topUpBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		this.setupView();
		this.setupEvent();
	}

	private void setupView() {
		this.historyBtn = (Button) this.findViewById(R.id.main_historyBtn);
		this.paymentBtn = (Button) this.findViewById(R.id.main_paymentBtn);
		this.topUpBtn = (Button) this.findViewById(R.id.main_top_upBtn);
	}

	private void setupEvent() {
		this.historyBtn.setOnClickListener(this);
		this.paymentBtn.setOnClickListener(this);
		this.topUpBtn.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		
		return true;
	}
	
	public void openSetting(MenuItem item) {
		Intent i = new Intent(this.getApplicationContext(), SettingActivity.class);
		this.startActivity(i);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.main_historyBtn:
			Intent hisIntent = new Intent(this.getApplicationContext(), HistoryActivity.class);
			this.startActivity(hisIntent);
//			 Toast.makeText(this.getApplicationContext(), "History", Toast.LENGTH_SHORT).show();
			break;
		case R.id.main_paymentBtn:
			Intent payIntent = new Intent(this.getApplicationContext(), PaymentActivity.class);
			this.startActivity(payIntent);
//			Toast.makeText(this.getApplicationContext(), "Payment", Toast.LENGTH_SHORT).show();
			break;
		case R.id.main_top_upBtn:
			Intent topIntent = new Intent(this.getApplicationContext(), TopUpActivity.class);
			this.startActivity(topIntent);
//			 Toast.makeText(this.getApplicationContext(), "Top Up", Toast.LENGTH_SHORT).show();
			break;
		default:
			break;
		}

	}

}
