package com.example.mwallet;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.Menu;
import android.widget.TextView;

public class InvoiceActivity extends Activity {

	private TextView transaction;
	private TextView tag;
	private TextView price;
	private TextView date;
	private TextView t_code;
	private String transactText;
	private String tagText;
	private String priceText;
	private String dateText;
	private String tCodeText;
	private String from;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_invoice);
		setupView();
		
		Intent intent = this.getIntent();
		if(intent.getStringExtra("tag") != null){
			tagText = intent.getStringExtra("tag").toString();
			tag.setText(tagText);
		}
		
		if(intent.getStringExtra("price") != null){
			priceText = intent.getStringExtra("price").toString();
			price.setText(priceText);
		}
		
		if(intent.getStringExtra("t_code") != null){
			tCodeText = intent.getStringExtra("t_code").toString();
			t_code.setText(tCodeText);
		}
		
		if(intent.getStringExtra("date") != null){
			dateText = intent.getStringExtra("date").toString();
			date.setText(dateText);
		}
		
		if(intent.getStringExtra("transaction") != null){
			transactText = intent.getStringExtra("transaction").toString();
			transaction.setText(transactText);
		}
		
		if(intent.getStringExtra("from") != null){
			from = intent.getStringExtra("from").toString();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.invoice, menu);
		return true;
	}

	public void setupView(){
		tag = (TextView) this.findViewById(R.id.transactKind);
		
		t_code = (TextView) this.findViewById(R.id.t_code);
	
		transaction = (TextView) this.findViewById(R.id.transactName);
		
		price = (TextView) this.findViewById(R.id.amount);
		date = (TextView) this.findViewById(R.id.dateOfTransact);
	}
	
	public void onBackPressed(){
		if(from.equals("history")){
			super.onBackPressed();
		}else{
			Intent intent = new Intent(this, DrawerActivity.class);
			startActivity(intent);
			super.onBackPressed();
		}
	}
}
