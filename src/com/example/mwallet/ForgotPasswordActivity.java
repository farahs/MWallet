package com.example.mwallet;


import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Random;

import org.json.JSONException;

import com.example.pengguna.PenggunaController;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ForgotPasswordActivity extends Activity implements OnClickListener {

	ProgressDialog pDialog;
	Context context;
	PenggunaController pController;
	
	EditText email;
	Button send;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forgot_pasword);
		
		pController = new PenggunaController();
		
		setupView();
		setupEvent();
	}

	private void setupView() {
		email = (EditText) findViewById(R.id.email_input);
		send = (Button) findViewById(R.id.reset_pwd_btn);
	}

	private void setupEvent() {
		send.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.reset_pwd_btn:
			String password = makePassword();
			String em = email.getText().toString();
			new ProcessSendingEmail().execute(em, password);
			break;

		default:
			break;
		}
	}
	
	public boolean cekInput(){
		
		return false;
	}
	
	public String makePassword() {
		String letters = "abcdefghjkmnpqrstuvwxyzABCDEFGHJKMNPQRSTUVWXYZ";
		//membuat password baru secara random.
		Random rand = new SecureRandom();
		String pw = "";
		for (int i = 0; i < 6; i++) {
			int index = (int) (rand.nextDouble() * letters.length());
			pw += letters.substring(index, index + 1);
		}
		return pw;
	}
	
	/**
	 * Background Async Task to Load all data by making HTTP Request
	 * */
	class ProcessSendingEmail extends AsyncTask<String, String, ArrayList<String>> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = ProgressDialog.show(ForgotPasswordActivity.this, "", "Process top up....",
					false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		@Override
		protected ArrayList<String> doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			try {
				return pController.forgotPassword(getApplicationContext(), arg0);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(ArrayList<String> b) {
			// dismiss the dialog after getting all products
			pDialog.dismiss();
			processResult(b);
		}

	}
	
	private void processResult(ArrayList<String> result) {

		if (result.size() == 0) {
			Toast.makeText(getApplicationContext(), "EMAIL SENT", Toast.LENGTH_SHORT)
					.show();
			this.finish();
		} else {
			Toast.makeText(getApplicationContext(), "EMAIL UNREGISTERED", Toast.LENGTH_SHORT)
			.show();
		}
	}

}
