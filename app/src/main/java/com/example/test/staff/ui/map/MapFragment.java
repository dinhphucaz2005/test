package com.example.test.staff.ui.map;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.test.databinding.FragmentMapBinding;
import com.example.test.model.Coordinate;
import com.example.test.model.Task;
import com.example.test.staff.ui.calendar.CalendarViewModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

import java.util.ArrayList;
import java.util.List;

public class MapFragment extends Fragment {

    private FragmentMapBinding binding;
    private FusedLocationProviderClient fusedLocationClient;
    private MapView mapView;
    private Marker currentMarker;
    private LocationCallback locationCallback;
    private CalendarViewModel calendarViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMapBinding.inflate(inflater, container, false);
        initMapView();
        observeData();
        return binding.getRoot();
    }

    private final List<Marker> markers = new ArrayList<>();

    private void observeData() {

        calendarViewModel = new ViewModelProvider(requireActivity()).get(CalendarViewModel.class);
        calendarViewModel.loadTasksOfToday();

        calendarViewModel.getTasks().observe(getViewLifecycleOwner(), tasks -> {
            markers.forEach(marker -> mapView.getOverlays().remove(marker));
            markers.clear();
            for (Task task : tasks) {
                Coordinate coordinate = task.getCoordinate();
                GeoPoint geoPoint = new GeoPoint(coordinate.getLatitude(), coordinate.getLongitude());
                Marker marker = new Marker(mapView);
                marker.setPosition(geoPoint);
                marker.setTitle(task.getTitle());
                markers.add(marker);
                mapView.getOverlays().add(marker);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        IMapController mapController = mapView.getController();
        mapController.setZoom(15f);

        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.getLastLocation().addOnSuccessListener(location -> {
                if (location != null) {
                    double lat = location.getLatitude();
                    double lon = location.getLongitude();
                    GeoPoint startPoint = new GeoPoint(lat, lon);
                    mapController.setCenter(startPoint);
                    addMarker(startPoint);
                }
            });
            startLocationUpdate();
        } else {
            ActivityCompat.requestPermissions(requireActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 100);
        }
    }


    private void initMapView() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity());
        mapView = binding.mapView;
        mapView.setTilesScaledToDpi(true);
        mapView.setBuiltInZoomControls(true);
        mapView.setMultiTouchControls(true);
        mapView.getController().setZoom(15f);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Configuration.getInstance().setUserAgentValue(requireActivity().getPackageName());
    }

    @SuppressLint("MissingPermission")
    private void startLocationUpdate() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(2000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                for (Location location : locationResult.getLocations()) {
                    if (location != null) {
                        double lat = location.getLatitude();
                        double lon = location.getLongitude();
                        addMarker(new GeoPoint(lat, lon));
                    }
                }
            }
        };

        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
    }


    private void addMarker(GeoPoint startPoint) {
        if (currentMarker != null) mapView.getOverlays().remove(currentMarker);

        currentMarker = new Marker(mapView);
        currentMarker.setPosition(startPoint);
        currentMarker.setTitle("Your Location");
        mapView.getOverlays().add(currentMarker);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (fusedLocationClient != null && locationCallback != null) {
            fusedLocationClient.removeLocationUpdates(locationCallback);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}