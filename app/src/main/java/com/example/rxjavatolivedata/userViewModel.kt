package com.example.rxjavatolivedata

import android.app.Application
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.internal.operators.flowable.FlowableZip
import io.reactivex.schedulers.Schedulers
import java.util.function.Function

class userViewModel constructor(application: Application) : AndroidViewModel(application){

    private  var userDB: userDB? = null
    private var livedata : Flowable<List<model>>
    private  var postData : MediatorLiveData<AuthResource<List<model>>> = MediatorLiveData()
    private var compositeDisposable : CompositeDisposable

    init {
        userDB = UserDataBase.getInstance(application).getUsersDao()
        livedata = userDB!!.getAllUsers()
        compositeDisposable = CompositeDisposable()
    }


    fun getAllUsers() : LiveData<AuthResource<List<model>>>{
        var livadataa : LiveData<AuthResource<List<model>>> = LiveDataReactiveStreams.fromPublisher(livedata.observeOn(AndroidSchedulers.mainThread())
            .onErrorReturn(object : io.reactivex.functions.Function<Throwable,List<model>>{
                override fun apply(t: Throwable): List<model> {
                    TODO("Not yet implemented")
                }
            }).map(object : io.reactivex.functions.Function<List<model>,AuthResource<List<model>>>{
                override fun apply(t: List<model>): AuthResource<List<model>> {
                    if(t.isNullOrEmpty()){
                        return Error("Something wrong..",t)!!
                    }
                    return success(t)
                }
            }))
        postData.addSource(livadataa, Observer {
            postData.value = it
            postData.removeSource(livadataa)

        })
        return postData
    }

    fun insertUsers(model: model){
       val disposable  =  userDB!!.insertUsers(model).subscribeOn(Schedulers.io())
           .observeOn(AndroidSchedulers.mainThread()).subscribe {
               Log.d("TAG","Data has been inserted")
           }
        compositeDisposable.add(disposable)

    }


    fun removeDuplicates(){
        val disposable = userDB!!.removeDuplicates().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                Log.d("TAG","Duplicates Removed..")
            }
        compositeDisposable.addAll(disposable)
    }


}