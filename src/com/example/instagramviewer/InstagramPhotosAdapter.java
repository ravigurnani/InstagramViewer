package com.example.instagramviewer;

import java.util.List;

import com.squareup.picasso.Picasso;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class InstagramPhotosAdapter extends ArrayAdapter<InstagramPhoto> {

	public InstagramPhotosAdapter(Context context, List<InstagramPhoto> photos) {
		super(context, R.layout.item_photo, photos);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		InstagramPhoto photo = getItem(position);
		
		//check recycling
		if (convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_photo, parent, false);
		}
		TextView tvCaption = (TextView) convertView.findViewById(R.id.tvCaption);
		TextView tvUsername = (TextView) convertView.findViewById(R.id.tvUsername);
		ImageView imgPhoto = (ImageView) convertView.findViewById(R.id.imgPhoto) ;
		TextView tvLikeCnt = (TextView) convertView.findViewById(R.id.tvLikes);
		ImageView imgProfPic = (ImageView) convertView.findViewById(R.id.ivProfilePic);
		
		//Custom fonts
		Typeface font = Typeface.createFromAsset(getContext().getAssets(), "fonts/Chantelli_Antiqua.ttf");
		
		
		tvCaption.setText(photo.caption +"\n");
		tvCaption.setTypeface(font);
		tvUsername.setText(photo.username);
		tvLikeCnt.setText(Integer.toString(photo.likes_count));
		imgPhoto.getLayoutParams().height = photo.imageHeight;
		//likesImg.setVisibility(1);
		imgPhoto.setImageResource(0);
		
		//likesImg.setActivated(true);
				
		Picasso.with(getContext()).load(photo.imageUrl).into(imgPhoto);
		Picasso.with(getContext()).load(photo.profilePicUrl).into(imgProfPic);

		
		return convertView;
	}

}
