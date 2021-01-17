package com.mobolajialabi.yubooks.auth.ui.fragments

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mobolajialabi.yubooks.*
import com.mobolajialabi.yubooks.auth.ui.viewmodels.RegisterViewModel
import com.mobolajialabi.yubooks.auth.ui.viewmodels.RegisterViewModelFactory
import com.mobolajialabi.yubooks.databinding.FragmentRegisterBinding
import com.mobolajialabi.yubooks.util.SignInClient


class RegisterFragment : Fragment(),
    SignInClient, View.OnClickListener {
    private val RC_SIGN_IN = 1

    private val binding : FragmentRegisterBinding by lazy{
        FragmentRegisterBinding.inflate(layoutInflater)
    }
    private val viewModel by viewModels<RegisterViewModel> {
        RegisterViewModelFactory(
            requireActivity().application,
            this
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setUpListeners()

        return binding.root
    }

    private fun setUpListeners() {
        binding.signUpGoogle.setOnClickListener(this)
        binding.register.setOnClickListener(this)
        binding.signIn.setOnClickListener(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN && resultCode == Activity.RESULT_OK && data != null) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                viewModel.firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
            }
        }
    }

    override fun signInStarted(client: GoogleSignInClient) {
        startActivityForResult(client.signInIntent, RC_SIGN_IN)
    }

    private fun goHome() {
        viewModel.isRegisterSuccessful.observe(viewLifecycleOwner) {
            if (it) {
                Toast.makeText(context, "Account successfully created", Toast.LENGTH_SHORT).show()
                requireView().findNavController()
                    .navigate(R.id.action_registerFragment_to_homeFragment)
            } else {
                Toast.makeText(context, "Authentication failed.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onClick(v: View?) {
        when(v){
            binding.register -> {
                val username = binding.username.text.toString()
                val email = binding.email.text.toString()
                val password = binding.password.text.toString()
                val phone = binding.phoneNo.text.toString()

                if (username.length < 2) {
                    Toast.makeText(activity, "Please enter a username longer than two characters", Toast.LENGTH_SHORT).show()
                } else if (!email.contains('@')) {
                    Toast.makeText(activity, "Please enter a valid email", Toast.LENGTH_SHORT).show()
                } else if (password.length < 6) {
                    Toast.makeText(activity, "Please enter a password with at least six characters", Toast.LENGTH_SHORT).show()
                } else {
                    viewModel.register(email, password, username, phone)
                    goHome()

                }
            }
            binding.signUpGoogle -> viewModel.handleGoogleSign()

            binding.signIn -> findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }

    }
}