package com.prathanbomb.tamboon.service

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context

import com.prathanbomb.tamboon.service.dao.CharityDao
import com.prathanbomb.tamboon.service.model.Charity

/**
 * Created by prathanbomb on 9/18/2017 AD.
 */

@Database(entities = arrayOf(Charity::class), version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun charityModel(): CharityDao

    companion object {

        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "tamboon_db").build()
            }
            return INSTANCE
        }
    }

}
