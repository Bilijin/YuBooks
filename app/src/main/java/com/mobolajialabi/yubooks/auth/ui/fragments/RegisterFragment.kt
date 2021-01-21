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
import com.mobolajialabi.yubooks.databinding.FragmentRegisterBinding
import com.mobolajialabi.yubooks.core.data.DatabaseHelper
import com.mobolajialabi.yubooks.core.data.DatabaseHelper.createUser
import com.mobolajialabi.yubooks.core.data.DatabaseHelper.firebaseAuthWithGoogle
import com.mobolajialabi.yubooks.core.data.DatabaseHelper.handleGoogleSignIn
import timber.log.Timber


class RegisterFragment : Fragment(),
     View.OnClickListener {
    private val RC_SIGN_IN = 1

    private val binding: FragmentRegisterBinding by lazy {
        FragmentRegisterBinding.inflate(layoutInflater)
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
                Timber.d("firebaseAuthWithGoogle:%s", account.id)
                account.idToken?.let { firebaseAuthWithGoogle(it, binding.root) }
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Timber.d(e, "Google sign in failed")
            }
        }
    }


    private fun goHome() {
        DatabaseHelper.apply {

            isRegisterSuccessful.observe(viewLifecycleOwner) {
                if (it) {
                   findNavController()
                        .navigate(R.id.action_registerFragment_to_homeFragment)
                }
            }

            googleSignInSuccessful.observe(viewLifecycleOwner,{
                if (it) {
                    findNavController()
                        .navigate(R.id.action_registerFragment_to_homeFragment)
                }
            })
        }
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.register -> {
                val username = binding.username.text.toString()
                val email = binding.email.text.toString()
                val password = binding.password.text.toString()
                val phone = binding.phoneNo.text.toString()

                if (username.length < 2) {
                    Toast.makeText(
                        activity,
                        "Please enter a username longer than two characters",
                        Toast.LENGTH_SHORT
                    ).show()
                } else if (!email.contains('@')) {
                    Toast.makeText(activity, "Please enter a valid email", Toast.LENGTH_SHORT)
                        .show()
                } else if (password.length < 6) {
                    Toast.makeText(
                        activity,
                        "Please enter a password with at least six characters",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    createUser(binding.root, email, password, username, phone)
                    goHome()

                }
            }
            binding.signUpGoogle -> {
                val client = handleGoogleSignIn(requireContext())
                startActivityForResult(client.signInIntent, RC_SIGN_IN)
            }

            binding.signIn -> findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }

    }
}