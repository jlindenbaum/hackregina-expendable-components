package com.android.hackregina;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.hackregina.models.Listing;

public class YellowListingAdapter extends ArrayAdapter<Listing> {

	private List<Listing> merchants;
	private Context context;

	public YellowListingAdapter(Context context, int textViewResourceId, List<Listing> items) {
		super(context, textViewResourceId, items);
		this.merchants = items;
		this.context = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		
		// Make sure we have a row model to fill
		if (v == null) {
			LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.listing_item, null);
		}

		Listing item = merchants.get(position);
		TextView name = (TextView) v.findViewById(R.id.businessName);
		TextView address = (TextView) v.findViewById(R.id.businessAddress);
		
		// Fill the row model with merchant informations
		name.setText(item.getName());
		address.setText(item.getAddress().toString());
		
		return v;
	}
}
