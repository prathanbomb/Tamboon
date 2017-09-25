package com.prathanbomb.tamboon.service.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcel
import android.os.Parcelable

/**
 * Created by prathanbomb on 9/13/2017 AD.
 */

@Entity
class Charity() : Parcelable {

    @PrimaryKey @ColumnInfo (name = "id") lateinit var id: String
    @ColumnInfo (name = "name") lateinit var name: String
    @ColumnInfo (name = "logo_url") lateinit var logo_url: String

    constructor(parcel: Parcel) : this() {
        id = parcel.readString()
        name = parcel.readString()
        logo_url = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeString(logo_url)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Charity> {
        override fun createFromParcel(parcel: Parcel): Charity {
            return Charity(parcel)
        }

        override fun newArray(size: Int): Array<Charity?> {
            return arrayOfNulls(size)
        }
    }

}
