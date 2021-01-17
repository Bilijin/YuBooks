package com.mobolajialabi.yubooks.auth.ui.fragments


import android.app.Activity
import android.content.ContentValues
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
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import com.mobolajialabi.yubooks.auth.ui.viewmodels.LoginViewModel
import com.mobolajialabi.yubooks.auth.ui.viewmodels.LoginViewModelFactory
import com.mobolajialabi.yubooks.R
import com.mobolajialabi.yubooks.util.SignInClient
import com.mobolajialabi.yubooks.databinding.FragmentLoginBinding


class LoginFragment : Fragment(), SignInClient, View.OnClickListener {

    private val binding: FragmentLoginBinding by lazy {
        FragmentLoginBinding.inflate(layoutInflater)
    }

    private val RC_SIGN_IN = 1

    private val viewModel by viewModels<LoginViewModel> {
        LoginViewModelFactory(
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
        binding.signUp.setOnClickListener(this)
        binding.forgotPassword.setOnClickListener(this)
        binding.login.setOnClickListener(this)
        binding.signInGoogle.setOnClickListener(this)
    }


    private fun navigateHome() {
        viewModel.apply {

        boo.observe(viewLifecycleOwner) {
            if (it) {
                requireView().findNavController()
                    .navigate(R.id.action_loginFragment_to_homeFragment)
                Toast.makeText(requireContext(), "Sign in successful", Toast.LENGTH_SHORT).show()
            }

        }
            errorMessage.observe(viewLifecycleOwner){
                if (it.isNotEmpty()){
                    Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
                }
            }

            googleSiginSuccessful.observe(viewLifecycleOwner){
                if (it) {
                    requireView().findNavController()
                        .navigate(R.id.action_loginFragment_to_homeFragment)
                    Toast.makeText(requireContext(), "Sign in successful", Toast.LENGTH_SHORT).show()
                }

            }

        }


    }


    override fun onClick(v: View?) {
        when (v) {
            binding.login -> {
                val email = binding.email.text.toString()
                val password = binding.password.text.toString()

                if (email.isEmpty() || !email.contains('@')) {
                    Toast.makeText(activity, "Please enter a valid email", Toast.LENGTH_SHORT)
                        .show()
                } else if (password.isEmpty() || password.length < 6) {
                    Toast.makeText(
                        activity,
                        "Enter a password with at least 6 characters",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    viewModel.handleSignIn(email, password)
                    navigateHome()
                }
            }

            binding.forgotPassword -> findNavController().navigate(R.id.action_loginFragment_to_forgotPasswordFragment)

            binding.signUp -> findNavController().navigate(R.id.action_loginFragment_to_registerFragment)

            binding.signInGoogle -> {
                viewModel.handleGoogleSign()

            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN && resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
//                Log.d(ContentValues.TAG, "firebaseAuthWithGoogle:" + account.id)
                account.idToken?.let { viewModel.firebaseAuthWithGoogle(it) }
                navigateHome()
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(ContentValues.TAG, "Google sign in failed", e)
            }
        }
    }

    override fun signInStarted(client: GoogleSignInClient) {
        startActivityForResult(client.signInIntent, RC_SIGN_IN)
    }

}