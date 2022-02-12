package com.papeliyodev.blogapp.presentation.auth

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.papeliyodev.blogapp.core.Result
import com.papeliyodev.blogapp.domain.auth.AuthRepo
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class AuthViewModel(private val repo: AuthRepo): ViewModel() {

    fun singIn(email: String, password: String) = liveData(viewModelScope.coroutineContext + Dispatchers.Main) {
        emit(Result.Loading())
        try {
            emit(Result.Success(repo.singIn(email,password)))
        } catch (e: Exception){
            emit(Result.Failure(e))
        }
    }
    fun singUp(email: String, password: String, username:String) = liveData(viewModelScope.coroutineContext + Dispatchers.Main) {
        emit(Result.Loading())
        try {
            emit(Result.Success(repo.singUp(email,password,username)))
        } catch (e: Exception){
            emit(Result.Failure(e))
        }
    }
    fun updateUserProfile(imageBitmap: Bitmap,username: String) = liveData(viewModelScope.coroutineContext + Dispatchers.Main) {
        emit(Result.Loading())
        try {
            emit(Result.Success(repo.updateProfile(imageBitmap,username)))
        } catch (e: Exception){
            emit(Result.Failure(e))
        }
    }
}

class AuthViewModelFactory(private val repo: AuthRepo): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AuthViewModel(repo) as T
    }
}