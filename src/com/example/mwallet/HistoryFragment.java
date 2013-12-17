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

	TextView page;
	ListView list;
	Context context = HistoryFragment.this.getActivity();
	ArrayList<ListData> myList = new ArrayList<ListData>();
	HistoryBaseAdapter hb;
	
	Button btn_prev;
	Button btn_next;
	private int pageCount;
	private int increment = 0;
	public int TOTAL_LIST_ITEMS = 8;
	public int NUM_ITEMS_PAGE = 3;
	
	String[] transaction = new String[] {
			"Title 1", "Title 2", "Title 3", "Title 4",
			"Title 5", "Title 6", "Title 7", "Title 8"
	};
	String[] date = new String[] {
			"Desc 1", "Desc 2", "Desc 3", "Desc 4",
			"Desc 5", "Desc 6", "Desc 7", "Desc 8" 
	};
	String[] price = new String[] {
			"Price 1", "Price 2", "Price 3", "Price 4",
			"Price 5", "Price 6", "Price 7", "Price 8" 
	};
	
	
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		this.rootView = inflater.inflate(R.layout.activity_history, container, false);
		this.activity = (DrawerActivity) this.getActivity();
		
		return this.rootView;
	}
	
}
