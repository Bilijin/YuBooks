package com.mobolajialabi.yubooks

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mobolajialabi.yubooks.databinding.FragmentForgotPasswordBinding


class ForgotPasswordFragment : Fragment() {
    private val binding : FragmentForgotPasswordBinding by lazy {
        FragmentForgotPasswordBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = binding.root
        binding.send.setOnClickListener{
            val email : String = binding.email.text.toString()

            Firebase.auth.sendPasswordResetEmail(email).addOnCompleteListener{
                task ->
                if (task.isSuccessful) {
                    Toast.makeText(activity, "Email sent", Toast.LENGTH_SHORT).show()
                }
            }
        }
        return view
    }

}