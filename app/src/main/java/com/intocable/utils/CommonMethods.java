package com.intocable.utils;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.xmlpull.v1.XmlPullParserException;

import android.util.Log;

import com.intocable.app_fragments.Fragment_Merchandise;
import com.intocable.app_fragments.Fragment_Music;
import com.intocable.object.Bio;
import com.intocable.object.ChildSection;
import com.intocable.object.Comments;
import com.intocable.object.Merchandise;
import com.intocable.object.Music;
import com.intocable.object.Photos;
import com.intocable.object.TimeObject;
import com.intocable.object.Videos;

public class CommonMethods {

	public static void addTimeObject(ArrayList<Object> list, TimeObject object,
			SimpleDateFormat format) {

		for (int i = 0; i < list.size(); i++) {
			Object obj = list.get(i);

			if ((obj instanceof ChildSection) && format != null) {
				try {
					ChildSection section = (ChildSection) obj;
					Date sectionDate = format.parse(section.getTitle());
					sectionDate = SetupApp.getStartDate(sectionDate);

					if (sectionDate.compareTo(object.getDate()) * -1 > 0) {
						list.add(i, object);
						return;
					} else
						continue;
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					continue;
				}
			} else if (obj instanceof TimeObject) {

				TimeObject to = (TimeObject) obj;
				if (to.getDate().compareTo(object.getDate()) * -1 > 0) {
					list.add(i, object);
					return;
				} else
					continue;
			}

		}

		list.add(object);
	}

