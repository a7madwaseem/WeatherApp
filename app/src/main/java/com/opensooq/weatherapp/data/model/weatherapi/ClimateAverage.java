package com.opensooq.weatherapp.data.model.weatherapi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by a7mad on 6/20/2017.
 */

public class ClimateAverage {
    @SerializedName("month")
    @Expose
    private List<Month> month = null;

    public List<Month> getMonth() {
        return month;
    }

    public void setMonth(List<Month> month) {
        this.month = month;
    }
}
