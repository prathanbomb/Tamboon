package com.prathanbomb.tamboon.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData

import com.prathanbomb.tamboon.service.model.Charity
import com.prathanbomb.tamboon.service.model.Request
import com.prathanbomb.tamboon.service.model.Result
import com.prathanbomb.tamboon.service.repository.TamboonRepository

/**
 * Created by prathanbomb on 9/14/2017 AD.
 */

class TamboonViewModel(application: Application) : AndroidViewModel(application) {
    val charityListObservable: LiveData<List<Charity>> = TamboonRepository.instance.charityList
    fun makeDonationObservable(request: Request): LiveData<Result> {
        return TamboonRepository.instance.makeDonation(request)
    }
}
