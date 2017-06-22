package com.opensooq.weatherapp.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.opensooq.weatherapp.R;
import com.opensooq.weatherapp.WeatherApp;
import com.opensooq.weatherapp.data.local.RealmDb;
import com.opensooq.weatherapp.data.model.weatherapi.Hourly;
import com.opensooq.weatherapp.data.model.weatherapi.WeatherResponse;
import com.opensooq.weatherapp.ui.base.BaseActivity;
import com.opensooq.weatherapp.ui.locations.LocationsActivity;
import com.opensooq.weatherapp.utils.ConnectivityReceiver;
import com.opensooq.weatherapp.utils.DateTimeUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;

public class MainActivity extends BaseActivity implements
        MainView,
        ConnectivityReceiver.ConnectivityReceiverListener,
        SwipeRefreshLayout.OnRefreshListener {

    private String[] days = new String[]{"3 AM", "6 AM", "9 AM", "12 PM", "3 PM", "6 PM", "9 PM", "12 AM"};

    private MainPresenter mainPresenter;
    private ForecastAdapter forecastAdapter;

    @BindView(R.id.layout_swipe_to_refresh)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.view_main)
    LinearLayout mainView;

    @BindView(R.id.layout_internet_connection)
    RelativeLayout internetConnectionLayout;

    @BindView(R.id.txt_city_name)
    TextView cityNameTxt;
    @BindView(R.id.txt_today_desc)
    TextView todayDescTxt;
    @BindView(R.id.txt_today_date)
    TextView todayDateTxt;
    @BindView(R.id.txt_today_temp)
    TextView todayTempTxt;

    @BindView(R.id.img_sad_smile_for_weather_data)
    ImageView sadSmileImgForWeather;

    @BindView(R.id.rc_forecast)
    RecyclerView forecastRecyclerView;

    @BindView(R.id.view_chart_line)
    LineChartView lineChartView;

    @OnClick(R.id.layout_internet_connection)
    public void setViewInterestConnection() {
        mainPresenter.loadWeatherData(RealmDb.getInstance().getSelectedLocationName());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        mainPresenter = new MainPresenter();
        mainPresenter.attachView(this);
        mainPresenter.loadWeatherData(RealmDb.getInstance().getSelectedLocationName());

        initViews();
    }

    private void initViews() {
        swipeRefreshLayout.setOnRefreshListener(this);

        forecastRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        forecastRecyclerView.setHasFixedSize(true);
        /*Prevent recycler view from scrolling because it inside nestedScrollView*/
        forecastRecyclerView.setNestedScrollingEnabled(false);

    }

    private void generateLineChartData(List<Hourly> hourlyList) {
        int numValues = hourlyList.size();

        List<AxisValue> axisValues = new ArrayList<>();
        List<PointValue> values = new ArrayList<>();

        for (int i = 0; i < numValues; ++i) {
            values.add(new PointValue(i, Integer.parseInt(hourlyList.get(i).getTempC())).setLabel(hourlyList.get(i).getTempC().concat(CELSIUS_CHAR)));
            axisValues.add(new AxisValue(i).setLabel(days[i]));
        }

        Line line = new Line(values);
        line.setColor(ContextCompat.getColor(this, R.color.colorGreen)).setCubic(true);
        line.setHasLabels(true);

        List<Line> lines = new ArrayList<>();
        lines.add(line);

        LineChartData lineData = new LineChartData(lines);
        lineData.setAxisXBottom(new Axis(axisValues));

        lineChartView.setLineChartData(lineData);

        // For build-up animation you have to disable viewport recalculation.
        lineChartView.setViewportCalculationEnabled(false);

        // And set initial max viewport and current viewport- remember to set viewports after data.
        Viewport v = new Viewport(0, 50, 7, 0);
        lineChartView.setMaximumViewport(v);
        lineChartView.setCurrentViewport(v);

        lineChartView.setZoomType(ZoomType.HORIZONTAL);
    }

    @Override
    public void setUpForecast(WeatherResponse weatherResponse) {

        cityNameTxt.setText(weatherResponse.getData().getRequest().get(TODAY).getQuery());
        todayDescTxt.setText(weatherResponse.getData().getCurrentCondition().get(TODAY).getWeatherDesc().get(TODAY).getValue());
        todayDateTxt.setText(DateTimeUtil.convertStringToDate(weatherResponse.getData().getWeather().get(TODAY).getDate()));
        todayTempTxt.setText(weatherResponse.getData().getCurrentCondition().get(TODAY).getTempC().concat(CELSIUS_CHAR));
        sadSmileImgForWeather.setVisibility(View.GONE);

        //*setup forecast recycler view*//
        forecastAdapter = new ForecastAdapter(weatherResponse.getData().getWeather());
        forecastRecyclerView.setAdapter(forecastAdapter);
        //*setup lineChartView *//
        generateLineChartData(weatherResponse.getData().getWeather().get(TODAY).getHourly());
    }

    @Override
    public void setUpDummyForecast() {
        cityNameTxt.setText(R.string.select_or_add_correct_city_name);
        todayDescTxt.setText(getString(R.string.no_weather_data_for).concat(RealmDb.getInstance().getSelectedLocationName()));
        todayTempTxt.setText(getString(R.string.zeroTemp).concat(CELSIUS_CHAR));
        sadSmileImgForWeather.setVisibility(View.VISIBLE);
        //*setup forecast recycler view*//
        forecastAdapter = new ForecastAdapter(new ArrayList<>());
        forecastRecyclerView.setAdapter(forecastAdapter);
        //*setup lineChartView *//

    }

    @Override
    public void showInternetView() {
        mainView.setVisibility(View.GONE);
        internetConnectionLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void showForecastView() {
        mainView.setVisibility(View.VISIBLE);
        internetConnectionLayout.setVisibility(View.GONE);
    }

    @Override
    public void showSwipeToRefreshProgress() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideSwipeToRefreshProgress() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        if (isConnected) {
            showForecastView();
        } else {
            showInternetView();
        }


    }

    @Override
    public void onRefresh() {
        mainPresenter.loadWeatherData(RealmDb.getInstance().getSelectedLocationName());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        if (i == R.id.menu_edit_location) {
            startActivityForResult(new Intent(MainActivity.this, LocationsActivity.class), REQUEST_CODE);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null)
            mainPresenter.loadWeatherData(data.getExtras().getString(KEY_LOCATION_NAME));
    }

    @Override
    protected void onResume() {
        super.onResume();
        /*for onNetworkConnectionChanged*/
        WeatherApp.getInstance().setConnectivityListener(this);
    }

}
