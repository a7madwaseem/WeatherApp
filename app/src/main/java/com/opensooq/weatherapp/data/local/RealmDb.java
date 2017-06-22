package com.opensooq.weatherapp.data.local;

import com.opensooq.weatherapp.data.model.realm.RealmLocations;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by a7mad on 6/21/2017.
 */

public class RealmDb {

    private static RealmDb instance;
    private Realm db;

    private RealmDb() {
        db = Realm.getDefaultInstance();
    }

    public static RealmDb getInstance() {
        if (instance == null) instance = new RealmDb();
        return instance;
    }

    private Realm getRealmDb() {

        return db;
    }

    public boolean hasLocations() {
        return !db.where(RealmLocations.class).findAll().isEmpty();
    }

    public void insertNewLocation(RealmLocations location) {

        getRealmDb().executeTransaction(realm ->
                realm.copyToRealmOrUpdate(location));
    }

    public RealmResults<RealmLocations> getAllLocations() {
        return getRealmDb().where(RealmLocations.class).findAll();
    }

    public void deleteLocation(String locationName) {
        getRealmDb().executeTransaction(realm -> {
            RealmResults<RealmLocations> rows = realm.where(RealmLocations.class)
                    .equalTo("locationName", locationName).findAll();
            rows.clear();
        });
    }

    public void setSelectedLocationName(String locationName) {
        getRealmDb().beginTransaction();

        RealmLocations toEdit = getRealmDb().where(RealmLocations.class).equalTo("locationSelected", true).findFirst();
        if (toEdit != null)
            toEdit.setLocationSelected(false);

        toEdit = getRealmDb().where(RealmLocations.class).equalTo("locationName", locationName).findFirst();
        if (toEdit != null)
            toEdit.setLocationSelected(true);

        getRealmDb().commitTransaction();
    }

    public String getSelectedLocationName() {
        RealmLocations currentLocation;

        currentLocation = getRealmDb().where(RealmLocations.class)
                .equalTo("locationSelected", true).findFirst();

        if (currentLocation != null)
            return currentLocation.getLocationName();
        else
            return null;
    }

    public boolean canDeleteMoreItem() {

        RealmResults<RealmLocations> rows = getRealmDb().where(RealmLocations.class).findAll();

        return rows.size() > 1;
    }
}
