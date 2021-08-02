package com.example.rentcar;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.smarteist.autoimageslider.DefaultSliderView;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import Classes.DialogBox;
import Model.Customer;
import Model.RentSchedule;
import Model.Vehicle;
import lk.payhere.androidsdk.PHConfigs;
import lk.payhere.androidsdk.PHConstants;
import lk.payhere.androidsdk.PHMainActivity;
import lk.payhere.androidsdk.PHResponse;
import lk.payhere.androidsdk.model.InitRequest;
import lk.payhere.androidsdk.model.StatusResponse;

public class Payment extends AppCompatActivity {
    int PAYHERE_REQUEST = 11010;
    SliderLayout sliderLayout;
    String docid;
    String vehicleid;
    String startDate;
    String endDate;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference VehicleCollectionReference;
    CollectionReference ScheduleCollectionReference;
    CollectionReference CustomerCollectionReference;
    String front,back,right,left,infront,inback;
    EditText no,model,brand,type,payment,error;
    String email,mobile,nostreet,city,firstname,lastname;
    double price;
    private double p1;
    private Double p;
   // Dialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        Intent intent=getIntent();
        //  dialog=new Dialog(getApplicationContext());
       startDate= intent.getStringExtra("startdate");
       endDate= intent.getStringExtra("enddate");
       vehicleid= intent.getStringExtra("vehicleid");
       docid= intent.getStringExtra("docid");

       VehicleCollectionReference = db.collection("Vehicle");
        CustomerCollectionReference = db.collection("Customer");
        ScheduleCollectionReference=db.collection("RentSchedule");
      no= findViewById(R.id.Vehiclenumber);
      model=findViewById(R.id.Vehiclemodel);
      brand=findViewById(R.id.Vehiclebrand);
      type=findViewById(R.id.Vehicletype);
      payment=findViewById(R.id.pay);



//        dialog.findViewById(R.id.okbtn).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

//        error=findViewById(R.id.Error);
//        dialog=new Dialog(this.getApplicationContext());
//        dialog.setContentView(R.layout.success_dialog);
//        dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.dialogbg));
//        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
//        dialog.setCancelable(false);
//
//

        sliderLayout = findViewById(R.id.imageSlider);
        sliderLayout.setIndicatorAnimation(IndicatorAnimations.SLIDE);
        sliderLayout.setScrollTimeInSec(3);



         searchdata();



