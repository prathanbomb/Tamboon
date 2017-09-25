package com.prathanbomb.tamboon.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.prathanbomb.tamboon.service.model.Request
import com.prathanbomb.tamboon.service.model.Result
import com.prathanbomb.tamboon.service.repository.TamboonRepository

/**
 * Created by prathanbomb on 9/18/2017 AD.
 */
class DonationViewModel(application: Application) : AndroidViewModel(application) {
    fun makeDonationObservable(request: Request): LiveData<Result> {
        return TamboonRepository.instance.makeDonation(request)
    }
}