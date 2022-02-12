package com.papeliyodev.blogapp.domain.home

import com.papeliyodev.blogapp.core.Result
import com.papeliyodev.blogapp.data.model.Post
import com.papeliyodev.blogapp.data.remote.home.HomeScreenDataSource

class HomeScreenRepoImpl(private val dataSource: HomeScreenDataSource): HomeScreenRepo {

    override suspend fun getLatestPost(): Result<List<Post>> = dataSource.getLastestPost()

    override suspend fun registerLikeButtonState(postId: String, liked: Boolean) = dataSource.registerLikeButtonState(postId, liked)
}