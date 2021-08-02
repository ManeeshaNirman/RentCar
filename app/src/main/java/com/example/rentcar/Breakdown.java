package com.example.rentcar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import Model.RentSchedule;

public class Breakdown extends AppCompatActivity {
String docid;
TextView issuetext;
Button send;
String rentid;
FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference RentShcehduleCollectionReference;
    CollectionReference BreakdownCollectionReference;
    CollectionReference CustomerCollectionReference;
   String rentdocid;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breakdown);

        RentShcehduleCollectionReference=db.collection("RentSchedule");
        BreakdownCollectionReference=db.collection("BreakIssue");
        Intent intent=getIntent();
      docid= intent.getStringExtra("docid");
        System.out.println("me docid:"+docid);
//        docid=sharedPreferences.getString("docid","No");
      check();


     issuetext= findViewById(R.id.issuebreak);
     send= findViewById(R.id.Sendissue);

     send.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {

             Model.Breakdown breakdown=new Model.Breakdown();
             breakdown.setCustomerdocid(docid);
             breakdown.setDate(new Date().toString());
             breakdown.setLongitude(MapsFragment.lon);
             breakdown.setLatitude(MapsFragment.lat);
             breakdown.setType(issuetext.getText().toString());
             breakdown.setRentsheduleid(rentdocid);
             breakdown.setStatus("Active");

             BreakdownCollectionReference.add(breakdown).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                 @Override
                 public void onSuccess(DocumentReference documentReference) {
                     Toast.makeText(getApplicationContext(),"Breakdown Isuue Saved",Toast.LENGTH_LONG);
                     Intent intent=new Intent(getApplicationContext(),Home.class);
                     intent.putExtra("docid",docid);
                     startActivity(intent);
                 }
             }).addOnFailureListener(new OnFailureListener() {
                 @Override
                 public void onFailure(@NonNull Exception e) {

                 }
             });


         }
     });


    }

    private void check() {
        System.out.println(docid);
        RentShcehduleCollectionReference.whereEqualTo("customerdocid",docid).whereEqualTo("status","Ongoing").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if(queryDocumentSnapshots.size()>0) {
                    List<DocumentSnapshot> documentSnapshots = queryDocumentSnapshots.getDocuments();
                    for (DocumentSnapshot documentSnapshot : documentSnapshots) {
                                 rentdocid=  documentSnapshot.getId().toString();
                        RentSchedule rentSchedule = documentSnapshot.toObject(RentSchedule.class);

                    }
                }else{
                    System.out.println("no ongoing");
                    Intent intent=new Intent(getApplicationContext(),Home.class);
                    intent.putExtra("docid",docid);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(),"You are Not in a Ongoing a trip",Toast.LENGTH_LONG).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }
}