package com.prathanbomb.tamboon.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.prathanbomb.tamboon.service.AppDatabase
import com.prathanbomb.tamboon.service.model.Charity
import com.prathanbomb.tamboon.service.repository.TamboonRepository



/**
 * Created by prathanbomb on 9/14/2017 AD.
 */

class CharityListViewModel(application: Application) : AndroidViewModel(application) {

    private var appDatabase: AppDatabase = AppDatabase.getDatabase(application)!!
    private var charityList: LiveData<List<Charity>>

    init {
        charityList = appDatabase.charityModel().getAllCharityItems
    }

    fun getCharityList(): LiveData<List<Charity>> {
        return charityList
    }

    fun charityListObservable(): LiveData<List<Charity>> {
        return TamboonRepository.instance.charityList()
    }

}
