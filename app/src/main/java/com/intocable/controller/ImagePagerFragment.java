/*******************************************************************************
 * Copyright 2011-2014 Sergey Tarasevich
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.intocable.controller;

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.intocable.R;
import com.intocable.SimpleImageActivity;
import com.intocable.utils.ConfigApp;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

/**
 * @author Sergey Tarasevich (nostra13[at]gmail[dot]com)
 */
public class ImagePagerFragment extends Fragment {

	public static final int INDEX = 2;

	ArrayList<String> imageUrls = ConfigApp.imageUrl;

	DisplayImageOptions options;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		options = new DisplayImageOptions.Builder()
				.showImageForEmptyUri(R.drawable.ic_empty)
				.showImageOnFail(R.drawable.ic_error)
				.resetViewBeforeLoading(true).cacheOnDisk(true)
				.imageScaleType(ImageScaleType.EXACTLY)
				.bitmapConfig(Bitmap.Config.RGB_565).considerExifParams(true)
				.displayer(new FadeInBitmapDisplayer(300)).build();
	}

	public static ImagePagerFragment newInstance(int position, ArrayList<String>imageUrl) {
		final ImagePagerFragment f = new ImagePagerFragment();

		final Bundle args = new Bundle();
		args.putStringArrayList(SimpleImageActivity.IMAGE_URLS, imageUrl);
		args.putInt(SimpleImageActivity.IMAGE_POSITION, position);
		f.setArguments(args);

		return f;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fr_image_pager, container,
				false);
		ViewPager pager = (ViewPager) rootView.findViewById(R.id.pager);
		pager.setAdapter(new ImageAdapter(getChildFragmentManager(),
				imageUrls.size()));
		pager.setCurrentItem(getArguments().getInt(
				SimpleImageActivity.IMAGE_POSITION, 0));

		

		return rootView;
	}

	private class ImageAdapter extends FragmentStatePagerAdapter {
		private final int mSize;

		public ImageAdapter(FragmentManager fm, int size) {
			super(fm);
			mSize = size;
		}

		@Override
		public int getCount() {
			return mSize;
		}

		@Override
		public Fragment getItem(int position) {
			return ImageDetailFragment.newInstance(imageUrls.get(position));
		}

	}

}