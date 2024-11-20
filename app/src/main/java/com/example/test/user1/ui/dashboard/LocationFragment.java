package com.example.test.user1.ui.dashboard;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.test.databinding.FragmentLocationBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class LocationFragment extends Fragment {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    private FragmentLocationBinding binding;
    private DashboardViewModel dashboardViewModel;
    private FusedLocationProviderClient fusedLocationClient;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLocationBinding.inflate(inflater, container, false);
        dashboardViewModel = new ViewModelProvider(requireActivity()).get(DashboardViewModel.class);
        setEvents();
        getLocation();
        return binding.getRoot();
    }

    private void requestLocationPermission() {
        ActivityCompat.requestPermissions(requireActivity(),
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                LOCATION_PERMISSION_REQUEST_CODE);
    }

    private void setEvents() {
        binding.btnBack.setOnClickListener(view -> {
            requireActivity().onBackPressed();
        });


        binding.btnAccessLocation.setOnClickListener(view -> {
            requestLocationPermission();
        });
    }

    @SuppressLint("MissingPermission")
    private void getLocation() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity());


        fusedLocationClient.getLastLocation().addOnSuccessListener(requireActivity(), location -> {
            if (location != null) {
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                Log.d("Location", "getLocation: " + latitude + " " + longitude);


                OkHttpClient client = new OkHttpClient();


                String apiKey = "5fe0880faa8147169c4f24b73257b42c";
                String url = "https://api.opencagedata.com/geocode/v1/json?q="
                        + latitude + "," + longitude + "&key=" + apiKey;

                Log.d("url", "getLocation: "+ url);
                Request request = new Request.Builder()
                        .url(url)
                        .build();

                new Thread(() -> {
                    try {
                        Response response = client.newCall(request).execute();
                        if (response.isSuccessful()) {
                            assert response.body() != null;
                            String responseBody = response.body().string();
                            Log.d("API Response", responseBody);
                        } else {
                            Log.e("API Error", "Request failed: " + response.message());
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();

            }
        });


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLocation();
            }
        }
    }
}