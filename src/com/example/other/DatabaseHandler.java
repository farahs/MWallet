package com.example.other;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "MWallet";

	private static final String TABLE_LOGIN = "login";

	private static final String KEY_ID = "id";
	private static final String KEY_IDUSER = "id_user";
	private static final String KEY_FULLNAME = "fullname";
	private static final String KEY_USERNAME = "username";
	private static final String KEY_EMAIL = "email";
	private static final String KEY_SEX = "sex";
	private static final String KEY_AGE = "age";
	private static final String KEY_BALANCE = "balance";
	private static final String KEY_BIRTHDATE = "birthdate";

	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_LOGIN + "("
				+ KEY_ID + " INTEGER PRIMARY KEY," + KEY_IDUSER
				+ " TEXT UNIQUE," + KEY_USERNAME + " TEXT UNIQUE," + KEY_EMAIL
				+ " TEXT UNIQUE," + KEY_FULLNAME + " TEXT," + KEY_SEX + " TEXT,"
				+ KEY_AGE + " TEXT,"+ KEY_BIRTHDATE + " TEXT," + KEY_BALANCE + " TEXT"+")";
		db.execSQL(CREATE_LOGIN_TABLE);
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
	 * Method ini berfungsi untuk melakukan perubahan terhadap data pengguna pada database 
	 * ketika pengguna mengubah profilnya
	 * @param id
	 * @param username
	 * @param email
	 * @param fullname
	 * @param sex
	 * @param address
	 * @param birthdate
	 */
	public void editUser(String id_user, String username, String email,
			String fullname, String birthdate, String sex,
			String age) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_FULLNAME, fullname);
		values.put(KEY_USERNAME, username);
		values.put(KEY_EMAIL, email);
		values.put(KEY_SEX, sex);
		values.put(KEY_AGE, age);
		values.put(KEY_BIRTHDATE, birthdate);

		// updating row
		db.update(TABLE_LOGIN, values, KEY_IDUSER + " = ?",
				new String[] { String.valueOf(id_user) });
		db.close();
	}

	/**
	 * Memasukkan data user yang login ke dalam database
	 * @param id_user
	 * @param username
	 * @param email
	 * @param fullname
	 * @param balance
	 * @param birthdate
	 * @param sex
	 * @param age
	 */
	public void loginUser(String id_user, String username, String email,
			String fullname, String balance, String birthdate, String sex,
			String age) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_IDUSER, id_user); // Id account
		values.put(KEY_USERNAME, username); // Username
		values.put(KEY_EMAIL, email); // Email
		values.put(KEY_FULLNAME, fullname); // Fullname
		values.put(KEY_BIRTHDATE, birthdate); // Tanggal Lahir
		values.put(KEY_SEX, sex); // Jenis Kelamin
		values.put(KEY_AGE, age); // Umur
		values.put(KEY_BALANCE, balance); // Saldo

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
			user.put("id_acc", cursor.getString(1));
			user.put("username", cursor.getString(2));
			user.put("email", cursor.getString(3));
			user.put("fullname", cursor.getString(4));
			user.put("balance", cursor.getString(8));
			user.put("birthdate", cursor.getString(7));
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

		if(rowCount == 0){
			return false;
		}else{
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
		db.close();
	}
}