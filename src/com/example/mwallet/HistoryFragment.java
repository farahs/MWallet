package com.example.mwallet;

import android.os.Bundle;
import android.app.Fragment;
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
	Button next;
	Button previous;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		this.rootView = inflater.inflate(R.layout.activity_history, container, false);
		this.activity = (DrawerActivity) this.getActivity();
		
		return this.rootView;
	}
	
}
