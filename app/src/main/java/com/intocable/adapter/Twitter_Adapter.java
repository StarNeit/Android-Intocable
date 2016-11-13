package com.intocable.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.intocable.R;
import com.intocable.object.Twitter;

public class Twitter_Adapter extends BaseAdapter {

	// public Activity activity;
	private ArrayList<Twitter> arrayList;
	private AQuery mAQuery;
	private LayoutInflater inflater;

	public Twitter_Adapter(Activity activity, ArrayList<Twitter> arrayList) {
		// this.activity = activity;
		this.arrayList = arrayList;
		mAQuery = new AQuery(activity);
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public void setData(ArrayList<Twitter> arrayList) {
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
		final Twitter item = arrayList.get(position);
		ViewHolder holder;
		if (convertView == null || convertView.getTag() == null) {
			convertView = inflater.inflate(R.layout.row_facebook, null);
			holder = new ViewHolder();
			holder.ivTweets = (ImageView) convertView
					.findViewById(R.id.iv_row_news);
			holder.tvTitle = (TextView) convertView
					.findViewById(R.id.tv_title_news);
			holder.tvTime = (TextView) convertView
					.findViewById(R.id.tv_time_news);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		if (holder != null) {
			holder.tvTitle.setText(Html.fromHtml(item.getBody()));

			holder.tvTime.setText(item.getTitle());

			mAQuery.id(holder.ivTweets).image(item.getLargeImage(), false,
					true, 200, 0, null, AQuery.FADE_IN);
		}

		// ImageView iv_row_news = (ImageView) convertView
		// .findViewById(R.id.iv_row_news);
		// TextView tv_title_news = (TextView) convertView
		// .findViewById(R.id.tv_title_news);
		// TextView tv_body_news = (TextView) convertView
		// .findViewById(R.id.tv_body_news);
		// TextView tv_time_news = (TextView) convertView
		// .findViewById(R.id.tv_time_news);
		//
		// tv_title_news.setText(Html.fromHtml(item.getBody()));
		// tv_time_news.setText(item.getTitle());
		//
		// // String url =
		// //
		// "https://fbcdn-profile-a.akamaihd.net/hprofile-ak-xpa1/v/t1.0-1/c17.17.212.212/s200x200/66722_10151361136486709_544848419_n.jpg?oh=6440632bc31d7dd36db42f9e7c6deba4&oe=54DB88F2&__gda__=1423972273_ed85c55bfb977c6a8aa4ced27d764cac";
		//
		// imageLoader.DisplayImage(item.getLargeImage(), iv_row_news);

		return convertView;

	}

	class ViewHolder {
		ImageView ivTweets;
		TextView tvTitle;
		TextView tvTime;
	}
}
