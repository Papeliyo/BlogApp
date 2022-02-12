package com.papeliyodev.blogapp.domain.auth

import android.graphics.Bitmap
import com.google.firebase.auth.FirebaseUser
import com.papeliyodev.blogapp.data.remote.auth.AuthDataSource

class AuthRepoImpl(private val dataSource: AuthDataSource) : AuthRepo {

    override suspend fun singIn(email: String, password: String): FirebaseUser? =
        dataSource.singIn(email, password)

    override suspend fun singUp(email: String, password: String, username: String): FirebaseUser? =
        dataSource.singUp(email, password, username)

    override suspend fun updateProfile(imageBitmap: Bitmap, username: String) =
        dataSource.updateUserProfile(imageBitmap, username)
}