package com.example.test.remote;


import com.example.test.dto.CoordinateResponse;
import com.example.test.dto.GeocodeResponse;
import com.example.test.dto.PlaceResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("Place/AutoComplete")
    Call<PlaceResponse> autoComplete(@Query("input") String query, @Query("api_key") String apiKey);

    @GET("Place/Detail")
    Call<CoordinateResponse> detail(@Query("place_id") String placeId, @Query("api_key") String apiKey);


    @GET("geocode")
    Call<GeocodeResponse> geocode(@Query("latlng") String latlng, @Query("api_key") String apiKey);
}
