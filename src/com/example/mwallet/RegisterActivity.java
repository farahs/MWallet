package com.example.mwallet;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import com.example.pengguna.PenggunaController;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.view.View;
import android.view.View.OnClickListener;

public class RegisterActivity extends Activity implements OnClickListener{
	private EditText 	username;
	private ImageView 	usernameClear;
	private EditText 	password;
	private ImageView 	passwordClear;
	private EditText 	name;
	private ImageView 	nameClear;
	private EditText 	email;
	private ImageView 	emailClear;
	private EditText 	pin;
	private ImageView 	pinClear;
	private EditText 	verifyPassword;
	private ImageView 	verifyPasswordClear;
	private EditText 	age;
	private ImageView 	ageClear;
	private RadioGroup 	sex;
	private RadioButton male;
	private RadioButton female;
	private RadioButton other;
	private Button 		registerBtn;
	
	private String 		usernameText;
	private String 		passwordText;
	private String 		nameText;
	private String 		pinText;
	private String 		verifyPasswordText;
	private String 		ageText;
	private String 		emailText;
	private String 		sexText;
	
	private PenggunaController penggunaController;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		this.setView();
		this.setEvent();
		penggunaController = new PenggunaController();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register, menu);
		return true;
	}
	
	private void setView() {
		this.username = (EditText) this.findViewById(R.id.usernameIn);
		this.password = (EditText) this.findViewById(R.id.passIn);
		this.pin = (EditText) this.findViewById(R.id.pinNumIn);
		this.name = (EditText) this.findViewById(R.id.nameIn);
		this.verifyPassword = (EditText) this.findViewById(R.id.verifyPassIn);
		this.email = (EditText) this.findViewById(R.id.emailIn);
		this.age = (EditText) this.findViewById(R.id.ageIn);
		
		this.usernameClear = (ImageView) this.findViewById(R.id.usernameIn_clear);
		this.passwordClear = (ImageView) this.findViewById(R.id.passIn_clear);
		this.pinClear = (ImageView) this.findViewById(R.id.pinNumIn_clear);
		this.nameClear = (ImageView) this.findViewById(R.id.nameIn_clear);
		this.verifyPasswordClear = (ImageView) this.findViewById(R.id.verifyPassIn_clear);
		this.emailClear = (ImageView) this.findViewById(R.id.emailIn_clear);
		this.ageClear = (ImageView) this.findViewById(R.id.age_clear);
		
		this.registerBtn = (Button) this.findViewById(R.id.signUpBtn);
		
		this.sex = (RadioGroup) findViewById(R.id.radioSex);
		this.male = (RadioButton) findViewById(R.id.radioMale);
		this.female = (RadioButton) findViewById(R.id.radioFemale);
		this.other = (RadioButton) findViewById(R.id.radioOther);
		this.other.setSelected(true);
	}

	private void setEvent() {
		this.registerBtn.setOnClickListener(this);

		this.usernameClear.setOnClickListener(this);
		this.passwordClear.setOnClickListener(this);
		this.pinClear.setOnClickListener(this);
		this.nameClear.setOnClickListener(this);
		this.verifyPasswordClear.setOnClickListener(this);
		this.emailClear.setOnClickListener(this);
		this.ageClear.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.signUpBtn:
			try {
				this.registerUser();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case R.id.usernameIn_clear:
			this.usernameClear();
			break;
		case R.id.passIn_clear:
			this.passwordClear();
			break;
		case R.id.pinNumIn_clear:
			this.pinClear();
			break;
		case R.id.nameIn_clear:
			this.nameClear();
			break;
		case R.id.verifyPassIn_clear:
			this.verifyPasswordClear();
			break;
		case R.id.emailIn_clear:
			this.emailClear();
			break;
		case R.id.age_clear:
			this.ageClear();
			break;
		}
	}
	
	public void registerUser() throws InterruptedException, ExecutionException{
		usernameText = username.getText().toString().trim();
		passwordText = password.getText().toString().trim();
		nameText = name.getText().toString().trim();
		pinText = pin.getText().toString().trim();
		verifyPasswordText = verifyPassword.getText().toString().trim();
		ageText = age.getText().toString().trim();
		emailText = email.getText().toString().trim();
		
		int selected = sex.getCheckedRadioButtonId();
		RadioButton pilihan = (RadioButton) findViewById(selected);
		sexText = pilihan.getText().toString();
		
		if(!passwordText.equals(verifyPasswordText)){
			Toast.makeText(this, "Your password doesn't match", Toast.LENGTH_LONG).show();
		}else{
			ArrayList<String> result = new RegisteringUser().execute().get();
			for(int i = 0; i < result.size(); i++){
				if(result.get(i).equals("success")){
					Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show();
					Intent intent = new Intent(this,LoginActivity.class);
					startActivity(intent);
					finish();
				}else if(result.get(i).equals("existed")){
					Toast.makeText(this, "Username or email already exists", Toast.LENGTH_SHORT).show();
				}else if(result.get(i).equals("error")){
					Toast.makeText(this, "Sorry, an error occured.\n Please try again.", Toast.LENGTH_SHORT).show();
				}else{
					if(result.get(i).equals("empty username")){
						username.setTextColor(Color.RED);
						Toast.makeText(this, "Username cannot be blank", Toast.LENGTH_SHORT).show();
					}
					if(result.get(i).equals("empty email")){
						email.setTextColor(Color.RED);
						Toast.makeText(this, "Email cannot be blank", Toast.LENGTH_SHORT).show();
					}
					if(result.get(i).equals("empty name")){
						name.setTextColor(Color.RED);
						Toast.makeText(this, "Name cannot be blank", Toast.LENGTH_SHORT).show();
					}
					if(result.get(i).equals("empty password")){
						password.setTextColor(Color.RED);
						Toast.makeText(this, "Password cannot be blank", Toast.LENGTH_SHORT).show();
					}
					if(result.get(i).equals("empty age")){
						age.setTextColor(Color.RED);
						Toast.makeText(this, "Age cannot be blank", Toast.LENGTH_SHORT).show();
					}
					if(result.get(i).equals("empty pin")){
						pin.setTextColor(Color.RED);
						Toast.makeText(this, "Pin cannot be blank", Toast.LENGTH_SHORT).show();
					}
					if(result.get(i).equals("length username")){
						username.setTextColor(Color.RED);
						Toast.makeText(this, "Username must be between 6 until 20 characters", Toast.LENGTH_SHORT).show();
					}
					if(result.get(i).equals("pattern username")){
						username.setTextColor(Color.RED);
						Toast.makeText(this, "Username only consists of alfanumeric character", Toast.LENGTH_SHORT).show();
					}
					if(result.get(i).equals("pattern email")){
						email.setTextColor(Color.RED);
						Toast.makeText(this, "Your email does not valid", Toast.LENGTH_SHORT).show();
					}
					if(result.get(i).equals("length password")){
						password.setTextColor(Color.RED);
						Toast.makeText(this, "Password must be 6 or more character", Toast.LENGTH_SHORT).show();
					}
					if(result.get(i).equals("length pin")){
						pin.setTextColor(Color.RED);
						Toast.makeText(this, "Pin must be 6 characters", Toast.LENGTH_SHORT).show();
					}
					if(result.get(i).equals("pattern pin")){
						pin.setTextColor(Color.RED);
						Toast.makeText(this, "Pin must be numeric", Toast.LENGTH_SHORT).show();
					}
					if(result.get(i).equals("pattern age")){
						age.setTextColor(Color.RED);
						Toast.makeText(this, "Age must be numeric", Toast.LENGTH_SHORT).show();
					}
					if(result.get(i).equals("limit age")){
						age.setTextColor(Color.RED);
						Toast.makeText(this, "you must be 17 years old above to use this app", Toast.LENGTH_SHORT).show();
					}
				}
			}
		}
	}
	
	public void usernameClear(){
		this.username.setText("");
	}
	
	public void passwordClear(){
		this.password.setText("");
	}

	public void verifyPasswordClear(){
		this.verifyPassword.setText("");
	}
	
	public void nameClear(){
		this.name.setText("");
	}
	
	public void emailClear(){
		this.email.setText("");
	}
	
	public void ageClear(){
		this.age.setText("");
	}
	
	public void pinClear(){
		this.pin.setText("");
	}
	
	/**
	 * Background Async Task to Load all data by making HTTP Request
	 * */
	class RegisteringUser extends AsyncTask<String, String, ArrayList<String>> {

		@Override
		protected ArrayList<String> doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			return penggunaController.registerUser(nameText, usernameText, emailText, passwordText, pinText, sexText, ageText);
		}

	}
}
