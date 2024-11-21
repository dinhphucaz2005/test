package com.example.test.user2.ui.map;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.test.R;
import com.example.test.databinding.FragmentMapBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

import java.util.Objects;

public class MapFragment extends Fragment {

    private FragmentMapBinding binding;
    private FusedLocationProviderClient fusedLocationClient;
    private MapView mapView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMapBinding.inflate(inflater, container, false);
        initMapView();
        return binding.getRoot();
    }

    private void initMapView() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity());
        mapView = binding.mapView;
        mapView.setMultiTouchControls(true);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Configuration.getInstance().setUserAgentValue(requireActivity().getPackageName());
        if (ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.getLastLocation().addOnSuccessListener(location -> {
                        if (location != null) {
                            double lat = location.getLatitude();
                            double lon = location.getLongitude();
                            setMapView(new GeoPoint(lat, lon));
                        } else {
                            ActivityCompat.requestPermissions(
                                    requireActivity(),
                                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                    100
                            );
                        }
                    }
            );
        }
    }

    private void setMapView(GeoPoint startPoint) {

        mapView.getController().setZoom(18.0);
        mapView.getController().setCenter(startPoint);

        Marker marker = new Marker(mapView);
        marker.setPosition(startPoint);
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        marker.setTitle("Vị trí của bạn");
        mapView.getOverlays().add(marker);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}