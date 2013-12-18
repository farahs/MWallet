package com.example.mwallet;

import android.os.Bundle;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class PaymentFragment extends Fragment implements OnClickListener {

	private DrawerActivity activity;
	View rootView;
	View layout;

	Spinner paymentMenus;

	LinearLayout content;
	LinearLayout cinemaPaymentLayout;
	LinearLayout trainPaymentLayout;
	LinearLayout airplanePaymentLayout;
	LinearLayout otherPaymentLayout;

	int CINEMA_TYPE = 1;
	int TRAIN_TYPE = 2;
	int AIRPLANE_TYPE = 3;
	int OTHERS_TYPE = 4;

	int paymentType;

	Button processPaymentBtn;
	Dialog processOtherPaymentDialog;
	Dialog processAirplanePaymentDialog;
	Dialog processTrainPaymentDialog;
	Dialog processCinemaPaymentDialog;

	Button okBtn;
	Button cancelBtn;

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
	TextView paycodeInfoTv;

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
					paymentType = position;
					break;
				case 1:
					// cinema
					paymentType = position;
					setupGoneView();
					cinemaPaymentLayout.setVisibility(View.VISIBLE);
					processPaymentBtn.setVisibility(View.VISIBLE);
					setupCinemaView();
					setupCinemaEvent();
					break;
				case 2:
					// train
					paymentType = position;
					setupGoneView();
					trainPaymentLayout.setVisibility(View.VISIBLE);
					processPaymentBtn.setVisibility(View.VISIBLE);
					setupTrainView();
					setupTrainEvent();
					break;
				case 3:
					// airplane
					paymentType = position;
					setupGoneView();
					airplanePaymentLayout.setVisibility(View.VISIBLE);
					processPaymentBtn.setVisibility(View.VISIBLE);
					setupAirplaneView();
					setupAirplaneEvent();
					break;
				case 4:
					// others
					paymentType = position;
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
		
		paycodeEt.setClickable(false);
		paycodeEt.setEnabled(false);
		paycodeInfoTv.setClickable(false);
		
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

								paycodeEt.setClickable(true);
								paycodeEt.setEnabled(true);
								paycodeInfoTv.setClickable(true);
							}
						});

				othersBuilder.show();

			}
		});
		
		paycodeInfoTv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(activity.getApplicationContext(), "PAYCODE INFO", Toast.LENGTH_SHORT).show();
			}
		});
	}

	private void setupOthersView() {
		otherCategoriesBtn = (Button) this.rootView
				.findViewById(R.id.payment_categories_button);
		paycodeEt = (EditText) this.rootView.findViewById(R.id.paycode_input);
		paycodeInfoTv = (TextView) this.rootView.findViewById(R.id.paycode_info);
	}

	private void setupAirplaneEvent() {
		
		disableAirplaneButton();
		
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

								airplaneDepartureBtn.setEnabled(true);
								airplaneDepartureBtn.setClickable(true);
								airplaneDepartureBtn.setBackgroundColor(getResources().getColor(R.color.our_blue));
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

								airplaneDestinationBtn.setEnabled(true);
								airplaneDestinationBtn.setClickable(true);
								airplaneDestinationBtn.setBackgroundColor(getResources().getColor(R.color.our_blue));
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

								airplaneDateBtn.setEnabled(true);
								airplaneDateBtn.setClickable(true);
								airplaneDateBtn.setBackgroundColor(getResources().getColor(R.color.our_blue));
							}
						});

				airplaneBuilder.show();

			}
		});

		airplaneDateBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				final String[] dates = new String[4];
				dates[0] = "Margo City";
				dates[1] = "Detos";
				dates[2] = "PIM";
				dates[3] = "Senayan City";

				AlertDialog.Builder airplaneBuilder = new AlertDialog.Builder(
						activity);
				airplaneBuilder.setTitle("AIRPLANE SCHEDULE").setItems(dates,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {

								airplaneDateBtn.setText(dates[which]);

								airplaneTimeBtn.setEnabled(true);
								airplaneTimeBtn.setClickable(true);
								airplaneTimeBtn.setBackgroundColor(getResources().getColor(R.color.our_blue));
							}
						});

				airplaneBuilder.show();
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

								airplaneNumOfTicketEt.setEnabled(true);
								airplaneNumOfTicketEt.setClickable(true);
							}
						});

				airplaneBuilder.show();

			}
		});

	}

	private void disableAirplaneButton() {

		airplaneDepartureBtn.setEnabled(false);
		airplaneDepartureBtn.setClickable(false);
		airplaneDepartureBtn.setBackgroundColor(getResources().getColor(R.color.default_input_gray));

		airplaneDestinationBtn.setEnabled(false);
		airplaneDestinationBtn.setClickable(false);
		airplaneDestinationBtn.setBackgroundColor(getResources().getColor(R.color.default_input_gray));

		airplaneDateBtn.setEnabled(false);
		airplaneDateBtn.setClickable(false);
		airplaneDateBtn.setBackgroundColor(getResources().getColor(R.color.default_input_gray));

		airplaneTimeBtn.setEnabled(false);
		airplaneTimeBtn.setClickable(false);
		airplaneTimeBtn.setBackgroundColor(getResources().getColor(R.color.default_input_gray));

		airplaneNumOfTicketEt.setEnabled(false);
		airplaneNumOfTicketEt.setClickable(false);

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
		
		disableTrainButton();
		
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

								trainDepartureBtn.setEnabled(true);
								trainDepartureBtn.setClickable(true);
								trainDepartureBtn.setBackgroundColor(getResources().getColor(R.color.our_blue));
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
								
								trainDestinationBtn.setEnabled(true);
								trainDestinationBtn.setClickable(true);
								trainDestinationBtn.setBackgroundColor(getResources().getColor(R.color.our_blue));
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

								trainDateBtn.setEnabled(true);
								trainDateBtn.setClickable(true);
								trainDateBtn.setBackgroundColor(getResources().getColor(R.color.our_blue));
							}
						});

				trainBuilder.show();

			}
		});

		trainDateBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				final String[] dates = new String[4];
				dates[0] = "Margo City";
				dates[1] = "Detos";
				dates[2] = "PIM";
				dates[3] = "Senayan City";

				AlertDialog.Builder trainBuilder = new AlertDialog.Builder(
						activity);
				trainBuilder.setTitle("TRAIN SCHEDULE").setItems(dates,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {

								trainDateBtn.setText(dates[which]);

								trainTimeBtn.setEnabled(true);
								trainTimeBtn.setClickable(true);
								trainTimeBtn.setBackgroundColor(getResources().getColor(R.color.our_blue));
							}
						});

				trainBuilder.show();
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
								
								trainNumOfTicketEt.setEnabled(true);
								trainNumOfTicketEt.setClickable(true);
							}
						});

				trainBuilder.show();

			}
		});

	}

	private void disableTrainButton() {

		trainDepartureBtn.setEnabled(false);
		trainDepartureBtn.setClickable(false);
		trainDepartureBtn.setBackgroundColor(getResources().getColor(R.color.default_input_gray));

		trainDestinationBtn.setEnabled(false);
		trainDestinationBtn.setClickable(false);
		trainDestinationBtn.setBackgroundColor(getResources().getColor(R.color.default_input_gray));

		trainDateBtn.setEnabled(false);
		trainDateBtn.setClickable(false);
		trainDateBtn.setBackgroundColor(getResources().getColor(R.color.default_input_gray));

		trainTimeBtn.setEnabled(false);
		trainTimeBtn.setClickable(false);
		trainTimeBtn.setBackgroundColor(getResources().getColor(R.color.default_input_gray));

		trainNumOfTicketEt.setEnabled(false);
		trainNumOfTicketEt.setClickable(false);

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

		disableCinemaButton();

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

								movieTitleBtn.setBackgroundColor(getResources()
										.getColor(R.color.our_blue));
								movieTitleBtn.setEnabled(true);
								movieTitleBtn.setClickable(true);

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

								movieDateBtn.setEnabled(true);
								movieDateBtn.setClickable(true);
								movieDateBtn.setBackgroundColor(getResources()
										.getColor(R.color.our_blue));

							}
						});

				movieBuilder.show();
			}
		});

		movieDateBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				final String[] dates = new String[4];
				dates[0] = "Insidious";
				dates[1] = "Jurrasic Park";
				dates[2] = "Petualangan Sherina";
				dates[3] = "Tusuk Jelangkung";

				AlertDialog.Builder movieBuilder = new AlertDialog.Builder(
						activity);
				movieBuilder.setTitle("MOVIE SCHEDULE").setItems(dates,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {

								movieDateBtn.setText(dates[which]);

								movieTimeBtn.setEnabled(true);
								movieTimeBtn.setClickable(true);
								movieTimeBtn.setBackgroundColor(getResources()
										.getColor(R.color.our_blue));
							}
						});

				movieBuilder.show();
			}
		});

		movieTimeBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				final String[] scheds = new String[4];
				scheds[0] = "Insidious";
				scheds[1] = "Jurrasic Park";
				scheds[2] = "Petualangan Sherina";
				scheds[3] = "Tusuk Jelangkung";

				AlertDialog.Builder movieBuilder = new AlertDialog.Builder(
						activity);
				movieBuilder.setTitle("MOVIE SCHEDULE").setItems(scheds,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {

								movieTimeBtn.setText(scheds[which]);

								movieNumOfTicketEt.setEnabled(true);
								movieNumOfTicketEt.setClickable(true);
							}
						});

				movieBuilder.show();
			}
		});

	}

	private void disableCinemaButton() {
		movieTitleBtn.setEnabled(false);
		movieTitleBtn.setClickable(false);
		movieTitleBtn.setBackgroundColor(getResources().getColor(
				R.color.default_input_gray));

		movieDateBtn.setEnabled(false);
		movieDateBtn.setClickable(false);
		movieDateBtn.setBackgroundColor(getResources().getColor(
				R.color.default_input_gray));

		movieTimeBtn.setEnabled(false);
		movieTimeBtn.setClickable(false);
		movieTimeBtn.setBackgroundColor(getResources().getColor(
				R.color.default_input_gray));

		movieNumOfTicketEt.setEnabled(false);
		movieNumOfTicketEt.setClickable(false);
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

			if (paymentType == CINEMA_TYPE) {
				setupCinemaDialog();
				this.processCinemaPaymentDialog.show();
			} else if (paymentType == TRAIN_TYPE) {
				setupTrainDialog();
				this.processTrainPaymentDialog.show();
			} else if (paymentType == AIRPLANE_TYPE) {
				setupAirplaneDialog();
				this.processAirplanePaymentDialog.show();
			} else if (paymentType == OTHERS_TYPE) {
				setupOthersDialog();
				this.processOtherPaymentDialog.show();
			}
			break;
		case R.id.cancel_process:

			if (paymentType == CINEMA_TYPE) {
				this.processCinemaPaymentDialog.hide();
			} else if (paymentType == TRAIN_TYPE) {
				this.processTrainPaymentDialog.hide();
			} else if (paymentType == AIRPLANE_TYPE) {
				this.processAirplanePaymentDialog.hide();
			} else if (paymentType == OTHERS_TYPE) {
				this.processOtherPaymentDialog.hide();
			}
			break;
		default:
			break;
		}

	}

	private void setupCinemaDialog() {
		this.processCinemaPaymentDialog = new Dialog(this.activity);
		this.processCinemaPaymentDialog
				.setContentView(R.layout.payment_process_dialog);
		this.processCinemaPaymentDialog.setTitle("YOUR PAYMENT");

		this.layout = LayoutInflater
				.from(getActivity().getApplicationContext()).inflate(
						R.layout.cinema_layout_dialog, null);

		this.okBtn = (Button) this.processCinemaPaymentDialog
				.findViewById(R.id.ok_process);
		this.cancelBtn = (Button) this.processCinemaPaymentDialog
				.findViewById(R.id.cancel_process);
		this.okBtn.setOnClickListener(this);
		this.cancelBtn.setOnClickListener(this);

		this.content = (LinearLayout) this.processCinemaPaymentDialog
				.findViewById(R.id.dialog_content);
		content.addView(layout);

	}

	private void setupTrainDialog() {
		this.processTrainPaymentDialog = new Dialog(this.activity);
		this.processTrainPaymentDialog
				.setContentView(R.layout.payment_process_dialog);
		this.processTrainPaymentDialog.setTitle("YOUR PAYMENT");

		this.layout = LayoutInflater
				.from(getActivity().getApplicationContext()).inflate(
						R.layout.train_layout_dialog, null);

		this.okBtn = (Button) this.processTrainPaymentDialog
				.findViewById(R.id.ok_process);
		this.cancelBtn = (Button) this.processTrainPaymentDialog
				.findViewById(R.id.cancel_process);
		this.okBtn.setOnClickListener(this);
		this.cancelBtn.setOnClickListener(this);

		this.content = (LinearLayout) this.processTrainPaymentDialog
				.findViewById(R.id.dialog_content);
		content.addView(layout);

	}

	private void setupAirplaneDialog() {
		this.processAirplanePaymentDialog = new Dialog(this.activity);
		this.processAirplanePaymentDialog
				.setContentView(R.layout.payment_process_dialog);
		this.processAirplanePaymentDialog.setTitle("YOUR PAYMENT");

		this.layout = LayoutInflater
				.from(getActivity().getApplicationContext()).inflate(
						R.layout.airplane_layout_dialog, null);

		this.okBtn = (Button) this.processAirplanePaymentDialog
				.findViewById(R.id.ok_process);
		this.cancelBtn = (Button) this.processAirplanePaymentDialog
				.findViewById(R.id.cancel_process);
		this.okBtn.setOnClickListener(this);
		this.cancelBtn.setOnClickListener(this);

		this.content = (LinearLayout) this.processAirplanePaymentDialog
				.findViewById(R.id.dialog_content);
		content.addView(layout);

	}

	private void setupOthersDialog() {

		this.processOtherPaymentDialog = new Dialog(this.activity);
		this.processOtherPaymentDialog
				.setContentView(R.layout.payment_process_dialog);
		this.processOtherPaymentDialog.setTitle("YOUR PAYMENT");

		this.layout = LayoutInflater
				.from(getActivity().getApplicationContext()).inflate(
						R.layout.others_layout_dialog, null);

		this.okBtn = (Button) this.processOtherPaymentDialog
				.findViewById(R.id.ok_process);
		this.cancelBtn = (Button) this.processOtherPaymentDialog
				.findViewById(R.id.cancel_process);
		this.okBtn.setOnClickListener(this);
		this.cancelBtn.setOnClickListener(this);

		this.content = (LinearLayout) this.processOtherPaymentDialog
				.findViewById(R.id.dialog_content);
		content.addView(layout);

	}

}