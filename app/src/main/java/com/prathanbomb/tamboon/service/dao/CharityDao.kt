package com.prathanbomb.tamboon.service.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import com.prathanbomb.tamboon.service.model.Charity

/**
 * Created by prathanbomb on 9/18/2017 AD.
 */

@Dao
interface CharityDao {

    @get:Query("select * from Charity")
    val getAllCharityItems: LiveData<List<Charity>>

    @Query("select * from Charity where id = :id")
    fun getItembyId(id: String): Charity

    @Insert(onConflict = REPLACE)
    fun addCharity(charity: Charity)

    @Delete
    fun deleteCharity(charity: Charity)

}
