package com.example.pengguna;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.example.jsonparser.JSONParser;
import com.example.other.DatabaseHandler;

public class TopUpController {

	private JSONParser jsonParser = new JSONParser();

	private static String URL_TOPUP = "http://www.mwallet.meximas.com/public_html/PHP/top_up.php";

	public ArrayList<String> driverMethodTopUp(Context context, String[] params)
			throws JSONException {

		return processTopUp(context, params[0], params[1], params[2], params[3]);
	}

	public ArrayList<String> processTopUp(Context c, String amount,
			String accountNumber, String accountName, String date)
			throws JSONException {

		DatabaseHandler db = new DatabaseHandler(c);
		ArrayList<String> results = new ArrayList<String>();

		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("process_topUp", "process_topUp"));
		params.add(new BasicNameValuePair("amount", amount));
		params.add(new BasicNameValuePair("accountNumber", accountNumber));
		params.add(new BasicNameValuePair("accountName", accountName));
		params.add(new BasicNameValuePair("date", date));
		params.add(new BasicNameValuePair("id_user", PenggunaController
				.getUser().getId()));

		JSONObject json_topup = jsonParser.getJSONFromUrl(URL_TOPUP, params);

		if (json_topup.getString("success").equals("1")) {
			JSONArray result = json_topup.getJSONArray("data_topUp");
			for (int i = 0; i < result.length(); i++) {
				JSONObject json = result.getJSONObject(i);
				db.insertTopUp(json.getString("ID"), json.getString("ID_USER"),
						json.getString("TOPUP_DATE"), json.getString("AMOUNT"),
						json.getString("STATUS"), json.getString("ACC_OWNER"),
						json.getString("ACC_NUM"));
			}
		}

		return results;
	}

}
