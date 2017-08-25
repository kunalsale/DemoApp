package com.example.hp.demoapp.database;

import android.os.AsyncTask;
import android.util.Log;

import com.example.hp.demoapp.model.CityVO;

import java.util.List;

/**
 * Created by kunal.sale on 25-08-2017.
 */

public class InsertDataAsynctask extends AsyncTask<Void,Void,Long>
{
    private List<CityVO>                mCityVos;
    private DatabaseOperationController mController;

    public InsertDataAsynctask(DatabaseOperationController databaseOperationController, List<CityVO> cityVOList)
    {
        mController = databaseOperationController;
        mCityVos    = cityVOList;
    }

    @Override
    protected Long doInBackground(Void... params)
    {
        for (CityVO cityVO : mCityVos)
        {
            if(!isCancelled())
            {
                if(mController.insertData(cityVO))
                {
                    Log.i("MainActivity",cityVO.getName());
                }
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Long aLong)
    {
        super.onPostExecute(aLong);
        mCityVos    = null;
        mController = null;
    }
}
