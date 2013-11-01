 
 
/*
 * Copyright 2013 - learnNcode (learnncode@gmail.com)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */







package com.learnNcode.android;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.text.format.Time;
import android.util.AttributeSet;
import android.view.View;

/**
 * 
 * @author learn and Code
 *
 */
public class Clock extends View {

	public Clock(Context context) {
		super(context);
	}

	private Drawable mHourHand;
	private Drawable mMinuteHand;
	private Drawable mSecondHand;
	private Drawable mDial;

	private int mDialWidth;
	private int mDialHeight;

	private boolean mAttached;

	private float mMinutes;
	private float mHour;
	private float mSeconds;
	private boolean mChanged;

	Context mContext;
	boolean isSecondHandVisible = false;

	public Clock(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public Clock(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		
		mContext = context;
		Resources resource = mContext.getResources();
		final TypedArray a = mContext.obtainStyledAttributes(attrs, R.styleable.Clock);
		
		/*mDial = resource.getDrawable(R.drawable.ic_timer_clock_dialer);
		mHourHand = resource.getDrawable(R.drawable.ic_timer_clock_hour_hand);
		mMinuteHand = resource.getDrawable(R.drawable.ic_timer_clock_minute_hand);
		mSecondHand = resource.getDrawable(R.drawable.ic_timer_clock_minute_hand);*/
		final int scrollBarPanelLayoutId = a.getResourceId(R.styleable.Clock_scrollBarPanel, -1);
		
		System.out.println("***** scrollBarPanelLayoutId ***********"+scrollBarPanelLayoutId);
		/**
		 * dialer background
		 */
		int dialerRes = a.getResourceId(R.styleable.Clock_hand_dial, -1);
		if (dialerRes != -1) {
			setDialDrawable(dialerRes);
		}else{
			mDial = resource.getDrawable(R.drawable.ic_timer_clock_dialer);
		}
		
		/**
		 * hour hand background
		 */
		int hourRes = a.getResourceId(R.styleable.Clock_hand_hour, -1);
		if (hourRes != -1) {
			setHourDrawable(hourRes);
		}else{
			mHourHand = resource.getDrawable(R.drawable.ic_timer_clock_hour_hand);
		}
		
		/**
		 * minute hand background
		 */
		int minuteRes = a.getResourceId(R.styleable.Clock_hand_minute, -1);
		if (minuteRes != -1) {
			setMinuteDrawable(minuteRes);
		}else{
			mMinuteHand = resource.getDrawable(R.drawable.ic_timer_clock_minute_hand);
		}
		
		/**
		 * second hand background
		 */
		int secondRes = a.getResourceId(R.styleable.Clock_hand_second, -1);
		if (secondRes != -1) {
			System.out.println("+++++if +++++"+secondRes);
			setSecondDrawable(secondRes);
		}else{
			System.out.println("+++++ else +++++"+secondRes);
			mSecondHand = resource.getDrawable(R.drawable.ic_timer_clock_minute_hand);
		}

		mDialWidth = mDial.getIntrinsicWidth();
		mDialHeight = mDial.getIntrinsicHeight();
	}

	/**
	 * to set drawable for a clock.
	 *  
	 * @param dial
	 * 				sets this drawable to clock's dial.
	 * 
	 * @param hourHand
	 * 				sets this drawable to clock's hour hand.
	 * 
	 * @param minuteHand
	 *  				sets this drawable to clock's minute hand.
	 * 
	 * @param secondHand
	 *  				sets this drawable to clock's second hand.
	 *  
	 * @param isSeconeHandVisible
	 * 					if true enabled second hand.
	 */
	public void setImages(Drawable dial,Drawable hourHand,Drawable minuteHand,Drawable secondHand,boolean isSeconeHandVisible){
		mDial = dial;
		mHourHand = hourHand;
		mMinuteHand = minuteHand;
		mSecondHand = secondHand;
		
		isSecondHandVisible = isSeconeHandVisible;

	}

	/**
	 * To enabled second hand 
	 * 
	 * @param isSeconeHandVisible
	 *  			if true enabled second hand.
	 */
	public void setSecondHandVisibility(boolean isSeconeHandVisible) {
		isSecondHandVisible = isSeconeHandVisible;
	}

	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();

		if (!mAttached) {
			mAttached = true;
		}
	}

	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		if (mAttached) {
			mAttached = false;
		}
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		mChanged = true;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		boolean changed = mChanged;
		if (changed) {
			mChanged = false;
		}

		float density = mContext.getResources().getDisplayMetrics().density;

		float px = 20 * density;

		int availableWidth = (int) px;
		int availableHeight = (int) px;

		int x = availableWidth / 2;
		int y = availableHeight / 2;

		boolean scaled = false;

