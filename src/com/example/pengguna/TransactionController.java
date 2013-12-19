package com.example.pengguna;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.jsonparser.JSONParser;
import com.example.other.DatabaseHandler;

import android.content.Context;
import android.util.Log;

public class TransactionController {

	private JSONParser jsonParser = new JSONParser();

	private static String URL_AIRPLANE = "http://www.mwallet.meximas.com/public_html/PHP/airplane.php";
	private static String URL_OTHERS = "http://www.mwallet.meximas.com/public_html/PHP/others.php";

	public ArrayList<String> getAirplaneCompany() {
		// TODO Auto-generated method stub
		ArrayList<String> listOfCompany = new ArrayList<String>();
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("get_company", "get_company"));
		JSONObject json = jsonParser.getJSONFromUrl(URL_AIRPLANE, params);
		try {
			JSONArray result = json.getJSONArray("company");
			for (int i = 0; i < result.length(); i++) {
				JSONObject companyList = result.getJSONObject(i);
				listOfCompany.add(companyList.getString("COMPANY"));
			}
		} catch (JSONException j) {

		}
		listOfCompany.add("AIRLINES");
		return listOfCompany;
	}

	public ArrayList<String> getDeparturePort(String company) {
		// TODO Auto-generated method stub
		ArrayList<String> listOfDeparture = new ArrayList<String>();
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("get_depart", "get_depart"));
		params.add(new BasicNameValuePair("company", company));
		JSONObject json = jsonParser.getJSONFromUrl(URL_AIRPLANE, params);
		try {
			JSONArray result = json.getJSONArray("departure");
			for (int i = 0; i < result.length(); i++) {
				JSONObject departureList = result.getJSONObject(i);
				listOfDeparture.add(departureList.getString("DEPART_PORT"));
			}
		} catch (JSONException j) {

		}
		listOfDeparture.add("DEPARTURE");
		return listOfDeparture;
	}

	public ArrayList<String> getDestinationPort(String company, String depart) {
		// TODO Auto-generated method stub
		ArrayList<String> listOfDestination = new ArrayList<String>();
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("get_dest", "get_dest"));
		params.add(new BasicNameValuePair("company", company));
		params.add(new BasicNameValuePair("departure", depart));
		JSONObject json = jsonParser.getJSONFromUrl(URL_AIRPLANE, params);
		try {
			JSONArray result = json.getJSONArray("destination");
			for (int i = 0; i < result.length(); i++) {
				JSONObject destinationList = result.getJSONObject(i);
				listOfDestination.add(destinationList.getString("DEST_PORT"));
			}
		} catch (JSONException j) {

		}
		listOfDestination.add("DESTINATION");
		return listOfDestination;
	}

	public ArrayList<String> getAirplaneDate(String company, String depart,
			String dest) {
		// TODO Auto-generated method stub
		ArrayList<String> listOfDate = new ArrayList<String>();
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("get_date", "get_date"));
		params.add(new BasicNameValuePair("company", company));
		params.add(new BasicNameValuePair("departure", depart));
		params.add(new BasicNameValuePair("destination", dest));
		JSONObject json = jsonParser.getJSONFromUrl(URL_AIRPLANE, params);
		try {
			JSONArray result = json.getJSONArray("date");
			for (int i = 0; i < result.length(); i++) {
				JSONObject dateList = result.getJSONObject(i);
				listOfDate.add(dateList.getString("PLANE_DATE"));
			}
		} catch (JSONException j) {

		}
		listOfDate.add("DATE (YYYY-MM-DD)");
		return listOfDate;
	}

	public ArrayList<String> getAirplaneTime(String company, String depart,
			String dest, String date) {
		// TODO Auto-generated method stub
		ArrayList<String> listOfTime = new ArrayList<String>();
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("get_time", "get_time"));
		params.add(new BasicNameValuePair("company", company));
		params.add(new BasicNameValuePair("departure", depart));
		params.add(new BasicNameValuePair("destination", dest));
		params.add(new BasicNameValuePair("date", date));
		JSONObject json = jsonParser.getJSONFromUrl(URL_AIRPLANE, params);
		try {
			JSONArray result = json.getJSONArray("time");
			for (int i = 0; i < result.length(); i++) {
				JSONObject dateList = result.getJSONObject(i);
				listOfTime.add(dateList.getString("PLANE_TIME"));
			}
			listOfTime.add(result.getJSONObject(0).getString("PRICE"));
		} catch (JSONException j) {

		}
		listOfTime.add("TIME");
		return listOfTime;
	}
	
	public ArrayList<String> getOthers(String categories, String payCode) {
		// TODO Auto-generated method stub
		ArrayList<String> listOfInformation = new ArrayList<String>();
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("get_others", "get_others"));
		params.add(new BasicNameValuePair(categories.toLowerCase(), categories.toLowerCase()));
		params.add(new BasicNameValuePair("pay_code", payCode));
		JSONObject json = jsonParser.getJSONFromUrl(URL_OTHERS, params);
		try {
			System.out.println(json.getString("success"));
			if(json.getString("success").equals("1")){
				JSONArray result = json.getJSONArray(categories.toLowerCase());
			for (int i = 0; i < result.length(); i++) {
				JSONObject data = result.getJSONObject(i);
				if(categories.equalsIgnoreCase("vending_machine")){
					listOfInformation.add("vending_machine");
					listOfInformation.add(data.getString("MCH_NAME"));
					listOfInformation.add(data.getString("MCH_LOC"));
					listOfInformation.add(data.getString("ITEM"));
					listOfInformation.add(data.getString("PRICE"));
					listOfInformation.add(data.getString("PAY_CODE"));
				}else if(categories.equalsIgnoreCase("bill")){
					if(data.getString("FLAG_ELECT").equals("1")){
						listOfInformation.add("bill");
						listOfInformation.add("Electric Bill");
						listOfInformation.add(data.getString("ELECT_ACC"));
						listOfInformation.add(data.getString("ACC_NAME"));
						listOfInformation.add(data.getString("PAID_AMOUNT"));
						listOfInformation.add(data.getString("PAY_CODE"));
					}else if(data.getString("FLAG_WATER").equals("1")){
						listOfInformation.add("bill");
						listOfInformation.add("Water Bill");
						listOfInformation.add(data.getString("WATER_ACC"));
						listOfInformation.add(data.getString("ACC_NAME"));
						listOfInformation.add(data.getString("PAID_AMOUNT"));
						listOfInformation.add(data.getString("PAY_CODE"));
					}else if(data.getString("FLAG_INT").equals("1")){
						listOfInformation.add("bill");
						listOfInformation.add("Internet Bill");
						listOfInformation.add(data.getString("INT_ACC"));
						listOfInformation.add(data.getString("ACC_NAME"));
						listOfInformation.add(data.getString("PAID_AMOUNT"));
						listOfInformation.add(data.getString("PAY_CODE"));
					}
				}else if(categories.equalsIgnoreCase("electric_pulse")){
					listOfInformation.add("electric_pulse");
					if(data.getString("ID_MART").equals("1")){
						listOfInformation.add("Alfamart");
					}else if(data.getString("ID_MART").equals("2")){
						listOfInformation.add("Indomaret");
					}else if(data.getString("ID_MART").equals("3")){
						listOfInformation.add("Giant");
					}
					listOfInformation.add(data.getString("PHONE_NUMBER"));
					listOfInformation.add(data.getString("COMPANY"));
					listOfInformation.add(data.getString("PRICE"));
					listOfInformation.add(data.getString("PAY_CODE"));
				}
			}
			}
		} catch (JSONException j) {

		}
		return listOfInformation;
	}

	public ArrayList<String> driverMethod(String[] params) {
		if (params[0].equalsIgnoreCase("get_company")) {
			return this.getAirplaneCompany();
		} else if (params[0].equalsIgnoreCase("get_depart")) {
			return this.getDeparturePort(params[1]);
		} else if (params[0].equalsIgnoreCase("get_dest")) {
			return this.getDestinationPort(params[1], params[2]);
		} else if (params[0].equalsIgnoreCase("get_plane_date")) {
			return this.getAirplaneDate(params[1], params[2], params[3]);
		} else if (params[0].equalsIgnoreCase("get_plane_time")) {
			return this.getAirplaneTime(params[1], params[2], params[3],
					params[4]);
		} else if(params[0].equalsIgnoreCase("get_others")){
			return this.getOthers(params[1], params[2]);
		}

		return null;
	}

	public ArrayList<String> driverMethodPayment(Context context,
			String[] params) throws JSONException {
		if (params[0].equalsIgnoreCase("process_airplane")) {
			return processAirplanePayment(context, params[1], params[2],
					params[3], params[4], params[5], params[6], params[7]);
		} else if (params[0].equalsIgnoreCase("process_train")) {

		} else if (params[0].equalsIgnoreCase("process_cinema")) {

		} else if (params[0].equalsIgnoreCase("process_others")) {
			if(params[1].equals("bill")){
				return this.processBillPayment(context, params[2], params[3]);
			}
		}

		return new ArrayList<String>();
	}

	public ArrayList<String> processAirplanePayment(Context c, String company, String depart, String dest, String date, String time, String numOfTicket, String amount) throws JSONException{
		// TODO Auto-generated method stub
		DatabaseHandler db = new DatabaseHandler(c);
		ArrayList<String> results = new ArrayList<String>();
		if(Float.parseFloat(amount) <= PenggunaController.getUser().getBalance()){
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("process_payment", "process_payment"));
				params.add(new BasicNameValuePair("company", company));
				params.add(new BasicNameValuePair("departure", depart));
				params.add(new BasicNameValuePair("destination", dest));
				params.add(new BasicNameValuePair("date", date));
				params.add(new BasicNameValuePair("time", time));
				params.add(new BasicNameValuePair("ticket", numOfTicket));
				params.add(new BasicNameValuePair("amount", amount));
				params.add(new BasicNameValuePair("id_user", PenggunaController.getUser().getId()));
				params.add(new BasicNameValuePair("transaction_code", this.makeTransactionCode()));
				JSONObject json_transaction = jsonParser.getJSONFromUrl(URL_AIRPLANE, params);
				if (json_transaction.getString("success").equals("1")) {
					JSONArray result = json_transaction.getJSONArray("data_transaction");
					for (int i = 0; i < result.length(); i++) {
						JSONObject json = result.getJSONObject(i);
						db.insertAirplaneTransaction(json.getString("ID_TRNSC"), json.getString("TRNSC_TYPE"), json.getString("ID_USER"), json.getString("TRNSC_CODE"), json.getString("AMOUNT"), json.getString("ID_PLANE"), json.getString("COMPANY"), json.getString("TOTAL_TICKET"), json.getString("PLANE_DATE"), json.getString("PLANE_TIME"), json.getString("DEPART_PORT"), json.getString("DEST_PORT"));
					}
				}else{
					results.add("insufficient ticket");
				}
		}else{
			results.add("insufficient amount");
		}
		
		return results;
	}
	
	public ArrayList<String> processBillPayment(Context c, String amount, String pay_code) throws JSONException{
		// TODO Auto-generated method stub
		DatabaseHandler db = new DatabaseHandler(c);
		ArrayList<String> results = new ArrayList<String>();
		if(Float.parseFloat(amount) <= PenggunaController.getUser().getBalance()){
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("process_bill", "process_bill"));
				params.add(new BasicNameValuePair("pay_code", pay_code));
				params.add(new BasicNameValuePair("amount", amount));
				params.add(new BasicNameValuePair("id_user", PenggunaController.getUser().getId()));
				params.add(new BasicNameValuePair("t_code", this.makeTransactionCode()));
				JSONObject json_transaction = jsonParser.getJSONFromUrl(URL_OTHERS, params);
				if (json_transaction.getString("success").equals("1")) {
					JSONArray result = json_transaction.getJSONArray("data_transaction");
					for (int i = 0; i < result.length(); i++) {
						JSONObject json = result.getJSONObject(i);
						db.insertBillTransaction1(json.getString("ID_TRNSC"), json.getString("TRNSC_TYPE"), json.getString("ID_USER"), json.getString("TRNSC_CODE"), json.getString("PAID_AMOUNT"), json.getString("ID_BILL"), json.getString("PAY_CODE"), json.getString("FLAG_ELECT"), json.getString("ELECT_ACC"), json.getString("FLAG_WATER"), json.getString("WATER_ACC"), json.getString("FLAG_INT"), json.getString("INT_ACC"), json.getString("ACC_NAME"));
					}
				}
		}else{
			results.add("insufficient amount");
		}
		
		return results;
	}

	public String makeTransactionCode() {
		String letters = "0123456789";
		Random rand = new SecureRandom();
		String pw = PenggunaController.getUser().getId() + "";
		for (int i = 0; i < 8; i++) {
			int index = (int) (rand.nextDouble() * letters.length());
			pw += letters.substring(index, index + 1);
		}
		return pw;
	}
}
