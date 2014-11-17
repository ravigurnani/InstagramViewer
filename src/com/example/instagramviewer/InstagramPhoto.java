
package com.example.instagramviewer;

public class InstagramPhoto {

	public String username;
	public String caption;
	public String imageUrl;
	public int imageHeight;
	public int likes_count;
	public String profilePicUrl;
	
	public String toString(){
		
		return "image" + imageUrl;
	}
}
