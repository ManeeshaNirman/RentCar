package com.example.rentcar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

public class MapsFragment extends Fragment {

    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int LOCATION_PERMISSION = 100;
    GoogleMap currentGoogleMap;
    static double lat,lon;

    private OnMapReadyCallback callback = new OnMapReadyCallback() {
        @Override
        public void onMapReady(GoogleMap googleMap) {
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(MapsFragment.super.getContext());
            currentGoogleMap = googleMap;
            updateCurrentLocation();
            if (ActivityCompat.checkSelfPermission(MapsFragment.super.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MapsFragment.super.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                //write permission request
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION},LOCATION_PERMISSION);
                return;
            }else{
                updateCurrentLocation();
            }
        }
    };


    private void requestPermissions(FragmentActivity activity, String[] grantResults, int locationPermission) {
        if (locationPermission== LOCATION_PERMISSION){
            if (grantResults.length>0){
                updateCurrentLocation();
            }
        }
    }

    public void updateCurrentLocation() {
        @SuppressLint("MissingPermission")
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {

            @Override
            public void onSuccess(Location location) {
                if (location!=null){
                    Toast.makeText(MapsFragment.super.getContext(), "location"+location.getLatitude()+" "+location.getLongitude(), Toast.LENGTH_SHORT).show();
                    lat = location.getLatitude();
                    lon = location.getLongitude();
                    LatLng cuslocation = new LatLng(location.getLatitude(),location.getLongitude());
                    currentGoogleMap.addMarker(new MarkerOptions().position(cuslocation).title("I am here"));
                    currentGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(cuslocation));
                    currentGoogleMap.moveCamera(CameraUpdateFactory.zoomTo(10));
                }else{
                    Toast.makeText(MapsFragment.super.getContext(), "location not found", Toast.LENGTH_SHORT).show();
                }
            }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MapsFragment.super.getContext(), "location error"+e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION){
            if (permissions.length>0){
                updateCurrentLocation();
            }
        }
    }
}