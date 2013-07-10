package com.example.com.learnncode.sample;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.learnNcode.android.Clock;
import com.learnNcode.android.ExtendedListView;
import com.learnNcode.android.ExtendedListView.OnPositionChangedListener;

public class MainActivity  extends Activity implements OnPositionChangedListener {

	private ExtendedListView mListView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(com.example.com.learnncode.sample.R.layout.activity_main);

		mListView = (ExtendedListView) findViewById(android.R.id.list);
		mListView.setAdapter(new DummyAdapter());
		mListView.setCacheColorHint(Color.TRANSPARENT);
		mListView.setOnPositionChangedListener(this);
	
//		mListView.setScrollBarPanelInAnimationDuration(500);
//		mListView.setScrollBarPanelOutAnimationDuration(8000);

	}

	private class DummyAdapter extends BaseAdapter {

		private int mNumDummies = 100;

		@Override
		public int getCount() {
			return mNumDummies;
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = LayoutInflater.from(MainActivity.this).inflate(R.layout.list_item, parent,
						false);
			}
			return convertView;
		}
	}

	@Override
	public void onPositionChanged(ExtendedListView listView, int position, View scrollBarPanel) {
		Clock analogClockObj = (Clock) scrollBarPanel.findViewById(R.id.analogClockScroller);
		
		TextView tv = (TextView) scrollBarPanel.findViewById(R.id.timeTextView);
		tv.setText(""+position);
		
		Time timeObj = new Time();
		analogClockObj.setSecondHandVisibility(false);
		analogClockObj.setVisibility(View.VISIBLE);
		timeObj.set(position+3, position, 5, 0, 0, 0);
		analogClockObj.onTimeChanged(timeObj);
		
	}
}
