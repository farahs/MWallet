package com.example.mwallet;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.json.JSONException;

import com.example.pengguna.PenggunaController;
import com.example.pengguna.TopUpController;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class TopUpFragment extends Fragment implements OnClickListener {

	DrawerActivity activity;
	View rootView;
	View layout;

	ProgressDialog pDialog;
	TopUpController tuController;
	Context context;

	EditText amount;
	EditText accountNumber;
	EditText accountName;
	TextView date;

	Button processTopUp;

	Dialog pinTopUpDialog;
	LinearLayout content;
	EditText pin;
	Button okBtn;
	Button cancelBtn;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		this.rootView = inflater.inflate(R.layout.activity_top_up, container,
				false);
		this.activity = (DrawerActivity) this.getActivity();
		this.context = activity.getApplicationContext();
		this.tuController = new TopUpController();

		setupView();
		setupEvent();
		return this.rootView;
	}

	private void setupView() {

		amount = (EditText) rootView.findViewById(R.id.topUp_amount_input);
	
		accountNumber = (EditText) rootView
				.findViewById(R.id.topUp_accountNumber_input);
	
		accountName = (EditText) rootView
				.findViewById(R.id.topUp_accountName_input);
		
		date = (TextView) rootView.findViewById(R.id.topUp_date);

		processTopUp = (Button) rootView.findViewById(R.id.topUpBtn);

		setupDialog();

	}

	private void setupDialog() {
		pinTopUpDialog = new Dialog(activity);
		pinTopUpDialog.setContentView(R.layout.payment_process_dialog);
		pinTopUpDialog.setTitle("INSERT PIN");
		layout = LayoutInflater.from(activity.getApplicationContext()).inflate(
				R.layout.topup_dialog, null);

		content = (LinearLayout) pinTopUpDialog
				.findViewById(R.id.dialog_content);
		content.addView(layout);

		pin = (EditText) pinTopUpDialog.findViewById(R.id.dialog_topUp_pin);

		okBtn = (Button) this.pinTopUpDialog.findViewById(R.id.ok_process);
		cancelBtn = (Button) this.pinTopUpDialog
				.findViewById(R.id.cancel_process);
		okBtn.setOnClickListener(this);
		cancelBtn.setOnClickListener(this);
	}

	private void setupEvent() {
		processTopUp.setOnClickListener(this);

		date.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				DialogFragment newFragment = new DatePickerFragment(date);
				newFragment.show(getFragmentManager(), "datePicker");
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.topUpBtn:
			pinTopUpDialog.show();
			break;
		case R.id.ok_process:
			String dateAfterFormat = processDate(date.getText().toString());
			if (cekPIN()) {
				new ProcessTopUp().execute(amount.getText().toString(),
						accountNumber.getText().toString(), accountName
								.getText().toString(), dateAfterFormat);
			} else {
				Toast.makeText(activity.getApplicationContext(),
						"PIN INCORRECT", Toast.LENGTH_SHORT).show();
			}

			break;
		case R.id.cancel_process:
			pinTopUpDialog.hide();
			break;
		}
	}

	public String processDate(String date) {

		String[] dateList = new String[3];
		dateList = date.split(" ");

		String tgl = dateList[0];
		String bln = "";
		String t = dateList[1];
		if (t.equals("January")) {
			bln = "01";
		} else if (t.equals("February")) {
			bln = "02";
		} else if (t.equals("March")) {
			bln = "03";
		} else if (t.equals("April")) {
			bln = "04";
		} else if (t.equals("May")) {
			bln = "05";
		} else if (t.equals("June")) {
			bln = "06";
		} else if (t.equals("July")) {
			bln = "07";
		} else if (t.equals("August")) {
			bln = "08";
		} else if (t.equals("September")) {
			bln = "09";
		} else if (t.equals("October")) {
			bln = "10";
		} else if (t.equals("November")) {
			bln = "11";
		} else if (t.equals("December")) {
			bln = "12";
		}
		String thn = dateList[2];

		return tgl + bln + thn;
	}

	/**
	 * Background Async Task to Load all data by making HTTP Request
	 * */
	class ProcessTopUp extends AsyncTask<String, String, ArrayList<String>> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = ProgressDialog.show(activity, "", "Process top up....",
					false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		@Override
		protected ArrayList<String> doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			try {
				return tuController.driverMethodTopUp(
						activity.getApplicationContext(), arg0);
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
			processResult(result1);
		}

	}

	private boolean cekPIN() {

		String dbPIN = PenggunaController.getUser().getPin();
		String inputPIN = pin.getText().toString();

		if (inputPIN.equals(dbPIN)) {
			return true;
		} else {
			return false;
		}
	}

	private void processResult(ArrayList<String> result) {

		if (result.size() == 0) {
			pinTopUpDialog.hide();
			clearData();
			Toast.makeText(context, "TOPUP SUCCESSFULL", Toast.LENGTH_SHORT)
					.show();
		}
	}
	
	private void clearData(){
		amount.setText("");
		accountNumber.setText("");
		accountName.setText("");
		date.setText("");
		pin.setText("");
	}
}

class DatePickerFragment extends DialogFragment implements
		DatePickerDialog.OnDateSetListener {

	TextView tv;

	public DatePickerFragment(TextView et) {
		this.tv = et;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the current date as the default date in the picker
		final Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DAY_OF_MONTH);

		// Create a new instance of DatePickerDialog and return it
		return new DatePickerDialog(this.getActivity(), this, year, month, day);
	}

	@Override
	public void onDateSet(DatePicker view, int year, int month, int day) {
		// Do something with the date chosen by the user

		final Calendar c = Calendar.getInstance();
		c.set(year, month, day);

		Date date = c.getTime();

		SimpleDateFormat dateFormat = new SimpleDateFormat("d MMMM yyyy",
				Locale.ENGLISH);

		tv.setText(dateFormat.format(date));
	}
}