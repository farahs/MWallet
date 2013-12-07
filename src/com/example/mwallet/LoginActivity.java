package com.example.mwallet;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity implements OnClickListener {
	
	private EditText	usernameInput;
	private ImageView	usernameClear;
	private EditText	passwordInput;
	private ImageView	passwordClear;
	private Button		submitBtn;
	private TextView	registerTV;
	private TextView	forgotPasswordTV;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		this.setView();
		this.setEvent();
	}

	private void setView() {
		this.usernameInput = (EditText) this.findViewById(R.id.login_username_input);
		this.passwordInput = (EditText) this.findViewById(R.id.login_password_input);
	
		this.usernameClear = (ImageView) this.findViewById(R.id.login_username_clear);
		this.passwordClear = (ImageView) this.findViewById(R.id.login_password_clear);
		
		this.submitBtn = (Button) this.findViewById(R.id.login_submitBtn);
		
		this.registerTV = (TextView) this.findViewById(R.id.login_register);
		this.forgotPasswordTV = (TextView) this.findViewById(R.id.login_forgot_password);
	}

	private void setEvent() {
		this.submitBtn.setOnClickListener(this);

		this.usernameClear.setOnClickListener(this);
		this.passwordClear.setOnClickListener(this);
		
		this.registerTV.setOnClickListener(this);
		this.forgotPasswordTV.setOnClickListener(this);
	}
/*
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}
*/
	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.login_username_clear:
			this.usernameInput.setText("");
			break;
		case R.id.login_password_clear:
			this.passwordInput.setText("");
			break;
		case R.id.login_submitBtn:
			Intent intent =  new Intent(this.getApplicationContext(), DrawerActivity.class);
			this.startActivity(intent);
			break;
		case R.id.login_register:
			Intent i =  new Intent(this.getApplicationContext(), RegisterActivity.class);
			this.startActivity(i);
			// Toast.makeText(this.getApplicationContext(), "Register", Toast.LENGTH_SHORT).show();
			break;
		case R.id.login_forgot_password:
			Toast.makeText(this.getApplicationContext(), "Forgot Password", Toast.LENGTH_SHORT).show();
			break;
		default:
			break;
		}
		
	}


}
