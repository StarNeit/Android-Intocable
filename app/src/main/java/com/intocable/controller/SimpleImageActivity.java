/*******************************************************************************
 * Copyright 2014 Sergey Tarasevich
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

import com.intocable.utils.ConfigApp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.Window;

/**
 * @author Sergey Tarasevich (nostra13[at]gmail[dot]com)
 */
public class SimpleImageActivity extends FragmentActivity {

	public static final String IMAGE_URLS = "image_urls";
	public static final String IMAGE_POSITION = "image_position";
	public static final String FRAGMENT_INDEX = "fragment_index";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		int frIndex = getIntent().getIntExtra(FRAGMENT_INDEX, 0);
		ArrayList<String>imageUrls = ConfigApp.imageUrl;
		Fragment fr;
		String tag;
		switch (frIndex) {
		default:

		case ImagePagerFragment.INDEX:
			tag = ImagePagerFragment.class.getSimpleName();
			fr = getSupportFragmentManager().findFragmentByTag(tag);
			if (fr == null) {
				fr = ImagePagerFragment.newInstance(getIntent().getExtras()
						.getInt(IMAGE_POSITION), imageUrls);
			}
			break;
		}

		getSupportFragmentManager().beginTransaction()
				.replace(android.R.id.content, fr, tag).commit();
	}
}