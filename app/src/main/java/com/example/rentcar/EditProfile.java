package com.example.rentcar;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import Model.Customer;


public class EditProfile extends Fragment {

    ImageView profileimage, drivinglfront, drivinglback, proof;
    Uri imageuri=null, drivingfront=null, drivingback=null, proofuri=null;

    private final List<String> districtarray = new ArrayList<>();

    private ArrayAdapter<String> districtAdapter;


    String districtname;


    Bitmap bitmapimage;
    TextInputEditText email, firstname, midname, lastname, mobile, no, street, city, lisenceno, dliseceNo;
    AutoCompleteTextView district;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference CustomerCollectionReference;
    Customer customer;
    String click = null;

    CollectionReference DistrictRef = db.collection("District");

    private StorageReference storageRef;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";





    private String mParam1;
    private String mParam2;
    private String docid;
    private String statussearch;
    private Button editprofilebtn;
    private String path;
    private String downloadImageUrl;
    private String downloadImagedrfrontUrl;
    private String downloadImagedrbackUrl;
    private String downloadImagedrproofUrl;

    public EditProfile() {

    }


    public static EditProfile newInstance(String param1, String param2) {
        EditProfile fragment = new EditProfile();
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
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);

        Bundle bundle = this.getArguments();
        docid = bundle.getString("docid");
        CustomerCollectionReference = db.collection("Customer");
        storageRef = FirebaseStorage.getInstance().getReference().child("ProfilePicture");
        profileimage = view.findViewById(R.id.profileimageview);
        editprofilebtn = view.findViewById(R.id.epconfirm);
        drivinglfront = view.findViewById(R.id.drivingfront);
        drivinglback = view.findViewById(R.id.drivingback);
        proof = view.findViewById(R.id.proof);
        district = view.findViewById(R.id.districtselect);

        districtsearch();

        searchData(docid);


        email = view.findViewById(R.id.emailedit);
        firstname = view.findViewById(R.id.firstnameedit);
        midname = view.findViewById(R.id.midnameedit);
        lastname = view.findViewById(R.id.lastnameedit);
        mobile = view.findViewById(R.id.mobileedit);
        no = view.findViewById(R.id.noedit);
        city = view.findViewById(R.id.cityedit);
        street = view.findViewById(R.id.streetedit);
        district = view.findViewById(R.id.districtselect);
        dliseceNo = view.findViewById(R.id.dlnedit);


