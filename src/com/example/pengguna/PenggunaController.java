package com.example.pengguna;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.example.jsonparser.JSONParser;
import com.example.other.DatabaseHandler;

public class PenggunaController {
	private static Pengguna user = null;

	private static JSONParser jsonParser = new JSONParser();

	private static String URL_LOGIN = "http://www.mwallet.meximas.com/public_html/PHP/login.php";
	private static String URL_REGISTER = "http://www.mwallet.meximas.com/public_html/PHP/register.php";

	private static String login_tag = "login";
	private static String register_tag = "register";

	public static Pengguna getUser() {
		return user;
	}

	public static void setUser(Pengguna user) {
		PenggunaController.user = user;
	}

	public static boolean isLogin() {
		return (user == null) ? false : true;
	}

	/**
	 * Function get Login status
	 * */
	public boolean isUserLoggedIn(Context context) {
		DatabaseHandler db = new DatabaseHandler(context);
		boolean login = db.getLoginStatus();
		if(login){
			HashMap<String,String> user = db.getUserDetails();
			setUser(new Pengguna(user.get("id_user"),user.get("username") , user.get("email"), user.get("name"), user.get("sex"), user.get("age"), user.get("pin"), Float.parseFloat(user.get("balance"))));
			return true;
		}else{
			return false;
		}
	}

	/**
	 * Function to logout user Reset Database
	 * */
	public boolean logoutUser(Context context) {
		DatabaseHandler db = new DatabaseHandler(context);
		db.resetTables();
		setUser(null);
		return true;
	}

	/**
	 * Function to register user
	 * @param name
	 * @param username
	 * @param email
	 * @param password
	 * @param pin
	 * @param sex
	 * @param age
	 * @return list of mistake
	 */
	public ArrayList<String> registerUser(String name, String username,
			String email, String password, String pin, String sex, String age) {
		ArrayList<String> mistakes = validateSignUp(name, username, email, password, pin,
				sex, age);
		if (mistakes.size() == 0) {
			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("register", register_tag));
			params.add(new BasicNameValuePair("name", name));
			params.add(new BasicNameValuePair("username", username));
			params.add(new BasicNameValuePair("email", email));
			params.add(new BasicNameValuePair("password", password));
			params.add(new BasicNameValuePair("pin", pin));
			params.add(new BasicNameValuePair("sex", sex));
			params.add(new BasicNameValuePair("age", age));

			// getting JSON Object
			JSONObject json = jsonParser.getJSONFromUrl(URL_REGISTER, params);
			// return json
			try {
				if (json.getString("success").equals("1")) {
					ArrayList<String> result = new ArrayList<String>();
					result.add("success");
					return result;
				} else if (json.getString("error").equals("2")) {
					ArrayList<String> result = new ArrayList<String>();
					result.add("existed");
					return result;
				} else {
					ArrayList<String> result = new ArrayList<String>();
					result.add("error");
					return result;
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				ArrayList<String> result = new ArrayList<String>();
				result.add("error");
				return result;
			}
		} else {
			return mistakes;
		}
	}

	public ArrayList<String> validateSignUp(String name,
			String username, String email, String password, String pin,
			String sex, String age) {
		ArrayList<String> mistakes = new ArrayList<String>();
		String pattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		boolean empty = false;
		if (name.equals("")) {
			mistakes.add("empty name");
			empty = true;
		}
		if (username.equals("")) {
			mistakes.add("empty username");
			empty = true;
		}
		if (email.equals("")) {
			mistakes.add("empty email");
			empty = true;
		}
		if (password.equals("")) {
			mistakes.add("empty password");
			empty = true;
		}
		if (pin.equals("")) {
			mistakes.add("empty pin");
			empty = true;
		}
		if (sex.equals("")) {
			mistakes.add("empty sex");
			empty = true;
		}
		if (age.equals("")) {
			mistakes.add("empty age");
			empty = true;
		}
		if ((username.length() < 6 || username.length() >= 20) && !empty) {
			mistakes.add("length username");
		}
		if (!username.matches("[A-Za-z0-9]+") && !empty) {
			mistakes.add("pattern username");
		}
		if ((!email.contains("@") || !email.matches(pattern)) && !empty) {
			mistakes.add("pattern email");
		}
		if (password.length() < 6 && !empty) {
			mistakes.add("length password");
		}
		if (pin.length() != 6 && !empty) {
			mistakes.add("length pin");
		}
		if (!pin.matches("[0-9]+") && !empty) {
			mistakes.add("pattern pin");
		}
		if (!age.matches("[0-9]+") && !empty) {
			mistakes.add("pattern age");
		}else{
			if (Integer.parseInt(age) < 18 && !empty) {
				mistakes.add("limit age");
			}
		}
		
		return mistakes;
	}
	
