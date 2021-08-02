package com.example.rentcar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import Fragment.rentacarsearch;

public class Rentacar extends AppCompatActivity {
EditText datepicker;
ImageView backbar;
String startdate=null;
String enddate=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rentacar);
        FragmentManager fragmentManager=getSupportFragmentManager();
      FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
      rentacarsearch rentfragment=new rentacarsearch();
      fragmentTransaction.add(R.id.Datecontainer,rentfragment,"rentsearch").commit();



        backbar= findViewById(R.id.barback);
        backbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Rentacar.this,Home.class);
                startActivity(intent);
            }
        });



    }
}