        view.findViewById(R.id.epconfirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                updateCustomer();


            }
        });

        drivinglfront.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click = "drivingfront";
                choosePhoto();
            }
        });


        profileimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click = "profile";
                choosePhoto();
            }
        });

        drivinglback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click = "drivingback";
                choosePhoto();
            }
        });

        proof.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                click = "proof";
                choosePhoto();
            }
        });


        return view;
    }

    private void districtsearch() {


//Add district data to spinner
        districtAdapter = new ArrayAdapter<String>(getActivity(), R.layout.dropdownitems, districtarray);
        district.setAdapter(districtAdapter);
        DistrictRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {

                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String dis = document.getString("name");
                        districtarray.add(dis);
                    }


                }
            }
        });

    }

    public void choosePhoto() {

        AlertDialog.Builder builder = new AlertDialog.Builder(EditProfile.super.getContext());
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alertdialog_profilepicture, null);
        builder.setCancelable(false);
        builder.setView(dialogView);


        final AlertDialog alertDialog = builder.create();
        TextView addprofilecamera = dialogView.findViewById(R.id.imageviewalertdialogpp);
        TextView addprofilegalery = dialogView.findViewById(R.id.imageviewalertdialoggalerypp);
        TextView cancel = dialogView.findViewById(R.id.cancel);
        alertDialog.show();

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        addprofilecamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                if (checkPermision()) {
                    if (checkPermisionex()) {
                        takePicturefromCamera();
                        alertDialog.cancel();
                    }
                }
            }
        });

        addprofilegalery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                getPictureFromGalery();
                alertDialog.cancel();
            }
        });
    }


    public void getPictureFromGalery() {

        Intent pickpicture = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickpicture, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 1:
                if (resultCode == Activity.RESULT_OK) {

                    if (click.equals("profile")) {
                        imageuri = data.getData();
                        profileimage.setImageURI(imageuri);
                        click = "null";

                    } else if (click.equals("proof")) {
                        proofuri = data.getData();
                        proof.setImageURI(proofuri);
                        click = "null";

                    } else if (click.equals("drivingback")) {
                        drivingback = data.getData();
                        drivinglback.setImageURI(drivingback);
                        click = "null";

                    } else if (click.equals("drivingfront")) {
                        drivingfront = data.getData();
                        drivinglfront.setImageURI(drivingfront);
                        click = "null";

                    }


                }
                break;
            case 2:

                if (resultCode == Activity.RESULT_OK) {
                    if (click.equals("profile")) {
                        Bundle bundle = data.getExtras();
                        bitmapimage = (Bitmap) bundle.get("data");
                        profileimage.setImageBitmap(bitmapimage);
                        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                        bitmapimage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                        String path = MediaStore.Images.Media.insertImage(getContext().getContentResolver(), bitmapimage, "val", null);
                        imageuri = Uri.parse(path);

                        click = "null";

                    } else if (click.equals("proof")) {
                        Bundle bundle = data.getExtras();
                        bitmapimage = (Bitmap) bundle.get("data");
                        proof.setImageBitmap(bitmapimage);
                        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                        bitmapimage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                        String path = MediaStore.Images.Media.insertImage(getContext().getContentResolver(), bitmapimage, "val", null);
                        proofuri = Uri.parse(path);
                        click = "null";

                    } else if (click.equals("drivingback")) {
                        Bundle bundle = data.getExtras();
                        bitmapimage = (Bitmap) bundle.get("data");
                        drivinglback.setImageBitmap(bitmapimage);
                        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                        bitmapimage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                        String path = MediaStore.Images.Media.insertImage(getContext().getContentResolver(), bitmapimage, "val", null);
                        drivingback = Uri.parse(path);
                        click = "null";

                    } else if (click.equals("drivingfront")) {
                        Bundle bundle = data.getExtras();
                        bitmapimage = (Bitmap) bundle.get("data");
                        drivinglfront.setImageBitmap(bitmapimage);
                        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                        bitmapimage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                        String path = MediaStore.Images.Media.insertImage(getContext().getContentResolver(), bitmapimage, "val", null);
                        drivingfront = Uri.parse(path);
                        click = "null";

                    }


//                    System.out.println("OW");
//                    Bundle bundle = data.getExtras();
//
//                    bitmapimage = (Bitmap) bundle.get("data");
//                    profileimage.setImageBitmap(bitmapimage);
//                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//                    bitmapimage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
//                    String path = MediaStore.Images.Media.insertImage(getContext().getContentResolver(), bitmapimage, "val", null);
//
//                    System.out.println("path:" + path);
//                    imageuri = Uri.parse(path);


                }

        }
    }

    public void takePicturefromCamera() {


        Intent takepicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takepicture.resolveActivity(getActivity().getPackageManager()) != null) {

            startActivityForResult(takepicture, 2);
        }

    }

    private boolean checkPermision() {

        if (Build.VERSION.SDK_INT >= 23) {

            int cameraPermission = ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA);
            if (cameraPermission == PackageManager.PERMISSION_DENIED) {

                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, 20);

                return false;
            }

        }
        return true;

    }


    private boolean checkPermisionex() {

        if (Build.VERSION.SDK_INT >= 23) {

            int externalPermission = ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (externalPermission == PackageManager.PERMISSION_DENIED) {

                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 25);

                return false;
            }

        }
        return true;

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {


        } else {

            Toast.makeText(getActivity(), "Permission Granted", Toast.LENGTH_LONG).show();

        }

        if (requestCode == 25 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {


        } else {

            Toast.makeText(getActivity(), "Permission Granted", Toast.LENGTH_LONG).show();

        }
    }


    // serach

    public void searchData(String id) {

        CustomerCollectionReference.document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                customer = documentSnapshot.toObject(Customer.class);

                statussearch = customer.getStatus().toString();

                if (statussearch.equals("Basic")) {

                    email.setText(customer.getEmail().toString());
                    firstname.setText(customer.getFirstname().toString());
                    mobile.setText(String.valueOf(customer.getMobile()));

                } else if (statussearch.equals("Pending")) {
                    email.setText(customer.getEmail().toString());
                    firstname.setText(customer.getFirstname().toString());
                    mobile.setText(String.valueOf(customer.getMobile()));
                    midname.setText(customer.getMidname().toString());
                    lastname.setText(customer.getLastname().toString());
                    no.setText(customer.getNo());
                    street.setText(customer.getStreet());
                    city.setText(customer.getCity());
                    dliseceNo.setText(customer.getDrivinglicennumber());
                    district.setText(customer.getDistric());
                    String pathss = customer.getProfileimagepath();
                    System.out.println("pathss:" + " " + pathss);
                    //  String profileimgurlpath = "ProfilePicture_ClOUrPVZ1l92embcW0JP.png";

                    Picasso.with(getView().getContext()).load(customer.getProfileimagepath()).into(profileimage);
                    Picasso.with(getView().getContext()).load(customer.getDrivinglicenphotopathfront()).into(drivinglfront);
                    Picasso.with(getView().getContext()).load(customer.getDrivinglicenphotopathback()).into(drivinglback);
                    Picasso.with(getView().getContext()).load(customer.getProof()).into(proof);


                }


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                System.out.println("Fail");
            }
        });

    }


    public void updateCustomer() {

        if (statussearch.toString().equals("Basic")) {

            String fn = firstname.getText().toString();
            String mn = midname.getText().toString();
            String ln = lastname.getText().toString();
            int mob = Integer.parseInt(mobile.getText().toString());
            String no1 = no.getText().toString();
            String street1 = street.getText().toString();
            String city1 = city.getText().toString();
            String dln = dliseceNo.getText().toString();
            String distric = district.getText().toString();


            StorageReference filePath = storageRef.child(imageuri.getLastPathSegment() + System.currentTimeMillis() + ".jpg");

            final UploadTask uploadTask = filePath.putFile(imageuri);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    String message = e.toString();
                    Toast.makeText(getActivity(), "Error: " + message, Toast.LENGTH_SHORT).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                    Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if (!task.isSuccessful()) {
                                throw task.getException();
                            }

                            downloadImageUrl = filePath.getDownloadUrl().toString();
                            return filePath.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {
                                downloadImageUrl = task.getResult().toString();

                                ///drfront
                                storageRef = FirebaseStorage.getInstance().getReference().child("DrivingLicenseFront");
                                StorageReference filePath = storageRef.child(drivingfront.getLastPathSegment() + System.currentTimeMillis() + ".jpg");

                                final UploadTask uploadTask = filePath.putFile(drivingfront);
                                uploadTask.addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        String message = e.toString();
                                        Toast.makeText(getActivity(), "Error: " + message, Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                                        Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                                            @Override
                                            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                                                if (!task.isSuccessful()) {
                                                    throw task.getException();
                                                }

                                                downloadImagedrfrontUrl = filePath.getDownloadUrl().toString();
                                                return filePath.getDownloadUrl();
                                            }
                                        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Uri> task) {
                                                if (task.isSuccessful()) {
                                                    downloadImagedrfrontUrl = task.getResult().toString();

                                                    ///// drback


                                                    storageRef = FirebaseStorage.getInstance().getReference().child("DrivingLicenseBack");
                                                    StorageReference filePath = storageRef.child(drivingback.getLastPathSegment() + System.currentTimeMillis() + ".jpg");

                                                    final UploadTask uploadTask = filePath.putFile(drivingback);
                                                    uploadTask.addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            String message = e.toString();
                                                            Toast.makeText(getActivity(), "Error: " + message, Toast.LENGTH_SHORT).show();
                                                        }
                                                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                        @Override
                                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                                                            Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                                                                @Override
                                                                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                                                                    if (!task.isSuccessful()) {
                                                                        throw task.getException();
                                                                    }

                                                                    downloadImagedrbackUrl = filePath.getDownloadUrl().toString();
                                                                    return filePath.getDownloadUrl();
                                                                }
                                                            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Uri> task) {
                                                                    if (task.isSuccessful()) {
                                                                        downloadImagedrbackUrl = task.getResult().toString();

                                                                        ///// proof
                                                                        storageRef = FirebaseStorage.getInstance().getReference().child("Proof");
                                                                        StorageReference filePath = storageRef.child(proofuri.getLastPathSegment() + System.currentTimeMillis() + ".jpg");

                                                                        final UploadTask uploadTask = filePath.putFile(proofuri);
                                                                        uploadTask.addOnFailureListener(new OnFailureListener() {
                                                                            @Override
                                                                            public void onFailure(@NonNull Exception e) {
                                                                                String message = e.toString();
                                                                                Toast.makeText(getActivity(), "Error: " + message, Toast.LENGTH_SHORT).show();
                                                                            }
                                                                        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                                            @Override
                                                                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                                                                                Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                                                                                    @Override
                                                                                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                                                                                        if (!task.isSuccessful()) {
                                                                                            throw task.getException();
                                                                                        }

                                                                                        downloadImagedrproofUrl = filePath.getDownloadUrl().toString();
                                                                                        return filePath.getDownloadUrl();
                                                                                    }
                                                                                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                                                                                    @Override
                                                                                    public void onComplete(@NonNull Task<Uri> task) {
                                                                                        if (task.isSuccessful()) {
                                                                                            downloadImagedrproofUrl = task.getResult().toString();


                                                                                            districtAdapter = new ArrayAdapter<String>(getActivity(), R.layout.dropdownitems, districtarray);
                                                                                            district.setAdapter(districtAdapter);
                                                                                            DistrictRef.whereEqualTo("name", distric).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                                                                @Override
                                                                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                                                                    if (task.isSuccessful()) {

                                                                                                        CustomerCollectionReference.document(docid).update("firstname", fn, "midname", mn, "lastname", ln, "mobile", mob, "no", no1, "street", street1,
                                                                                                                "city", city1, "drivinglicennumber", dln, "status", "Pending", "profileimagepath", downloadImageUrl, "drivinglicenphotopathfront", downloadImagedrfrontUrl, "drivinglicenphotopathback", downloadImagedrbackUrl, "proof", downloadImagedrproofUrl, "distric", distric).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                                            @Override
                                                                                                            public void onSuccess(Void aVoid) {
                                                                                                                searchData(docid);

                                                                                                                Toast.makeText(getActivity(), "Updated", Toast.LENGTH_LONG).show();

                                                                                                            }
                                                                                                        }).addOnFailureListener(new OnFailureListener() {
                                                                                                            @Override
                                                                                                            public void onFailure(@NonNull Exception e) {
                                                                                                                System.out.println("Fail putha");
                                                                                                                e.printStackTrace();
                                                                                                            }
                                                                                                        });

                                                                                                    } else {
                                                                                                        district.requestFocus();
                                                                                                        Toast.makeText(EditProfile.super.getContext(), "Invalid District", Toast.LENGTH_LONG).show();

                                                                                                    }
                                                                                                }
                                                                                            });


                                                                                        }
                                                                                    }
                                                                                });
                                                                            }
                                                                        });


                                                                    }
                                                                }
                                                            });
                                                        }
                                                    });


                                                }
                                            }
                                        });
                                    }
                                });


                            }
                        }
                    });
                }
            });


