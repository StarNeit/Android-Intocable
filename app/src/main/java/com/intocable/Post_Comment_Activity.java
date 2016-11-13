package com.intocable;

import org.json.JSONObject;

import android.os.Bundle;

import android.view.View;
import android.widget.EditText;

import com.androidquery.AQuery;
import com.intocable.utils.BaseActivity;
import com.intocable.utils.SetupApp;
import com.intocable.ws.GetXmlWeb;
import com.intocable.ws.OnResultAsyncTask;

public class Post_Comment_Activity extends BaseActivity implements
		OnResultAsyncTask {

	private EditText edtName, edtComment;

	private String eventId;

	private GetXmlWeb post = null;

	private AQuery aQuery = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_post_comment);
		SetupApp.setHeader(getString(R.string.s_post_cmt), this);
		aQuery = new AQuery(this);
		initmain();
		initData();

	}

	public void showLoading() {
		aQuery.id(R.id.rl_home_loading).visibility(View.VISIBLE);
	}

	public void hideLoading() {
		aQuery.id(R.id.rl_home_loading).visibility(View.INVISIBLE);
	}

	private void initmain() {
		// TODO Auto-generated method stub
		hideLoading();
		edtName = (EditText) findViewById(R.id.ed_name);
		edtComment = (EditText) findViewById(R.id.ed_post_cmt);
	}

	private void initData() {
		// TODO Auto-generated method stub
		eventId = getIntent().getExtras().getString("EVENT_ID");
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		int id = v.getId();
		switch (id) {
		case R.id.btn_post_cmt_2:
			String name = edtName.getText().toString();

			if (name.isEmpty()) {
				SetupApp.showMessageOk(this, getString(R.string.error),
						getString(R.string.error_empty_name));
				break;
			}

			String comment = edtComment.getText().toString();
			if (comment.isEmpty()) {
				SetupApp.showMessageOk(this, getString(R.string.error),
						getString(R.string.error_empty_comment));
				break;
			}

			SetupApp.hiddenKeyboard(this);
			postComment(name, comment);
			break;
		default:
			break;
		}
	}

	private void postComment(String name, String comment) {
		if (post == null || post.isCancelled()) {
			showLoading();
			post = new GetXmlWeb(this, GetXmlWeb.FOREGROUND,
					GetXmlWeb.POST_COMMENT);
			post.eventId = eventId;
			post.name = name;
			post.comment = comment;
			post.execute();
		}
	}

	@Override
	protected void onDestroy() {
		if (post != null && !post.isCancelled())
			post.cancel(true);
		super.onDestroy();
	}

	@Override
	public void showResult(JSONObject result) {
		// TODO Auto-generated method stub

	}

	@Override
	public void showResult(String xml) {
		// TODO Auto-generated method stub
		if (xml != null && !xml.isEmpty() && xml.equals("200"))
			onBackPressed();
		hideLoading();
	}
}
