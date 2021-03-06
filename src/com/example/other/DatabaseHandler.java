package com.example.other;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.example.mwallet.PaymentFragment;
import com.example.pengguna.AirplaneTransaction;
import com.example.pengguna.BillTransaction;
import com.example.pengguna.PenggunaController;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper {

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "MWallet";

	private static final String TABLE_LOGIN = "login";
	private static final String TABLE_TRNSC_HSTY = "transaction_history";
	private static final String TABLE_AIRPLANE_TRNSC = "airplane_transaction";
	private static final String TABLE_BILL_TRNSC = "bill_transaction";
	private static final String TABLE_TOPUP_HSTY = "topup_history";

	private static final String KEY_ID = "id";
	private static final String KEY_IDUSER = "id_user";
	private static final String KEY_NAME = "name";
	private static final String KEY_USERNAME = "username";
	private static final String KEY_EMAIL = "email";
	private static final String KEY_SEX = "sex";
	private static final String KEY_AGE = "age";
	private static final String KEY_BALANCE = "balance";
	private static final String KEY_PIN = "pin";

	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_LOGIN + "("
				+ KEY_ID + " INTEGER PRIMARY KEY," + KEY_IDUSER
				+ " TEXT UNIQUE," + KEY_USERNAME + " TEXT UNIQUE," + KEY_EMAIL
				+ " TEXT UNIQUE," + KEY_NAME + " TEXT," + KEY_SEX + " TEXT,"
				+ KEY_AGE + " TEXT,"+ KEY_PIN + " TEXT," + KEY_BALANCE + " TEXT"+")";
		db.execSQL(CREATE_LOGIN_TABLE);
		String CREATE_TRNSC_HSTY_TABLE = "CREATE TABLE " + TABLE_TRNSC_HSTY +"(ID_TRNSC TEXT UNIQUE,TRNSC_TYPE TEXT,ID_USER TEXT,TRNSC_CODE TEXT,AMOUNT TEXT)";
		db.execSQL(CREATE_TRNSC_HSTY_TABLE);
		String CREATE_AIRPLANE_TRANSACTION_TABLE = "CREATE TABLE " + TABLE_AIRPLANE_TRNSC +"(ID_TRNSC TEXT UNIQUE,ID_PLANE TEXT,COMPANY TEXT,TOTAL_TICKET TEXT,TRNSC_TYPE TEXT,PLANE_DATE TEXT,PLANE_TIME TEXT, DEST_PORT TEXT, DEPART_PORT TEXT)";
		db.execSQL(CREATE_AIRPLANE_TRANSACTION_TABLE);
		String CREATE_BILL_TRANSACTION_TABLE = "CREATE TABLE " + TABLE_BILL_TRNSC +"(ID_TRNSC TEXT UNIQUE,ID_BILL TEXT, TRNSC_TYPE TEXT, PAY_CODE TEXT, FLAG_ELECT TEXT, ELECT_ACC TEXT, FLAG_WATER TEXT, WATER_ACC TEXT, FLAG_INT TEXT, INT_ACC TEXT, PAID_AMOUNT TEXT, ACC_NAME TEXT)";
		db.execSQL(CREATE_BILL_TRANSACTION_TABLE);
		
		String CREATE_TOPUP_HISTORY = "CREATE TABLE " + TABLE_TOPUP_HSTY + "(ID TEXT UNIQUE, ID_USER TEXT, TOPUP_DATE TEXT, AMOUNT TEXT, STATUS TEXT, ACC_OWNER TEXT, ACC_NUM TEXT)";
		db.execSQL(CREATE_TOPUP_HISTORY);
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOGIN);

		// Create tables again
		onCreate(db);
	}

	/**
	 * Method ini berfungsi untuk melakukan perubahan terhadap data pengguna
	 * pada database ketika pengguna mengubah profilnya
	 * 
	 * @param id
	 * @param username
	 * @param email
	 * @param fullname
	 * @param sex
	 * @param address
	 * @param birthdate
	 */
	public void editUser(String id_user, String username, String email,
			String fullname, String sex, String age) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_NAME, fullname);
		values.put(KEY_USERNAME, username);
		values.put(KEY_EMAIL, email);
		values.put(KEY_SEX, sex);
		values.put(KEY_AGE, age);

		// updating row
		db.update(TABLE_LOGIN, values, KEY_IDUSER + " = ?",
				new String[] { String.valueOf(id_user) });
		db.close();
	}
	
	public void editUser(String username, String email, String name, String pin){
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_USERNAME, username); // Username
		values.put(KEY_EMAIL, email); // Email
		values.put(KEY_NAME, name); // Fullname
		values.put(KEY_PIN, pin); // Saldo
		db.update(TABLE_LOGIN, values, "id_user = ?",
				new String[] { String.valueOf(PenggunaController.getUser().getId()) });
		PenggunaController.getUser().setEmail(email);
		PenggunaController.getUser().setUsername(username);
		PenggunaController.getUser().setName(name);
		PenggunaController.getUser().setPin(pin);
		
		db.close();
	}

	/**
	 * Memasukkan data user yang login ke dalam database
	 * 
	 * @param id_user
	 * @param username
	 * @param email
	 * @param fullname
	 * @param balance
	 * @param birthdate
	 * @param sex
	 * @param age
	 */
	public void loginUser(String id, String username, String email,
			String name, String sex, String age, String pin, String balance) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_IDUSER, id); // Id account
		values.put(KEY_USERNAME, username); // Username
		values.put(KEY_EMAIL, email); // Email
		values.put(KEY_NAME, name); // Fullname
		values.put(KEY_SEX, sex); // Jenis Kelamin
		values.put(KEY_AGE, age); // Umur
		values.put(KEY_BALANCE, balance); // Saldo
		values.put(KEY_PIN, pin); // Saldo

		// Inserting Row
		db.insert(TABLE_LOGIN, null, values);
		db.close(); // Closing database connection
	}

	/**
	 * Getting user data from database
	 * */
	public HashMap<String, String> getUserDetails() {
		HashMap<String, String> user = new HashMap<String, String>();
		String selectQuery = "SELECT  * FROM " + TABLE_LOGIN;

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		// Move to first row
		cursor.moveToFirst();
		if (cursor.getCount() > 0) {
			user.put("id_user", cursor.getString(1));
			user.put("username", cursor.getString(2));
			user.put("email", cursor.getString(3));
			user.put("name", cursor.getString(4));
			user.put("balance", cursor.getString(8));
			user.put("pin", cursor.getString(7));
			user.put("sex", cursor.getString(5));
			user.put("age", cursor.getString(6));
		}
		cursor.close();
		db.close();
		// return user
		return user;
	}

	/**
	 * Getting user login status return true if rows are there in table
	 * */
	public boolean getLoginStatus() {
		String countQuery = "SELECT  * FROM " + TABLE_LOGIN;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		int rowCount = cursor.getCount();
		db.close();
		cursor.close();

		if (rowCount == 0) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Re create database Delete all tables and create them again
	 * */
	public void resetTables() {
		SQLiteDatabase db = this.getWritableDatabase();
		// Hapus semua data di tabel login dan check in
		db.delete(TABLE_LOGIN, null, null);
		db.delete(TABLE_AIRPLANE_TRNSC, null, null);
		db.delete(TABLE_BILL_TRNSC, null, null);
		db.delete(TABLE_TRNSC_HSTY, null, null);
		db.close();
	}

	public void insertAirplaneTransaction(String transaction_id,
			String transaction_type, String id_user, String transaction_code,
			String amount, String id_plane, String company,
			String total_ticket, String date, String time, String depart,
			String dest) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put("ID_TRNSC", transaction_id);
		values.put("TRNSC_TYPE", transaction_type);
		values.put("ID_USER", id_user);
		values.put("TRNSC_CODE", transaction_code);
		PaymentFragment.t_code = transaction_code;
		values.put("AMOUNT", amount);
		db.insert(TABLE_TRNSC_HSTY, null, values);

		values.put("ID_PLANE", id_plane);
		values.put("COMPANY", company);
		values.put("TOTAL_TICKET", total_ticket);
		values.put("PLANE_DATE", date);
		values.put("PLANE_TIME", time);
		values.put("DEPART_PORT", depart);
		values.put("DEST_PORT", dest);
		values.remove("ID_USER");
		values.remove("TRNSC_CODE");
		values.remove("AMOUNT");
		// Inserting Row
		db.insert(TABLE_AIRPLANE_TRNSC, null, values);

		values = new ContentValues();
		values.put(
				"balance",
				PenggunaController.getUser().getBalance()
						- Float.parseFloat(amount));
		db.update(TABLE_LOGIN, values, "id_user = ?", new String[] { String
				.valueOf(PenggunaController.getUser().getId()) });
		PenggunaController.getUser().setBalance(
				PenggunaController.getUser().getBalance()
						- Float.parseFloat(amount));

		db.close(); // Closing database connection
	}

	public void insertTopUp(String topUp_id, String id_user, String topUp_date,
			String amount, String status, String acc_owner, String acc_number) {
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put("ID", topUp_id);
		values.put("ID_USER", id_user);
		values.put("TOPUP_DATE", topUp_date);
		values.put("AMOUNT", amount);
		values.put("STATUS", status);
		values.put("ACC_OWNER", acc_owner);
		values.put("ACC_NUM", acc_number);
		
		db.insert(TABLE_TOPUP_HSTY, null, values);
		
		values = new ContentValues();
		values.put("balance", PenggunaController.getUser().getBalance()
				+ Float.parseFloat(amount));
		
		PenggunaController.getUser().setBalance(PenggunaController.getUser().getBalance() + Float.parseFloat(amount));
		
		Log.i("mwallet","" + PenggunaController.getUser().getBalance());
		
		db.update(TABLE_LOGIN, values, "id_user = ?", new String[] { String
				.valueOf(PenggunaController.getUser().getId()) });

		db.close(); // Closing database connection

	}
	public void insertBillTransaction(String transaction_id, String transaction_type,
			String id_user, String transaction_code, String amount,
			String id_bill, String pay_code, String flag_elect, String elect_acc, String flag_water, String water_acc, String flag_int, String int_acc, String acc_name) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put("ID_TRNSC", transaction_id);
		values.put("TRNSC_TYPE", transaction_type);
		values.put("ID_USER", id_user);
		values.put("TRNSC_CODE",transaction_code);
		PaymentFragment.t_code = transaction_code;
		values.put("AMOUNT", amount); 
		db.insert(TABLE_TRNSC_HSTY, null, values);

		values.put("ID_BILL", id_bill);
		values.put("PAY_CODE", pay_code);
		values.put("FLAG_ELECT", flag_elect);
		values.put("ELECT_ACC", elect_acc);
		values.put("FLAG_WATER", flag_water);
		values.put("WATER_ACC", water_acc);
		values.put("FLAG_INT", flag_int);
		values.put("INT_ACC", int_acc);
		values.put("ACC_NAME", acc_name);
		values.put("PAID_AMOUNT", amount);
		values.remove("ID_USER");
		values.remove("TRNSC_CODE");
		values.remove("AMOUNT");
		// Inserting Row
		db.insert(TABLE_BILL_TRNSC, null, values);
		db.close(); // Closing database connection
	}
	public void insertBillTransaction1(String transaction_id, String transaction_type,
			String id_user, String transaction_code, String amount,
			String id_bill, String pay_code, String flag_elect, String elect_acc, String flag_water, String water_acc, String flag_int, String int_acc, String acc_name) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put("ID_TRNSC", transaction_id);
		values.put("TRNSC_TYPE", transaction_type);
		values.put("ID_USER", id_user);
		values.put("TRNSC_CODE",transaction_code);
		PaymentFragment.t_code = transaction_code;
		values.put("AMOUNT", amount); 
		db.insert(TABLE_TRNSC_HSTY, null, values);

		values.put("ID_BILL", id_bill);
		values.put("PAY_CODE", pay_code);
		values.put("FLAG_ELECT", flag_elect);
		values.put("ELECT_ACC", elect_acc);
		values.put("FLAG_WATER", flag_water);
		values.put("WATER_ACC", water_acc);
		values.put("FLAG_INT", flag_int);
		values.put("INT_ACC", int_acc);
		values.put("ACC_NAME", acc_name);
		values.put("PAID_AMOUNT", amount);
		values.remove("ID_USER");
		values.remove("TRNSC_CODE");
		values.remove("AMOUNT");
		// Inserting Row
		db.insert(TABLE_BILL_TRNSC, null, values);
		
		values = new ContentValues();
		values.put("balance", PenggunaController.getUser().getBalance() - Float.parseFloat(amount));
		db.update(TABLE_LOGIN, values, "id_user = ?",
				new String[] { String.valueOf(PenggunaController.getUser().getId()) });
		PenggunaController.getUser().setBalance(PenggunaController.getUser().getBalance() - Float.parseFloat(amount));
		db.close(); // Closing database connection
	}
	
	public ArrayList<AirplaneTransaction> getAirplaneTransaction() {
		SQLiteDatabase db = this.getReadableDatabase();
		ArrayList<AirplaneTransaction> airplaneTransaction = new ArrayList<AirplaneTransaction>();
		String selectQuery = "SELECT  * FROM " + TABLE_TRNSC_HSTY;

		Cursor cursor = db.rawQuery(selectQuery, null);
		// Move to first row
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			String id_transaction = cursor.getString(0);
			String trnsc_type = cursor.getString(1);
			String id_user = cursor.getString(2);
			String trnsc_code = cursor.getString(3);
			String amount = cursor.getString(4);

			selectQuery = "SELECT  * FROM " + TABLE_AIRPLANE_TRNSC
					+ " WHERE ID_TRNSC = ?";
			Cursor cursor1 = db.rawQuery(selectQuery,
					new String[] { id_transaction });
			cursor1.moveToFirst();
			while (!cursor1.isAfterLast()) {
				String id_plane = cursor1.getString(1);
				String company = cursor1.getString(2);
				String total_ticket = cursor1.getString(3);
				String plane_date = cursor1.getString(5);
				String plane_time = cursor1.getString(6);
				String depart_port = cursor1.getString(8);
				String dest_port = cursor1.getString(7);
				AirplaneTransaction ar = new AirplaneTransaction(
						id_transaction, trnsc_type, id_user, trnsc_code,
						amount, id_plane, company, total_ticket, plane_date,
						plane_time, depart_port, dest_port);
				airplaneTransaction.add(ar);
				cursor1.moveToNext();
			}
			cursor1.close();
			cursor.moveToNext();
		}
		cursor.close();
		db.close();
		// return user
		return airplaneTransaction;
	}
	
	public void insertAirplaneTransaction1(String transaction_id, String transaction_type,
			String id_user, String transaction_code, String amount,
			String id_plane, String company, String total_ticket, String date,
			String time, String depart, String dest) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put("ID_TRNSC", transaction_id);
		values.put("TRNSC_TYPE", transaction_type);
		values.put("ID_USER", id_user);
		values.put("TRNSC_CODE",transaction_code);
		PaymentFragment.t_code = transaction_code;
		values.put("AMOUNT", amount); 
		db.insert(TABLE_TRNSC_HSTY, null, values);

		values.put("ID_PLANE", id_plane);
		values.put("COMPANY", company);
		values.put("TOTAL_TICKET", total_ticket);
		values.put("PLANE_DATE", date);
		values.put("PLANE_TIME", time);
		values.put("DEPART_PORT", depart);
		values.put("DEST_PORT", dest);
		values.remove("ID_USER");
		values.remove("TRNSC_CODE");
		values.remove("AMOUNT");
		// Inserting Row
		db.insert(TABLE_AIRPLANE_TRNSC, null, values);
		db.close(); // Closing database connection
	}
	
	public ArrayList<BillTransaction> getBillTransaction() {
		SQLiteDatabase db = this.getReadableDatabase();
		ArrayList<BillTransaction> billTransaction = new ArrayList<BillTransaction>();
		String selectQuery = "SELECT  * FROM " + TABLE_TRNSC_HSTY;
		
		Cursor cursor = db.rawQuery(selectQuery,null);
		// Move to first row
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			String id_transaction = cursor.getString(0);
			String trnsc_type = cursor.getString(1);
			String id_user = cursor.getString(2);
			String trnsc_code = cursor.getString(3);
			String amount = cursor.getString(4);

 			
 			selectQuery = "SELECT  * FROM " + TABLE_BILL_TRNSC + " WHERE ID_TRNSC = ?";
 			Cursor cursor1 = db.rawQuery(selectQuery,new String[]{id_transaction});
 			cursor1.moveToFirst();
 			while(!cursor1.isAfterLast()){
 				String id_bill = cursor1.getString(1);
 				String pay_code = cursor1.getString(3);
 				String flag_elect = cursor1.getString(4);
 	 			String elect_acc = cursor1.getString(5);
 	 			String flag_water = cursor1.getString(6);
 	 			String water_acc = cursor1.getString(7);
 	 			String flag_int = cursor1.getString(8);
 	 			String int_acc = cursor1.getString(9);
 	 			String acc_name = cursor1.getString(11);
 	 			BillTransaction br = new BillTransaction(id_transaction, trnsc_type,
 	 					id_user, trnsc_code, amount,
 	 					id_bill, pay_code, flag_elect,
 	 					elect_acc, flag_water, water_acc,
 	 					flag_int, int_acc, acc_name);
 	 			billTransaction.add(br);
 	 			cursor1.moveToNext();
 			}
 			cursor1.close();
 			cursor.moveToNext();
		}
		cursor.close();
		db.close();
		// return user
		return billTransaction;
	}
}