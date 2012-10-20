package com.android.hackregina.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.hackregina.R;
import com.android.hackregina.models.LeaderPerson;
import com.android.hackregina.utils.Logger;

public class LeaderBoardAdapter extends ArrayAdapter<LeaderPerson> {

	private static final String TAG = "### LeaderBoardAdapter";
	private Context context;
	private ArrayList<LeaderPerson> people;

	public LeaderBoardAdapter(Context context, int textViewResourceId, ArrayList<LeaderPerson> objects) {
		super(context, textViewResourceId, objects);
		Logger.log(TAG, "Creating adapter");
		this.people = objects;
		this.context = context;
	}

	@Override
	public int getCount() {
		return this.people.size();
	}

	@Override
	public LeaderPerson getItem(int position) {
		return this.people.get(position);
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.leaderboard_item, null, true);
		}

		LeaderPerson currentPerson = this.people.get(position);

		// name
		TextView name = (TextView) convertView.findViewById(R.id.leaderboardItem_name);
		String stringName = (currentPerson.getUserName() == null) ? currentPerson.getUserId() : currentPerson.getUserName();
		name.setText(stringName);

		// points
		TextView points = (TextView) convertView.findViewById(R.id.leaderboardItem_points);
		points.setText("Points " + currentPerson.getPoints().toString());

		// total checkins
		TextView checkins = (TextView) convertView.findViewById(R.id.leaderboardItem_checkins);
		checkins.setText("Total checkins " + currentPerson.getTotalCheckins().toString());

		return convertView;
	}
}
