package com.opensooq.weatherapp.ui.locations;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.opensooq.weatherapp.R;
import com.opensooq.weatherapp.data.local.RealmDb;
import com.opensooq.weatherapp.data.model.realm.RealmLocations;
import com.opensooq.weatherapp.ui.base.BaseActivity;
import com.opensooq.weatherapp.utils.DialogFactory;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.RealmResults;

public class LocationsActivity extends BaseActivity implements
        LocationsView,
        LocationsAdapter.OnRowLocationClicks {

    private LocationsPresenter locationsPresenter;
    private LocationsAdapter locationsAdapter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.rc_all_my_locations)
    RecyclerView allMyLocationsRecyclerView;

    @OnClick(R.id.btn_add_location)
    public void setOnAddLocationButtonClick() {

        createAddLocationDialog();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_list);

        ButterKnife.bind(this);

        locationsPresenter = new LocationsPresenter();
        locationsPresenter.attachView(this);
        locationsPresenter.loadLocation();

        initView();
    }

    private void initView() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle(R.string.locations);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        allMyLocationsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        allMyLocationsRecyclerView.setHasFixedSize(true);

    }

    @Override
    public void showLocations(RealmResults<RealmLocations> allLocations) {
        locationsAdapter = new LocationsAdapter(allLocations);
        locationsAdapter.setOnRowLocationClick(this);
        allMyLocationsRecyclerView.setAdapter(locationsAdapter);
    }

    @Override
    public void showAlertDialog() {
        DialogFactory.createRemoveAlertDialog(this, getString(R.string.cant_remove_this_location_you_must_have_at_least_one_location)).show();
    }

    @Override
    public void onNewLocationAdded(String locationName) {
        newLocationSet(locationName);
    }

    @Override
    public void showToast(int message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRowClick(String locationName) {
        newLocationSet(locationName);
    }

    @Override
    public void onRemoveClick(String locationName) {
        locationsPresenter.removeLocation(locationName);
        locationsAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        if (i == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void newLocationSet(String locationName) {
        Intent data = new Intent();
        data.putExtra(KEY_LOCATION_NAME, locationName);
        setResult(Activity.RESULT_OK, data);
        RealmDb.getInstance().setSelectedLocationName(locationName);
        finish();
    }

    public void createAddLocationDialog() {

        @SuppressLint("InflateParams") View dialogViewAddLocation = LayoutInflater.from(this).inflate(R.layout.dialog_view_add_location, null, false);

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this)
                .setView(dialogViewAddLocation);

        dialogViewAddLocation.findViewById(R.id.btn_save_location).setOnClickListener(v -> locationsPresenter.addNewLocation(((EditText) dialogViewAddLocation.findViewById(R.id.edt_location_name)).getText().toString()));

        alertDialog.create().show();

    }
}
