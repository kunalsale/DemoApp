package com.example.hp.demoapp;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hp.demoapp.adapter.viewholders.CitiesRecyclerAdapter;
import com.example.hp.demoapp.adapter.viewholders.DividerItemDecorator;
import com.example.hp.demoapp.adapter.viewholders.OnRecyclerClickedListener;
import com.example.hp.demoapp.database.CityDatabaseHelper;
import com.example.hp.demoapp.database.DatabaseOperationController;
import com.example.hp.demoapp.database.FetchCitiesAsyncTask;
import com.example.hp.demoapp.database.InsertDataAsynctask;
import com.example.hp.demoapp.database.OnFetchDataListener;
import com.example.hp.demoapp.model.CityVO;
import com.example.hp.demoapp.network.APIController;
import com.example.hp.demoapp.network.OnWebServiceListener;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements TextWatcher,
                                                               OnWebServiceListener,
                                                               OnFetchDataListener,
                                                               OnRecyclerClickedListener
{
    private ArrayList<CityVO>           mCityVOs;
    private ArrayList<CityVO>           mPermanentCityVOs;
    private ProgressDialog              mProgressDialog;
    private CitiesRecyclerAdapter       mCityRecyclerAdapter;
    private FetchCitiesAsyncTask        mFetchCitiesAsyncTask;
    private InsertDataAsynctask         mInsertDataAsynctask;
    private APIController               mApiController;
    private CityDatabaseHelper          mCityDatabaseHelper;
    private DatabaseOperationController mController;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
        fetchData();
    }

    private void initialize()
    {
        mCityVOs                    = new ArrayList<>();
        mPermanentCityVOs           = new ArrayList<>();
        mProgressDialog             = new ProgressDialog(this , ProgressDialog.STYLE_SPINNER);
        mCityRecyclerAdapter        = new CitiesRecyclerAdapter(mCityVOs);
        mCityDatabaseHelper         = new CityDatabaseHelper(this);
        mApiController              = new APIController();
        mController                 = new DatabaseOperationController(mCityDatabaseHelper);
        mProgressDialog.setMessage(getResources().getString(R.string.progress_message));
        RecyclerView recyclerCities = (RecyclerView) findViewById(R.id.recyclerCities);
        EditText edtCityName        = (EditText)findViewById(R.id.edtEnterCity);

        recyclerCities.addItemDecoration(new DividerItemDecorator(this));
        recyclerCities.setLayoutManager(new LinearLayoutManager(this));
        recyclerCities.setAdapter(mCityRecyclerAdapter);
        edtCityName.addTextChangedListener(this);
    }

    // checks whether list is saved in the database or not if not then fetch the data from API
    private void fetchData()
    {
        mProgressDialog.show();
        mFetchCitiesAsyncTask = new FetchCitiesAsyncTask(mController);
        mFetchCitiesAsyncTask.registerListener(this);
        mFetchCitiesAsyncTask.execute();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        mCityRecyclerAdapter.registerListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after)
    {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count)
    {
        final String query = s.toString();
        final List<CityVO> cityVOs = new ArrayList<>();
        Observable.fromIterable(mPermanentCityVOs)
                .filter(new Predicate<CityVO>() {
                    @Override
                    public boolean test(@NonNull CityVO cityVO) throws Exception {
                        return cityVO.getName().toLowerCase().contains(query.toLowerCase());
                    }
                })
                .map(new Function<CityVO, List<CityVO>>()
                {
                    @Override
                    public List<CityVO> apply(@NonNull CityVO cityVO) throws Exception
                    {
                        cityVOs.add(cityVO);
                        return cityVOs;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<CityVO>>()
                {
                    @Override
                    public void onError(Throwable e)
                    {
                    }

                    @Override
                    public void onComplete()
                    {

                    }

                    @Override
                    public void onSubscribe(@NonNull Disposable d)
                    {

                    }

                    @Override
                    public void onNext(@NonNull List<CityVO> cityVOs)
                    {
                        mCityVOs.clear();
                        mCityVOs.addAll(cityVOs);
                        mCityRecyclerAdapter.notifyDataSetChanged();
                    }
                });
    }

    @Override
    public void afterTextChanged(Editable s)
    {
    }

    @Override
    public void onWebServiceSuccess(List<CityVO> cityVOs)
    {
        stopProgressDialog();
        if(cityVOs != null)
        {
            mCityVOs.clear();
            mCityVOs.addAll(cityVOs);
            mPermanentCityVOs.addAll(cityVOs);
            mCityRecyclerAdapter.notifyDataSetChanged();
            mInsertDataAsynctask = new InsertDataAsynctask(mController , mCityVOs);
            mInsertDataAsynctask.execute();
        }
    }

    @Override
    public void onWebServiceFailed()
    {
        stopProgressDialog();
        Toast.makeText(this, getString(R.string.error_message), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDBFetchedSuccess(List<CityVO> cityVOs)
    {
        stopProgressDialog();
        if(cityVOs != null)
        {
            mCityVOs.clear();
            mCityVOs.addAll(cityVOs);
            mPermanentCityVOs.addAll(cityVOs);
            mCityRecyclerAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onDBFetchFialed()
    {
        mApiController.registerListener(this);
        mApiController.fetchData();
    }

    private void stopProgressDialog()
    {
        if(mProgressDialog!=null && mProgressDialog.isShowing())
        {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void onItemClicked(int position)
    {
        Toast.makeText(this, getString(R.string.item_clicked_message)+" - "+mCityVOs.get(position).getName(),Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        mApiController.unregisterListener();
        mCityRecyclerAdapter.unregisterListener();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        mCityVOs             = null;
        mCityDatabaseHelper  = null;
        mApiController       = null;
        mController          = null;
        mCityRecyclerAdapter = null;
        if(mFetchCitiesAsyncTask != null)
        {
            mFetchCitiesAsyncTask.cancel(true);
            mFetchCitiesAsyncTask.unregisterListener();
            mFetchCitiesAsyncTask = null;
        }
        if(mInsertDataAsynctask != null)
        {
            mInsertDataAsynctask .cancel(true);
            mInsertDataAsynctask = null;
        }
    }
}
