package com.onurmert.telephonedirectory.ViewModel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.onurmert.telephonedirectory.Model.PhoneModel
import com.onurmert.telephonedirectory.Room.PhoneDatabase
import kotlinx.coroutines.launch

class PhoneListViewModel : ViewModel() {

    val phoneList = MutableLiveData<List<PhoneModel>>()

    fun getAll(context: Context){
        viewModelScope.launch {
            phoneList.value = PhoneDatabase.getDatabase(context).phoneDao().getAllPhoneList()
        }
    }
}