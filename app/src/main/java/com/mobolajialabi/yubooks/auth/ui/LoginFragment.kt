package com.mobolajialabi.yubooks.auth.ui

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mobolajialabi.yubooks.R
import com.mobolajialabi.yubooks.databinding.FragmentLoginBinding


class LoginFragment : Fragment() {

    private val binding : FragmentLoginBinding by lazy{
        FragmentLoginBinding.inflate(layoutInflater)
    }
    private val auth : FirebaseAuth by lazy{
        Firebase.auth
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view : View = binding.root

        Navigation.findNavController(context as Activity, R.id.fragment)
        binding.signUp.setOnClickListener{
            view.findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        binding.forgotPassword.setOnClickListener {
            view.findNavController().navigate(R.id.action_loginFragment_to_forgotPasswordFragment)
        }
        binding.login.setOnClickListener {
            val email = binding.email.text.toString()
            val password = binding.password.text.toString()

            if (email.isEmpty() || !email.contains('@')) {
                Toast.makeText(activity, "Please enter a valid email", Toast.LENGTH_SHORT).show()
            } else if (password.isEmpty() || password.length < 6) {
                Toast.makeText(activity, "Enter a password with at least 6 characters", Toast.LENGTH_SHORT).show()
            } else {
                handleSignIn(email, password)
            }
        }
        // Inflate the layout for this fragment
        return view
    }

    private fun handleSignIn(email : String, password : String) {
        auth.signInWithEmailAndPassword(email, password).
        addOnCompleteListener {
                task ->
            if (task.isSuccessful) {
                Toast.makeText(activity, "Sign in successful", Toast.LENGTH_SHORT).show()
                //switch to home fragment
            } else {
                Toast.makeText(activity, "Sign in failed. Please try again ${task.exception}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}