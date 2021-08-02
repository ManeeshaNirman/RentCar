package com.example.rentcar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import Model.Customer;
import Model.RentSchedule;

public class Mytripload extends AppCompatActivity {
    private RecyclerView tripRecyclerView;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private StorageReference storageRef;
    private FirestoreRecyclerAdapter adapter;
    Spinner spinner;
    String docid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mytripload);
        spinner=findViewById(R.id.tripspiner);

        tripRecyclerView= findViewById(R.id.trip_list);
       Intent intent=this.getIntent();
       docid=intent.getStringExtra("docid");
        System.out.println(docid);

        ArrayAdapter<CharSequence> arrayAdapter=ArrayAdapter.createFromResource(this,R.array.tripsearch, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(arrayAdapter);
        search("Booked");
        arrayAdapter.notifyDataSetChanged();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String select=parent.getItemAtPosition(position).toString();
                System.out.println(select);
                search(select);
                 arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
             search("Booked");

                arrayAdapter.notifyDataSetChanged();
            }
        });





    }


    private void search(String field) {
        System.out.println("AWA");
        Query query=db.collection("RentSchedule").whereEqualTo("customerdocid",docid).whereEqualTo("status",field);
        System.out.println("AWA1");
        FirestoreRecyclerOptions<RentSchedule> options= new FirestoreRecyclerOptions.Builder<RentSchedule>().setQuery(query,RentSchedule.class).build();

        adapter= new FirestoreRecyclerAdapter<RentSchedule, tripViewHolder>(options) {

            @NonNull
            @Override
            public tripViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view1= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_trip,parent,false);

                return new tripViewHolder(view1);
            }

            @Override
            protected void onBindViewHolder(@NonNull tripViewHolder holder, int position, @NonNull RentSchedule model) {

                holder.vehicleno.setText(model.getVehicleno());
                holder.status.setText(model.getStatus());
                holder.payment.setText(String.valueOf(model.getPaid()));
                holder.docid=docid;
                holder.start.setText(model.getStart());
                holder.end.setText(model.getEnd());


            }
        };

        tripRecyclerView.setHasFixedSize(true);
        tripRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        tripRecyclerView.setAdapter(adapter);

    }


    private class tripViewHolder extends RecyclerView.ViewHolder{

        private TextView start,end,payment,status,vehicleno;
        private ImageView imageView;
        private Button button;
        String docid;


        public tripViewHolder(@NonNull View itemView) {
            super(itemView);
            start=itemView.findViewById(R.id.start);
            end=itemView.findViewById(R.id.end);
            payment=itemView.findViewById(R.id.payment);
            vehicleno=itemView.findViewById(R.id.vehicleno);
            status=itemView.findViewById(R.id.status);
            System.out.println("Awe na");

//            button.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
////                    Intent intent=new Intent(itemView.getContext(), CustomerVerify.class);
////                    intent.putExtra("docid",docid);
////                    intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
////                    itemView.getContext().startActivity(intent);
//                }
//            });


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