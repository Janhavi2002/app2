package com.example.myapplication2;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TimePicker;
import androidx.fragment.app.Fragment;


public class Fragment1 extends Fragment {
    TimePicker t1,t2;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }



    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_first, container, false);

        t1 = (TimePicker) view.findViewById(R.id.timepicker1);
        t1.setIs24HourView(true);
        t2 = (TimePicker) view.findViewById(R.id.timepicker2);
        t2.setIs24HourView(true);

        return view;
    }

    public int getHour1(){
        return t1.getHour();

    }
    public int getHour2(){
        return t2.getHour();

    }

    public int getMin1(){
        return t1.getMinute();

    }
    public int getMin2() {
        return t2.getMinute();

    }

}
