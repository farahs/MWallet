package com.example.mwallet;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.os.Bundle;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class PaymentFragment extends Fragment implements OnClickListener {

	private DrawerActivity activity;
	View rootView;

	Spinner paymentMenus;

	LinearLayout cinemaPaymentLayout;
	LinearLayout trainPaymentLayout;
	LinearLayout airplanePaymentLayout;
	LinearLayout otherPaymentLayout;

	Button processPaymentBtn;

	/*
	 * CINEMA
	 */
	Button cinemaNameBtn;
	Button movieTitleBtn;
	Button movieDateBtn;
	Button movieTimeBtn;
	EditText movieNumOfTicketEt;
	TextView movieAmountTv;

	/*
	 * TRAIN
	 */
	Button trainNameBtn;
	Button trainDepartureBtn;
	Button trainDestinationBtn;
	Button trainDateBtn;
	Button trainTimeBtn;
	EditText trainNumOfTicketEt;
	TextView trainAmountTv;

	/*
	 * AIRPLANE
	 */
	Button airlineNameBtn;
	Button airplaneDepartureBtn;
	Button airplaneDestinationBtn;
	Button airplaneDateBtn;
	Button airplaneTimeBtn;
	EditText airplaneNumOfTicketEt;
	TextView airplaneAmountTv;

	/*
	 * OTHERS
	 */
	Button otherCategoriesBtn;
	EditText paycodeEt;
	EditText amountEt;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		this.rootView = inflater.inflate(R.layout.activity_payment, container,
				false);
		this.activity = (DrawerActivity) this.getActivity();

		setupView();
		setupEvent();
		setupGoneView();
		return this.rootView;
	}

	private void setupView() {
		paymentMenus = (Spinner) rootView.findViewById(R.id.payment_spinner);

		cinemaPaymentLayout = (LinearLayout) rootView
				.findViewById(R.id.cinema_payment);
		trainPaymentLayout = (LinearLayout) rootView
				.findViewById(R.id.train_payment);
		airplanePaymentLayout = (LinearLayout) rootView
				.findViewById(R.id.airplane_payment);
		otherPaymentLayout = (LinearLayout) rootView
				.findViewById(R.id.other_payment);

		processPaymentBtn = (Button) rootView
				.findViewById(R.id.payment_process_button);
	}

	private void setupEvent() {

		paymentMenus.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> av, View v, int position,
					long id) {

				switch (position) {
				case 0:
					// pilih salah satu
					setupGoneView();
					break;
				case 1:
					// cinema
					setupGoneView();
					cinemaPaymentLayout.setVisibility(View.VISIBLE);
					processPaymentBtn.setVisibility(View.VISIBLE);
					setupCinemaView();
					setupCinemaEvent();
					break;
				case 2:
					// train
					setupGoneView();
					trainPaymentLayout.setVisibility(View.VISIBLE);
					processPaymentBtn.setVisibility(View.VISIBLE);
					setupTrainView();
					setupTrainEvent();
					break;
				case 3:
					// airplane
					setupGoneView();
					airplanePaymentLayout.setVisibility(View.VISIBLE);
					processPaymentBtn.setVisibility(View.VISIBLE);
					setupAirplaneView();
					setupAirplaneEvent();
					break;
				case 4:
					// others
					setupGoneView();
					otherPaymentLayout.setVisibility(View.VISIBLE);
					processPaymentBtn.setVisibility(View.VISIBLE);
					setupOthersView();
					setupOthersEvent();
					break;
				default:
					break;
				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> av) {

			}
		});

		processPaymentBtn.setOnClickListener(this);
	}

	private void setupGoneView() {
		cinemaPaymentLayout.setVisibility(View.GONE);
		trainPaymentLayout.setVisibility(View.GONE);
		airplanePaymentLayout.setVisibility(View.GONE);
		otherPaymentLayout.setVisibility(View.GONE);
		processPaymentBtn.setVisibility(View.GONE);
	}

	private void setupOthersEvent() {
		otherCategoriesBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				final String[] categories = new String[3];
				categories[0] = "VENDING MACHINE";
				categories[1] = "BILL";
				categories[2] = "ELECTRIC PULSE";

				AlertDialog.Builder othersBuilder = new AlertDialog.Builder(
						activity);
				othersBuilder.setTitle("CATEGORIES").setItems(categories,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {

								otherCategoriesBtn.setText(categories[which]);

							}
						});

				othersBuilder.show();

			}
		});
	}

	private void setupOthersView() {
		otherCategoriesBtn = (Button) this.rootView
				.findViewById(R.id.payment_categories_button);
		paycodeEt = (EditText) this.rootView.findViewById(R.id.paycode_input);
		amountEt = (EditText) this.rootView
				.findViewById(R.id.others_amount_input);
	}

	private void setupAirplaneEvent() {
		airlineNameBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				final String[] airlines = new String[2];
				airlines[0] = "Lion Air";
				airlines[1] = "Garuda Indonesia";

				AlertDialog.Builder airplaneBuilder = new AlertDialog.Builder(
						activity);
				airplaneBuilder.setTitle("AIRLINES").setItems(airlines,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {

								airlineNameBtn.setText(airlines[which]);

							}
						});

				airplaneBuilder.show();

			}
		});

		airplaneDepartureBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				final String[] airplaneDept = new String[2];
				airplaneDept[0] = "Cengkareng";
				airplaneDept[1] = "Kualanamu";

				AlertDialog.Builder airplaneBuilder = new AlertDialog.Builder(
						activity);
				airplaneBuilder.setTitle("DEPARTURE").setItems(airplaneDept,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {

								airplaneDepartureBtn
										.setText(airplaneDept[which]);

							}
						});

				airplaneBuilder.show();

			}
		});

		airplaneDestinationBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				final String[] airplaneDest = new String[2];
				airplaneDest[0] = "Cengkareng";
				airplaneDest[1] = "Kualanamu";

				AlertDialog.Builder airplaneBuilder = new AlertDialog.Builder(
						activity);
				airplaneBuilder.setTitle("DESTINATION").setItems(airplaneDest,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {

								airplaneDestinationBtn
										.setText(airplaneDest[which]);

							}
						});

				airplaneBuilder.show();

			}
		});

		airplaneDateBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				DialogFragment newFragment = new DatePickerFragment(
						airplaneDateBtn);
				newFragment.show(getFragmentManager(), "datePicker");

			}
		});

		airplaneTimeBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				final String[] schedules = new String[4];
				schedules[0] = "Margo City";
				schedules[1] = "Detos";
				schedules[2] = "PIM";
				schedules[3] = "Senayan City";

				AlertDialog.Builder airplaneBuilder = new AlertDialog.Builder(
						activity);
				airplaneBuilder.setTitle("AIRPLANE SCHEDULE").setItems(
						schedules, new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {

								airplaneTimeBtn.setText(schedules[which]);

							}
						});

				airplaneBuilder.show();

			}
		});

	}

	private void setupAirplaneView() {
		airlineNameBtn = (Button) this.rootView
				.findViewById(R.id.airline_name_button);
		airplaneDepartureBtn = (Button) this.rootView
				.findViewById(R.id.airplane_departure_button);
		airplaneDestinationBtn = (Button) this.rootView
				.findViewById(R.id.airplane_destination_button);
		airplaneDateBtn = (Button) this.rootView
				.findViewById(R.id.airplane_date_button);
		airplaneTimeBtn = (Button) this.rootView
				.findViewById(R.id.airplane_time_button);
		airplaneNumOfTicketEt = (EditText) this.rootView
				.findViewById(R.id.airplane_sum_ticket);
		airplaneAmountTv = (TextView) this.rootView
				.findViewById(R.id.airplane_amount_input);
	}

	private void setupTrainEvent() {
		trainNameBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				final String[] trains = new String[2];
				trains[0] = "Argo Anggrek";
				trains[1] = "Argo Lawu";

				AlertDialog.Builder trainBuilder = new AlertDialog.Builder(
						activity);
				trainBuilder.setTitle("TRAIN NAME").setItems(trains,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {

								trainNameBtn.setText(trains[which]);

							}
						});

				trainBuilder.show();

			}
		});

		trainDepartureBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				final String[] trainDept = new String[2];
				trainDept[0] = "Gambir";
				trainDept[1] = "Manggarai";

				AlertDialog.Builder trainBuilder = new AlertDialog.Builder(
						activity);
				trainBuilder.setTitle("DEPARTURE").setItems(trainDept,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {

								trainDepartureBtn.setText(trainDept[which]);

							}
						});

				trainBuilder.show();

			}
		});

		trainDestinationBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				final String[] trainDest = new String[2];
				trainDest[0] = "Bandung";
				trainDest[1] = "Surabaya";

				AlertDialog.Builder trainBuilder = new AlertDialog.Builder(
						activity);
				trainBuilder.setTitle("DESTINATION").setItems(trainDest,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {

								trainDestinationBtn.setText(trainDest[which]);

							}
						});

				trainBuilder.show();

			}
		});

		trainDateBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				DialogFragment newFragment = new DatePickerFragment(
						trainDateBtn);
				newFragment.show(getFragmentManager(), "datePicker");

			}
		});

		trainTimeBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				final String[] schedules = new String[4];
				schedules[0] = "Margo City";
				schedules[1] = "Detos";
				schedules[2] = "PIM";
				schedules[3] = "Senayan City";

				AlertDialog.Builder trainBuilder = new AlertDialog.Builder(
						activity);
				trainBuilder.setTitle("TRAIN SCHEDULE").setItems(schedules,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {

								trainTimeBtn.setText(schedules[which]);

							}
						});

				trainBuilder.show();

			}
		});

	}

	private void setupTrainView() {
		trainNameBtn = (Button) this.rootView
				.findViewById(R.id.train_name_button);
		trainDepartureBtn = (Button) this.rootView
				.findViewById(R.id.train_departure_button);
		trainDestinationBtn = (Button) this.rootView
				.findViewById(R.id.train_destination_button);
		trainDateBtn = (Button) this.rootView
				.findViewById(R.id.train_date_button);
		trainTimeBtn = (Button) this.rootView
				.findViewById(R.id.train_time_button);
		trainNumOfTicketEt = (EditText) this.rootView
				.findViewById(R.id.train_sum_ticket);
		trainAmountTv = (TextView) this.rootView
				.findViewById(R.id.train_amount_input);
	}

	private void setupCinemaEvent() {

		cinemaNameBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				final String[] cinemas = new String[4];
				cinemas[0] = "Margo City";
				cinemas[1] = "Detos";
				cinemas[2] = "PIM";
				cinemas[3] = "Senayan City";

				AlertDialog.Builder cinemaBuilder = new AlertDialog.Builder(
						activity);
				cinemaBuilder.setTitle("CINEMA NAME").setItems(cinemas,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {

								cinemaNameBtn.setText(cinemas[which]);

							}
						});

				cinemaBuilder.show();

			}
		});

		movieTitleBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				final String[] movies = new String[4];
				movies[0] = "Insidious";
				movies[1] = "Jurrasic Park";
				movies[2] = "Petualangan Sherina";
				movies[3] = "Tusuk Jelangkung";

				AlertDialog.Builder movieBuilder = new AlertDialog.Builder(
						activity);
				movieBuilder.setTitle("MOVIE TITLE").setItems(movies,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {

								movieTitleBtn.setText(movies[which]);

							}
						});

				movieBuilder.show();
			}
		});

		movieDateBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				DialogFragment newFragment = new DatePickerFragment(
						movieDateBtn);
				newFragment.show(getFragmentManager(), "datePicker");

			}
		});

		movieTimeBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				final String[] movies = new String[4];
				movies[0] = "Insidious";
				movies[1] = "Jurrasic Park";
				movies[2] = "Petualangan Sherina";
				movies[3] = "Tusuk Jelangkung";

				AlertDialog.Builder movieBuilder = new AlertDialog.Builder(
						activity);
				movieBuilder.setTitle("MOVIE SCHEDULE").setItems(movies,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {

								movieTimeBtn.setText(movies[which]);

							}
						});

				movieBuilder.show();
			}
		});
	}

	private void setupCinemaView() {

		cinemaNameBtn = (Button) this.rootView
				.findViewById(R.id.cinema_name_button);
		movieTitleBtn = (Button) this.rootView
				.findViewById(R.id.movie_title_button);
		movieDateBtn = (Button) this.rootView
				.findViewById(R.id.movie_date_button);
		movieTimeBtn = (Button) this.rootView
				.findViewById(R.id.movie_time_button);
		movieNumOfTicketEt = (EditText) this.rootView
				.findViewById(R.id.movie_sum_ticket);
		movieAmountTv = (TextView) this.rootView
				.findViewById(R.id.movie_amount_input);

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.payment_process_button:
			Toast.makeText(activity.getApplicationContext(),
					"READY TO PROCESSING PAYMENT", Toast.LENGTH_SHORT).show();
			break;
		default:
			break;
		}

	}

}

class DatePickerFragment extends DialogFragment implements
		DatePickerDialog.OnDateSetListener {

	Button bt;

	public DatePickerFragment(Button et) {
		this.bt = et;
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

		bt.setText(dateFormat.format(date));
	}
}