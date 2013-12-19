package com.example.mwallet;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.app.DatePickerDialog;
import android.app.Dialog;
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

public class TopUpFragment extends Fragment implements OnClickListener {

	DrawerActivity activity;
	View rootView;
	View layout;

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
				DialogFragment newFragment = new DatePickerFragment(
						date);
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
			break;
		case R.id.cancel_process:
			pinTopUpDialog.hide();
			break;
		}
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