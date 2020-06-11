package com.example.rxjavatolivedata

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MediatorLiveData
import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.internal.operators.flowable.FlowableZip
import io.reactivex.schedulers.Schedulers

class userViewModel constructor(application: Application) : AndroidViewModel(application){

    private  var userDB: userDB? = null
    private var livedata : Flowable<List<model>>
    private lateinit var postData : MediatorLiveData<List<model>>
    private var compositeDisposable : CompositeDisposable

    init {
        userDB = UserDataBase.getInstance(application).getUsersDao()
        livedata = userDB!!.getAllUsers()
        compositeDisposable = CompositeDisposable()
    }


    fun getAllUsers() : LiveData<List<model>>{
        return LiveDataReactiveStreams.fromPublisher(livedata)
    }

    fun insertUsers(model: model){
       val disposable  =  userDB!!.insertUsers(model).subscribeOn(Schedulers.io())
           .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe {
               Log.d("TAG","Data has been inserted")
           }
        compositeDisposable.add(disposable)

    }


}