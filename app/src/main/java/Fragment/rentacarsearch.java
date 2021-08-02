package Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.rentcar.Home;
import com.example.rentcar.R;
import com.example.rentcar.Rentacar;
import com.example.rentcar.Vehiclebook;
import com.example.rentcar.rentsearch;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.StorageReference;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import Model.RentSchedule;
import Model.Vehicle;

public class rentacarsearch extends Fragment {

    EditText datepicker;
    Button Bookvehicle;
    List<String> list = new ArrayList();
   String startdate=null;
    String enddate=null;
    String docid;
    private RecyclerView vehicleRecyclerView;
    private StorageReference storageRef;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference VehicleCollectionReference;
    CollectionReference RentScheduleCollectionReference;
    SimpleDateFormat simpleDateFormat;

    public rentacarsearch() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view= inflater.inflate(R.layout.fragment_rentacarsearch, container, false);

       Bundle bundle=this.getArguments();
      docid= bundle.getString("docid");

        VehicleCollectionReference = VehicleCollectionReference = db.collection("Vehicle");
        RentScheduleCollectionReference = RentScheduleCollectionReference = db.collection("RentSchedule");

        Calendar calendar=Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.clear();
        Long today= MaterialDatePicker.todayInUtcMilliseconds();
        calendar.setTimeInMillis(today);


        calendar.set(Calendar.MONTH,Calendar.JANUARY);
        Long January=calendar.getTimeInMillis();

        calendar.set(Calendar.MONTH,Calendar.DECEMBER);
        Long December=calendar.getTimeInMillis();
        CalendarConstraints.Builder cBuilder=new CalendarConstraints.Builder();
        cBuilder.setStart(January);
        cBuilder.setEnd(December);
        cBuilder.setOpenAt(today);
        cBuilder.setValidator(DateValidatorPointForward.now());



        MaterialDatePicker.Builder <Pair<Long,Long>> builder=MaterialDatePicker.Builder.dateRangePicker();
        builder.setTitleText("SELECT A DATE");

        builder.setCalendarConstraints(cBuilder.build());
        final MaterialDatePicker materialDatePicker=builder.build();

        datepicker=view.findViewById(R.id.datepicker);
        // pickend=findViewById(R.id.enddate);

        datepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialDatePicker.show(getActivity().getSupportFragmentManager(),"DATE_PICKER");
            }
        });

//        pickend.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                materialDatePicker.show(getSupportFragmentManager(),"DATE_PICKER");
//            }
//        });







        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                selection.getClass();

                Pair pair=(Pair)selection;



                long start= Long.parseLong(pair.first.toString());
                long end=Long.parseLong(pair.second.toString());
                Calendar calendar1=Calendar.getInstance(TimeZone.getTimeZone("UTC"));
                calendar1.setTimeInMillis(start);
                Date date=calendar1.getTime();
                SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
                startdate = format1.format(date);
                calendar1.setTimeInMillis(end);
                Date date2=calendar1.getTime();
                enddate=format1.format(date2);



                datepicker.setText(startdate+" "+"to"+" "+enddate );




            }
        });

//       Bookvehicle= view.findViewById(R.id.BookVehicle);
//       Bookvehicle.setOnClickListener(new View.OnClickListener() {
//           @Override
//           public void onClick(View v) {
//               FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
//               FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
//               Vehiclebook bookfragment=new Vehiclebook();
//               fragmentTransaction.replace(R.id.Datecontainer,bookfragment,"book").commit();
//           }
//       });

              view.findViewById(R.id.searchbtn).setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {


               if(startdate==null && enddate==null){

                   System.out.println("null");
               }else{
               FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
               FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
               rentsearch bookfragment=new rentsearch();
               Bundle bundle=new Bundle();
               bundle.putString("docid",docid);
               bundle.putString("startdate",startdate);
               bundle.putString("enddate",enddate);
              bundle.putString("type","Car");
              bookfragment.setArguments(bundle);
               fragmentTransaction.replace(R.id.bookingcontainer,bookfragment,"book").commit();
           }}
       });


       return view;
    }


}