package com.example.rentcar;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.util.BuddhistCalendar;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import HttpwithVolley.VolleyRequest;
import Model.Customer;
import io.grpc.internal.JsonUtil;


public class Login extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    TextView signupbtn, email, password;
    Button signin;
    Dialog dialog;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference CustomerCollectionReference;
    SharedPreferences preferences;
    CheckBox checkBox;
    SharedPreferences sharedPreferences;

    public Login() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        dialog = new Dialog(getContext());
        email = view.findViewById(R.id.emaillogin);
        password = view.findViewById(R.id.passwordlogin);
        signin = view.findViewById(R.id.Signinfragment);

        signupbtn = view.findViewById(R.id.Signupsigningragment);

//        autosignin();

        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Signupfr signUp = new Signupfr();
                fragmentTransaction.replace(R.id.MainLAYOUT, signUp, "signup");
                fragmentTransaction.commit();


            }
        });


        CustomerCollectionReference = db.collection("Customer");
        Bundle bundle = this.getArguments();
        System.out.println("bbbb:");
        if (bundle == null) {

            signin.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    CustomerCollectionReference.whereEqualTo("email", email.getText().toString().trim()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            System.out.println("No problem at");
                            if (!queryDocumentSnapshots.getDocuments().isEmpty()) {
                                System.out.println("No problem at");
                                DocumentSnapshot documentSnapshot = queryDocumentSnapshots.getDocuments().get(0);
                                Customer customer = documentSnapshot.toObject(Customer.class);

                                if (customer.getEmail().equals(email.getText().toString()) && customer.getPassword().equals(password.getText().toString())
                                        && customer.getStatus().equals("Basic") || customer.getStatus().equals("Pending") || customer.getStatus().equals("Verified")) {
                                    System.out.println("No problem at");

                                        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString("email", email.getText().toString());
                                        editor.putString("password", password.getText().toString());
                                        editor.putString("docid",documentSnapshot.getId());
                                        editor.commit();


                                    Intent home = new Intent(getActivity(), Home.class);
                                    home.putExtra("docid", documentSnapshot.getId());
                                    home.putExtra("name", customer.getFirstname());
                                    home.putExtra("status", customer.getStatus());
                                    home.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(home);


                                }

                            } else {


                            }


                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                          e.printStackTrace();
                        }
                    });

                }
            });


        } else {

            String from = bundle.getString("from");

            if (from.equals("basic")) {


                String Customerdocid = bundle.getString("docid");

                CustomerCollectionReference.document(Customerdocid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                        Customer customer = documentSnapshot.toObject(Customer.class);

                        String emaildb = customer.getEmail().toString();
                        String passworddb = customer.getPassword().toString();
                        String namedb = customer.getFirstname().toString();
                        String status = customer.getStatus().toString();

                        email.setText(emaildb);
                        signin.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (emaildb.equals(email.getText().toString()) && passworddb.equals(password.getText().toString()) && status.equals("Basic")) {

                                    Intent home = new Intent(getActivity(), Home.class);
                                    home.putExtra("docid", Customerdocid);
                                    home.putExtra("name", namedb);
                                    home.putExtra("status", status);
                                    home.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(home);


                                } else {
                                    email.setText(null);
                                    password.setText(null);
                                    System.out.println("ttttttttttttt");
                                    //

                                }

                            }
                        });


                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });


            } else {
                System.out.println("wena ekak");

            }


        }


        return view;
    }

    public void autosignin(){

        String emails=sharedPreferences.getString("email","No");
        String passwrods=sharedPreferences.getString("password","No");


        if(!emails.equals("No")||!passwrods.equals("No")){}

        CustomerCollectionReference.whereEqualTo("email",email).whereEqualTo("password",passwrods).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {



            }
        }).addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

            }
        });


    }









    }




//dialog.setContentView(R.layout.success_dialog);
//dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                dialog.show();
//
//                Intent intent=new Intent(getActivity(),Home.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);

