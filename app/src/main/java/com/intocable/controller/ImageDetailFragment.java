/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.intocable.controller;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.intocable.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;


/**
 * Class to customize image
 * 
 * @author DungVHP
 * 
 */
public class ImageDetailFragment extends BaseFragment_Image {
	private static final String IMAGE_DATA_EXTRA = "extra_image_data";
	private String mImageUrl;
	private TouchImageView mImageView;
	ProgressBar spinner;
	DisplayImageOptions options;

	/**
	 * Factory method to generate a new instance of the fragment given an image
	 * number.
	 * 
	 * @param imageUrl
	 *            The image url to load
	 * @return A new instance of ImageDetailFragment with imageNum extras
	 */
	public static ImageDetailFragment newInstance(String imageUrl) {
		final ImageDetailFragment f = new ImageDetailFragment();

		final Bundle args = new Bundle();
		args.putString(IMAGE_DATA_EXTRA, imageUrl);
		f.setArguments(args);

		return f;
	}

	/**
	 * Empty constructor as per the Fragment documentation
	 */
	public ImageDetailFragment() {
	}

	/**
	 * Populate image using a url from extras, use the convenience factory
	 * method {@link ImageDetailFragment#newInstance(String)} to create this
	 * fragment.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mImageUrl = getArguments() != null ? getArguments().getString(
				IMAGE_DATA_EXTRA) : null;
		options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.ic_stub)
				.showImageForEmptyUri(R.drawable.ic_empty)
				.showImageOnFail(R.drawable.ic_error).cacheInMemory(true)
				.cacheOnDisk(true).considerExifParams(true)
				.bitmapConfig(Bitmap.Config.RGB_565).build();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate and locate the main ImageView
		final View v = inflater.inflate(R.layout.image_detail_fragment,
				container, false);
		// mImageView = (ImageView) v.findViewById(R.id.imageView);
		mImageView = (TouchImageView) v.findViewById(R.id.imageView);
		spinner = (ProgressBar) v.findViewById(R.id.loading);
		return v;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		ImageLoader.getInstance().displayImage(mImageUrl, mImageView, options,
				new SimpleImageLoadingListener() {
					@Override
					public void onLoadingStarted(String imageUri, View view) {
						spinner.setVisibility(View.VISIBLE);
					}

					@Override
					public void onLoadingFailed(String imageUri, View view,
							FailReason failReason) {
						String message = null;
						switch (failReason.getType()) {
						case IO_ERROR:
							message = "Input/Output error";
							break;
						case DECODING_ERROR:
							message = "Image can't be decoded";
							break;
						case NETWORK_DENIED:
							message = "Downloads are denied";
							break;
						case OUT_OF_MEMORY:
							message = "Out Of Memory error";
							break;
						case UNKNOWN:
							message = "Unknown error";
							break;
						}

						spinner.setVisibility(View.GONE);
					}

					@Override
					public void onLoadingComplete(String imageUri, View view,
							Bitmap loadedImage) {
						spinner.setVisibility(View.GONE);
					}
				});

	}

	@Override
	public void onDestroy() {
		super.onDestroy();

	}

}
