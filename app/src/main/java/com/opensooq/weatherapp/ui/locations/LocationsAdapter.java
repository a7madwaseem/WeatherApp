package com.opensooq.weatherapp.ui.locations;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.opensooq.weatherapp.R;
import com.opensooq.weatherapp.data.model.realm.RealmLocations;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.RealmResults;

/**
 * Created by a7mad on 6/19/2017.
 */

public class LocationsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private RealmResults<RealmLocations> locationsList;
    private OnRowLocationClicks onRowLocationClick;

    LocationsAdapter(RealmResults<RealmLocations> locationsList) {
        this.locationsList = locationsList;
    }

    void setOnRowLocationClick(OnRowLocationClicks onRowLocationClick) {
        this.onRowLocationClick = onRowLocationClick;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new LocationsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_location, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof LocationsViewHolder) {
            ((LocationsViewHolder) holder).locationNameTxt.setText(locationsList.get(position).getLocationName());
        }
    }

    @Override
    public int getItemCount() {
        return locationsList.size();
    }

    class LocationsViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txt_location_name)
        TextView locationNameTxt;

        @OnClick(R.id.row_location)
        void onRowLocationClick() {
            if (onRowLocationClick != null)
                onRowLocationClick.onRowClick(locationsList.get(getAdapterPosition()).getLocationName());
        }

        @OnClick(R.id.btn_remove_location)
        void onRemoveLocationClick() {
            if (onRowLocationClick != null)
                onRowLocationClick.onRemoveClick(locationsList.get(getAdapterPosition()).getLocationName());
        }

        LocationsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    interface OnRowLocationClicks {
        void onRowClick(String locationName);

        void onRemoveClick(String locationName);
    }

}
