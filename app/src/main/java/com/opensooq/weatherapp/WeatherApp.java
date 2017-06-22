package com.opensooq.weatherapp;

import android.app.Application;

import com.opensooq.weatherapp.data.local.RealmDb;
import com.opensooq.weatherapp.data.model.realm.RealmLocations;
import com.opensooq.weatherapp.utils.ConnectivityReceiver;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by a7mad on 6/19/2017.
 */

public class WeatherApp extends Application {

    private static WeatherApp mInstance;

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;

        dbConfig();
    }

    private void dbConfig() {
            /*Realm Db Configuration*/
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this)
                .name(Realm.DEFAULT_REALM_NAME).schemaVersion(1)
                .deleteRealmIfMigrationNeeded()
                .build();

        Realm.setDefaultConfiguration(realmConfiguration);

        /*Insert default locations*/
        if (!RealmDb.getInstance().hasLocations()) {
            RealmDb.getInstance().insertNewLocation(new RealmLocations("Amman", true));
            RealmDb.getInstance().insertNewLocation(new RealmLocations("London", false));
            RealmDb.getInstance().insertNewLocation(new RealmLocations("New York", false));
            RealmDb.getInstance().insertNewLocation(new RealmLocations("Barcelona", false));
        }
    }

    public static synchronized WeatherApp getInstance() {
        return mInstance;
    }

    public static void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {
        ConnectivityReceiver.connectivityReceiverListener = listener;
    }
}
