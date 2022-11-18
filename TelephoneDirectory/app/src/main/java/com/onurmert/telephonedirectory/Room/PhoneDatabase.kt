package com.onurmert.telephonedirectory.Room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.onurmert.telephonedirectory.Model.PhoneModel

@Database(entities = arrayOf(PhoneModel::class), version = 1, exportSchema = false)
abstract class PhoneDatabase : RoomDatabase() {

    abstract fun phoneDao() : PhoneDao

    companion object{
        @Volatile
        private var INSTANCE : PhoneDatabase? = null

        fun getDatabase(context: Context) : PhoneDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(context, PhoneDatabase::class.java, "tel_database").build()
                INSTANCE = instance
                instance
            }
        }
    }
}