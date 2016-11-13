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
import com.intocable.object.Facebook;

public class Facebook_Adapter extends BaseAdapter {

	// private Activity activity;
	private ArrayList<Facebook> arrayList;
	private LayoutInflater inflater;
	private String url = "https://fbcdn-profile-a.akamaihd.net/hprofile-ak-xpa1/v/t1.0-1/c17.17.212.212/s200x200/66722_10151361136486709_544848419_n.jpg?oh=6440632bc31d7dd36db42f9e7c6deba4&oe=54DB88F2&__gda__=1423972273_ed85c55bfb977c6a8aa4ced27d764cac";
	private AQuery mAQuery;
	public Facebook_Adapter(Activity activity, ArrayList<Facebook> arrayList) {
		// this.activity = activity;
		this.arrayList = arrayList;
		mAQuery = new AQuery(activity);
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public void setData(ArrayList<Facebook> arrayList) {
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
		final Facebook item = arrayList.get(position);
		ViewHolder holder;
		if (convertView == null || convertView.getTag() == null) {
			convertView = inflater.inflate(R.layout.row_facebook, null);
			holder = new ViewHolder();
			holder.ivFacebook = (ImageView) convertView
					.findViewById(R.id.iv_row_news);
			holder.tvTitle = (TextView) convertView
					.findViewById(R.id.tv_title_news);
			holder.tvTime = (TextView) convertView
					.findViewById(R.id.tv_time_news);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		if (holder != null) {
			holder.tvTitle.setText(Html.fromHtml((String) item.getTitle())
					.toString());

			holder.tvTime.setText(item.getTime());

			mAQuery.id(holder.ivFacebook).image(url, false,
					true, 200, 0, null, AQuery.FADE_IN);
		}
		// ImageView iv_row_news = (ImageView) convertView
		// .findViewById(R.id.iv_row_face);
		// TextView tv_title_news = (TextView) convertView
		// .findViewById(R.id.tv_title_facebook);
		// TextView tv_time_news = (TextView) convertView
		// .findViewById(R.id.tv_time_facebook);
		// // Html.fromHtml((String) htmlCode).toString();
		//
		// tv_title_news.setText(Html.fromHtml((String) item.getTitle())
		// .toString());
		// // tv_title_news.setText(TextUtils.htmlEncode(item.gettitle()));
		//
		// tv_time_news.setText(item.getTime());
		//
		// String url =
		// "https://fbcdn-profile-a.akamaihd.net/hprofile-ak-xpa1/v/t1.0-1/c17.17.212.212/s200x200/66722_10151361136486709_544848419_n.jpg?oh=6440632bc31d7dd36db42f9e7c6deba4&oe=54DB88F2&__gda__=1423972273_ed85c55bfb977c6a8aa4ced27d764cac";
		//
		// imageLoader.DisplayImage(url, iv_row_news);

		return convertView;

	}

	class ViewHolder {
		ImageView ivFacebook;
		TextView tvTitle;
		TextView tvTime;
	}
}
