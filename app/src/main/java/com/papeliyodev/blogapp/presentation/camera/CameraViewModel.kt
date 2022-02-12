package com.papeliyodev.blogapp.presentation.camera

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.papeliyodev.blogapp.core.Result
import com.papeliyodev.blogapp.domain.auth.AuthRepo
import com.papeliyodev.blogapp.domain.camera.CameraRepo
import com.papeliyodev.blogapp.presentation.auth.AuthViewModel
import kotlinx.coroutines.Dispatchers

class CameraViewModel(private val repo: CameraRepo): ViewModel() {

    fun uploadPhoto(imageBitmap: Bitmap, description: String) = liveData(viewModelScope.coroutineContext + Dispatchers.Main) {
        emit(Result.Loading())
        try {
            emit(Result.Success(repo.uploadPhoto(imageBitmap, description)))
        }catch (e: Exception){
            emit(Result.Failure(e))
        }

    }
}

class CameraViewModelFactory(private val repo: CameraRepo): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(CameraRepo::class.java).newInstance(repo)
    }
}