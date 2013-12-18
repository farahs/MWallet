package com.example.mwallet;

import java.util.ArrayList;

import com.example.other.HistoryBaseAdapter;
import com.example.other.ListData;

//import com.irfan.customlistviewdemo.R;


import android.os.Bundle;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class HistoryFragment extends Fragment {

	DrawerActivity activity;
	View rootView;

	
	ListView list;
	Context context = activity.getApplicationContext();
	ArrayList<ListData> myList = new ArrayList<ListData>();
	HistoryBaseAdapter hb;
	TextView title;
	
	Button btn_prev;
	Button btn_next;
	private int pageCount;
	private int increment = 0;
	public int TOTAL_LIST_ITEMS = 8;
	public int NUM_ITEMS_PAGE = 3;
	
	String[] transaction = new String[] {
			"21-Film The Hobbit", "21-Film The Hobbit", "21-Film The Hobbit", "Train-Jakarta Kota to Depok",
			"Train-Jakarta Kota to Depok", "Electricity Bill-October Period", "Food Delivery-PHD", "Food Delivery-Sederhana"
	};
	String[] date = new String[] {
			"20 November 2013", "20 November 2013", "20 November 2013", "20 November 2013",
			"20 November 2013", "20 November 2013", "20 November 2013", "20 November 2013" 
	};
	String[] price = new String[] {
			"Rp70.000,00", "Rp5.000,00", "Rp200.000,00", "Rp70.000,00",
			"Rp5.000,00", "Rp200.000,00", "Rp70.000,00", "Rp70.000,00" 
	};
	
	
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		this.rootView = inflater.inflate(R.layout.activity_history, container, false);
		this.activity = (DrawerActivity) this.getActivity();
		list = (ListView) rootView.findViewById(R.id.list);
		btn_prev = (Button) rootView.findViewById(R.id.prev);
		btn_next = (Button) rootView.findViewById(R.id.next);
		title = (TextView) rootView.findViewById(R.id.title);
		
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
		for(int i=0;i<8;i++)
		{
			ListData ld = new ListData();
			ld.setDate(date[i]);
			ld.setPrice(price[i]);
			ld.setTransaction(transaction[i]);
			myList.add(ld);
		}
		
	}
}
