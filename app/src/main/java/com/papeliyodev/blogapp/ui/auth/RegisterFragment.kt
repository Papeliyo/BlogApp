package com.papeliyodev.blogapp.ui.auth

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.papeliyodev.blogapp.R
import com.papeliyodev.blogapp.core.Result
import com.papeliyodev.blogapp.data.remote.auth.AuthDataSource
import com.papeliyodev.blogapp.databinding.FragmentRegisterBinding
import com.papeliyodev.blogapp.domain.auth.AuthRepoImpl
import com.papeliyodev.blogapp.presentation.auth.AuthViewModel
import com.papeliyodev.blogapp.presentation.auth.AuthViewModelFactory

class RegisterFragment : Fragment(R.layout.fragment_register) {

    private lateinit var binding: FragmentRegisterBinding
    private val viewModel by viewModels<AuthViewModel> {
        AuthViewModelFactory(
            AuthRepoImpl(
                AuthDataSource()
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRegisterBinding.bind(view)
        singUp()
    }

    private fun singUp(){
        binding.btnSingUp.setOnClickListener {

            val username = binding.editTextUsername.text.toString().trim()
            val email = binding.editTextEmail.text.toString().trim()
            val password = binding.editTextPassword.text.toString().trim()
            val confirmPassword = binding.editTextConfirmPassword.text.toString().trim()

            if (validateUserDate(
                    password,
                    confirmPassword,
                    username,
                    email
                )
            ) return@setOnClickListener

            createUser(email,password,username)
        }
    }

    private fun createUser(email: String, password: String, username: String) {
        viewModel.singUp(email, password, username).observe(viewLifecycleOwner, Observer { result ->
            when(result){
                is Result.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.btnSingUp.isEnabled = false
                }
                is Result.Success -> {
                    binding.progressBar.visibility = View.GONE
                    findNavController().navigate(R.id.action_registerFragment_to_setupProfileFragment)
                }
                is Result.Failure -> {
                    binding.progressBar.visibility = View.GONE
                    binding.btnSingUp.isEnabled = true
                    Toast.makeText(requireContext(), "Error: ${result.exception}", Toast.LENGTH_SHORT).show()
                }
            }

        })
    }

    private fun validateUserDate(
        password: String,
        confirmPassword: String,
        username: String,
        email: String
    ): Boolean {
        if (password != confirmPassword) {
            binding.editTextConfirmPassword.error = "Password does not match"
            binding.editTextPassword.error = "Password does not match"
            return true        }

        if (username.isEmpty()) {
            binding.editTextUsername.error = "Username is empty"
            return true
        }
        if (email.isEmpty()) {
            binding.editTextEmail.error = "Email is empty"
            return true
        }
        if (password.isEmpty()) {
            binding.editTextPassword.error = "Password is empty"
            return true
        }
        if (confirmPassword.isEmpty()) {
            binding.editTextConfirmPassword.error = "Confirm password is empty"
            return true
        }
        if (email.isEmpty()) {
            binding.editTextEmail.error = "Email is empty"
            return true
        }
        if (username.isEmpty()) {
            binding.editTextUsername.error = "Username is empty"
            return true
        }
        return false
    }
}