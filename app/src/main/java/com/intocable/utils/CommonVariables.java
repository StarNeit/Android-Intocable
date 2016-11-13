package com.intocable.utils;

import java.util.ArrayList;

import com.intocable.object.Bio;
import com.intocable.object.Comments;
import com.intocable.object.Merchandise;
import com.intocable.object.Music;
import com.intocable.object.Photos;
import com.intocable.object.Videos;

public class CommonVariables {

	public static final String URL_MUSIC = "url_music";

	public static final String BROADCAST_ACTION = "com.intocable.BROADCAST_ACTION";

	public static final String ACTION_SERVICE = "action";

	public static final String ACTION_LOAD_MUSIC = "loadMusic";

	public static final String ACTION_PLAY_MUSIC = "playMusic";

	public static final String ACTION_FINISH_MUSIC = "finishMusic";

	public static Bio bio = null;

	public static ArrayList<Music> musics = new ArrayList<Music>();

	public static ArrayList<Merchandise> merchandises = new ArrayList<Merchandise>();

	public static ArrayList<Videos> videos = new ArrayList<Videos>();

	public static ArrayList<Photos> photos = new ArrayList<Photos>();
	
	public static ArrayList<Comments> comments = new ArrayList<Comments>();

	public static String about = "";
	
	public static String author = "";
	
	public static String comment = "";
	
	public static void resetData()
	{
		musics.clear();
		merchandises.clear();
		videos.clear();
		photos.clear();
	}
	
}
