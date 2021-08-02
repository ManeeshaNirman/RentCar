package com.example.rentcar;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import Model.Vehicle;

public class VehicleAdapter extends RecyclerView.Adapter<VehicleAdapter.ViewHolder> {

    List<Vehicle> map;
    ArrayList list=new ArrayList();

    String startdate;
    String enddate;
    String docid;

    public VehicleAdapter(List<Vehicle> map,String startdate,String enddate,String docid) {
        this.map = map;
        this.startdate=startdate;
        this.enddate=enddate;
        this.docid=docid;
    }

    @NonNull
    @Override
    public VehicleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.booking_vehicle,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VehicleAdapter.ViewHolder holder, int position) {



          holder.modelv.setText(map.get(position).getModel());
          holder.brand.setText(map.get(position).getBrand());
          holder.price.setText(String.valueOf("Rs:"+" "+map.get(position).getPrice()));
          holder.add.setText(String.valueOf("Rs:"+" "+map.get(position).getAdditional()));
        Picasso.with(holder.itemView.getContext()).load(map.get(position).getVehiclefront()).into(holder.imageView);
        holder.startDate=startdate;
        holder.endDate=enddate;
        holder.docid=docid;
         holder.vehicleid=map.get(position).getVehicleno();




    }

    @Override
    public int getItemCount() {
        return map.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView brand,modelv,price,add;
        private ImageView imageView;
        String docid;
        String vehicleid;
        String startDate;
        String endDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);


           brand= itemView.findViewById(R.id.brand);
            modelv= itemView.findViewById(R.id.model);
            price= itemView.findViewById(R.id.price);
            add= itemView.findViewById(R.id.additional);
            imageView=itemView.findViewById(R.id.vehicleimg);

            itemView.findViewById(R.id.car_item).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent=new Intent(itemView.getContext(),Payment.class);
                    intent.putExtra("docid",docid);
                    intent.putExtra("vehicleid",vehicleid);
                    intent.putExtra("startdate",startdate);
                    intent.putExtra("enddate",enddate);
                    itemView.getContext().startActivity(intent);





                }
            });

        }
    }
}
