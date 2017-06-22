package com.opensooq.weatherapp.data.model.weatherapi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by a7mad on 6/20/2017.
 */

public class WeatherDesc_ {
    @SerializedName("value")
    @Expose
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
