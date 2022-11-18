package com.onurmert.telephonedirectory.Model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tel_table")
data class PhoneModel(
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    @ColumnInfo(name = "phoneNumber")
    val phoneNumber : String,
    @ColumnInfo(name = "userName")
    val userName : String,
    @ColumnInfo(name = "email")
    val email : String
){
    constructor(
        phoneNumber: String,
        userName: String,
        email: String): this(0, phoneNumber, userName, email)

    constructor(
        id: Int,
        phoneNumber: String,
        userName: String,
        ): this(id, phoneNumber, userName, "")
}
