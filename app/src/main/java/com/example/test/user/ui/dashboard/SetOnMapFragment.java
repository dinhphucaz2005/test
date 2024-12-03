package com.example.test.user.ui.dashboard;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.test.R;
import com.example.test.databinding.FragmentSetOnMapBinding;
import com.example.test.model.Coordinate;
import com.example.test.model.Location;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;


public class SetOnMapFragment extends Fragment {

    private FragmentSetOnMapBinding binding;
    private LocationViewModel locationViewModel;
    private DashboardViewModel dashboardViewModel;
    private FusedLocationProviderClient fusedLocationClient;
    private MapView mapView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSetOnMapBinding.inflate(inflater, container, false);
        locationViewModel = new ViewModelProvider(requireActivity()).get(LocationViewModel.class);
        dashboardViewModel = new ViewModelProvider(requireActivity()).get(DashboardViewModel.class);

        initMapView();
        observeData();
        setEvents();
        return binding.getRoot();
    }

    private void setEvents() {
        binding.btnSaveLocation.setOnClickListener(v -> {
            GeoPoint center = (GeoPoint) mapView.getMapCenter();
            Coordinate coordinate = new Coordinate(center.getLatitude(), center.getLongitude());
            locationViewModel.getLocationByCoordinate(coordinate, location -> {
                if (location != null) {
                    dashboardViewModel.setLocation(location);
                    System.out.println("Location saved: " + location.getCoordinate().getLatitude() + " " + location.getCoordinate().getLongitude());
                    requireActivity().onBackPressed();
                }
            });
        });
    }

    private void observeData() {
        locationViewModel.observeLocation().observe(getViewLifecycleOwner(), this::onLocationChanged);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Configuration.getInstance().setTileDownloadThreads((short) 2);
        Configuration.getInstance().setTileFileSystemCacheMaxBytes(500L * 1024 * 1024);
        Configuration.getInstance().setUserAgentValue(requireActivity().getPackageName());
    }

    private void onLocationChanged(Location location) {
        if (location == null) return;
        System.out.println("Location changed: " + location.getCoordinate().getLatitude() + " " + location.getCoordinate().getLongitude());
        Coordinate coordinate = location.getCoordinate();
        if (coordinate == null) return;
        mapView.getController().setCenter(new GeoPoint(coordinate.getLatitude(), coordinate.getLongitude()));
    }

    private void initMapView() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity());
        mapView = binding.userMapView;
        mapView.setTilesScaledToDpi(true);
        mapView.setBuiltInZoomControls(true);
        mapView.setMultiTouchControls(true);
        mapView.getController().setZoom(15f);
    }

    @Override
    public void onResume() {
        super.onResume();
        IMapController mapController = mapView.getController();
        mapController.setZoom(15f);
    }
}