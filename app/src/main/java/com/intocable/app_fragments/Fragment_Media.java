package com.intocable.app_fragments;

import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.androidquery.AQuery;
import com.intocable.MainHome_Activity;
import com.intocable.R;
import com.intocable.SimpleImageActivity;
import com.intocable.app_fragments.Youtube_video_Activity;
import com.intocable.controller.ImagePagerFragment;
import com.intocable.utils.AppFragment;
import com.intocable.utils.CommonMethods;
import com.intocable.utils.CommonVariables;
import com.intocable.utils.ConfigApp;
import com.intocable.utils.SetupApp;
import com.intocable.ws.GetXmlWeb;
import com.intocable.ws.OnResultAsyncTask;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

public class Fragment_Media extends AppFragment implements OnResultAsyncTask {
	private static int tab_select_media = 0;

	private View view_photo_tab, view_video_tab;
	// private ProgressBar progress;
	private GridView gr_media;

	public static GetXmlWeb xmlGetter = null;

	private Configuration config;

	private int dimen20, dimen10;

	// private ArrayList<String> mThumbImageArr;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		SetupApp.setVisibilityHeader(View.VISIBLE, getActivity());
		View view = inflater.inflate(R.layout.fragment_media, null);

		config = getResources().getConfiguration();
		dimen20 = (int) getResources().getDimension(R.dimen.layout_20);
		dimen10 = (int) getResources().getDimension(R.dimen.layout_30);
		initmain(view);
		initData();
		return view;
	}

	private void initmain(View view) {
		SetupApp.setHeader(getString(R.string.s_media), getActivity());
		view_photo_tab = (View) view.findViewById(R.id.view_photo_tab);
		view_video_tab = (View) view.findViewById(R.id.view_video_tab);

		gr_media = (GridView) view.findViewById(R.id.gr_media);

		gr_media.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				if (tab_select_media == 0) {
					Intent intent = new Intent(getActivity(),
							SimpleImageActivity.class);
					intent.putExtra(SimpleImageActivity.FRAGMENT_INDEX,
							ImagePagerFragment.INDEX);
					intent.putExtra(SimpleImageActivity.IMAGE_POSITION, arg2);
					startActivity(intent);
				} else {
					Youtube_video_Activity.url_play = CommonVariables.videos
							.get(arg2).getPlayURL();
					Youtube_video_Activity.name_video = CommonVariables.videos
							.get(arg2).getName();
					SetupApp.gotoActivity(getActivity(),
							Youtube_video_Activity.class, false, false);
					// MainHome_Activity.m_FragmentActivity.set_Fragment(
					// new Fragment_VideoView(), R.id.content_frame,true);
					// MainHome_Activity.m_FragmentActivity.set

				}
			}
		});
		selectTab();

	}

	// Load du lieu phai de trong nay
	private void initData() {
		// TODO Auto-generated method stub

		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				getActivity()).threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.diskCacheFileNameGenerator(new Md5FileNameGenerator())
				.diskCacheSize(50 * 1024 * 1024)
				// 50 Mb
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.writeDebugLogs() // Remove for release app
				.build();
		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config);

	}

	private void load_from_server() {
		MainHome_Activity.showLoading();
		gr_media.removeAllViewsInLayout();
		if (xmlGetter != null && !xmlGetter.isCancelled())
			xmlGetter.cancel(true);

		if (tab_select_media == 0) {
			xmlGetter = new GetXmlWeb(this, GetXmlWeb.BACKGROUND,
					GetXmlWeb.TYPE_PHOTO);

		} else {
			xmlGetter = new GetXmlWeb(this, GetXmlWeb.BACKGROUND,
					GetXmlWeb.TYPE_VIDEO);
		}
		xmlGetter.execute();
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		int id = v.getId();
		switch (id) {
		case R.id.ll_photo:
			tab_select_media = 0;
			selectTab();
			break;
		case R.id.ll_video:
			tab_select_media = 1;
			selectTab();
			break;

		default:
			break;
		}
	}

	private void selectTab() {
		if (tab_select_media == 0) {
			view_photo_tab.setBackgroundResource(R.color.blue);
			view_video_tab.setBackgroundResource(R.color.silver);
			if (ConfigApp.imageUrl == null || ConfigApp.imageUrl.size() == 0) {
				load_from_server();
			} else {
				update_Gridview();
			}

		} else {
			view_photo_tab.setBackgroundResource(R.color.silver);
			view_video_tab.setBackgroundResource(R.color.blue);
			if (CommonVariables.videos == null
					|| CommonVariables.videos.size() == 0) {
				load_from_server();
			} else {
				update_Gridview();
			}
		}

	}

	GridViewAdapter adapter = null;

	private void update_Gridview() {
		if (adapter == null && getActivity() != null) {
			GridViewAdapter adapter = new GridViewAdapter(getActivity(),
					tab_select_media);
			gr_media.setAdapter(adapter);
		} else {

			GridViewAdapter adapter = (GridViewAdapter) gr_media.getAdapter();
			if (adapter == null)
				return;
			adapter.setType(tab_select_media);
			adapter.notifyDataSetChanged();
		}
	}

	@Override
	public void showResult(JSONObject result) {

	}

	@Override
	public void showResult(String xml) {
		MainHome_Activity.hideLoading();
		if (xml != null && !xml.isEmpty()) {

			// parser photo
			if (xmlGetter.getType() == GetXmlWeb.TYPE_PHOTO) {
				CommonMethods.getPhotos(xml);

			}
			// parser video
			else if (xmlGetter.getType() == GetXmlWeb.TYPE_VIDEO) {
				CommonMethods.getVideos(xml);
			}
			update_Gridview();
		}
	}

	private void setHeightGridview() {
		if (config.orientation == Configuration.ORIENTATION_PORTRAIT) {
			SetupApp.setGridViewHeightBasedOnChildren(gr_media, 3, dimen20);
		} else if (config.orientation == Configuration.ORIENTATION_LANDSCAPE) {
			SetupApp.setGridViewHeightBasedOnChildren(gr_media, 6, dimen10);
		}
	}

	@Override
	public void finish() {
		// TODO Auto-generated method stub
		super.finish();
	}

	public class GridViewAdapter extends BaseAdapter {

		private LayoutInflater mInflater;
		private AQuery mAQuery;
		private int type = 0;

		public GridViewAdapter(Context context, int type) {
			this.type = type;
			mInflater = LayoutInflater.from(context);
			mAQuery = new AQuery(context);
		}

		public void setType(int type) {
			this.type = type;
		}

		public int getCount() {
			if ((type == 0) && (ConfigApp.imageUrl != null))
				return ConfigApp.imageUrl.size();
			else if ((type == 1) && (CommonVariables.videos != null))
				return CommonVariables.videos.size();
			return 0;
		}

		public Object getItem(int position) {
			return position;
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(final int position, View convertView,
				ViewGroup parent) {
			convertView = null;
			ViewHolder holder = new ViewHolder();
			if (type == 0) {

				convertView = mInflater.inflate(
						R.layout.item_gridview_photosdetail_fragment, null);
				holder.imageview = (ImageView) convertView
						.findViewById(R.id.iv_photo_detail);
				mAQuery.id(holder.imageview).image(
						ConfigApp.imageUrl.get(position), true, true, 200, 0,
						null, AQuery.FADE_IN);

				holder.imageview.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(getActivity(),
								SimpleImageActivity.class);
						intent.putExtra(SimpleImageActivity.FRAGMENT_INDEX,
								ImagePagerFragment.INDEX);
						intent.putExtra(SimpleImageActivity.IMAGE_POSITION,
								position);
						startActivity(intent);
					}
				});

			} else if (type == 1) {
				convertView = mInflater
						.inflate(R.layout.row_videos_media, null);

				holder.imageview = (ImageView) convertView
						.findViewById(R.id.iv_media_videos);
				convertView.setTag(holder);
				mAQuery.id(holder.imageview)
						.image(CommonVariables.videos.get(position)
								.getBackgroundURL(), true, true, 200, 0, null,
								AQuery.FADE_IN);
			}
			return convertView;
		}
	}

	class ViewHolder {
		ImageView imageview;
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
			SetupApp.setGridViewHeightBasedOnChildren(gr_media, 3, dimen20);
		} else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
			SetupApp.setGridViewHeightBasedOnChildren(gr_media, 6, dimen10);
		}
	}

}
