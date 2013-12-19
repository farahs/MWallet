package com.example.mwallet;

import java.util.ArrayList;

import org.json.JSONException;

import com.example.pengguna.PenggunaController;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

public class SettingActivity extends Activity implements OnClickListener{
	
	private EditText usernameIn;
	private EditText nameIn;
	private EditText emailIn;
	private EditText passIn;
	private EditText verifyPassIn;
	private EditText pinNumIn;
	Button ok_process;
	Button cancel_process;
	private Context context;
	private ProgressDialog pDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		usernameIn = (EditText) findViewById(R.id.usernameIn);
		usernameIn.setText(PenggunaController.getUser().getUsername());
		usernameIn.setOnClickListener(this);
		ok_process = (Button) findViewById(R.id.ok_process);
		cancel_process = (Button) findViewById(R.id.cancel_process);
		nameIn = (EditText) findViewById(R.id.nameIn);
		nameIn.setText(PenggunaController.getUser().getName());
		nameIn.setOnClickListener(this);
		emailIn = (EditText) findViewById(R.id.emailIn);
		emailIn.setText(PenggunaController.getUser().getEmail());
		emailIn.setOnClickListener(this);
		passIn = (EditText) findViewById(R.id.passIn);
		passIn.setOnClickListener(this);
		passIn.setText(PenggunaController.getUser().hashCode());
		passIn.setOnEditorActionListener(new OnEditorActionListener(){

			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				// TODO Auto-generated method stub
				verifyPassIn = (EditText) findViewById(R.id.verifyPassIn);
				verifyPassIn.setVisibility(View.VISIBLE);
				return false;
			}
	
		});
		verifyPassIn = (EditText) findViewById(R.id.verifyPassIn);
		verifyPassIn.setVisibility(View.GONE);
		verifyPassIn.setOnClickListener(this);
//		verifyPassIn.setText(PenggunaController.getUser().hashCode());
		pinNumIn = (EditText) findViewById(R.id.pinNumIn);
		pinNumIn.setOnClickListener(this);
		pinNumIn.setText(PenggunaController.getUser().getPin());
		
	}
	
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.ok_process:
			new ProcessEdit().execute(usernameIn.getText().toString(),
					nameIn.getText().toString(), emailIn.getText().toString(),
					passIn.getText().toString(),
					);
			break;
		case R.id.cancel_process:
			break;
		default:
			break;
		}
	}
	/**
	 * Background Async Task to Load all data by making HTTP Request
	 * */
	class ProcessEdit extends AsyncTask<String, String, ArrayList<String>> {

		
		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = ProgressDialog.show(context, "", "Process payment....",
					false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		@Override
		protected ArrayList<String> doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			try {
				return tController.driverMethodPayment(context, arg0);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(ArrayList<String> result1) {
			// dismiss the dialog after getting all products
			pDialog.dismiss();
			processPaymentResult(result1);
		}

	}

}


	
