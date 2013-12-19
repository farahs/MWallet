package com.example.mwallet;

import com.example.pengguna.PenggunaController;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class MainFragment extends Fragment implements OnClickListener {

	DrawerActivity activity;
	View rootView;
	
	private TextView username;
	private TextView name;
	private TextView email;
	private TextView balance;
	private TextView sex;
	private TextView age;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		this.rootView = inflater.inflate(R.layout.activity_main, container, false);
		this.activity = (DrawerActivity) this.getActivity();
		
		this.setupView();
		this.setupEvent();
		return this.rootView;
	}
	
	private void setupView() {
		username = (TextView) rootView.findViewById(R.id.username);
		username.setText(PenggunaController.getUser().getUsername());
		name = (TextView) rootView.findViewById(R.id.name);
		name.setText(PenggunaController.getUser().getName());
		email = (TextView) rootView.findViewById(R.id.email);
		email.setText(PenggunaController.getUser().getEmail());
		balance = (TextView) rootView.findViewById(R.id.balance);
		balance.setText("Rp. "+ (int)PenggunaController.getUser().getBalance());
		sex = (TextView) rootView.findViewById(R.id.sex);
		sex.setText(PenggunaController.getUser().getSex());
		age = (TextView) rootView.findViewById(R.id.age);
		age.setText(PenggunaController.getUser().getAge());
	}

	private void setupEvent() {
	
	}

	@Override
	public void onClick(View v) {

	}

}
