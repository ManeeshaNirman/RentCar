package com.example.rentcar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirestoreRegistrar;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.StorageReference;


import java.util.ArrayList;
import java.util.List;

import Model.Customer;


public class Notification extends AppCompatActivity {

    String docid;
    private RecyclerView notificationRecyclerView;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirestoreRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        Intent intent=getIntent();
        docid=intent.getStringExtra("docid");

        notificationRecyclerView=findViewById(R.id.notificationrecycle);
        Query query=db.collection("Notification").whereEqualTo("customerId",docid).whereEqualTo("mark","Not Read");


        FirestoreRecyclerOptions<Model.Notification> options= new FirestoreRecyclerOptions.Builder<Model.Notification>().setQuery(query, Model.Notification.class).build();

        adapter=new FirestoreRecyclerAdapter<Model.Notification,NotificationViewholder>(options) {
            @NonNull
            @Override
            public NotificationViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view1= LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_item,parent,false);
                return new NotificationViewholder(view1);
            }

            @Override
            protected void onBindViewHolder(@NonNull NotificationViewholder holder, int position, @NonNull Model.Notification model) {

               holder.discription.setText(model.getNotification());
               holder.docId=docid;
               holder.type=model.getType();
               holder.notificationId= getSnapshots().getSnapshot(position).getId();

            }




        };
       notificationRecyclerView.setHasFixedSize(true);
        notificationRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        notificationRecyclerView.setAdapter(adapter);



    }

    private class NotificationViewholder extends RecyclerView.ViewHolder {
        private TextView date,discription;
        String docId;
        String type;
        String notificationId;

        public NotificationViewholder(@NonNull View itemView) {
            super(itemView);

            date=itemView.findViewById(R.id.date);
            discription=itemView.findViewById(R.id.notidis);

           itemView.findViewById(R.id.visit).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    System.out.println("Maneesha noti");

                    if(type.equals("Profile")){

                        db.collection("Notification").document(notificationId).update("mark","read").addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Intent intent = new Intent(getApplicationContext(),Profile.class);
                                intent.putExtra("docid",docid);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });


                    }


                }
            });
        }
    }
    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }
}