	/**
	 * function make Login Request
	 * 
	 * @param email
	 * @param password
	 * */
	public ArrayList<String> loginUser(String id, String password, Context c) {
		ArrayList<String> mistakes = validateSignIn(id, password);
		if (mistakes.size() == 0) {
			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("login", login_tag));
			params.add(new BasicNameValuePair("id", id));
			params.add(new BasicNameValuePair("password", password));
			JSONObject json = jsonParser.getJSONFromUrl(URL_LOGIN, params);
			try {
				if (json.getString("success").equals("1")) {
					// User berhasil login
					// Memasukkan data user yang login ke SQLite Database
					DatabaseHandler db = new DatabaseHandler(c);
					JSONObject json_user = json.getJSONObject("data_user");

					// Membuat sebuah model pengguna
					setUser(new Pengguna(json.getString("id_user"), json_user.getString("username"), json_user.getString("email"), json_user.getString("name"), json_user.getString("sex"), json_user.getString("age"), json_user.getString("pin"), Float.parseFloat(json_user.getString("balance"))));
					// Memasukkan data user login ke database hp
					db.loginUser(json.getString("id_user"), json_user.getString("username"), json_user.getString("email"), json_user.getString("name"), json_user.getString("sex"), json_user.getString("age"), json_user.getString("pin"), json_user.getString("balance"));
					
					String id_user = json.getString("id_user");
					
					params = new ArrayList<NameValuePair>();
					params.add(new BasicNameValuePair("login_airplane_history", login_tag));
					params.add(new BasicNameValuePair("id_user", id_user));
					JSONObject json_transaction = jsonParser.getJSONFromUrl(URL_LOGIN, params);
					
					
					if (json_transaction.getString("success").equals("1")) {
						JSONArray result = json_transaction.getJSONArray("data_transaction");
						for (int i = 0; i < result.length(); i++) {
							JSONObject json1 = result.getJSONObject(i);
							db.insertAirplaneTransaction1(json1.getString("ID_TRNSC"), json1.getString("TRNSC_TYPE"), json1.getString("ID_USER"), json1.getString("TRNSC_CODE"), json1.getString("AMOUNT"), json1.getString("ID_PLANE"), json1.getString("COMPANY"), json1.getString("TOTAL_TICKET"), json1.getString("PLANE_DATE"), json1.getString("PLANE_TIME"), json1.getString("DEPART_PORT"), json1.getString("DEST_PORT"));
						}
					}
					ArrayList<String> result = new ArrayList<String>();
					result.add("success");
;					return result;
				} else {
					ArrayList<String> result = new ArrayList<String>();
					result.add("wrong");
;					return result;
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				ArrayList<String> result = new ArrayList<String>();
				result.add("error");
;				return result;
			}
		} else {
			return mistakes;
		}
	}
	
	public ArrayList<String> validateSignIn(String username, String password) {
		boolean empty = false;
		ArrayList<String> mistakes = new ArrayList<String>();
		
		if (username.equals("")) {
			mistakes.add("empty username");
			empty = true;
		}
		if(password.equals("")){
			mistakes.add("empty password");
			empty = true;
		}
		if ((username.length() < 6 || username.length() > 200 ) && !empty) {
			mistakes.add("length username");
		}
		if (!username.matches("[A-Za-z0-9]+") && !empty) {
			mistakes.add("pattern username");
		}
		if(password.length() < 6 && !empty){
			mistakes.add("length password");
		}
		return mistakes;
	}
	
}
