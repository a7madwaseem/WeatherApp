package com.opensooq.weatherapp;

import com.opensooq.weatherapp.ui.main.MainPresenter;
import com.opensooq.weatherapp.ui.main.MainView;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import io.reactivex.disposables.CompositeDisposable;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


/**
 * Created by a7mad on 6/22/2017.
 */

public class MainPresenterTest {
    @Mock
    MainView mMockMainMvpView;
    private MainPresenter mMainPresenter;

    @Before
    public void setUp() {
        mMainPresenter = new MainPresenter();
        mMainPresenter.attachView(mMockMainMvpView);
    }

    @After
    public void tearDown() {
        mMainPresenter.detachView();
    }

    @Test
    public void loadForCastReturnEmpty() {
        when(mMainPresenter.handleResponse(null)).then(null);

        verify(mMockMainMvpView, never()).hideSwipeToRefreshProgress();
        verify(mMockMainMvpView, never()).setUpDummyForecast();
    }
    /*Just Try :) but nothing work*/


}
