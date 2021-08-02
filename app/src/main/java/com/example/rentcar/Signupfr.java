package com.example.rentcar;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import HttpwithVolley.VolleyRequest;
import Model.Customer;


public class Signupfr extends Fragment {


    TextView signinbtn, name, email, mobile, password, cpassword, signin;
    Button signupbtn;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public Signupfr() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static Signupfr newInstance(String documentid, String code) {
        Signupfr fragment = new Signupfr();
//        Bundle args = new Bundle();
//        args.putString("docid", documentid);
//        args.putString("code", code);
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_signupfr, container, false);

        signin = view.findViewById(R.id.signupsignin);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Signupfr signupfr = new Signupfr();
                Login Loginfragment = new Login();
                fragmentTransaction.remove(signupfr);
                fragmentTransaction.replace(R.id.MainLAYOUT, Loginfragment);
                fragmentTransaction.commit();
            }
        });

        name = view.findViewById(R.id.signupname);
        email = view.findViewById(R.id.signupemail);
        mobile = view.findViewById(R.id.signupmobile);
        password = view.findViewById(R.id.signuppassword);
        cpassword = view.findViewById(R.id.signuppasswordcon);

        signupbtn = view.findViewById(R.id.signupfr);

        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });


        return view;
    }

    public void signUp() {

        String namei = name.getText().toString();
        String emaili = email.getText().toString();
        String mobilei = mobile.getText().toString();
        String passwordi = password.getText().toString();
        String passwordi2 = cpassword.getText().toString();


//        if (!namei.equals(null)) {
//            if (!emaili.equals(null)) {
//
//                if (!mobilei.equals(null)) {
//
//                    if (!passwordi.equals(null)) {
//
//                        if (!passwordi2.equals(null)) {
//
//                            if (passwordi.equals(passwordi2)) {
//
//                                try {
//                                    int number = Integer.parseInt(mobilei);
//                                    if (number == 10) {


        Customer customer = new Customer();
        customer.setEmail(emaili);
        customer.setFirstname(namei);
        customer.setMobile(Integer.parseInt(mobilei));
        customer.setPassword(passwordi);
        customer.setStatus("Not Verified");

        db.collection("Customer").add(customer).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {

                String docid = documentReference.getId();
                String url = "http://192.168.43.13:8080/RentcarEmail/SendEmail";

                RequestQueue queue = Volley.newRequestQueue(getActivity());

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {


                                System.out.println("respone eke code eka" + response);

                                if(!response.equals(null)){


                                    Bundle bundle=new Bundle();
                                    bundle.putString("docid",docid);
                                    bundle.putString("code",response);


                                    System.out.println("Athule");
                                    FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
                                    FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();

                                    Email emailFragment=new Email();
                                    emailFragment.setArguments(bundle);
                                    fragmentTransaction.replace(R.id.MainLAYOUT,emailFragment,"forgetpassword");

                                    fragmentTransaction.commit();

                                }else{
                                    //methana email giye nthnm validation
                                    System.out.println("check connection");
                                }



                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //  Toast.makeText(getActivity(),error.getMessage(),Toast.LENGTH_LONG).show();
                        Log.d("volley", "onErrorResponse: " + "" + error);
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        Map<String, String> map = new HashMap();
                        map.put("name", namei);
                        map.put("email", emaili);
                        Log.d("volley", "OK Map");
                        return map;
                    }


                };


                queue.add(stringRequest);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), "Fail SignUp", Toast.LENGTH_LONG);
            }
        });

//
//                                    } else {
//                                        //mobile number same na
//
//                                    }
//
//
//                                } catch (Exception e) {
//
//
//                                }
//
//
//                            } else {
//
//                                //password eqal na
//                            }
//
//                        } else {
//
//                            //password confirm na
//                        }
//
//                    } else {
//
//
//                        //password na
//                    }
//
//                } else {
//                    //mobile null
//                }
//
//            } else {
//
//                //email null
//            }
//
//        } else {
//
//            //name null
//        }
//
//
    }


}