package com.opensooq.weatherapp.data.remote;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by a7mad on 6/19/2017.
 */

public class ApiClient {

    private ApiClient() {
        /*private constructor to hide the implicit public one*/
    }

    private static WeatherService requestInterface;

    public static WeatherService getClient() {
        if (requestInterface == null) {

            String baseUrl = "http://api.worldweatheronline.com/premium/v1/";

            requestInterface = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(WeatherService.class);
        }
        return requestInterface;
    }
}
