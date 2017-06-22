package com.opensooq.weatherapp.data.model.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by a7mad on 6/21/2017.
 */

public class RealmLocations extends RealmObject {

    @PrimaryKey
    private
    String locationName;
    private boolean locationSelected;

    public RealmLocations() {

      /*Needed for realm*/

    }

    public RealmLocations(String locationName, boolean locationSelected) {
        this.locationName = locationName;
        this.locationSelected = locationSelected;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public boolean isLocationSelected() {
        return locationSelected;
    }

    public void setLocationSelected(boolean locationSelected) {
        this.locationSelected = locationSelected;
    }
}