		if(availableWidth < mDialWidth || availableHeight < mDialHeight) {
			scaled = true;
			float scale = Math.min((float) availableWidth / (float) mDialWidth, (float) availableHeight / (float) mDialHeight);
			canvas.save();
			canvas.rotate(180, x, y);
			canvas.scale(scale, scale, x, y);
		}

		if(changed) {
			mDial.setBounds(x - (mDialWidth / 2), y - (mDialHeight / 2), x + (mDialWidth / 2), y + (mDialHeight / 2));
		}

		mDial.draw(canvas);

		canvas.save();
		canvas.rotate(mHour / 12.0f * 360.0f, x, y);
		final Drawable hourHand = mHourHand;
		if (changed) {
			int hourHandWidth = hourHand.getIntrinsicWidth();
			int hourHandHeight = hourHand.getIntrinsicHeight();
			hourHand.setBounds(x - (hourHandWidth / 2), y - (hourHandHeight / 2), x + (hourHandWidth / 2), y + (hourHandHeight / 2));
		}
		hourHand.draw(canvas);
		canvas.restore();

		canvas.save();
		canvas.rotate(mMinutes / 60.0f * 360.0f, x, y);
		final Drawable minuteHand = mMinuteHand;
		if (changed) {
			int minuteHandWidth = minuteHand.getIntrinsicWidth();
			int minuteHandHeight = minuteHand.getIntrinsicHeight();
			minuteHand.setBounds(x - (minuteHandWidth / 2), y - (minuteHandHeight / 2), x + (minuteHandWidth / 2), y + (minuteHandHeight / 2));
		}
		minuteHand.draw(canvas);
		canvas.restore();
		canvas.save();
		canvas.rotate(mSeconds, x, y);

		if(isSecondHandVisible){
			int secondHandWidth = mSecondHand.getIntrinsicWidth();
			int secondHandHeight = mSecondHand.getIntrinsicHeight();
			mSecondHand.setBounds(x - (secondHandWidth / 2), y - (secondHandHeight / 2), x + (secondHandWidth / 2), y + (secondHandHeight / 2));
		}
		mSecondHand.draw(canvas);
		canvas.restore();
		if(scaled) {
			canvas.restore();
		}
	}

	/**
	 * 
	 * @param mCalendar
	 */
	public void onTimeChanged(Time timeObject) {
		int hour = timeObject.hour;
		int minute = timeObject.minute;
		int second = timeObject.second;

		mSeconds = second;
		mMinutes = minute + second / 60.0f;
		mHour = hour + mMinutes / 60.0f;
		mChanged = true;
	}
	
	
	/**
	 * Set a drawable that will be used for Dialer background.
	 *
	 * @param d Drawable to display between pages
	 */
	public void setDialDrawable(Drawable dialer) {
		mDial = dialer;
		if (dialer != null) refreshDrawableState();
		setWillNotDraw(dialer == null);
		invalidate();
	}

	/**
	 * Set a drawable that will be used for Dialer background.
	 *
	 * @param resId Resource ID of a drawable to display between pages
	 */
	public void setDialDrawable(int resId) {
		setDialDrawable(getContext().getResources().getDrawable(resId));
	}
	
	/**
	 * Set a drawable that will be used for Minute-hand background.
	 *
	 * @param d Drawable to display between pages
	 */
	public void setMinuteDrawable(Drawable minute) {
		mMinuteHand = minute;
		if (minute != null) refreshDrawableState();
		setWillNotDraw(minute == null);
		invalidate();
	}

	/**
	 * Set a drawable that will be used for Minute-hand background.
	 *
	 * @param resId Resource ID of a drawable to display between pages
	 */
	public void setMinuteDrawable(int resId) {
		setMinuteDrawable(getContext().getResources().getDrawable(resId));
	}
	
	/**
	 * Set a drawable that will be used for Hour-hand background.
	 *
	 * @param d Drawable to display between pages
	 */
	public void setHourDrawable(Drawable hour) {
		mHourHand = hour;
		if (hour != null) refreshDrawableState();
		setWillNotDraw(hour == null);
		invalidate();
	}

	/**
	 * Set a drawable that will be used for Hour-hand background.
	 *
	 * @param resId Resource ID of a drawable to display between pages
	 */
	public void setHourDrawable(int resId) {
		setHourDrawable(getContext().getResources().getDrawable(resId));
	}
	
	/**
	 * Set a drawable that will be used for Second-hand background.
	 *
	 * @param d Drawable to display between pages
	 */
	public void setSecondDrawable(Drawable second) {
		mSecondHand = second;
		if (second != null) refreshDrawableState();
		setWillNotDraw(second == null);
		invalidate();
	}

	/**
	 * Set a drawable that will be used for Second-hand background.
	 *
	 * @param resId Resource ID of a drawable to display between pages
	 */
	public void setSecondDrawable(int resId) {
		setSecondDrawable(getContext().getResources().getDrawable(resId));
	}
}
