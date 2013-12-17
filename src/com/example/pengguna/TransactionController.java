package com.example.pengguna;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.jsonparser.JSONParser;
import com.example.other.DatabaseHandler;

import android.content.Context;

public class TransactionController {
	
	private JSONParser jsonParser = new JSONParser();
	
	private static String URL_AIRPLANE = "http://www.mwallet.meximas.com/public_html/PHP/airplane.php";
	
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
	
	public ArrayList<String> getDeparturePort(String company){
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
	
	public ArrayList<String> getDestinationPort(String company, String depart){
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
	public ArrayList<String> driverMethod(String[] params){
		if(params[0].equalsIgnoreCase("get_company")){
			return this.getAirplaneCompany();
		}else if(params[0].equalsIgnoreCase("get_depart")){
			return this.getDeparturePort(params[1]);
		}else if(params[0].equalsIgnoreCase("get_dest")){
			return this.getDestinationPort(params[1], params[2]);
		}
		
		return null;
	}
}
