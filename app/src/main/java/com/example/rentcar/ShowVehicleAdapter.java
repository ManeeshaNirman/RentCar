package com.example.rentcar;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import Model.Vehicle;

public class ShowVehicleAdapter extends RecyclerView.Adapter<ShowVehicleAdapter.viewHolder> {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference VehicleCollectionReference=db.collection("Vehicle");

    public ShowVehicleAdapter(ArrayList<String> map, String docid, String startdate, String enddate) {
        this.map = map;
        this.docid = docid;
        this.startdate = startdate;
        this.enddate = enddate;
    }

    ArrayList<String> map;
    String docid;
    String startdate;
    String enddate;
    @NonNull
    @Override
    public ShowVehicleAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.vehicle_item,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShowVehicleAdapter.viewHolder holder, int position) {

        VehicleCollectionReference.document(map.get(position).toString()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

              Vehicle vehicle=  documentSnapshot.toObject(Vehicle.class);
              holder.textView.setText(vehicle.getVehicleno());
              holder.brandcard.setText(vehicle.getBrand());
           //   holder.pricecard.setText(String.valueOf(vehicle.getPrice()));
              holder.vehicledocid=map.get(position);
                Picasso.with(holder.itemView.getContext()).load(vehicle.getVehiclefront()).into(holder.img);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

//
//        holder.textView.setText(map.get(position));
    }

    @Override
    public int getItemCount() {
        return map.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView textView,brandcard,pricecard;
        ImageView img;
        String vehicledocid;
        String customerdocid;



        public viewHolder(@NonNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.vehicleNumber);
            brandcard=itemView.findViewById(R.id.brandcard);
            textView=itemView.findViewById(R.id.pricecard);
            img=itemView.findViewById(R.id.vehicleimg);

            itemView.findViewById(R.id.cardview).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(itemView.getContext(),Payment.class);
                    intent.putExtra("docid",docid);
                    intent.putExtra("vehicleid",vehicledocid);
                    intent.putExtra("startdate",startdate);
                    intent.putExtra("enddate",enddate);
                    itemView.getContext().startActivity(intent);


                }
            });

            itemView.findViewById(R.id.cardclick).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(itemView.getContext(),Payment.class);
                    intent.putExtra("docid",docid);
                    intent.putExtra("vehicleid",vehicledocid);
                    intent.putExtra("startdate",startdate);
                    intent.putExtra("enddate",enddate);
                    itemView.getContext().startActivity(intent);
                }
            });


        }
    }
}
