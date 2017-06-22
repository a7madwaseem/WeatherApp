package com.opensooq.weatherapp.ui.main;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.opensooq.weatherapp.R;
import com.opensooq.weatherapp.data.model.weatherapi.Weather;
import com.opensooq.weatherapp.utils.Constants;
import com.opensooq.weatherapp.utils.DateTimeUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by a7mad on 6/19/2017.
 */

public class ForecastAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Weather> forecastList;

    ForecastAdapter(List<Weather> forecastList) {
        this.forecastList = forecastList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ForecastViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_forecast, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ForecastViewHolder) {

            ((ForecastViewHolder) holder).dayNameTxt.setText(DateTimeUtil.getDayNameFromDateString(forecastList.get(position).getDate()));
            ((ForecastViewHolder) holder).todayDateTxt.setText(forecastList.get(position).getDate());
            ((ForecastViewHolder) holder).tempMaxTxt.setText(forecastList.get(position).getMaxtempC().concat(Constants.CELSIUS_CHAR));
            ((ForecastViewHolder) holder).tempMinTxt.setText(forecastList.get(position).getMintempC().concat(Constants.CELSIUS_CHAR));
        }
    }

    @Override
    public int getItemCount() {
        return forecastList.size();
    }



    class ForecastViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txt_max_temp)
        TextView tempMaxTxt;
        @BindView(R.id.txt_min_temp)
        TextView tempMinTxt;
        @BindView(R.id.txt_day_name)
        TextView dayNameTxt;
        @BindView(R.id.txt_today_date)
        TextView todayDateTxt;
        @BindView(R.id.row_forecast)
        LinearLayout rowForecast;
        
        ForecastViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
