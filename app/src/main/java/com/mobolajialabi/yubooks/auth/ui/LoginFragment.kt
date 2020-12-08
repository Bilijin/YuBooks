package com.mobolajialabi.yubooks.auth.ui

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.mobolajialabi.yubooks.R
import com.mobolajialabi.yubooks.databinding.FragmentLoginBinding


class LoginFragment : Fragment() {

    private val binding : FragmentLoginBinding by lazy{
        FragmentLoginBinding.inflate(layoutInflater)
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
        // Inflate the layout for this fragment
        return view
    }
}