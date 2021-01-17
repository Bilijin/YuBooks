package com.mobolajialabi.yubooks.auth.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mobolajialabi.yubooks.DatabaseHelper

class ForgetPasswordViewModel: ViewModel() {
    private val _sentSuccessfully = MutableLiveData<Boolean>()
    val sentSuccessfully: LiveData<Boolean> = _sentSuccessfully


    fun forgetPassword(email: String){
       _sentSuccessfully.value =  DatabaseHelper.forgotPassword(email)
    }
}