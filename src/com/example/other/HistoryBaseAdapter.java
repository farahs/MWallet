package com.example.other;

import java.util.ArrayList;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
//import android.widget.ImageView;
import android.widget.TextView;

public class HistoryBaseAdapter extends BaseAdapter
{
	ArrayList<ListData> myList = new ArrayList<ListData>();
	LayoutInflater inflater;
	Context context;
	
	public HistoryBaseAdapter(Context context, ArrayList<ListData> myList)
	{
		this.myList = myList;
		this.context = context;
		inflater = LayoutInflater.from(this.context);
	}
	public int getCount() {
		// TODO Auto-generated method stub
		return myList.size();
	}


	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return myList.get(position);
	}


	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		MyViewHolder mViewHolder;
		
		if(convertView == null)
		{
			convertView = inflater.inflate(com.example.mwallet.R.layout.list_item_layout, null);
			mViewHolder = new MyViewHolder();
			convertView.setTag(mViewHolder);
		}
		else
		{
			mViewHolder = (MyViewHolder) convertView.getTag();
		}
		
		mViewHolder.transaction = detail(convertView, com.example.mwallet.R.id.transaction, myList.get(position).getTransaction());
		mViewHolder.date = detail(convertView, com.example.mwallet.R.id.date, myList.get(position).getDate());
		mViewHolder.price = detail(convertView, com.example.mwallet.R.id.price, myList.get(position).getPrice());
		mViewHolder.tag = detail(convertView, com.example.mwallet.R.id.tag, myList.get(position).getTag());
		mViewHolder.t_code = detail(convertView, com.example.mwallet.R.id.t_code, myList.get(position).getT_code());
		
		return convertView;
	}
	
	private TextView detail(View v, int resId, String text) {
		// TODO Auto-generated method stub
		TextView tv = (TextView) v.findViewById(resId);
		tv.setText(text);
		return tv;
	}

	
	
	private class MyViewHolder
	{
		TextView transaction, price,date,tag, t_code;
		
	}

}
