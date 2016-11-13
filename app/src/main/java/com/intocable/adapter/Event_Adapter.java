package com.intocable.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.intocable.R;
import com.intocable.object.Events;

public class Event_Adapter extends BaseAdapter {

	public Activity activity;
	public ArrayList<Events> arrayList;

	public Event_Adapter(Activity activity, ArrayList<Events> arrayList) {
		this.activity = activity;
		this.arrayList = arrayList;
	}

	public void setData(ArrayList<Events> arrayList) {
		this.arrayList = arrayList;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return arrayList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return arrayList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final Events item = arrayList.get(position);
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) activity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.row_events, null);

		}

		TextView tv_sub_events = (TextView) convertView
				.findViewById(R.id.tv_sub_events);
		TextView tv_Title_events = (TextView) convertView
				.findViewById(R.id.tv_Title_events);
		TextView tv_time_events = (TextView) convertView
				.findViewById(R.id.tv_time_events);
		
		tv_sub_events.setText(item.getDay());
		tv_Title_events.setText(item.getTitle());
		tv_time_events.setText(item.getTime());

		return convertView;

	}

}
