package com.example.mwallet;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

public class PaymentFragment extends Fragment implements OnClickListener {
	
	DrawerActivity activity;
	View rootView;

	Spinner paymentMenus;
	
	Button cinemaLocation;
	Button movieName;
	Button movieTime;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		this.rootView = inflater.inflate(R.layout.activity_payment, container, false);
		this.activity = (DrawerActivity) this.getActivity();
		
		setupView();
		setupEvent();
		return this.rootView;
	}

	private void setupView() {
		
		cinemaLocation = (Button) this.rootView.findViewById(R.id.cinema_location_button);
		movieName = (Button) this.rootView.findViewById(R.id.movie_name_button);
		movieTime = (Button) this.rootView.findViewById(R.id.movie_date);
		
	}

	private void setupEvent() {
		
		cinemaLocation.setOnClickListener(this);
		movieName.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
	
		switch (v.getId()) {
		case R.id.cinema_location_button:
			final String[] cinemas = new String[4];
			cinemas[0] = "Margo City";
			cinemas[1] = "Detos";
			cinemas[2] = "PIM";
			cinemas[3] = "Senayan City";
			
			AlertDialog.Builder cinemaBuilder = new AlertDialog.Builder(activity);
			cinemaBuilder.setTitle("CINEMA LOCATION").setItems(cinemas, new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {

					cinemaLocation.setText(cinemas[which]);

				}
			}); 
			
			cinemaBuilder.show();
			break;
			
		case R.id.movie_name_button:
			final String[] movies = new String[4];
			movies[0] = "Insidious";
			movies[1] = "Jurrasic Park";
			movies[2] = "Petualangan Sherina";
			movies[3] = "Tusuk Jelangkung";
			
			AlertDialog.Builder movieBuilder = new AlertDialog.Builder(activity);
			movieBuilder.setTitle("CINEMA LOCATION").setItems(movies, new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {

					movieName.setText(movies[which]);

				}
			}); 
			
			movieBuilder.show();
			break;
		default:
			break;
		}
		
	}

}

class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener
{

	TextView	et;

	@SuppressLint("ValidFragment")
	public DatePickerFragment(TextView et)
	{
		this.et = et;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		// Use the current date as the default date in the picker
		final Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DAY_OF_MONTH);

		Date date = c.getTime();

		// Create a new instance of DatePickerDialog and return it
		return new DatePickerDialog(this.getActivity(), this, year, month, day);
	}

	@Override
	public void onDateSet(DatePicker view, int year, int month, int day)
	{
		// Do something with the date chosen by the user

		final Calendar c = Calendar.getInstance();
		c.set(year, month, day);

		Date date = c.getTime();

		SimpleDateFormat dateFormat = new SimpleDateFormat("d MMMM yyyy", Locale.ENGLISH);

		this.et.setText(dateFormat.format(date));
	}
}