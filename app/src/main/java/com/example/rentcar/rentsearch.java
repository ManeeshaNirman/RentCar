package com.example.rentcar;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.StorageReference;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Model.RentSchedule;
import Model.Vehicle;


public class rentsearch extends Fragment {

    Date startdate;
    Date enddate;
    String type;
    String docid;
    private RecyclerView vehicleRecyclerView;
    private StorageReference storageRef;
    private FirestoreRecyclerAdapter adapter;
    RecyclerView.Adapter vadapter;
    RecyclerView.LayoutManager layoutManager;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference VehicleCollectionReference;
    CollectionReference RentScheduleCollectionReference;
    SimpleDateFormat simpleDateFormat;
    ArrayList<String> list = new ArrayList();
    ArrayList<String> removelist = new ArrayList();

    public rentsearch() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_rentsearch, container, false);




        try {
            Bundle bundle = this.getArguments();
          simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            startdate = simpleDateFormat.parse(bundle.getString("startdate"));
            enddate = simpleDateFormat.parse(bundle.getString("enddate"));
            type = bundle.getString("type");
            docid = bundle.getString("docid");
            vehicleRecyclerView = view.findViewById(R.id.vehicle_book_list);


            VehicleCollectionReference = VehicleCollectionReference = db.collection("Vehicle");
            RentScheduleCollectionReference = RentScheduleCollectionReference = db.collection("RentSchedule");



             check();
            System.out.println("list eka na:"+list.size());






        } catch (Exception e) {

            e.printStackTrace();
        }

        System.out.println("gone");
        return view;
    }




    public void check() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println("enawa yko");


        VehicleCollectionReference.whereEqualTo("bookstatus","Not Booked").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                System.out.println("size see:"+queryDocumentSnapshots.size());
                List <DocumentSnapshot> documentSnapshots= queryDocumentSnapshots.getDocuments();
                for (DocumentSnapshot documentSnapshot:documentSnapshots){

                    list.add(documentSnapshot.getId().toString());



                    Vehicle rentSchedule=documentSnapshot.toObject(Vehicle.class);
              // list.add(rentSchedule);

                }

                RentScheduleCollectionReference.whereNotEqualTo("status","Finished").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {

                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        System.out.println("mata oni"+queryDocumentSnapshots.getDocuments().size());

                        List <DocumentSnapshot> vehivlesnapnapshots= queryDocumentSnapshots.getDocuments();
                        System.out.println("still"+ vehivlesnapnapshots.size());

                        for (DocumentSnapshot v:vehivlesnapnapshots){


                            RentSchedule rentSchedule=v.toObject(RentSchedule.class);
                            String vehicle=rentSchedule.getVehicleno();
                            try {
                                Date sdate = simpleDateFormat.parse(rentSchedule.getStart());
                                Date edate = simpleDateFormat.parse(rentSchedule.getEnd());

                               ArrayList<LocalDate> dates = new ArrayList<>();
                                ArrayList<LocalDate> dates1 = new ArrayList<>();

                                try {
                                    LocalDate start = LocalDate.parse(simpleDateFormat.format(startdate));
                                    LocalDate end = LocalDate.parse(simpleDateFormat.format(enddate));

                                    LocalDate sdate1 = LocalDate.parse(simpleDateFormat.format(sdate));
                                    LocalDate edate1 = LocalDate.parse(simpleDateFormat.format(edate));

//                                    if(start.isEqual(end)){
//                                        System.out.println("jjjjj");
//                                            dates.add(start);
//                                    }else {
                                        while (!start.isAfter(end)) {
                                            dates.add(start);

                                            start = start.plusDays(1);
                                        }
                                  //  }
                                    while (!sdate1.isAfter(edate1)) {
                                        dates1.add(sdate1);
                                        sdate1 = sdate1.plusDays(1);
                                    }


                                }catch (Exception e){
                                    e.printStackTrace();
                                }

                                System.out.println("date:"+dates.size());

                                for(LocalDate date:dates){

                                    if(dates1.contains(date)){
                                      removelist.add(vehicle);
                                        if (list.contains(vehicle)){
                                            list.remove(vehicle);
                                        }
                                        //ganna epa
                                        System.out.println(rentSchedule.getVehicleno());
                                        Log.d("loop", rentSchedule.getVehicleno());

                                    }else{

                                        if(!list.contains(vehicle) && !removelist.contains(vehicle)){

                                            list.add(rentSchedule.getVehicleno());
                                        }

                                        System.out.println("No:"+rentSchedule.getVehicleno());
                                        Log.d("loop", rentSchedule.getVehicleno());
                                    }


                                }


                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                        }
                        vehicleRecyclerView.setHasFixedSize(true);
                        layoutManager=new LinearLayoutManager(getContext());
                        vadapter=new ShowVehicleAdapter(list,docid,simpleDateFormat.format(startdate),simpleDateFormat.format(enddate));
                        vehicleRecyclerView.setLayoutManager(layoutManager);
                        vehicleRecyclerView.setAdapter(vadapter);



                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
                vehicleRecyclerView.setHasFixedSize(true);
                layoutManager=new LinearLayoutManager(getContext());
                vadapter=new ShowVehicleAdapter(list,docid,simpleDateFormat.format(startdate),simpleDateFormat.format(enddate));
                vehicleRecyclerView.setLayoutManager(layoutManager);
                vehicleRecyclerView.setAdapter(vadapter);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

    }


}


