package com.papeliyodev.blogapp.domain.home

import com.papeliyodev.blogapp.core.Result
import com.papeliyodev.blogapp.data.model.Post

interface HomeScreenRepo {
    suspend fun getLatestPost(): Result<List<Post>>
    suspend fun registerLikeButtonState(postId: String, liked: Boolean)
}