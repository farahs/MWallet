package com.example.mwallet;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TopUpFragment extends Fragment {

	DrawerActivity activity;
	View rootView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		this.rootView = inflater.inflate(R.layout.activity_top_up, container, false);
		this.activity = (DrawerActivity) this.getActivity();
		
		return this.rootView;
	}

}
