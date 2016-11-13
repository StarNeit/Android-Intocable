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
import com.intocable.object.Videos;

public class Media_videos_Adapter extends BaseAdapter {

	// private Activity activity;
	private ArrayList<Videos> arrayList;
	private LayoutInflater inflater;
	private AQuery mAQuery;
	public Media_videos_Adapter(Activity activity, ArrayList<Videos> arrayList) {
		// this.activity = activity;
		mAQuery = new AQuery(activity);
		this.arrayList = arrayList;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public void setData(ArrayList<Videos> arrayList) {
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
		final Videos item = arrayList.get(position);
		ViewHolder holder;
		if (convertView == null || convertView.getTag() == null) {
			convertView = inflater.inflate(R.layout.row_videos_media, null);
			holder = new ViewHolder();
			holder.ivBackgroundVideo = (ImageView) convertView
					.findViewById(R.id.iv_media_videos);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		if (holder != null)
		mAQuery.id(holder.ivBackgroundVideo).image(item.getBackgroundURL(), false,
				true, 200, 0, null, AQuery.FADE_IN);

		return convertView;

	}

	class ViewHolder {
		ImageView ivBackgroundVideo;
	}
}
