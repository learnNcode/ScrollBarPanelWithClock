ScrollBarPanelWithClock
==================

Path 2.0  like scrollbar with clock widget for Android.

This is an open source library which uses the scroll bar library. I have added a Clock widget inside the scroll bar panel which gives a Path 2.0  like effect and can be customised according to your needs. Please see the screenshots below to get a better idea.

Screenshot
=========

![without second hand](https://dl.dropboxusercontent.com/u/61919232/learnNcode/without_second_hand.png "without second hand")

![with second hand](https://dl.dropboxusercontent.com/u/61919232/learnNcode/with_second_hand.png "with second hand")
Usage
=====

Check the attached demo sample app.
    
Layout
=====

   
   The ExtendedListView replaces a standard ListView widget
      and provides the ScrollBarPanel capability.
    

    <com.learnNcode.android.ExtendedListView
        xmlns:clock="http://schemas.android.com/apk/res-auto"
        android:id="@android:id/list"
        android:layout_width="fill_parent"
        android:layout_height="fill_parent"
        android:choiceMode="singleChoice"
        clock:hand_second="@drawable/ic_timer_clock_minute_hand"
        clock:scrollBarPanel="@layout/scrollbarpanel"
        clock:scrollBarPanelInAnimation="@anim/in"
        clock:scrollBarPanelOutAnimation="@anim/out" />

 You can use/edit the clock widget the following way, this should be done in the layout for scrollbar panel:

    <com.learnNcode.android.Clock
        xmlns:clock="http://schemas.android.com/apk/res-auto"
        android:id="@+id/analogClockScroller"
        android:layout_width="25dp"
        android:layout_height="25dp"
        clock:hand_second="@drawable/ic_timer_clock_minute_hand"
        clock:hand_minute="@drawable/ic_timer_clock_minute_hand"
        clock:hand_hour="@drawable/ic_timer_clock_hour_hand"
        clock:hand_dial="@drawable/ic_timer_clock_dialer"/>

Activity
=====

Set your scrollBarPanel

    ExtendedListView mListView = (ExtendedListView) findViewById(android.R.id.list);

You can attach a position changed listener on the listview and write your desired implementation.

    mListView.setOnPositionChangedListener(new OnPositionChangedListener() {

        @Override
        public void onPositionChanged(ExtendedListView listView, int firstVisiblePosition, View scrollBarPanel) {
          Clock analogClockInstance = (Clock) scrollBarPanel.findViewById(R.id.analogClockScroller);
            

            Time time = new Time();
            analogClockInstance.setSecondHandVisibility(true);  // to visible second hand
            time.set(position+3, position, 5, 0, 0, 0);
            analogClockInstance.onTimeChanged(time);

        }

You can also directly implement the OnPositionChangedListener, and write your implementation in the overridden method.

      public class MainActivity extends Activity implements OnPositionChangedListener {

      @Override
        public void onPositionChanged(ExtendedListView listView, int position, View scrollBarPanel) {
            Clock analogClockInstance = (Clock) scrollBarPanel.findViewById(R.id.analogClockScroller);
            
            Time time = new Time();
            analogClockInstance.setSecondHandVisibility(true);
            time.set(position+3, position, 5, 0, 0, 0);
            analogClockInstance.onTimeChanged(time);
        }
      }

        
 This is how you initialize the clock widget, this should be done inside the OnPositionChanged().

      Time timeObj = new Time();
      analogClockObj.setSecondHandVisibility(true);
      analogClockObj.setVisibility(View.VISIBLE);
      timeObj.set(position+3, position, 5, 0, 0, 0); //pass respective values to the clock here.
      analogClockObj.onTimeChanged(timeObj);


NOTE :
=====

>You can set visibility for the seconds hand by:  analogClockObj.setSecondHandVisibility(true/false);

>You can set visibility for the clock widget by:   analogClockObj.setVisibility(View.VISIBLE);
      
Acknowledgements
==============

 [Android-ScrollBarPanel](https://github.com/rno/Android-ScrollBarPanel) 

License
======

Licensed under the [ Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0.html) 

Thankyou
=======

  If you like my work say a hi :)
  Happy coding.
      


