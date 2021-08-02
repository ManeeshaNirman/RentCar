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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;


public class Email extends Fragment {

    TextView otocode;
    Button otpbtn;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference CustomerCollectionReference;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String code;
    String docidcustomer;



    public Email() {
        // Required empty public constructor
    }


    public static Email newInstance(String param1, String param2) {
        Email fragment = new Email();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_email, container, false);

        Bundle bundle=this.getArguments();
       docidcustomer= bundle.getString("docid");
       code=bundle.getString("code");

        CustomerCollectionReference=db.collection("Customer");

        otocode=view.findViewById(R.id.otp);
        otpbtn=view.findViewById(R.id.otpconfirm);

        otpbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("email btn");
                System.out.println(code);
                System.out.println(docidcustomer);

                try {
                    int key=Integer.parseInt(otocode.getText().toString());


                    if(Integer.parseInt(code.trim())==key){
                        System.out.println("same code");

                        CustomerCollectionReference.document(docidcustomer).update("status","Basic").addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                                Toast.makeText(getActivity(), "email verified succesfully", Toast.LENGTH_SHORT).show();
                                FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
                                FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                                Bundle bundle=new Bundle();
                                bundle.putString("from","basic");
                                bundle.putString("docid",docidcustomer);

                                Login loginFragment=new Login();
                                loginFragment.setArguments(bundle);
                                fragmentTransaction.replace(R.id.MainLAYOUT,loginFragment,"signin");

                                fragmentTransaction.commit();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getActivity(), "email verifing fail!", Toast.LENGTH_SHORT).show();
                                System.out.println("Error update");
                            }
                        });





                    }else{

                        System.out.println("Same na code eka");
                    }

                }catch (Exception e){

                    e.printStackTrace();
                }



            }
        });


return view;
    }
}