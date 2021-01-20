package com.mobolajialabi.yubooks.auth.ui.fragments


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.mobolajialabi.yubooks.R
import com.mobolajialabi.yubooks.databinding.FragmentLoginBinding
import com.mobolajialabi.yubooks.core.data.DatabaseHelper
import com.mobolajialabi.yubooks.core.data.DatabaseHelper.firebaseAuthWithGoogle
import com.mobolajialabi.yubooks.core.data.DatabaseHelper.handleGoogleSignIn
import com.mobolajialabi.yubooks.core.data.DatabaseHelper.handleSignIn


class LoginFragment : Fragment(), View.OnClickListener {

    private val binding: FragmentLoginBinding by lazy {
        FragmentLoginBinding.inflate(layoutInflater)
    }

    private val RC_SIGN_IN = 1



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

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
        DatabaseHelper.apply {

            isSignInSuccessful.observe(viewLifecycleOwner) {
                if (it) {
                    findNavController()
                        .navigate(R.id.action_loginFragment_to_homeFragment)
                }

            }

            googleSignInSuccessful.observe(viewLifecycleOwner) {
                if (it) {
                    findNavController()
                        .navigate(R.id.action_loginFragment_to_homeFragment)
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
                    handleSignIn(binding.root, email, password)
                    navigateHome()
                }
            }

            binding.forgotPassword -> findNavController().navigate(R.id.action_loginFragment_to_forgotPasswordFragment)

            binding.signUp -> findNavController().navigate(R.id.action_loginFragment_to_registerFragment)

            binding.signInGoogle -> {
               val client =  handleGoogleSignIn(requireContext())
                startActivityForResult(client.signInIntent, RC_SIGN_IN)
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
                account.idToken?.let { firebaseAuthWithGoogle(it, binding.root) }
                navigateHome()
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
//                Timber.d("Google sign in failed %s", e.localizedMessage)
            }
        }
    }

}