package com.example.mwallet;

import java.util.List;

import android.os.Bundle;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;

public class PaymentFragment extends Fragment implements OnClickListener {
	
	DrawerActivity activity;
	View rootView;

	Spinner paymentMenus;
	
	Button cinemaLocation;
	Button movieName;
	
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
			
			AlertDialog.Builder builder = new AlertDialog.Builder(activity);
			builder.setTitle("CINEMA LOCATION").setItems(cinemas, new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {

					cinemaLocation.setText(cinemas[which]);

				}
			}); 
			
			builder.show();
			break;
			
		case R.id.movie_name_button:
		
			break;
		default:
			break;
		}
		
	}

}
