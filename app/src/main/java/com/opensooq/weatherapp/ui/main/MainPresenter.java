package com.opensooq.weatherapp.ui.main;

import android.util.Log;

import com.opensooq.weatherapp.data.model.weatherapi.WeatherResponse;
import com.opensooq.weatherapp.data.remote.ApiClient;
import com.opensooq.weatherapp.ui.base.BasePresenter;
import com.opensooq.weatherapp.utils.ConnectivityReceiver;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import static android.content.ContentValues.TAG;

/**
 * Created by a7mad on 6/18/2017.
 */

public class MainPresenter extends BasePresenter<MainView> {

    private CompositeDisposable mCompositeDisposable;

    @Override
    public void attachView(MainView mvpView) {
        super.attachView(mvpView);
        mCompositeDisposable = new CompositeDisposable();
    }

   void loadWeatherData(String cityName) {
        if (cityName != null) {
            if (ConnectivityReceiver.isConnected()) {

                getMvpView().showForecastView();
                getMvpView().showSwipeToRefreshProgress();

                mCompositeDisposable.add(ApiClient.getClient().getWeather(cityName, NUMBER_OF_DAYS, FORMAT, API_KEY)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(this::handleResponse, this::handleError));
            } else {
                /*No Internet Connection*/
                getMvpView().hideSwipeToRefreshProgress();
                getMvpView().showInternetView();
            }
        }
    }

    public WeatherResponse handleResponse(WeatherResponse weatherResponse) {
        getMvpView().hideSwipeToRefreshProgress();
        getMvpView().setUpForecast(weatherResponse);
        return weatherResponse;
    }

    private void handleError(Throwable throwable) {
        getMvpView().hideSwipeToRefreshProgress();
        getMvpView().setUpDummyForecast();
        Log.e(TAG, "handleError: " + throwable.getLocalizedMessage());
    }


}
