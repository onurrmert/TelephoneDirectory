package com.onurmert.telephonedirectory.Room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.onurmert.telephonedirectory.Model.PhoneModel

@Dao
interface PhoneDao {

    @Query("SELECT * FROM tel_table")
    suspend fun getAllPhoneList() : List<PhoneModel>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(telModel: PhoneModel)

    @Query ("SELECT * FROM tel_table WHERE id = :id")
    suspend fun getOnePhone(id : Int) : PhoneModel

    @Query("DELETE FROM tel_table WHERE id = :id")
    suspend fun delete(id: Int)

    @Update(entity = PhoneModel::class)
    suspend fun update(phoneModel: PhoneModel)
}