package com.intocable.adapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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
import com.intocable.object.ChildSection;
import com.intocable.object.News;

public class News_Adapter extends BaseAdapter {

	// private Activity activity;
	private ArrayList<Object> arrayList;
	private LayoutInflater inflater;
	private AQuery mAQuery;

	public News_Adapter(Activity activity, ArrayList<Object> arrayList) {
		// this.activity = activity;

		mAQuery = new AQuery(activity);
		this.arrayList = arrayList;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		sortList();
	}

	public void setData(ArrayList<Object> arrayList) {
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

	public void sortList() {
		// sort alphabetical
		Collections.sort(arrayList, new Comparator<Object>() {
			@Override
			public int compare(Object left, Object right) {
				// TODO Auto-generated method stub
				if (left instanceof News && right instanceof News) {
					return ((News) left).getDatePosted().compareTo(
							((News) right).getDatePosted())
							* -1;
				} else
					return 0;
			}
		});

		// add first section
		String prefix = "";
		for (int i = 0; i < arrayList.size(); i++) {
			if (arrayList.get(i) instanceof News) {
				prefix = ((News) arrayList.get(i)).getPosted();
				arrayList.add(i, new ChildSection(prefix));
				break;
			} else if (arrayList.get(i) instanceof ChildSection) {
				prefix = ((ChildSection) arrayList.get(i)).getTitle();
				break;
			}
		}

		// add section
		for (int i = 1; i < arrayList.size(); i++) {
			if (arrayList.get(i) instanceof News) {
				News item = (News) arrayList.get(i);
				if (item.getPosted().equals(prefix))
					continue;
				prefix = item.getPosted();
				arrayList.add(i, new ChildSection(prefix));
			} else if (arrayList.get(i) instanceof ChildSection) {
				prefix = ((ChildSection) arrayList.get(i)).getTitle();
				continue;
			}
		}
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Object obj = arrayList.get(position);
		if (obj instanceof ChildSection) {
			ChildSection section = (ChildSection) obj;
			convertView = inflater.inflate(R.layout.row_sub_events, null);
			TextView tv_sub_events = (TextView) convertView
					.findViewById(R.id.tv_sub_events);
			tv_sub_events.setText(section.getTitle());
		} else if (obj instanceof News) {
			final News item = (News) obj;
			ViewHolder holder;
			if (arrayList.get(position - 1) instanceof ChildSection)
				convertView = null;

			if (convertView == null || convertView.getTag() == null) {
				convertView = inflater.inflate(R.layout.row_news, null);
				holder = new ViewHolder();
				holder.ivNews = (ImageView) convertView
						.findViewById(R.id.iv_row_news);
				holder.tvTitle = (TextView) convertView
						.findViewById(R.id.tv_title_news);
				holder.tvBody = (TextView) convertView
						.findViewById(R.id.tv_body_news);
				holder.tvTime = (TextView) convertView
						.findViewById(R.id.tv_time_news);
				convertView.setTag(holder);
			} else {
				if (convertView.getTag() instanceof ViewHolder)
					holder = (ViewHolder) convertView.getTag();
				else {
					holder = new ViewHolder();
					holder.ivNews = (ImageView) convertView
							.findViewById(R.id.iv_row_news);
					holder.tvTitle = (TextView) convertView
							.findViewById(R.id.tv_title_news);
					holder.tvBody = (TextView) convertView
							.findViewById(R.id.tv_body_news);
					holder.tvTime = (TextView) convertView
							.findViewById(R.id.tv_time_news);
					convertView.setTag(holder);
				}

			}

			if (holder != null) {
				holder.tvTitle.setText(item.getTitle());
				holder.tvBody.setText(Html.fromHtml(item.getBody()));
				holder.tvTime.setText(item.getPosted());

				String url = item.getSmallImage();
				mAQuery.id(holder.ivNews).image(url, false, true, 200, 0, null,
						AQuery.FADE_IN);
			}
		}
		// ImageView iv_row_news = (ImageView) convertView
		// .findViewById(R.id.iv_row_news);
		// TextView tv_title_news = (TextView) convertView
		// .findViewById(R.id.tv_title_news);
		// TextView tv_body_news = (TextView) convertView
		// .findViewById(R.id.tv_body_news);
		// TextView tv_time_news = (TextView) convertView
		// .findViewById(R.id.tv_time_news);

		// tv_title_news.setText(item.getTitle());
		// tv_body_news.setText(Html.fromHtml(item.getBody()));
		// tv_time_news.setText(item.getPosted());
		//
		// String url = item.getSmallImage();
		// imageLoader.DisplayImage(url, iv_row_news);

		return convertView;

	}

	@Override
	public void notifyDataSetChanged() {
		// TODO Auto-generated method stub
		sortList();
		super.notifyDataSetChanged();
	}

	class ViewHolder {
		TextView tvTitle;
		TextView tvBody;
		TextView tvTime;
		ImageView ivNews;
	}
}
