package com.onurmert.telephonedirectory.ViewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.onurmert.telephonedirectory.Model.PhoneModel
import com.onurmert.telephonedirectory.Room.PhoneDatabase
import kotlinx.coroutines.launch

class AddPhoneViewModel : ViewModel(){

    fun insert(phoneModel: PhoneModel, context: Context){
        viewModelScope.launch {
            PhoneDatabase.getDatabase(context).phoneDao().insert(phoneModel)
        }
    }
}