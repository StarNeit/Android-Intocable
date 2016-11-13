package com.intocable.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.intocable.R;
import com.intocable.object.ChildSection;
import com.intocable.object.Events;

public class Event_Adapter_1 extends ArrayAdapter<Object> {

	Activity context;
	ArrayList<Object> items;

	public Event_Adapter_1(Activity context, int textViewResourceId,
			ArrayList<Object> objects) {
		super(context, textViewResourceId, objects);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.items = objects;
		sortList();
	}

	/**
	 * Sort list as alphabetical and add section
	 */
	public void sortList() {
		// sort alphabetical
//				Collections.sort(items, new Comparator<Object>() {
//					@Override
//					public int compare(Object left, Object right) {
//						// TODO Auto-generated method stub
//						if (left instanceof Events && right instanceof Events) {
//							return ((Events) left).getDay().compareTo(
//									((Events) right).getDay());
//						} else
//							return 0;
//					}
//				});

		// add first section
		String prefix = "";
		for (int i = 0; i < items.size(); i++) {
			if (items.get(i) instanceof Events) {
				// prefix = String.valueOf(
				// ((ParseObject) items.get(i)).getString("kidName")
				// .charAt(0)).toUpperCase();
				prefix = ((Events) items.get(i)).getDay();
				items.add(i, new ChildSection(prefix));
				break;
			} else if (items.get(i) instanceof ChildSection) {
				prefix = ((ChildSection) items.get(i)).getTitle();
				break;
			}
		}

		// add section
		for (int i = 1; i < items.size(); i++) {
			if (items.get(i) instanceof Events) {
				Events item = (Events) items.get(i);
				if (item.getDay().equals(prefix))
					continue;
				prefix = String.valueOf(item.getDay());
				items.add(i, new ChildSection(prefix));
			} else if (items.get(i) instanceof ChildSection) {
				prefix = ((ChildSection) items.get(i)).getTitle();
				continue;
			}
		}
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		convertView = null;
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			Object item = items.get(position);

			// section Item
			if (item instanceof ChildSection) {
				ChildSection section = (ChildSection) item;
				convertView = inflater.inflate(R.layout.row_sub_events, null);
				TextView tv_sub_events = (TextView) convertView
						.findViewById(R.id.tv_sub_events);
				tv_sub_events.setText(section.getTitle());
			}

			// non section item
			else {
				final Events child = (Events) item;

				convertView = inflater.inflate(R.layout.row_events, null);
				TextView tv_Title_events = (TextView) convertView
						.findViewById(R.id.tv_Title_events);
				TextView tv_time_events = (TextView) convertView
						.findViewById(R.id.tv_time_events);
				

				tv_Title_events.setText(child.getTitle());
				tv_time_events.setText(child.getTime());

			}
		}

		return convertView;
	}

	@Override
	public void notifyDataSetChanged() {
		// TODO Auto-generated method stub
		sortList();
		super.notifyDataSetChanged();
	}
}