        findViewById(R.id.vrconfirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payment();

            }
        });







    }




    private void setSliderViews(String f,String b,String r,String l,String in,String incack) {

        for (int i = 0; i <= 5; i++) {

            DefaultSliderView sliderView = new DefaultSliderView(this);

            switch (i) {
                case 0:
                    sliderView.setImageUrl(f);
                    break;
                case 1:
                    sliderView.setImageUrl(b);
                    break;
                case 2:
                    sliderView.setImageUrl(r);
                    break;

                case 3:
                    sliderView.setImageUrl(l);
                    break;
                case 4:
                    sliderView.setImageUrl(in);
                    break;

                case 5:
                    sliderView.setImageUrl(incack);
                    break;


            }

            sliderView.setImageScaleType(
                    ImageView.ScaleType.CENTER_CROP);
            //at last add this view in your layout :
            sliderLayout.addSliderView(sliderView);
        }
    }




    public  void payment(){

        if(p1<=p*40/100 && p>p1) {

            double amount = Double.valueOf(payment.getText().toString());
            String noo = no.getText().toString();

            InitRequest req = new InitRequest();
            req.setMerchantId("1216856");       // Your Merchant PayHere ID
            req.setMerchantSecret("4JHLA2JDVg58MMzm0n7lXc4OUvzNV3hFp8gi0ymyVMP8"); // Your Merchant secret (Add your app at Settings > Domains & Credentials, to get this))
            req.setCurrency("LKR");             // Currency code LKR/USD/GBP/EUR/AUD
            req.setAmount(amount);             // Final Amount to be charged
            req.setOrderId("230000123");        // Unique Reference ID
            req.setItemsDescription(noo);  // Item description title
            req.setCustom1("Vehicle Book" + startDate + " " + "to" + " " + endDate);
            req.setCustom2("Thank You!");
            req.getCustomer().setFirstName(firstname);
            req.getCustomer().setLastName(lastname);
            req.getCustomer().setEmail(email);
            req.getCustomer().setPhone(mobile);
            req.getCustomer().getAddress().setAddress(nostreet);
            req.getCustomer().getAddress().setCity(city);
            req.getCustomer().getAddress().setCountry("Sri Lanka");

//Optional Params
//        req.getCustomer().getDeliveryAddress().setAddress("No.2, Kandy Road");
//        req.getCustomer().getDeliveryAddress().setCity("Kadawatha");
//        req.getCustomer().getDeliveryAddress().setCountry("Sri Lanka");
//        req.getItems().add(new Item(null, "Door bell wireless", 1, 1000.0));

            Intent intent = new Intent(this, PHMainActivity.class);
            intent.putExtra(PHConstants.INTENT_EXTRA_DATA, req);
            PHConfigs.setBaseUrl(PHConfigs.SANDBOX_URL);
            startActivityForResult(intent, PAYHERE_REQUEST); //unique request ID like private final static int PAYHERE_REQUEST = 11010;
        }else{


        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PAYHERE_REQUEST && data != null && data.hasExtra(PHConstants.INTENT_EXTRA_RESULT)) {
            PHResponse<StatusResponse> response = (PHResponse<StatusResponse>) data.getSerializableExtra(PHConstants.INTENT_EXTRA_RESULT);
            if (resultCode == Activity.RESULT_OK) {
                System.out.println("Paymenton");
                String msg;
                if (response != null)
                    if (response.isSuccess())
                        msg = "Activity result:" + response.getData().toString();
                update(response.getData().getPrice(),response.getData().getPaymentNo());

            } else if (resultCode == Activity.RESULT_CANCELED) {
                System.out.println("Paymentoff");
                if (response != null)
                    update(response.getData().getPrice(),response.getData().getPaymentNo());
                else
                    error.setText("User canceled the request");
            }
        }

    }

    public void searchdata(){
        VehicleCollectionReference.document(vehicleid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                Vehicle vehicle= documentSnapshot.toObject(Vehicle.class);



                     front=vehicle.getVehiclefront().toString();
                    back=vehicle.getVehicleback().toString();
                    right=vehicle.getVehicleright().toString();
                    left=vehicle.getVehicleleft().toString();
                    infront=vehicle.getInteriorfront().toString();
                    inback=vehicle.getInteriorback().toString();

                    no.setText(vehicle.getVehicleno().toString());
                    model.setText(vehicle.getModel().toString());
                    brand.setText(vehicle.getBrand().toString());
                    type.setText(vehicle.getType().toString());
                    price=vehicle.getPrice();

                    p=Double.valueOf(vehicle.getPrice());

                     p1=p*40/100;
                System.out.println("paid:"+p1);

                    payment.setText(String.valueOf(p1));


                    setSliderViews(front,back,left,right,infront,inback);


   CustomerCollectionReference.document(docid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
       @Override
       public void onSuccess(DocumentSnapshot documentSnapshot) {

         Customer customer=  documentSnapshot.toObject(Customer.class);
         firstname=customer.getFirstname();
         lastname=customer.getLastname();
         mobile=String.valueOf(customer.getMobile());
         email=customer.getEmail();
         nostreet=customer.getNo()+","+customer.getStreet();
         city=customer.getCity();





       }
   }).addOnFailureListener(new OnFailureListener() {
       @Override
       public void onFailure(@NonNull Exception e) {

       }
   });





            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });



    }

    public void update(double pricel,long inid){


                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");

                double remain=price-p1;


                RentSchedule rentSchedule=new RentSchedule();
                rentSchedule.setVehicleno(vehicleid);
                rentSchedule.setCustomerdocid(docid);
                rentSchedule.setStart(startDate);
                rentSchedule.setEnd(endDate);
                rentSchedule.setPaid(p1);
                rentSchedule.setInvoiceno(inid);
                rentSchedule.setStatus("Booked");
                rentSchedule.setDate( simpleDateFormat.format(System.currentTimeMillis()));
                rentSchedule.setRemain(remain);


                ScheduleCollectionReference.add(rentSchedule).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if(task.isSuccessful()){

                            VehicleCollectionReference.document(vehicleid).update("bookstatus","Booked").addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

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




                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

            }




    }
