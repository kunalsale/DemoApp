package com.example.hp.demoapp.network;

import com.example.hp.demoapp.model.CityVO;

import java.util.List;

/**
 * Created by kunal.sale on 25-08-2017.
 * Listener to
 */

public interface OnWebServiceListener
{
    void onWebServiceSuccess(List<CityVO> cityVOs);
    void onWebServiceFailed();
}
