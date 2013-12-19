package com.example.mwallet;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import com.example.pengguna.PenggunaController;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity implements OnClickListener {

	private EditText usernameInput;
	private ImageView usernameClear;
	private EditText passwordInput;
	private ImageView passwordClear;
	private Button submitBtn;
	private TextView registerTV;
	private TextView forgotPasswordTV;
	private String usernameText;
	private String passwordText;

	private Context context;

	private PenggunaController penggunaController;
	
	private ProgressDialog pDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		context = this;

		this.setView();
		this.setEvent();

		penggunaController = new PenggunaController();
	}

	private void setView() {
		this.usernameInput = (EditText) this
				.findViewById(R.id.login_username_input);
		this.passwordInput = (EditText) this
				.findViewById(R.id.login_password_input);

		this.usernameClear = (ImageView) this
				.findViewById(R.id.login_username_clear);
		this.passwordClear = (ImageView) this
				.findViewById(R.id.login_password_clear);

		this.submitBtn = (Button) this.findViewById(R.id.login_submitBtn);

		this.registerTV = (TextView) this.findViewById(R.id.login_register);
		this.forgotPasswordTV = (TextView) this
				.findViewById(R.id.login_forgot_password);
	}

	private void setEvent() {
		this.submitBtn.setOnClickListener(this);

		this.usernameClear.setOnClickListener(this);
		this.passwordClear.setOnClickListener(this);

		this.registerTV.setOnClickListener(this);
		this.forgotPasswordTV.setOnClickListener(this);
	}

	/*
	 * @Override public boolean onCreateOptionsMenu(Menu menu) { // Inflate the
	 * menu; this adds items to the action bar if it is present.
	 * getMenuInflater().inflate(R.menu.login, menu); return true; }
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
			try {
				this.loginUser();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case R.id.login_register:
			Intent i = new Intent(this, RegisterActivity.class);
			this.startActivity(i);
			break;
		case R.id.login_forgot_password:
			Intent a = new Intent(this, ForgotPasswordActivity.class);
			this.startActivity(a);
			break;
		default:
			break;
		}

	}

	public void loginUser() throws InterruptedException, ExecutionException {
		usernameText = usernameInput.getText().toString();
		passwordText = passwordInput.getText().toString();

		new LoginUser().execute();
		
	}

	/**
	 * Background Async Task to Load all data by making HTTP Request
	 * */
	class LoginUser extends AsyncTask<String, String, ArrayList<String>> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = ProgressDialog.show(context,"","Login.... Please wait...",false);
			pDialog.setCancelable(false);
			pDialog.show();
		}
		@Override
		protected ArrayList<String> doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			return penggunaController.loginUser(usernameText, passwordText,
					context);
		}
		
		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(ArrayList<String> result1) {
			// dismiss the dialog after getting all products
			pDialog.dismiss();
			processResult(result1);
		}

	}
	
	public void processResult(ArrayList<String> result){
		for (int i = 0; i < result.size(); i++) {
			if (result.get(i).equals("success")) {
				Toast.makeText(this, "Login successful",
						Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(this, DrawerActivity.class);
				this.startActivity(intent);
				finish();
			} else if (result.get(i).equals("wrong")) {
				Toast.makeText(this, "Wrong username or password",
						Toast.LENGTH_SHORT).show();
			} else if (result.get(i).equals("error")) {
				Toast.makeText(this,
						"Sorry, an error occured.\n Please try again.",
						Toast.LENGTH_SHORT).show();
			} else {
				if (result.get(i).equals("empty username")) {
					usernameInput.setTextColor(Color.RED);
					Toast.makeText(this, "Username cannot be blank",
							Toast.LENGTH_SHORT).show();
				}
				if (result.get(i).equals("empty password")) {
					passwordInput.setTextColor(Color.RED);
					Toast.makeText(this, "Password cannot be blank",
							Toast.LENGTH_SHORT).show();
				}
				if (result.get(i).equals("length username")) {
					usernameInput.setTextColor(Color.RED);
					Toast.makeText(this,
							"Username must be between 6 until 20 characters",
							Toast.LENGTH_SHORT).show();
				}
				if (result.get(i).equals("pattern username")) {
					usernameInput.setTextColor(Color.RED);
					Toast.makeText(this,
							"Username only consists of alfanumeric character",
							Toast.LENGTH_SHORT).show();
				}
				if (result.get(i).equals("length password")) {
					passwordInput.setTextColor(Color.RED);
					Toast.makeText(this,
							"Password must be 6 or more character",
							Toast.LENGTH_SHORT).show();
				}
			}
		}
	}
}
