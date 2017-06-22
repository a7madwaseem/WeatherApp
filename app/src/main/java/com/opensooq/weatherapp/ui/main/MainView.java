package com.opensooq.weatherapp.ui.main;

import com.opensooq.weatherapp.data.model.weatherapi.WeatherResponse;
import com.opensooq.weatherapp.ui.base.MvpView;


/**
 * Created by a7mad on 6/18/2017.
 */

public interface MainView extends MvpView {

    void setUpForecast(WeatherResponse weatherResponse);

    void showInternetView();

    void showForecastView();

    void showSwipeToRefreshProgress();

    void hideSwipeToRefreshProgress();

    void setUpDummyForecast();
}
