package com.example.mwallet.adapter;

import java.util.ArrayList;
import java.util.List;

import com.example.mwallet.R;
import com.example.mwallet.R.drawable;

import android.R.color;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class DrawerAdapter extends BaseAdapter {

	private Context context;
	private LayoutInflater layoutInflater;
	private List<String> menuTitles = new ArrayList<String>();

	public DrawerAdapter(Context context, List<String> list) {
		this.context = context;
		this.layoutInflater = LayoutInflater.from(context);
		this.menuTitles = list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return this.menuTitles.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return this.menuTitles.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@SuppressWarnings("deprecation")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		ViewHolder holder;
		if (convertView == null) {
			convertView = this.layoutInflater.inflate(
					R.layout.module_drawer_list_item, null);
			holder = new ViewHolder();
			holder.text = (TextView) convertView.findViewById(R.id.text);
			holder.icon = (ImageView) convertView.findViewById(R.id.icon);

			switch (position) {
			case 0:
				holder.icon.setBackgroundDrawable(null);
				holder.icon.setBackgroundResource(drawable.home);	
				break;
			case 1:
				holder.icon.setBackgroundDrawable(null);
				holder.icon.setBackgroundResource(drawable.payment);
				break;
			case 2:
				holder.icon.setBackgroundDrawable(null);
				holder.icon.setBackgroundResource(drawable.topup);
				break;
			case 3:
				holder.icon.setBackgroundDrawable(null);
				holder.icon.setBackgroundResource(drawable.history);
				break;
			}

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.text.setText(menuTitles.get(position));
		return convertView;
	}

	static class ViewHolder {

		TextView text;
		ImageView icon;

	}

}
