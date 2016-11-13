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
import com.intocable.object.Comments;
import com.intocable.object.Facebook;
import com.intocable.utils.CommonVariables;

public class Comment_Adapter extends BaseAdapter {

	// private Activity activity;
	private ArrayList<Comments> arrayList;
	private LayoutInflater inflater;
	private AQuery mAQuery;

	public Comment_Adapter(Activity activity, ArrayList<Comments> arrayList) {
		// this.activity = activity;
		this.arrayList = arrayList;
		mAQuery = new AQuery(activity);
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public void setData(ArrayList<Comments> arrayList) {
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
		final Comments item = arrayList.get(position);
		ViewHolder holder;
		if (convertView == null || convertView.getTag() == null) {
			convertView = inflater.inflate(R.layout.row_comments, null);
			holder = new ViewHolder();
			holder.tvTitle = (TextView) convertView
					.findViewById(R.id.tv_Title_comment);
			holder.tvContent = (TextView) convertView
					.findViewById(R.id.tv_content_comment);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.tvTitle.setText(item.getAuthor());

		holder.tvContent.setText(item.getComments());

		return convertView;

	}

	class ViewHolder {
		TextView tvTitle;
		TextView tvContent;
	}
}
