package com.opensooq.weatherapp.ui.locations;

import com.opensooq.weatherapp.data.model.realm.RealmLocations;
import com.opensooq.weatherapp.ui.base.MvpView;

import io.realm.RealmResults;

/**
 * Created by a7mad on 6/21/2017.
 */

public interface LocationsView extends MvpView {

    void showLocations(RealmResults<RealmLocations> allLocations);

    void showAlertDialog();

    void onNewLocationAdded(String locationName);

    void showToast(int message);
}
