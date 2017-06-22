package com.opensooq.weatherapp.data.remote;

import com.opensooq.weatherapp.data.model.weatherapi.WeatherResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by a7mad on 6/18/2017.
 */

public interface WeatherService {

    @GET("weather.ashx")
    Observable<WeatherResponse> getWeather(@Query("q") String q, @Query("num_of_days") String numOfDays, @Query("format") String format, @Query("key") String apiKey);
}
