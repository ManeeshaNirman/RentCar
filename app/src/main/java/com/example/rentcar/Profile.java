package com.example.rentcar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Profile extends AppCompatActivity {
ImageView backbar;
TextView bartextaction;
String docid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        System.out.println("Profile inne");
        Intent intent=getIntent();
        docid=intent.getStringExtra("docid");
        System.out.println(docid);

        FragmentManager fragmentManager=getSupportFragmentManager();
       FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
       Bundle bundle=new Bundle();
       bundle.putString("docid",docid);
       profilebasic profilebasicfragment=new profilebasic();
       profilebasicfragment.setArguments(bundle);
       fragmentTransaction.add(R.id.fragmentcontainerprofile,profilebasicfragment,"profilebasic").commit();





//       backbar= findViewById(R.id.barback);
//       backbar.setOnClickListener(new View.OnClickListener() {
//           @Override
//           public void onClick(View v) {
//               Intent intent=new Intent(Profile.this,Home.class);
//               startActivity(intent);
//           }
//       });

    }
}