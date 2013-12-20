package com.example.mwallet;

import java.util.ArrayList;

import org.json.JSONException;

import com.example.pengguna.PenggunaController;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TextView.OnEditorActionListener;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

public class SettingActivity extends Activity implements OnClickListener {

	private EditText usernameIn;
	private EditText nameIn;
	private EditText emailIn;
	private EditText pinNumIn;
	TextView changePassword;

	PenggunaController pCont;

	Button ok_process;
	Button cancel_process;
	private Context context;
	private ProgressDialog pDialog;

	View layout;

	Dialog changePasswordDialog;
	LinearLayout content;
	EditText pswrdOld;
	EditText pswrdNew;
	EditText verifyPswrd;
	Button okBtn;
	Button cancelBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		pCont = new PenggunaController();
		context = this;
		usernameIn = (EditText) findViewById(R.id.usernameIn);
		usernameIn.setText(PenggunaController.getUser().getUsername());
		usernameIn.setOnClickListener(this);
		ok_process = (Button) findViewById(R.id.save_edit_profile);
		ok_process.setOnClickListener(this);
		cancel_process = (Button) findViewById(R.id.cancel_edit_profile);
		cancel_process.setOnClickListener(this);
		nameIn = (EditText) findViewById(R.id.nameIn);
		nameIn.setText(PenggunaController.getUser().getName());
		nameIn.setOnClickListener(this);
		emailIn = (EditText) findViewById(R.id.emailIn);
		emailIn.setText(PenggunaController.getUser().getEmail());
		emailIn.setOnClickListener(this);
		pinNumIn = (EditText) findViewById(R.id.pinNumIn);
		pinNumIn.setOnClickListener(this);
		pinNumIn.setText(PenggunaController.getUser().getPin());

		changePassword = (TextView) findViewById(R.id.change_password);
		changePassword.setOnClickListener(this);

	}

	private void setupDialog() {
		changePasswordDialog = new Dialog(this);
		changePasswordDialog.setContentView(R.layout.payment_process_dialog);
		changePasswordDialog.setTitle("CHANGE PASSWORD");
		layout = LayoutInflater.from(this.getApplicationContext()).inflate(
				R.layout.change_password_dialog, null);

		content = (LinearLayout) changePasswordDialog
				.findViewById(R.id.dialog_content);
		content.addView(layout);

		pswrdOld = (EditText) changePasswordDialog
				.findViewById(R.id.pass_old_In);
		pswrdNew = (EditText) changePasswordDialog
				.findViewById(R.id.pass_new_In);
		verifyPswrd = (EditText) changePasswordDialog
				.findViewById(R.id.verifyPassIn);

		okBtn = (Button) this.changePasswordDialog
				.findViewById(R.id.ok_process);
		cancelBtn = (Button) this.changePasswordDialog
				.findViewById(R.id.cancel_process);
		okBtn.setOnClickListener(this);
		cancelBtn.setOnClickListener(this);
	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.save_edit_profile:
			new ProcessEditProfile().execute(usernameIn.getText().toString(),
					nameIn.getText().toString(), emailIn.getText().toString(),
					pinNumIn.getText().toString());
			break;
		case R.id.cancel_edit_profile:
			break;
		case R.id.change_password:
			setupDialog();
			changePasswordDialog.show();
			break;
		case R.id.ok_process:
			if (match()) {
				new ProcessChangePassword().execute(pswrdOld.getText()
						.toString(), pswrdNew.getText().toString());
			} else {
				Toast.makeText(getApplicationContext(),
						"PASSWORD DIDN'T MATCH", Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.cancel_process:
			changePasswordDialog.dismiss();

			break;
		default:
			break;
		}
	}

	private boolean match() {
		return pswrdNew.getText().toString()
				.equals(verifyPswrd.getText().toString());

	}

	/**
	 * Background Async Task to Load all data by making HTTP Request
	 * */
	class ProcessEditProfile extends
			AsyncTask<String, String, ArrayList<String>> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = ProgressDialog.show(context, "",
					"Process edit profile....", false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		@Override
		protected ArrayList<String> doInBackground(String... arg0) {
			try {
				return pCont.changeProfile(getApplicationContext(), arg0[0],
						arg0[1], arg0[2], arg0[3]);
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
			result(result1);
		}

	}

	private void result(ArrayList<String> result1) {
		if (result1.size() == 0) {
			Toast.makeText(getApplicationContext(), "PROFILE CHANGED",
					Toast.LENGTH_SHORT).show();
			Intent i = new Intent(getApplicationContext(), DrawerActivity.class);
			startActivity(i);
			finish();
		}
	}

	/**
	 * Background Async Task to Load all data by making HTTP Request
	 * */
	class ProcessChangePassword extends
			AsyncTask<String, String, ArrayList<String>> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = ProgressDialog.show(context, "",
					"Process change password....", false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		@Override
		protected ArrayList<String> doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			try {
				return pCont.changePassword(getApplicationContext(), arg0[0],
						arg0[1]);
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
			changePasswordDialog.dismiss();
			processResult(result1);
		}

	}

	public void processResult(ArrayList<String> result1) {
		if (result1.size() == 0) {
			Toast.makeText(getApplicationContext(), "PASSWORD CHANGED",
					Toast.LENGTH_SHORT).show();
		}

	}
}
