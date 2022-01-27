package com.example.notify;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CalendarFragment extends Fragment {

    CalendarFragment.onCalendarFragmentListener callback;


    public interface onCalendarFragmentListener
    {
        List<PostData>  getPostList();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try{
            callback =(CalendarFragment.onCalendarFragmentListener) context;
        }
        catch (ClassCastException e){
            throw new ClassCastException(context.toString()+"Must be implemented onCalendarFragmentListener!");
        }
    }


    public CalendarFragment(){}
    private static final String TAG = "MainActivity";


    @SuppressLint("SetTextI18n")
    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_calendar, container, false);

        //CalendarView calendarView = view.findViewById(R.id.calendar);
        // Log.e("date","date: "+calendarView.getDate() );
        TextView monthName = view.findViewById(R.id.month_name);
        final CompactCalendarView compactCalendarView = (CompactCalendarView) view.findViewById(R.id.compactcalendar_view);


        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat month = new SimpleDateFormat("MMMM", Locale.getDefault());
        SimpleDateFormat year = new SimpleDateFormat("yyyy", Locale.getDefault());

        String currentMonth = month.format(c);
        String currentYear = year.format(c);

        Log.e("date", currentMonth);
        Log.e("date", currentYear);

        monthName.setText(currentMonth + " , " + currentYear);

        List<PostData> postList = callback.getPostList();

        for (int i = 0; i < postList.size(); i++)
        {
            String myDate = "2014/10/29 18:10:45";
            myDate = postList.get(i).getDeadlineYear()+"/"+postList.get(i).getDeadlineMonth()+"/"+postList.get(i).getDeadlineDay()+" "+postList.get(i).getDeadlineHour()+":"+postList.get(i).getDeadlineMinute()+":00";
            @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MMM/dd HH:mm:ss");
            Date date = null;
            try {
                date = sdf.parse(myDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            long timeInMillis = date.getTime();

            Event ev2 = new Event(Color.argb(255, 169, 68, 65), timeInMillis);
            compactCalendarView.addEvent(ev2);

            Log.e("timeinMillis",timeInMillis+" ");
            Log.e("postList",postList.get(i).getDeadlineDay()+" "+postList.get(i).getDeadlineMonth()+" "+postList.get(i).getDeadlineYear()+" "+postList.get(i).getDeadlineHour()+" "+postList.get(i).getDeadlineMinute());
        }




        // Set first day of week to Monday, defaults to Monday so calling setFirstDayOfWeek is not necessary
        // Use constants provided by Java Calendar class
        compactCalendarView.setFirstDayOfWeek(Calendar.MONDAY);
        Date date = new Date();
        long timeDiff = date.getTime();
        // Add event 1 on Sun, 07 Jun 2015 18:20:51 GMT
        Event ev1 = new Event(Color.GREEN, 1433701251000L, "Some extra data that I want to store.");
        compactCalendarView.addEvent(ev1);

        // Added event 2 GMT: Sun, 07 Jun 2015 19:10:51 GMT




        // Query for events on Sun, 07 Jun 2015 GMT.
        // Time is not relevant when querying for events, since events are returned by day.
        // So you can pass in any arbitary DateTime and you will receive all events for that day.
        List<Event> events = compactCalendarView.getEvents(1433701251000L); // can also take a Date object

        // events has size 2 with the 2 events inserted previously
        Log.d(TAG, "Events: " + events);

        // define a listener to receive callbacks when certain events happen.
        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                List<Event> events = compactCalendarView.getEvents(dateClicked);
                Log.e(TAG, "Day was clicked: " + dateClicked + " with events " + events);
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                Log.d(TAG, "Month was scrolled to: " + firstDayOfNewMonth);

                Calendar cal = Calendar.getInstance();
                cal.setTime(firstDayOfNewMonth);
                @SuppressLint("SimpleDateFormat") String monthNameStr = new SimpleDateFormat("MMMM").format(cal.getTime());
                @SuppressLint("SimpleDateFormat") String yearNameStr = new SimpleDateFormat("yyyy").format(cal.getTime());
                monthName.setText(monthNameStr+" , "+yearNameStr);
            }
        });



        return view;
    }



}
