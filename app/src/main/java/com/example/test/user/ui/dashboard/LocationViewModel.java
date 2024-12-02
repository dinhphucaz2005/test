package com.example.test.user.ui.dashboard;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.test.dto.CoordinateResponse;
import com.example.test.dto.GeocodeResponse;
import com.example.test.dto.LocationDto;
import com.example.test.dto.PlaceResponse;
import com.example.test.model.Coordinate;
import com.example.test.model.Location;
import com.example.test.remote.ApiService;
import com.example.test.remote.RetrofitClient;
import com.google.android.gms.tasks.OnSuccessListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LocationViewModel extends ViewModel {

    private final Handler handler = new Handler(Looper.getMainLooper());
    private Runnable workRunnable;

    private static final long DEBOUNCE_DELAY = 500; // milliseconds
    private final MutableLiveData<PlaceResponse> placeResponse = new MutableLiveData<>();

    public LiveData<PlaceResponse> observePlaceResponse() {
        return placeResponse;
    }

    private final ApiService apiService;

    public LocationViewModel() {
        apiService = RetrofitClient.getRetrofit().create(ApiService.class);
    }

    public void debounceGetPlaceComplete(String query) {
        if (workRunnable != null) {
            handler.removeCallbacks(workRunnable);
        }

        workRunnable = () -> callApi(query);

        handler.postDelayed(workRunnable, DEBOUNCE_DELAY);
    }

    private void callApi(String query) {
        apiService.autoComplete(query, RetrofitClient.API_KEY).enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<PlaceResponse> call, @NonNull Response<PlaceResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    placeResponse.postValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<PlaceResponse> call, @NonNull Throwable t) {
                placeResponse.setValue(null);
            }
        });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        handler.removeCallbacksAndMessages(null);
    }

    private final MutableLiveData<Location> location = new MutableLiveData<>(new Location());

    public LiveData<Location> observeLocation() {
        return location;
    }

    public void loadLocation(Location location) {

        new Thread(() -> {
            try {
                apiService.detail(location.getPlaceId(), RetrofitClient.API_KEY).enqueue(new Callback<>() {
                    @Override
                    public void onResponse(@NonNull Call<CoordinateResponse> call, @NonNull Response<CoordinateResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            double lat = response.body().getResult().getGeometry().getLocation().getLat();
                            double lng = response.body().getResult().getGeometry().getLocation().getLng();
                            location.setCoordinate(new Coordinate(lat, lng));
                            Location newLocation = new Location(location.getTitle(), location.getAddress(), location.getPlaceId(), new Coordinate(lat, lng));
                            System.out.println("Location from goong map: " + newLocation.getCoordinate().getLatitude() + " " + newLocation.getCoordinate().getLongitude());
                            LocationViewModel.this.location.postValue(newLocation);
                        }

                    }

                    @Override
                    public void onFailure(@NonNull Call<CoordinateResponse> call, @NonNull Throwable t) {
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

    }

    public void getLocationByCoordinate(Coordinate coordinate, OnSuccessListener<Location> listener) {
        new Thread(() -> apiService.geocode(coordinate.getLatitude() + "," + coordinate.getLongitude(), RetrofitClient.API_KEY).enqueue(new Callback<>() {

            @Override
            public void onResponse(Call<GeocodeResponse> call, Response<GeocodeResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Location location = new Location();
                    location.setCoordinate(coordinate);
                    location.setPlaceId(response.body().getResults().get(0).getPlaceId());
                    location.setTitle(response.body().getResults().get(0).getFormattedAddress());
                    location.setAddress(response.body().getResults().get(0).getFormattedAddress());
                    listener.onSuccess(location);
                }
            }

            @Override
            public void onFailure(Call<GeocodeResponse> call, Throwable t) {

            }
        })).start();
    }
}
