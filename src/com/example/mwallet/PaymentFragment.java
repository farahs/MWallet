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
import android.support.v4.app.FragmentManager;
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

				final String[] cinemas = new String[4];
				cinemas[0] = "Margo City";
				cinemas[1] = "Detos";
				cinemas[2] = "PIM";
				cinemas[3] = "Senayan City";

				AlertDialog.Builder cinemaBuilder = new AlertDialog.Builder(
						activity);
				cinemaBuilder.setTitle("CINEMA LOCATION").setItems(cinemas,
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
	}

	private void setupOthersView() {
		otherCategoriesBtn = (Button) this.rootView.findViewById(R.id.payment_categories_button);
		paycodeEt = (EditText) this.rootView.findViewById(R.id.paycode_input);
		amountEt = (EditText) this.rootView.findViewById(R.id.others_amount_input);
	}

	private void setupAirplaneEvent() {
		airlineNameBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				final String[] cinemas = new String[4];
				cinemas[0] = "Margo City";
				cinemas[1] = "Detos";
				cinemas[2] = "PIM";
				cinemas[3] = "Senayan City";

				AlertDialog.Builder cinemaBuilder = new AlertDialog.Builder(
						activity);
				cinemaBuilder.setTitle("CINEMA LOCATION").setItems(cinemas,
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
		airplaneDepartureBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				final String[] cinemas = new String[4];
				cinemas[0] = "Margo City";
				cinemas[1] = "Detos";
				cinemas[2] = "PIM";
				cinemas[3] = "Senayan City";

				AlertDialog.Builder cinemaBuilder = new AlertDialog.Builder(
						activity);
				cinemaBuilder.setTitle("CINEMA LOCATION").setItems(cinemas,
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
		airplaneDestinationBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				final String[] cinemas = new String[4];
				cinemas[0] = "Margo City";
				cinemas[1] = "Detos";
				cinemas[2] = "PIM";
				cinemas[3] = "Senayan City";

				AlertDialog.Builder cinemaBuilder = new AlertDialog.Builder(
						activity);
				cinemaBuilder.setTitle("CINEMA LOCATION").setItems(cinemas,
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
		airplaneDateBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				final String[] cinemas = new String[4];
				cinemas[0] = "Margo City";
				cinemas[1] = "Detos";
				cinemas[2] = "PIM";
				cinemas[3] = "Senayan City";

				AlertDialog.Builder cinemaBuilder = new AlertDialog.Builder(
						activity);
				cinemaBuilder.setTitle("CINEMA LOCATION").setItems(cinemas,
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
		airplaneTimeBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				final String[] cinemas = new String[4];
				cinemas[0] = "Margo City";
				cinemas[1] = "Detos";
				cinemas[2] = "PIM";
				cinemas[3] = "Senayan City";

				AlertDialog.Builder cinemaBuilder = new AlertDialog.Builder(
						activity);
				cinemaBuilder.setTitle("CINEMA LOCATION").setItems(cinemas,
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

	}

	private void setupAirplaneView() {
		airlineNameBtn = (Button) this.rootView.findViewById(R.id.airline_name_button);
		airplaneDepartureBtn = (Button) this.rootView.findViewById(R.id.airplane_departure_button);
		airplaneDestinationBtn = (Button) this.rootView.findViewById(R.id.airplane_destination_button);
		airplaneDateBtn = (Button) this.rootView.findViewById(R.id.airplane_date_button);
		airplaneTimeBtn = (Button) this.rootView.findViewById(R.id.airplane_time_button);
		airplaneNumOfTicketEt = (EditText) this.rootView.findViewById(R.id.airplane_sum_ticket);
		airplaneAmountTv = (TextView) this.rootView.findViewById(R.id.airplane_amount_input);
	}

	private void setupTrainEvent() {
		trainNameBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				final String[] cinemas = new String[4];
				cinemas[0] = "Margo City";
				cinemas[1] = "Detos";
				cinemas[2] = "PIM";
				cinemas[3] = "Senayan City";

				AlertDialog.Builder cinemaBuilder = new AlertDialog.Builder(
						activity);
				cinemaBuilder.setTitle("CINEMA LOCATION").setItems(cinemas,
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
		trainDepartureBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				final String[] cinemas = new String[4];
				cinemas[0] = "Margo City";
				cinemas[1] = "Detos";
				cinemas[2] = "PIM";
				cinemas[3] = "Senayan City";

				AlertDialog.Builder cinemaBuilder = new AlertDialog.Builder(
						activity);
				cinemaBuilder.setTitle("CINEMA LOCATION").setItems(cinemas,
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
		trainDestinationBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				final String[] cinemas = new String[4];
				cinemas[0] = "Margo City";
				cinemas[1] = "Detos";
				cinemas[2] = "PIM";
				cinemas[3] = "Senayan City";

				AlertDialog.Builder cinemaBuilder = new AlertDialog.Builder(
						activity);
				cinemaBuilder.setTitle("CINEMA LOCATION").setItems(cinemas,
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
		trainDateBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				final String[] cinemas = new String[4];
				cinemas[0] = "Margo City";
				cinemas[1] = "Detos";
				cinemas[2] = "PIM";
				cinemas[3] = "Senayan City";

				AlertDialog.Builder cinemaBuilder = new AlertDialog.Builder(
						activity);
				cinemaBuilder.setTitle("CINEMA LOCATION").setItems(cinemas,
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
		trainTimeBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				final String[] cinemas = new String[4];
				cinemas[0] = "Margo City";
				cinemas[1] = "Detos";
				cinemas[2] = "PIM";
				cinemas[3] = "Senayan City";

				AlertDialog.Builder cinemaBuilder = new AlertDialog.Builder(
						activity);
				cinemaBuilder.setTitle("CINEMA LOCATION").setItems(cinemas,
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
		
	}

	private void setupTrainView() {
		trainNameBtn = (Button) this.rootView.findViewById(R.id.train_name_button);
		trainDepartureBtn = (Button) this.rootView.findViewById(R.id.train_departure_button);
		trainDestinationBtn = (Button) this.rootView.findViewById(R.id.train_destination_button);
		trainDateBtn = (Button) this.rootView.findViewById(R.id.train_date_button);
		trainTimeBtn = (Button) this.rootView.findViewById(R.id.train_time_button);
		trainNumOfTicketEt = (EditText) this.rootView.findViewById(R.id.train_sum_ticket);
		trainAmountTv = (TextView) this.rootView.findViewById(R.id.train_amount_input);
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
				cinemaBuilder.setTitle("CINEMA LOCATION").setItems(cinemas,
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
				movieBuilder.setTitle("CINEMA LOCATION").setItems(movies,
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
				
				 DialogFragment newFragment = new
				 DatePickerFragment(movieDateBtn);
				 newFragment.show(getFragmentManager(), "datePicker");

			}
		});

		movieTimeBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//
				// DialogFragment newFragment = new
				// DatePickerFragment(movieDate);
				// newFragment.show(getFragmentManager(), "datePicker");

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
		case R.id.cinema_name_button:

			break;

		case R.id.movie_title_button:

			break;
		case R.id.movie_date_button:

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