/////


        } else if (statussearch.toString().equals("Pending")) {


            String fn = firstname.getText().toString();
            String mn = midname.getText().toString();
            String ln = lastname.getText().toString();
            int mob = Integer.parseInt(mobile.getText().toString());
            String no1 = no.getText().toString();
            String street1 = street.getText().toString();
            String city1 = city.getText().toString();
            String dln = dliseceNo.getText().toString();
            String distric = district.getText().toString();


            StorageReference filePath = storageRef.child(imageuri.getLastPathSegment() + System.currentTimeMillis() + ".jpg");

            final UploadTask uploadTask = filePath.putFile(imageuri);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    String message = e.toString();
                    Toast.makeText(getActivity(), "Error: " + message, Toast.LENGTH_SHORT).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                    Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if (!task.isSuccessful()) {
                                throw task.getException();
                            }

                            downloadImageUrl = filePath.getDownloadUrl().toString();
                            return filePath.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {
                                downloadImageUrl = task.getResult().toString();

                                ///drfront
                                storageRef = FirebaseStorage.getInstance().getReference().child("DrivingLicenseFront");
                                StorageReference filePath = storageRef.child(drivingfront.getLastPathSegment() + System.currentTimeMillis() + ".jpg");

                                final UploadTask uploadTask = filePath.putFile(drivingfront);
                                uploadTask.addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        String message = e.toString();
                                        Toast.makeText(getActivity(), "Error: " + message, Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                                        Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                                            @Override
                                            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                                                if (!task.isSuccessful()) {
                                                    throw task.getException();
                                                }

                                                downloadImagedrfrontUrl = filePath.getDownloadUrl().toString();
                                                return filePath.getDownloadUrl();
                                            }
                                        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Uri> task) {
                                                if (task.isSuccessful()) {
                                                    downloadImagedrfrontUrl = task.getResult().toString();

                                                    ///// drback


                                                    storageRef = FirebaseStorage.getInstance().getReference().child("DrivingLicenseBack");
                                                    StorageReference filePath = storageRef.child(drivingback.getLastPathSegment() + System.currentTimeMillis() + ".jpg");

                                                    final UploadTask uploadTask = filePath.putFile(drivingback);
                                                    uploadTask.addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            String message = e.toString();
                                                            Toast.makeText(getActivity(), "Error: " + message, Toast.LENGTH_SHORT).show();
                                                        }
                                                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                        @Override
                                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                                                            Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                                                                @Override
                                                                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                                                                    if (!task.isSuccessful()) {
                                                                        throw task.getException();
                                                                    }

                                                                    downloadImagedrbackUrl = filePath.getDownloadUrl().toString();
                                                                    return filePath.getDownloadUrl();
                                                                }
                                                            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Uri> task) {
                                                                    if (task.isSuccessful()) {
                                                                        downloadImagedrbackUrl = task.getResult().toString();

                                                                        ///// proof
                                                                        storageRef = FirebaseStorage.getInstance().getReference().child("Proof");
                                                                        StorageReference filePath = storageRef.child(proofuri.getLastPathSegment() + System.currentTimeMillis() + ".jpg");

                                                                        final UploadTask uploadTask = filePath.putFile(proofuri);
                                                                        uploadTask.addOnFailureListener(new OnFailureListener() {
                                                                            @Override
                                                                            public void onFailure(@NonNull Exception e) {
                                                                                String message = e.toString();
                                                                                Toast.makeText(getActivity(), "Error: " + message, Toast.LENGTH_SHORT).show();
                                                                            }
                                                                        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                                            @Override
                                                                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                                                                                Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                                                                                    @Override
                                                                                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                                                                                        if (!task.isSuccessful()) {
                                                                                            throw task.getException();
                                                                                        }

                                                                                        downloadImagedrproofUrl = filePath.getDownloadUrl().toString();
                                                                                        return filePath.getDownloadUrl();
                                                                                    }
                                                                                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                                                                                    @Override
                                                                                    public void onComplete(@NonNull Task<Uri> task) {
                                                                                        if (task.isSuccessful()) {
                                                                                            downloadImagedrproofUrl = task.getResult().toString();


                                                                                            districtAdapter = new ArrayAdapter<String>(getActivity(), R.layout.dropdownitems, districtarray);
                                                                                            district.setAdapter(districtAdapter);
                                                                                            DistrictRef.whereEqualTo("name", distric).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                                                                @Override
                                                                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                                                                    if (task.isSuccessful()) {

                                                                                                        CustomerCollectionReference.document(docid).update("firstname", fn, "midname", mn, "lastname", ln, "mobile", mob, "no", no1, "street", street1,
                                                                                                                "city", city1, "drivinglicennumber", dln, "status", "Pending", "profileimagepath", downloadImageUrl, "drivinglicenphotopathfront", downloadImagedrfrontUrl, "drivinglicenphotopathback", downloadImagedrbackUrl, "proof", downloadImagedrproofUrl, "distric", distric).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                                            @Override
                                                                                                            public void onSuccess(Void aVoid) {
                                                                                                                searchData(docid);

                                                                                                                Toast.makeText(getActivity(), "Updated", Toast.LENGTH_LONG).show();

                                                                                                            }
                                                                                                        }).addOnFailureListener(new OnFailureListener() {
                                                                                                            @Override
                                                                                                            public void onFailure(@NonNull Exception e) {
                                                                                                                System.out.println("Fail putha");
                                                                                                                e.printStackTrace();
                                                                                                            }
                                                                                                        });

                                                                                                    } else {
                                                                                                        district.requestFocus();
                                                                                                        Toast.makeText(EditProfile.super.getContext(), "Invalid District", Toast.LENGTH_LONG).show();

                                                                                                    }
                                                                                                }
                                                                                            });


                                                                                        }
                                                                                    }
                                                                                });
                                                                            }
                                                                        });


                                                                    }
                                                                }
                                                            });
                                                        }
                                                    });


                                                }
                                            }
                                        });
                                    }
                                });


                            }
                        }
                    });
                }
            });


        } else if (statussearch.toString().equals("Verified")) {




                String fn = firstname.getText().toString();
                String mn = midname.getText().toString();
                String ln = lastname.getText().toString();
                int mob = Integer.parseInt(mobile.getText().toString());
                String no1 = no.getText().toString();
                String street1 = street.getText().toString();
                String city1 = city.getText().toString();
                String dln = dliseceNo.getText().toString();
                String distric = district.getText().toString();


                StorageReference filePath = storageRef.child(imageuri.getLastPathSegment() + System.currentTimeMillis() + ".jpg");

                final UploadTask uploadTask = filePath.putFile(imageuri);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        String message = e.toString();
                        Toast.makeText(getActivity(), "Error: " + message, Toast.LENGTH_SHORT).show();
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                        Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                            @Override
                            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                                if (!task.isSuccessful()) {
                                    throw task.getException();
                                }

                                downloadImageUrl = filePath.getDownloadUrl().toString();
                                return filePath.getDownloadUrl();
                            }
                        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {
                                if (task.isSuccessful()) {
                                    downloadImageUrl = task.getResult().toString();

                                    ///drfront
                                    storageRef = FirebaseStorage.getInstance().getReference().child("DrivingLicenseFront");
                                    StorageReference filePath = storageRef.child(drivingfront.getLastPathSegment() + System.currentTimeMillis() + ".jpg");

                                    final UploadTask uploadTask = filePath.putFile(drivingfront);
                                    uploadTask.addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            String message = e.toString();
                                            Toast.makeText(getActivity(), "Error: " + message, Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                                            Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                                                @Override
                                                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                                                    if (!task.isSuccessful()) {
                                                        throw task.getException();
                                                    }

                                                    downloadImagedrfrontUrl = filePath.getDownloadUrl().toString();
                                                    return filePath.getDownloadUrl();
                                                }
                                            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Uri> task) {
                                                    if (task.isSuccessful()) {
                                                        downloadImagedrfrontUrl = task.getResult().toString();

                                                        ///// drback


                                                        storageRef = FirebaseStorage.getInstance().getReference().child("DrivingLicenseBack");
                                                        StorageReference filePath = storageRef.child(drivingback.getLastPathSegment() + System.currentTimeMillis() + ".jpg");

                                                        final UploadTask uploadTask = filePath.putFile(drivingback);
                                                        uploadTask.addOnFailureListener(new OnFailureListener() {
                                                            @Override
                                                            public void onFailure(@NonNull Exception e) {
                                                                String message = e.toString();
                                                                Toast.makeText(getActivity(), "Error: " + message, Toast.LENGTH_SHORT).show();
                                                            }
                                                        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                            @Override
                                                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                                                                Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                                                                    @Override
                                                                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                                                                        if (!task.isSuccessful()) {
                                                                            throw task.getException();
                                                                        }

                                                                        downloadImagedrbackUrl = filePath.getDownloadUrl().toString();
                                                                        return filePath.getDownloadUrl();
                                                                    }
                                                                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<Uri> task) {
                                                                        if (task.isSuccessful()) {
                                                                            downloadImagedrbackUrl = task.getResult().toString();

                                                                            ///// proof
                                                                            storageRef = FirebaseStorage.getInstance().getReference().child("Proof");
                                                                            StorageReference filePath = storageRef.child(proofuri.getLastPathSegment() + System.currentTimeMillis() + ".jpg");

                                                                            final UploadTask uploadTask = filePath.putFile(proofuri);
                                                                            uploadTask.addOnFailureListener(new OnFailureListener() {
                                                                                @Override
                                                                                public void onFailure(@NonNull Exception e) {
                                                                                    String message = e.toString();
                                                                                    Toast.makeText(getActivity(), "Error: " + message, Toast.LENGTH_SHORT).show();
                                                                                }
                                                                            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                                                @Override
                                                                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                                                                                    Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                                                                                        @Override
                                                                                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                                                                                            if (!task.isSuccessful()) {
                                                                                                throw task.getException();
                                                                                            }

                                                                                            downloadImagedrproofUrl = filePath.getDownloadUrl().toString();
                                                                                            return filePath.getDownloadUrl();
                                                                                        }
                                                                                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                                                                                        @Override
                                                                                        public void onComplete(@NonNull Task<Uri> task) {
                                                                                            if (task.isSuccessful()) {
                                                                                                downloadImagedrproofUrl = task.getResult().toString();


                                                                                                districtAdapter = new ArrayAdapter<String>(getActivity(), R.layout.dropdownitems, districtarray);
                                                                                                district.setAdapter(districtAdapter);
                                                                                                DistrictRef.whereEqualTo("name", distric).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                                                                    @Override
                                                                                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                                                                        if (task.isSuccessful()) {

                                                                                                            CustomerCollectionReference.document(docid).update("firstname", fn, "midname", mn, "lastname", ln, "mobile", mob, "no", no1, "street", street1,
                                                                                                                    "city", city1, "drivinglicennumber", dln, "status", "Pending", "profileimagepath", downloadImageUrl, "drivinglicenphotopathfront", downloadImagedrfrontUrl, "drivinglicenphotopathback", downloadImagedrbackUrl, "proof", downloadImagedrproofUrl, "distric", distric).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                                                @Override
                                                                                                                public void onSuccess(Void aVoid) {
                                                                                                                    searchData(docid);

                                                                                                                    Toast.makeText(getActivity(), "Updated", Toast.LENGTH_LONG).show();

                                                                                                                }
                                                                                                            }).addOnFailureListener(new OnFailureListener() {
                                                                                                                @Override
                                                                                                                public void onFailure(@NonNull Exception e) {
                                                                                                                    System.out.println("Fail putha");
                                                                                                                    e.printStackTrace();
                                                                                                                }
                                                                                                            });

                                                                                                        } else {
                                                                                                            district.requestFocus();
                                                                                                            Toast.makeText(EditProfile.super.getContext(), "Invalid District", Toast.LENGTH_LONG).show();

                                                                                                        }
                                                                                                    }
                                                                                                });


                                                                                            }
                                                                                        }
                                                                                    });
                                                                                }
                                                                            });


                                                                        }
                                                                    }
                                                                });
                                                            }
                                                        });


                                                    }
                                                }
                                            });
                                        }
                                    });


                                }
                            }
                        });
                    }
                });




        }
    }

//    private boolean districtsearchfinal(String districtname) {
//
//        boolean result;
////Add district data to spinner
//
//
//        return true;
//    }


}