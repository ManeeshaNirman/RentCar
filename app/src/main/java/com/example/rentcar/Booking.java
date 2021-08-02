package com.example.rentcar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

import Fragment.rentacarsearch;

public class Booking extends AppCompatActivity {
String docid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        Intent intent=getIntent();
       docid= intent.getStringExtra("docid");

       Bundle bundle=new Bundle();
       bundle.putString("docid",docid);
                FragmentManager fragmentManager=getSupportFragmentManager();
                FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                rentacarsearch bookingfragment=new rentacarsearch();
                bookingfragment.setArguments(bundle);
                fragmentTransaction.add(R.id.bookingcontainer,bookingfragment);

                fragmentTransaction.commit();
    }
}