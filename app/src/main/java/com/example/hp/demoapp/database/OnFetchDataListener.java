package com.example.hp.demoapp.database;

import com.example.hp.demoapp.model.CityVO;

import java.util.List;

/**
 * Created by kunal.sale on 25-08-2017.
 *
 * Listener to notify database operation results
 */

public interface OnFetchDataListener
{
    void onDBFetchedSuccess(List<CityVO> cityVOs);
    void onDBFetchFialed();
}
