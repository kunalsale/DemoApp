package com.example.hp.demoapp.network;

import com.example.hp.demoapp.model.ResponseVO;


import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by hp on 24-08-2017.
 */

public interface APIService
{
    @GET("city/")
    Observable<ResponseVO> getCities();
}
