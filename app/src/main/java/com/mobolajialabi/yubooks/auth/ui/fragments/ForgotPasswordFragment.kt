package com.mobolajialabi.yubooks.auth.ui.fragments

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.mobolajialabi.yubooks.R
import com.mobolajialabi.yubooks.auth.ui.viewmodels.ForgetPasswordViewModel
import com.mobolajialabi.yubooks.databinding.FragmentForgotPasswordBinding

class ForgotPasswordFragment : Fragment(), View.OnClickListener {
    private val binding : FragmentForgotPasswordBinding by lazy {
        FragmentForgotPasswordBinding.inflate(layoutInflater)
    }
    private val viewModel:ForgetPasswordViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setUpListeners()
        return binding.root
    }

    private fun setUpListeners() {
        binding.send.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v){
            binding.send -> {
                if (binding.email.text.toString().isNotEmpty() &&
                    Patterns.EMAIL_ADDRESS.matcher(binding.email.text.toString()).matches() ) {
                    viewModel.forgetPassword(binding.email.text.toString())
                    setupObserver()
                }else{
                    Toast.makeText(requireContext(), "Please Input a valid Email", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun setupObserver() = viewModel.sentSuccessfully.observe(viewLifecycleOwner){
        if (it){
            findNavController().navigate(R.id.login)
        }

    }
}