package com.android.hackregina.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.hackregina.R;
import com.android.hackregina.models.YelpListing;
import com.android.hackregina.utils.Logger;

public class YelpListingAdapter extends ArrayAdapter<YelpListing> {

	private static final String TAG = "### LeaderBoardAdapter";
	private Context context;
	private ArrayList<YelpListing> listings;

	public YelpListingAdapter(Context context, int textViewResourceId, ArrayList<YelpListing> objects) {
		super(context, textViewResourceId, objects);
		Logger.log(TAG, "Creating adapter");
		this.listings = objects;
		this.context = context;
	}

	@Override
	public int getCount() {
		return this.listings.size();
	}

	@Override
	public YelpListing getItem(int position) {
		return this.listings.get(position);
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.yelp_item, null, true);
		}

		YelpListing currentListing = this.listings.get(position);
		// name
		TextView name = (TextView) convertView.findViewById(R.id.yelp_name);
		name.setText(currentListing.getBusinessName());

		// distance
		TextView distance = (TextView) convertView.findViewById(R.id.yelp_distance);
		distance.setText(currentListing.getDistance(true));

		return convertView;
	}
}
