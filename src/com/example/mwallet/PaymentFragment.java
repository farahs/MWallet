package com.example.mwallet;

import android.os.Bundle;
import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
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
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.example.pengguna.TransactionController;

public class PaymentFragment extends Fragment implements OnClickListener {

	private Context context;
	private ProgressDialog pDialog;
	private TransactionController tController;
	private DrawerActivity activity;
	private String base_price = "";
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

	TextView dialogCinemaName;
	TextView dialogMovieTitle;
	TextView dialogMovieDate;
	TextView dialogMovieTime;
	TextView dialogMovieTicket;
	TextView dialogMovieAmount;

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

	TextView dialogTrainName;
	TextView dialogTrainFrom;
	TextView dialogTrainTo;
	TextView dialogTrainDate;
	TextView dialogTrainTime;
	TextView dialogTrainTicket;
	TextView dialogTrainAmount;

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

	TextView dialogAirlineName;
	TextView dialogAirplaneFrom;
	TextView dialogAirplaneTo;
	TextView dialogAirplaneDate;
	TextView dialogAirplaneTime;
	TextView dialogAirplaneTicket;
	TextView dialogAirplaneAmount;

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
		this.context = this.getActivity();

		this.tController = new TransactionController();
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

		processPaymentBtn.setEnabled(false);
		processPaymentBtn.setBackgroundColor(Color.GRAY);

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
				Toast.makeText(activity.getApplicationContext(),
						"PAYCODE INFO", Toast.LENGTH_SHORT).show();
			}
		});

		paycodeEt.setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {

				processPaymentBtn.setBackgroundColor(getResources().getColor(
						R.color.default_red));
				processPaymentBtn.setClickable(true);
				processPaymentBtn.setEnabled(true);

				return false;
			}
		});
	}

	private void setupOthersView() {
		otherCategoriesBtn = (Button) this.rootView
				.findViewById(R.id.payment_categories_button);
		paycodeEt = (EditText) this.rootView.findViewById(R.id.paycode_input);
		paycodeInfoTv = (TextView) this.rootView
				.findViewById(R.id.paycode_info);
	}

	private void setupAirplaneEvent() {

		disableAirplaneButton();
		processPaymentBtn.setEnabled(false);
		processPaymentBtn.setBackgroundColor(Color.GRAY);

		airlineNameBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				ArrayList<View> views = new ArrayList<View>();
				views.add(airplaneDepartureBtn);
				views.add(airplaneDestinationBtn);
				views.add(airplaneDateBtn);
				views.add(airplaneTimeBtn);
				views.add(airplaneNumOfTicketEt);
				deactivateButton(views);
				new GetData().execute("get_company");

			}
		});

		airplaneDepartureBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				ArrayList<View> views = new ArrayList<View>();
				views.add(airplaneDestinationBtn);
				views.add(airplaneDateBtn);
				views.add(airplaneTimeBtn);
				views.add(airplaneNumOfTicketEt);
				deactivateButton(views);
				new GetData().execute("get_depart", airlineNameBtn.getText()
						.toString());

			}
		});

		airplaneDestinationBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				ArrayList<View> views = new ArrayList<View>();
				views.add(airplaneDateBtn);
				views.add(airplaneTimeBtn);
				views.add(airplaneNumOfTicketEt);
				deactivateButton(views);
				new GetData().execute("get_dest", airlineNameBtn.getText()
						.toString(), airplaneDepartureBtn.getText().toString());

			}
		});

		airplaneDateBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				ArrayList<View> views = new ArrayList<View>();
				views.add(airplaneTimeBtn);
				views.add(airplaneNumOfTicketEt);
				deactivateButton(views);
				new GetData().execute("get_plane_date", airlineNameBtn.getText()
						.toString(), airplaneDepartureBtn.getText().toString(),
						airplaneDestinationBtn.getText().toString());
			}
		});

		airplaneTimeBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				ArrayList<View> views = new ArrayList<View>();
				views.add(airplaneNumOfTicketEt);
				deactivateButton(views);
				new GetData().execute("get_plane_time", airlineNameBtn.getText()
						.toString(), airplaneDepartureBtn.getText().toString(),
						airplaneDestinationBtn.getText().toString(),
						airplaneDateBtn.getText().toString());

			}
		});

		airplaneNumOfTicketEt
				.setOnEditorActionListener(new OnEditorActionListener() {

					@Override
					public boolean onEditorAction(TextView v, int actionId,
							KeyEvent event) {

						int count = Integer.parseInt(v.getText().toString());
						int price = Integer.parseInt(base_price) * count;

						airplaneAmountTv.setText(price + "");

						processPaymentBtn.setBackgroundColor(getResources()
								.getColor(R.color.default_red));
						processPaymentBtn.setClickable(true);
						processPaymentBtn.setEnabled(true);

						return false;
					}
				});

	}

	private void disableAirplaneButton() {

		airplaneDepartureBtn.setEnabled(false);
		airplaneDepartureBtn.setClickable(false);
		airplaneDepartureBtn.setBackgroundColor(getResources().getColor(
				R.color.default_input_gray));

		airplaneDestinationBtn.setEnabled(false);
		airplaneDestinationBtn.setClickable(false);
		airplaneDestinationBtn.setBackgroundColor(getResources().getColor(
				R.color.default_input_gray));

		airplaneDateBtn.setEnabled(false);
		airplaneDateBtn.setClickable(false);
		airplaneDateBtn.setBackgroundColor(getResources().getColor(
				R.color.default_input_gray));

		airplaneTimeBtn.setEnabled(false);
		airplaneTimeBtn.setClickable(false);
		airplaneTimeBtn.setBackgroundColor(getResources().getColor(
				R.color.default_input_gray));

		airplaneNumOfTicketEt.setEnabled(false);
		airplaneNumOfTicketEt.setClickable(false);

	}

	private void setupAirplaneView() {
		airlineNameBtn = (Button) this.rootView
				.findViewById(R.id.airline_name_button);
		airplaneDepartureBtn = (Button) this.rootView
				.findViewById(R.id.airplane_departure_button);
		airplaneDepartureBtn.setEnabled(false);
		airplaneDepartureBtn.setBackgroundColor(Color.GRAY);
		airplaneDestinationBtn = (Button) this.rootView
				.findViewById(R.id.airplane_destination_button);
		airplaneDestinationBtn.setEnabled(false);
		airplaneDestinationBtn.setBackgroundColor(Color.GRAY);
		airplaneDateBtn = (Button) this.rootView
				.findViewById(R.id.airplane_date_button);
		airplaneDateBtn.setEnabled(false);
		airplaneDateBtn.setBackgroundColor(Color.GRAY);
		airplaneTimeBtn = (Button) this.rootView
				.findViewById(R.id.airplane_time_button);
		airplaneTimeBtn.setEnabled(false);
		airplaneTimeBtn.setBackgroundColor(Color.GRAY);
		airplaneNumOfTicketEt = (EditText) this.rootView
				.findViewById(R.id.airplane_sum_ticket);
		airplaneNumOfTicketEt.setEnabled(false);
		airplaneAmountTv = (TextView) this.rootView
				.findViewById(R.id.airplane_amount_input);
	}

	private void setupTrainEvent() {

		disableTrainButton();

		processPaymentBtn.setEnabled(false);
		processPaymentBtn.setBackgroundColor(Color.GRAY);

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
								trainDepartureBtn
										.setBackgroundColor(getResources()
												.getColor(R.color.our_blue));
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
								trainDestinationBtn
										.setBackgroundColor(getResources()
												.getColor(R.color.our_blue));
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
								trainDateBtn.setBackgroundColor(getResources()
										.getColor(R.color.our_blue));
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
								trainTimeBtn.setBackgroundColor(getResources()
										.getColor(R.color.our_blue));
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

		trainNumOfTicketEt
				.setOnEditorActionListener(new OnEditorActionListener() {

					@Override
					public boolean onEditorAction(TextView v, int actionId,
							KeyEvent event) {

						int BASE_PRICE = 50000;
						int count = Integer.parseInt(v.getText().toString());
						int price = BASE_PRICE * count;

						trainAmountTv.setText(price + "");

						processPaymentBtn.setBackgroundColor(getResources()
								.getColor(R.color.default_red));
						processPaymentBtn.setClickable(true);
						processPaymentBtn.setEnabled(true);

						return false;
					}
				});

	}

	private void disableTrainButton() {

		trainDepartureBtn.setEnabled(false);
		trainDepartureBtn.setClickable(false);
		trainDepartureBtn.setBackgroundColor(getResources().getColor(
				R.color.default_input_gray));

		trainDestinationBtn.setEnabled(false);
		trainDestinationBtn.setClickable(false);
		trainDestinationBtn.setBackgroundColor(getResources().getColor(
				R.color.default_input_gray));

		trainDateBtn.setEnabled(false);
		trainDateBtn.setClickable(false);
		trainDateBtn.setBackgroundColor(getResources().getColor(
				R.color.default_input_gray));

		trainTimeBtn.setEnabled(false);
		trainTimeBtn.setClickable(false);
		trainTimeBtn.setBackgroundColor(getResources().getColor(
				R.color.default_input_gray));

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

		processPaymentBtn.setEnabled(false);
		processPaymentBtn.setBackgroundColor(Color.GRAY);

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

		movieNumOfTicketEt
				.setOnEditorActionListener(new OnEditorActionListener() {

					@Override
					public boolean onEditorAction(TextView v, int actionId,
							KeyEvent event) {

						int BASE_PRICE = 50000;
						int count = Integer.parseInt(v.getText().toString());
						int price = BASE_PRICE * count;

						movieAmountTv.setText(price + "");

						processPaymentBtn.setBackgroundColor(getResources()
								.getColor(R.color.default_red));
						processPaymentBtn.setClickable(true);
						processPaymentBtn.setEnabled(true);

						return false;
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
		case R.id.ok_process:
			if (paymentType == CINEMA_TYPE) {
				
			} else if (paymentType == TRAIN_TYPE) {
				
			} else if (paymentType == AIRPLANE_TYPE) {
				
			} else if (paymentType == OTHERS_TYPE) {
				
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

		this.content = (LinearLayout) this.processCinemaPaymentDialog
				.findViewById(R.id.dialog_content);
		content.addView(layout);

		this.dialogCinemaName = (TextView) this.processCinemaPaymentDialog
				.findViewById(R.id.dialog_cinema_name);
		this.dialogMovieTitle = (TextView) this.processCinemaPaymentDialog
				.findViewById(R.id.dialog_movie_title);
		this.dialogMovieDate = (TextView) this.processCinemaPaymentDialog
				.findViewById(R.id.dialog_movie_date);
		this.dialogMovieTime = (TextView) this.processCinemaPaymentDialog
				.findViewById(R.id.dialog_movie_time);
		this.dialogMovieTicket = (TextView) this.processCinemaPaymentDialog
				.findViewById(R.id.dialog_movie_ticket);
		this.dialogMovieAmount = (TextView) this.processCinemaPaymentDialog
				.findViewById(R.id.dialog_movie_amount);

		// setup data dialog

		this.dialogCinemaName.setText(cinemaNameBtn.getText().toString());
		this.dialogMovieTitle.setText(movieTitleBtn.getText().toString());
		this.dialogMovieDate.setText(movieDateBtn.getText().toString());
		this.dialogMovieTime.setText(movieTimeBtn.getText().toString());
		this.dialogMovieTicket.setText(movieNumOfTicketEt.getText().toString());
		this.dialogMovieAmount.setText(movieAmountTv.getText().toString());

		this.okBtn = (Button) this.processCinemaPaymentDialog
				.findViewById(R.id.ok_process);
		this.cancelBtn = (Button) this.processCinemaPaymentDialog
				.findViewById(R.id.cancel_process);
		this.okBtn.setOnClickListener(this);
		this.cancelBtn.setOnClickListener(this);

	}

	private void setupTrainDialog() {
		this.processTrainPaymentDialog = new Dialog(this.activity);
		this.processTrainPaymentDialog
				.setContentView(R.layout.payment_process_dialog);
		this.processTrainPaymentDialog.setTitle("YOUR PAYMENT");

		this.layout = LayoutInflater
				.from(getActivity().getApplicationContext()).inflate(
						R.layout.train_layout_dialog, null);

		this.content = (LinearLayout) this.processTrainPaymentDialog
				.findViewById(R.id.dialog_content);
		content.addView(layout);

		this.dialogTrainName = (TextView) this.processTrainPaymentDialog
				.findViewById(R.id.dialog_train_name);
		this.dialogTrainFrom = (TextView) this.processTrainPaymentDialog
				.findViewById(R.id.dialog_train_from);
		this.dialogTrainTo = (TextView) this.processTrainPaymentDialog
				.findViewById(R.id.dialog_train_to);
		this.dialogTrainDate = (TextView) this.processTrainPaymentDialog
				.findViewById(R.id.dialog_train_date);
		this.dialogTrainTime = (TextView) this.processTrainPaymentDialog
				.findViewById(R.id.dialog_train_time);
		this.dialogTrainTicket = (TextView) this.processTrainPaymentDialog
				.findViewById(R.id.dialog_train_ticket);
		this.dialogTrainAmount = (TextView) this.processTrainPaymentDialog
				.findViewById(R.id.dialog_train_amount);

		// setup data

		this.dialogTrainName.setText(trainNameBtn.getText().toString());
		this.dialogTrainFrom.setText(trainDepartureBtn.getText().toString());
		this.dialogTrainTo.setText(trainDestinationBtn.getText().toString());
		this.dialogTrainDate.setText(trainDateBtn.getText().toString());
		this.dialogTrainTime.setText(trainTimeBtn.getText().toString());
		this.dialogTrainTicket.setText(trainNumOfTicketEt.getText().toString());
		this.dialogTrainAmount.setText(trainAmountTv.getText().toString());

		this.okBtn = (Button) this.processTrainPaymentDialog
				.findViewById(R.id.ok_process);
		this.cancelBtn = (Button) this.processTrainPaymentDialog
				.findViewById(R.id.cancel_process);
		this.okBtn.setOnClickListener(this);
		this.cancelBtn.setOnClickListener(this);

	}

	private void setupAirplaneDialog() {
		this.processAirplanePaymentDialog = new Dialog(this.activity);
		this.processAirplanePaymentDialog
				.setContentView(R.layout.payment_process_dialog);
		this.processAirplanePaymentDialog.setTitle("YOUR PAYMENT");

		this.layout = LayoutInflater
				.from(getActivity().getApplicationContext()).inflate(
						R.layout.airplane_layout_dialog, null);

		this.content = (LinearLayout) this.processAirplanePaymentDialog
				.findViewById(R.id.dialog_content);
		content.addView(layout);

		this.dialogAirlineName = (TextView) this.processAirplanePaymentDialog
				.findViewById(R.id.dialog_airplane_name);
		this.dialogAirplaneFrom = (TextView) this.processAirplanePaymentDialog
				.findViewById(R.id.dialog_airplane_from);
		this.dialogAirplaneTo = (TextView) this.processAirplanePaymentDialog
				.findViewById(R.id.dialog_airplane_to);
		this.dialogAirplaneDate = (TextView) this.processAirplanePaymentDialog
				.findViewById(R.id.dialog_airplane_date);
		this.dialogAirplaneTime = (TextView) this.processAirplanePaymentDialog
				.findViewById(R.id.dialog_airplane_time);
		this.dialogAirplaneTicket = (TextView) this.processAirplanePaymentDialog
				.findViewById(R.id.dialog_airplane_ticket);
		this.dialogAirplaneAmount = (TextView) this.processAirplanePaymentDialog
				.findViewById(R.id.dialog_airplane_amount);

		// setup data

		this.dialogAirlineName.setText(airlineNameBtn.getText().toString());
		this.dialogAirplaneFrom.setText(airplaneDepartureBtn.getText()
				.toString());
		this.dialogAirplaneTo.setText(airplaneDestinationBtn.getText()
				.toString());
		this.dialogAirplaneDate.setText(airplaneDateBtn.getText().toString());
		this.dialogAirplaneTime.setText(airplaneTimeBtn.getText().toString());
		this.dialogAirplaneTicket.setText(airplaneNumOfTicketEt.getText()
				.toString());
		this.dialogAirplaneAmount
				.setText(airplaneAmountTv.getText().toString());

		this.okBtn = (Button) this.processAirplanePaymentDialog
				.findViewById(R.id.ok_process);
		this.cancelBtn = (Button) this.processAirplanePaymentDialog
				.findViewById(R.id.cancel_process);
		this.okBtn.setOnClickListener(this);
		this.cancelBtn.setOnClickListener(this);

	}

	private void setupOthersDialog() {

		this.processOtherPaymentDialog = new Dialog(this.activity);
		this.processOtherPaymentDialog
				.setContentView(R.layout.payment_process_dialog);
		this.processOtherPaymentDialog.setTitle("YOUR PAYMENT");

		this.layout = LayoutInflater
				.from(getActivity().getApplicationContext()).inflate(
						R.layout.others_layout_dialog, null);

		this.content = (LinearLayout) this.processOtherPaymentDialog
				.findViewById(R.id.dialog_content);
		content.addView(layout);

		this.okBtn = (Button) this.processOtherPaymentDialog
				.findViewById(R.id.ok_process);
		this.cancelBtn = (Button) this.processOtherPaymentDialog
				.findViewById(R.id.cancel_process);
		this.okBtn.setOnClickListener(this);
		this.cancelBtn.setOnClickListener(this);

	}

	public void activateButton(ArrayList<View> views) {
		for (int i = 0; i < views.size(); i++) {
			views.get(i).setEnabled(true);
			if(views.get(i) instanceof Button){
				views.get(i).setBackgroundColor(
					getResources().getColor(R.color.our_blue));
			}else{
				views.get(i).setBackgroundColor(Color.WHITE);
			}
		}
	}

	public void deactivateButton(ArrayList<View> views) {
		for (int i = 0; i < views.size(); i++) {
			views.get(i).setEnabled(false);
			if (views.get(i) instanceof Button) {
				Button temp = (Button) views.get(i);
				temp.setText("CHOOSE");
				views.get(i).setBackgroundColor(Color.GRAY);
			} else if (views.get(i) instanceof EditText) {
				EditText temp = (EditText) views.get(i);
				temp.setText("");
			}
		}
	}

	/**
	 * Background Async Task to Load all data by making HTTP Request
	 * */
	class GetData extends AsyncTask<String, String, ArrayList<String>> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = ProgressDialog.show(context, "", "Getting data....",
					false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		@Override
		protected ArrayList<String> doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			return tController.driverMethod(arg0);
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

	/**
	 * Background Async Task to Load all data by making HTTP Request
	 * */
	class ProcessPayment extends AsyncTask<String, String, ArrayList<String>> {

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
			return tController.driverMethod(arg0);
		}

		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(ArrayList<String> result1) {
			// dismiss the dialog after getting all products
			pDialog.dismiss();
			//processResult(result1);
		}

	}
	
	public void processResult(ArrayList<String> result) {
		final String title = result.get(result.size() - 1);
		result.remove(result.size() - 1);
		if(title.equals("TIME")){
			base_price = result.get(result.size() - 1);
			result.remove(result.size() - 1);
		}
		String[] listOfResult = new String[result.size()];
		listOfResult = result.toArray(listOfResult);

		final String[] finalResult = listOfResult;
		
		AlertDialog.Builder airplaneBuilder = new AlertDialog.Builder(activity);
		airplaneBuilder.setTitle(title).setItems(listOfResult,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int position) {
						if (title.equals("AIRLINES")) {
							airlineNameBtn.setText(finalResult[position]);
							ArrayList<View> views = new ArrayList<View>();
							views.add(airplaneDepartureBtn);
							activateButton(views);
						} else if (title.equals("DEPARTURE")) {
							airplaneDepartureBtn.setText(finalResult[position]);
							ArrayList<View> views = new ArrayList<View>();
							views.add(airplaneDestinationBtn);
							activateButton(views);
						} else if (title.equals("DESTINATION")) {
							airplaneDestinationBtn
									.setText(finalResult[position]);
							ArrayList<View> views = new ArrayList<View>();
							views.add(airplaneDateBtn);
							activateButton(views);
						} else if (title.equals("DATE (YYYY-MM-DD)")) {
							airplaneDateBtn.setText(finalResult[position]);
							ArrayList<View> views = new ArrayList<View>();
							views.add(airplaneTimeBtn);
							activateButton(views);
						} else if (title.equals("TIME")) {
							airplaneTimeBtn.setText(finalResult[position]);
							ArrayList<View> views = new ArrayList<View>();
							views.add(airplaneNumOfTicketEt);
							activateButton(views);
						}
					}
				});

		airplaneBuilder.show();
	}
}