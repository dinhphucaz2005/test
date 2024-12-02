package com.example.test.user.ui.dashboard;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.test.R;
import com.example.test.databinding.FragmentLocationBinding;
import com.example.test.dto.Prediction;
import com.example.test.model.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class LocationFragment extends Fragment {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    private FragmentLocationBinding binding;
    private DashboardViewModel dashboardViewModel;
    private LocationViewModel locationViewModel;
    private LocationAdapter locationAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentLocationBinding.inflate(inflater, container, false);
        dashboardViewModel = new ViewModelProvider(requireActivity()).get(DashboardViewModel.class);
        locationViewModel = new ViewModelProvider(requireActivity()).get(LocationViewModel.class);
        locationAdapter = new LocationAdapter(location -> {
            locationViewModel.loadLocation(location);
            NavController navController = Navigation.findNavController(binding.getRoot());
            navController.navigate(R.id.user_action_navigation_location_to_setOnMapFragment);
        });
        binding.recyclerViewLocation.setAdapter(locationAdapter);
        setEvents();
        observeData();
        return binding.getRoot();
    }

    private void observeData() {
        dashboardViewModel.getArticle().observe(getViewLifecycleOwner(), article -> {
            if (article != null) {
                String address = Optional
                        .ofNullable(article.getLocation())
                        .map(Location::getAddress)
                        .orElse("");
                binding.edtLocation.setText(address);
            }
        });
        locationViewModel.observePlaceResponse().observe(getViewLifecycleOwner(), placeResponse -> {
            if (placeResponse != null && placeResponse.getPredictions() != null) {
                List<Location> locations = new ArrayList<>();
                for (Prediction prediction : placeResponse.getPredictions()) {
                    if (prediction == null) continue;
                    if (prediction.getStructuredFormatting() != null) {
                        String title = prediction.getStructuredFormatting().getMainText();
                        String address = prediction.getStructuredFormatting().getSecondaryText();
                        String placeId = prediction.getPlaceid();
                        locations.add(new Location(title, address, placeId, null));
                    }
                }

                System.out.println("locations: " + locations.size());
                locationAdapter.setLocations(locations);

            } else {
                System.out.println("No predictions available");
            }
        });

    }

    private void setEvents() {
        binding.btnBack.setOnClickListener(view -> requireActivity().onBackPressed());

        // observe text change
        binding.edtLocation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                locationViewModel.debounceGetPlaceComplete(s.toString().trim());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.appCompatButton.setOnClickListener(v -> {
            Location location = locationViewModel.observeLocation().getValue();
            if (location != null) {
                dashboardViewModel.setLocation(location);
                requireActivity().onBackPressed();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void getLocation() {


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLocation();
            }
        }
    }
}