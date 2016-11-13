package com.intocable.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.androidquery.AQuery;
import com.intocable.R;
import com.intocable.object.Merchandise;
public class Merchandise_Adapter extends BaseAdapter {

	public Activity activity;
	public ArrayList<Merchandise> arrayList;
	private LayoutInflater inflater;
	private AQuery mAQuery;
	public Merchandise_Adapter(Activity activity,
			ArrayList<Merchandise> arrayList) {
		this.activity = activity;
		mAQuery = new AQuery(activity);
		this.arrayList = arrayList;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public void setData(ArrayList<Merchandise> arrayList) {
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
		final Merchandise item = arrayList.get(position); 
		ViewHolder holder;
		if (convertView == null || convertView.getTag() == null) {
			convertView = inflater.inflate(R.layout.row_merchandise, null);
			holder = new ViewHolder();
			holder.ivMerchandise = (ImageView) convertView
					.findViewById(R.id.iv_row_merchandise);
			convertView.setTag(holder);
		}
		else
		{
			holder = (ViewHolder) convertView.getTag();
		}
		
		if(holder!= null)
		{
			String url = item.getSmallImageURL();
			mAQuery.id(holder.ivMerchandise).image(url, false,
					true, 200, 0, null, AQuery.FADE_IN);
		}
		// ImageView iv_row_merchandise = (ImageView) convertView
		// .findViewById(R.id.iv_row_merchandise);
		//
		// String url = item.getSmallImageURL();
		// imageLoader.DisplayImage(url, iv_row_merchandise);

		return convertView;

	}

	class ViewHolder {
		ImageView ivMerchandise;
	}
}