	/**
	 * Get Bio
	 * 
	 * @param url
	 *            link webservice
	 * @param xml
	 *            xml file
	 * @return true if change bio, false if not change
	 */
	public static final boolean getBio(String url, String xml) {
		if (url == null || url.isEmpty() || xml == null || xml.isEmpty())
			return false;

		String TAG = "GET BIO";

		try {
			// tao helper, truyen cai xml
			XMLParserHelper parser = new XMLParserHelper(xml, null);

			// get theo thu tu tu tren xuong duoi
			String urlImage = parser.getAttributeOfTag("Image", "URL");
			String bio = parser.getCDATAOfTag("Bio");

			if (CommonVariables.bio != null) {
				// same information
				if (CommonVariables.bio.getUrlImage().equals(urlImage)
						&& CommonVariables.bio.getBio().equals(bio))
					return false;

				CommonVariables.bio = new Bio(url, urlImage, bio);
			} else
				CommonVariables.bio = new Bio(url, urlImage, bio);

			return true;
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Get list musics from xml
	 * 
	 * @param url
	 * @param xml
	 */
	public static final void getMusic(String url, String xml) {
		if (url == null || url.isEmpty() || xml == null || xml.isEmpty())
			return;

		String TAG = "GET MUSIC";

		try {
			XMLParserHelper parser = new XMLParserHelper(xml, null);
			// get banner
			Fragment_Music.linkBanner = parser.getAttributeOfTag("Page",
					"Background");

			// get list song
			while (!parser.isEndDocument()) {
				String id = parser.getAttributeOfTag("Song", "ID");
				String canBuy = parser.getAttributeOfTag("Song", "CanBuy");
				String canPlay = parser.getAttributeOfTag("Song", "CanPlay");
				String buy = parser.getAttributeOfTag("Song", "Buy");
				String play = parser.getAttributeOfTag("Song", "Play");
				String artist = parser.getCDATAOfTag("Artist");
				String title = parser.getCDATAOfTag("Title");

				if (!id.isEmpty() && !canBuy.isEmpty() && !canPlay.isEmpty()
						&& !buy.isEmpty() && !play.isEmpty()
						&& !artist.isEmpty() && !title.isEmpty()) {
					Music music = new Music(url, id, canBuy, canPlay, buy,
							play, artist, title);

					if (!CommonVariables.musics.contains(music))
						CommonVariables.musics.add(music);
				}
				parser.nextToken();
			}
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Get list products from xml
	 * 
	 * @param url
	 * @param xml
	 */
	public static final void getMerchanise(String url, String xml) {
		if (url == null || url.isEmpty() || xml == null || xml.isEmpty())
			return;

		String TAG = "GET MERCHANDISE";

		try {
			CommonVariables.merchandises.clear();
			XMLParserHelper parser = new XMLParserHelper(xml, null);
			// get banner
			Fragment_Merchandise.linkBanner = parser.getAttributeOfTag("Page",
					"Background");

			// get list merchandise
			while (!parser.isEndDocument()) {
				String id = parser.getAttributeOfTag("Product", "ID");
				String title = parser.getAttributeOfTag("Product", "Title");
				String urlImage = parser.getAttributeOfTag("Product",
						"SmallImageURL");

				if (!id.isEmpty() && !title.isEmpty() && !urlImage.isEmpty()) {
					Merchandise merchandise = new Merchandise(url, title, id,
							urlImage);

					if (!CommonVariables.merchandises.contains(merchandise))
						CommonVariables.merchandises.add(merchandise);
				}
				parser.nextToken();
			}
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Get list photos from xml
	 * 
	 * @param xml
	 */
	public static final void getPhotos(String xml) {
		if (xml == null || xml.isEmpty())
			return;

		String TAG = "GET PHOTOS";

		try {
			XMLParserHelper parser = new XMLParserHelper(xml, null);

			// get list photos
			while (!parser.isEndDocument()) {
				String id = parser.getAttributeOfTag("Photo", "ID");
				String title = parser.getAttributeOfTag("Photo", "Title");
				String urlImage = parser.getAttributeOfTag("Photo", "URL");

				if (!id.isEmpty() && !title.isEmpty() && !urlImage.isEmpty()) {
					ConfigApp.imageUrl.add(urlImage);
				}
				parser.nextToken();
			}
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Get list Video from xml
	 * 
	 * @param xml
	 */
	public static final void getVideos(String xml) {
		if (xml == null || xml.isEmpty())
			return;

		String TAG = "GET VIDEOS";

		try {
			XMLParserHelper parser = new XMLParserHelper(xml, null);

			// get list photos
			while (!parser.isEndDocument()) {
				String id = parser.getAttributeOfTag("Video", "ID");
				String name = parser.getAttributeOfTag("Video", "Name");
				String playUrl = parser.getAttributeOfTag("Video", "PlayURL");
				String backgroundUrl = parser.getAttributeOfTag("Video",
						"BackgroundURL");

				if (!id.isEmpty() && !name.isEmpty() && !playUrl.isEmpty()
						&& !backgroundUrl.isEmpty()) {
					Videos video = new Videos(id, name, playUrl, backgroundUrl);

					// if (!CommonVariables.videos.contains(video))
					CommonVariables.videos.add(video);
				}
				parser.nextToken();
			}
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static final void getAbout(String xml) {
		if (xml == null || xml.isEmpty())
			return;

		String TAG = "GET ABOUT";

		try {
			XMLParserHelper parser = new XMLParserHelper(xml, null);
			CommonVariables.about = parser.getCDATAOfTag("About");
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
	}

	public static final void getComment(String xml) {
		if (xml == null || xml.isEmpty())
			return;

		String TAG = "GET COMMENT";
		// author truoc comment, lay author truoc
		// get list commets
		CommonVariables.comments.clear();
		try {
			XMLParserHelper parser = new XMLParserHelper(xml, null);
			while (!parser.isEndDocument()) {
				String author = parser.getAttributeOfTag("Comment", "Author");
				String comments = parser.getCDATAOfTag("Comment");

				if (!author.isEmpty() && !comments.isEmpty()) {
					Comments comment = new Comments(author, comments);

					// if (!CommonVariables.videos.contains(video))
					CommonVariables.comments.add(comment);
				}
				parser.nextToken();

				
			}
			Log.i(TAG, CommonVariables.comments.size() + "   ");
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
	}
}
