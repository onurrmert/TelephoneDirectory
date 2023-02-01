package com.onurmert.telephonedirectory.ViewModel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.onurmert.telephonedirectory.Model.PhoneModel
import com.onurmert.telephonedirectory.Room.PhoneDatabase
import kotlinx.coroutines.launch

class UpdateViewModel : ViewModel() {

    val phoneModel = MutableLiveData<PhoneModel>()

    fun update(context: Context, phoneModel: PhoneModel){
        viewModelScope.launch {
            PhoneDatabase.getDatabase(context).phoneDao().update(phoneModel)
        }
    }

    fun getPhoneModel(context: Context, id: Int){
        viewModelScope.launch {
            phoneModel.value = PhoneDatabase.getDatabase(context).phoneDao().getOnePhone(id)
        }
    }
}