package com.prathanbomb.tamboon.service.model

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by prathanbomb on 9/17/2017 AD.
 */
class Receipt(var charity: Charity,
              var name: String,
              var amount: String): Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readParcelable(Charity::class.java.classLoader),
            parcel.readString(),
            parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(charity, flags)
        parcel.writeString(name)
        parcel.writeString(amount)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Receipt> {
        override fun createFromParcel(parcel: Parcel): Receipt {
            return Receipt(parcel)
        }

        override fun newArray(size: Int): Array<Receipt?> {
            return arrayOfNulls(size)
        }
    }
}