package com.example.mwallet;

import com.example.pengguna.PenggunaController;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.app.Activity;
import android.content.DialogInterface;

public class SettingActivity extends Activity implements OnClickListener{
	
	private EditText usernameIn;
	private EditText nameIn;
	private EditText emailIn;
	private EditText passIn;
	private EditText verifyPassIn;
	private EditText pinNumIn;
	View rootView;
	Button ok_process;
	Button cancel_process;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		usernameIn = (EditText) rootView.findViewById(R.id.usernameIn);
		usernameIn.setText(PenggunaController.getUser().getUsername());
		ok_process = (Button) rootView.findViewById(R.id.ok_process);
		cancel_process = (Button) rootView.findViewById(R.id.cancel_process);
	
		nameIn = (EditText) rootView.findViewById(R.id.nameIn);
		nameIn.setOnClickListener(this);
		emailIn = (EditText) rootView.findViewById(R.id.emailIn);
		emailIn.setOnClickListener(this);
		passIn = (EditText) passIn.findViewById(R.id.passIn);
		passIn.setOnClickListener(this);
		verifyPassIn = (EditText) rootView.findViewById(R.id.verifyPassIn);
		verifyPassIn.setOnClickListener(this);
		pinNumIn = (EditText) passIn.findViewById(R.id.pinNumIn);
		pinNumIn.setOnClickListener(this);
		
		
	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()){
		case R.id.usernameIn:
			usernameIn.setText(usernameIn.getText().toString());
			break;
		case R.id.nameIn:
			nameIn.setText(nameIn.getText().toString());
		case R.id.passIn:
			passIn.setText(passIn.getText().toString());
		case R.id.emailIn:
			emailIn.setText(emailIn.getText().toString());
		case R.id.verifyPassIn:
			verifyPassIn.setText(verifyPassIn.getText().toString());
		case R.id.pinNumIn:
			pinNumIn.setText(verifyPassIn.getText().toString());
	}
	
}

	}
