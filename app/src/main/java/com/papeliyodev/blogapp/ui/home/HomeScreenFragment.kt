package com.papeliyodev.blogapp.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.papeliyodev.blogapp.R
import com.papeliyodev.blogapp.core.Result
import com.papeliyodev.blogapp.core.hide
import com.papeliyodev.blogapp.core.show
import com.papeliyodev.blogapp.data.model.Post
import com.papeliyodev.blogapp.data.remote.home.HomeScreenDataSource
import com.papeliyodev.blogapp.databinding.FragmentHomeScreenBinding
import com.papeliyodev.blogapp.domain.home.HomeScreenRepoImpl
import com.papeliyodev.blogapp.presentation.HomeScreenViewModel
import com.papeliyodev.blogapp.presentation.HomeScreenViewModelFactory
import com.papeliyodev.blogapp.ui.home.adapter.HomeScreenAdapter
import com.papeliyodev.blogapp.ui.home.adapter.OnPostClickListener

class HomeScreenFragment : Fragment(R.layout.fragment_home_screen), OnPostClickListener {

    private lateinit var binding: FragmentHomeScreenBinding
    private val viewModel by viewModels<HomeScreenViewModel> {
        HomeScreenViewModelFactory(HomeScreenRepoImpl(HomeScreenDataSource()))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeScreenBinding.bind(view)

        viewModel.fetchLastestPosts().observe(viewLifecycleOwner, Observer { result ->
            when(result){
                is Result.Loading -> {
                    binding.progressBar.show()
                }

                is Result.Success -> {
                    binding.progressBar.visibility = View.GONE
                    if (result.data.isEmpty()){
                        binding.emptyContainer.show()
                        return@Observer
                    } else {
                        binding.emptyContainer.hide()
                    }
                    binding.rvHome.adapter = HomeScreenAdapter(result.data, this)
                }

                is Result.Failure -> {
                    binding.progressBar.hide()
                    Toast.makeText(
                        requireContext(),
                        "Ocurrio un error: ${result.exception}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

        })


    }

    override fun onLikeButtonClick(post: Post, liked: Boolean) {
        viewModel.registerLikeButtonState(post.id, liked).observe(viewLifecycleOwner){ result ->
            when(result){
                is Result.Loading -> { }
                is Result.Success -> {
                }

                is Result.Failure -> {
                    Toast.makeText(
                        requireContext(),
                        "Ocurrio un error: ${result.exception}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}