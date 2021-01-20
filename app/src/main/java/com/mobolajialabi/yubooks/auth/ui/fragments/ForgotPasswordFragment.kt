package com.mobolajialabi.yubooks.auth.ui.fragments

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.mobolajialabi.yubooks.core.data.DatabaseHelper
import com.mobolajialabi.yubooks.core.data.DatabaseHelper.forgotPassword
import com.mobolajialabi.yubooks.R
import com.mobolajialabi.yubooks.databinding.FragmentForgotPasswordBinding
import com.mobolajialabi.yubooks.util.Helpers.showMessage

class ForgotPasswordFragment : Fragment(), View.OnClickListener {
    private val binding: FragmentForgotPasswordBinding by lazy {
        FragmentForgotPasswordBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setUpListeners()
        return binding.root
    }

    private fun setUpListeners() {
        binding.send.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.send -> {
                if (binding.email.text.toString().isNotEmpty() &&
                    Patterns.EMAIL_ADDRESS.matcher(binding.email.text.toString()).matches()
                ) {
                    forgotPassword(binding.email.text.toString(), binding.root)
                    setupObserver()
                } else {
                    showMessage("Please Input a valid Email", binding.root)
                }
            }
        }
    }

    private fun setupObserver() {
        DatabaseHelper.hasEmailBeenSent.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().navigate(R.id.login)
            }

        }
    }
}