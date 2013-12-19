package com.example.mwallet;

import java.util.ArrayList;

import com.example.other.DatabaseHandler;
import com.example.other.HistoryBaseAdapter;
import com.example.other.ListData;
import com.example.pengguna.AirplaneTransaction;
import com.example.pengguna.BillTransaction;

//import com.irfan.customlistviewdemo.R;


import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class HistoryFragment extends Fragment {

	DrawerActivity activity;
	View rootView;

	
	ListView list;
	Context context;
	ArrayList<ListData> myList = new ArrayList<ListData>();
	HistoryBaseAdapter hb;
	TextView title;
	
	Button btn_prev;
	Button btn_next;
	private int pageCount;
	private int increment = 0;
	public int TOTAL_LIST_ITEMS;
	public int NUM_ITEMS_PAGE = 5;
	
	ArrayList<String> transaction = new ArrayList<String>();
	ArrayList<String> date = new ArrayList<String>();
	ArrayList<String> price = new ArrayList<String>();
	ArrayList<String> tag = new ArrayList<String>();
	ArrayList<String> t_code = new ArrayList<String>();
	
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		this.rootView = inflater.inflate(R.layout.activity_history, container, false);
		this.activity = (DrawerActivity) this.getActivity();
		this.context = activity.getApplicationContext();
		list = (ListView) rootView.findViewById(R.id.list);
		
		btn_prev = (Button) rootView.findViewById(R.id.prev);
		btn_next = (Button) rootView.findViewById(R.id.next);
		title = (TextView) rootView.findViewById(R.id.title);
		Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/ColabReg.otf");
	    title.setTypeface(font); 
		DatabaseHandler db = new DatabaseHandler(getActivity());
		ArrayList<AirplaneTransaction> ar = db.getAirplaneTransaction();
		
		for(int i=0; i < ar.size(); i++){
			AirplaneTransaction art = ar.get(i);
			String transaction1 = art.getCompany()+"\nFrom: "+art.getDepart_port()+" To: "+art.getDest_port();
			String date1 = art.getDate()+ " "+ art.getTime();
			String price1 = "Rp. "+art.getAmount();
			transaction.add(transaction1);
			date.add(date1);
			price.add(price1);
			tag.add("Airplane");
			t_code.add(art.getTransaction_code());
		}
		
		ArrayList<BillTransaction> br = db.getBillTransaction();
		
		for(int i=0; i < br.size(); i++){
			BillTransaction art = br.get(i);
			String tipe = "";
			if(art.getFlag_elect().equals("1")){
				tipe = "Electric Bill";
			}else if(art.getFlag_int().equals("1")){
				tipe = "Internet Bill";
			}else if(art.getFlag_water().equals("1")){
				tipe = "Water Bill";
			}
			
			String account = "";
			if(art.getFlag_elect().equals("1")){
				account = art.getElect_acc();
			}else if(art.getFlag_int().equals("1")){
				account = art.getInt_acc();
			}else if(art.getFlag_water().equals("1")){
				account = art.getWater_acc();
			}
			
			String transaction1 = tipe
					+ "\nAccount Name: "
					+ art.getAcc_name() + "\nAccount Number: "+account;
			String date1 = art.getPay_code();
			String price1 = "Rp. "+art.getAmount();
			transaction.add(transaction1);
			date.add(date1);
			price.add(price1);
			tag.add("Bill");
			t_code.add(art.getTransaction_code());
		}
		
		list.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				TextView tag = (TextView) view.findViewById(R.id.tag);
				Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/ColabReg.otf");
	    		tag.setTypeface(font); 
				TextView transaction = (TextView) view.findViewById(R.id.transaction);
				transaction.setTypeface(font); 
				TextView date = (TextView) view.findViewById(R.id.date);
				date.setTypeface(font); 
				TextView price = (TextView) view.findViewById(R.id.price);
				price.setTypeface(font); 
				TextView t_code = (TextView) view.findViewById(R.id.t_code);
				t_code.setTypeface(font); 
				Intent intent = new Intent(context, InvoiceActivity.class);
				intent.putExtra("tag", tag.getText().toString());
				intent.putExtra("transaction", transaction.getText().toString());
				intent.putExtra("date", date.getText().toString());
				intent.putExtra("price",price.getText().toString());
				intent.putExtra("t_code",t_code.getText().toString());
				intent.putExtra("from", "history");
				startActivity(intent);
			}
			
		});
		TOTAL_LIST_ITEMS = date.size();
		/**
	     * this block is for checking the number of pages
	     * ====================================================
	     */
		int val = TOTAL_LIST_ITEMS%NUM_ITEMS_PAGE;
		val = val==0?0:1;
		pageCount = TOTAL_LIST_ITEMS/NUM_ITEMS_PAGE+val;
		 /**
	     * =====================================================
	     */
				 
		if(pageCount==1)
		{
			btn_prev.setEnabled(false);
			btn_next.setEnabled(false);
		}
		/**
	     * The ArrayList data contains all the list items
	     */
		getDataInList();
		btn_prev.setEnabled(false);
		loadList(0);
		
		//lvDetail.setAdapter(new MyBaseAdapter(context, myList));
		btn_next.setOnClickListener(new View.OnClickListener() {
	         
	        public void onClick(View v) {
	             
	            increment++;
	            loadList(increment);
	            CheckEnable();
	        }
	    });
		
		btn_prev.setOnClickListener(new View.OnClickListener() {
            
            public void onClick(View v) {
                    
            	increment--;
                    loadList(increment);
                    CheckEnable();
            }
		});
		
		return this.rootView;
	}
	private void CheckEnable()
	{
		if(increment+1 == pageCount)
		{
			btn_next.setEnabled(false);
			btn_prev.setEnabled(true);
		}
		else if(increment==0)
		{
			btn_prev.setEnabled(false);
			btn_next.setEnabled(true);
		}
		else
		{
			btn_prev.setEnabled(true);
			btn_next.setEnabled(true);
		}
	}
	
	private void loadList(int number) 
	{
		ArrayList<ListData> sort = new ArrayList<ListData>();
		title.setText("Page "+(number+1)+" of "+pageCount);
		
		int start = number * NUM_ITEMS_PAGE;
		for(int i=start;i<(start)+NUM_ITEMS_PAGE;i++)
		{
			if(i<myList.size())
			{
				sort.add(myList.get(i));
			}
			else
			{
				break;
			}
		}
		hb = new HistoryBaseAdapter(context, sort);
		list.setAdapter(hb);		
	}
	private void getDataInList() {
		// TODO Auto-generated method stub
		for(int i=0;i<date.size();i++)
		{
			ListData ld = new ListData();
			ld.setDate(date.get(i));
			ld.setPrice(price.get(i));
			ld.setTransaction(transaction.get(i));
			ld.setTag(tag.get(i));
			ld.setT_code(t_code.get(i));
			myList.add(ld);
		}
		
	}
}
