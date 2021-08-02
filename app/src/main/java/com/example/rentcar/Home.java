package com.example.rentcar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ResourceBundle;

public class Home extends AppCompatActivity {
    DrawerLayout drawerLayout;
    ImageView toogle;
    Button set;
    String docid, name, status;
    TextView welcometxt;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Intent intent =this.getIntent();
        docid = intent.getStringExtra("docid");
        name = intent.getStringExtra("name");
        status = intent.getStringExtra("status");



        System.out.println(docid);

        welcometxt=findViewById(R.id.welcometxtnav);

         welcometxt.setText(name);

        findViewById(R.id.profilenav).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Home.this, Profile.class);
                intent.putExtra("docid",docid);
                startActivity(intent);

            }
        });

        findViewById(R.id.homenav).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), Home.class);
                intent.putExtra("docid",docid);
                startActivity(intent);

            }
        });
        findViewById(R.id.layoutAddVehicle).setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                Intent intent1=new Intent(getApplicationContext(),Breakdown.class);
                System.out.println("home eken yaddi enawa"+docid);
                intent1.putExtra("docid",docid);
                startActivity(intent1);


            }
        });
///////////////////
        findViewById(R.id.mytrip).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplication(),Mytripload.class);
                intent.putExtra("docid",docid);
                startActivity(intent);
            }
        });
        //////////
        findViewById(R.id.layoutbooktrip).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(),Booking.class);
                intent.putExtra("docid",docid);
                startActivity(intent);
//                Intent intent=new Intent(getApplication(),Rentfragmentload.class);
//                startActivity(intent);
//                ProgressDialog p=new ProgressDialog(getApplicationContext());
//                p.setMessage("Wait");
//                p.setCanceledOnTouchOutside(false);
            }
        });

        findViewById(R.id.imagemenu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), Notification.class);
                intent.putExtra("docid",docid);
                startActivity(intent);
            }
        });
    }

    public void empty(){

        if(docid==null){
            docid=sharedPreferences.getString("docid","No");
        }
    }
}