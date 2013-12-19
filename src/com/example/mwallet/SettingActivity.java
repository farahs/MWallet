package com.example.mwallet;

import com.example.pengguna.PenggunaController;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.app.Activity;

public class SettingActivity extends Activity {
	
	private EditText usernameIn;
	private EditText nameIn;
	private EditText emailIn;
	private EditText passIn;
	private EditText verifyPassIn;
	private EditText pinNumIn;
	View rootView;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		usernameIn = (EditText) rootView.findViewById(R.id.usernameIn);
		usernameIn.setText(PenggunaController.getUser().getUsername());
//		this.setUpView();
//		this.setUpEvent();
	}
	
	
	public void onClick(View v){
		
	}
}
