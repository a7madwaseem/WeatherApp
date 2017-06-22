package com.opensooq.weatherapp.ui.locations;

import android.util.Log;

import com.opensooq.weatherapp.R;
import com.opensooq.weatherapp.data.local.RealmDb;
import com.opensooq.weatherapp.data.model.realm.RealmLocations;
import com.opensooq.weatherapp.ui.base.BasePresenter;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.realm.RealmResults;


/**
 * Created by a7mad on 6/21/2017.
 */

class LocationsPresenter extends BasePresenter<LocationsView> {

    private static final String TAG = LocationsPresenter.class.getSimpleName();

    void loadLocation() {
        Observable<RealmResults<RealmLocations>> listObservable = Observable.just(RealmDb.getInstance().getAllLocations());
        listObservable.subscribe(new Observer<RealmResults<RealmLocations>>() {
            @Override
            public void onSubscribe(Disposable disposable) {
                Log.d(TAG, "onSubscribe");
            }

            @Override
            public void onNext(RealmResults<RealmLocations> realmLocations) {
                getMvpView().showLocations(realmLocations);
            }

            @Override
            public void onError(Throwable throwable) {
                Log.e(TAG, "onError: ".concat(throwable.getMessage()));
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete");

            }
        });

    }

    void removeLocation(String locationName) {
        if (RealmDb.getInstance().canDeleteMoreItem())
            RealmDb.getInstance().deleteLocation(locationName);
        else
            getMvpView().showAlertDialog();
    }

    void addNewLocation(String locationName) {
        if (!locationName.isEmpty()) {
            RealmDb.getInstance().insertNewLocation(new RealmLocations(locationName, false));
            RealmDb.getInstance().setSelectedLocationName(locationName);
            getMvpView().onNewLocationAdded(locationName);
        } else {
            getMvpView().showToast(R.string.please_enter_location_name);
        }
    }
}
