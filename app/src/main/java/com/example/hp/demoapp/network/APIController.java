package com.example.hp.demoapp.network;

import android.util.Log;

import com.example.hp.demoapp.model.CityVO;
import com.example.hp.demoapp.model.ResponseVO;

import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by kunal.sale on 25-08-2017.
 *
 * This class is used to fetch data from the api
 */

public class APIController
{
    private final static String  BASE_URL = "https://api.aasaanjobs.com/api/v4/";
    private OnWebServiceListener onWebServiceListener;

    public void registerListener(OnWebServiceListener onWebServiceListener)
    {
        this.onWebServiceListener = onWebServiceListener;
    }

    // To release the listener to prevent memory leak
    public void unregisterListener()
    {
        onWebServiceListener = null;
    }

    //method to fetch the data
    public void fetchData()
    {
        APIService apiService = RetrofitClient
                .getClient(BASE_URL)
                .create(APIService.class);

        apiService.getCities()
                .subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseVO>()
                {
                    @Override
                    public void onSubscribe(Disposable d)
                    {
                    }

                    @Override
                    public void onNext(ResponseVO responseVO)
                    {
                        ArrayList<CityVO> cityVOs = responseVO.getCities();
                        if (cityVOs != null)
                        {
                            onWebServiceListener.onWebServiceSuccess(cityVOs);
                        }
                    }

                    @Override
                    public void onError(Throwable t)
                    {
                        onWebServiceListener.onWebServiceFailed();
                    }

                    @Override
                    public void onComplete()
                    {
                    }
                });
    }
}
