package com.example.rentcar;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import Model.Customer;

public class profilebasic extends Fragment {

    Button editprofile;
    String docid;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference CustomerCollectionReference;
    Customer customer;
    TextView email,name,mobile,status;
    private String statussearch;


    public profilebasic() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view=inflater.inflate(R.layout.fragment_profilebasic, container, false);

       CustomerCollectionReference=db.collection("Customer");

       Bundle bundle=this.getArguments();
      docid= bundle.getString("docid");
       name= view.findViewById(R.id.namebasic);
       email= view.findViewById(R.id.emailbasic);
        mobile=view.findViewById(R.id.mobilebasic);
        status=view.findViewById(R.id.statusbasic);
        System.out.println("basic:"+docid);

         searchData(docid);




      editprofile= view.findViewById(R.id.editprofilebasic);
      editprofile.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
              Bundle bundle1=new Bundle();
              bundle1.putString("docid",docid);
              bundle1.putString("status",statussearch);

              FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
             EditProfile editProfile=new EditProfile();
             editProfile.setArguments(bundle1);
              fragmentTransaction.replace(R.id.fragmentcontainerprofile,editProfile,"editprofile").commit();
          }
      });

       return view;
    }


    public void searchData(String id){

        CustomerCollectionReference.document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                customer= documentSnapshot.toObject(Customer.class);

                statussearch=customer.getStatus();

                name.setText(customer.getFirstname());
                email.setText(customer.getEmail());
                mobile.setText(String.valueOf(customer.getMobile()));
                status.setText(customer.getStatus());



            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                System.out.println("Fail");
            }
        });

    }
}