package com.mobolajialabi.yubooks.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mobolajialabi.yubooks.profile.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private val binding : FragmentProfileBinding by lazy{
        FragmentProfileBinding.inflate(layoutInflater)
    }

    private val auth : FirebaseAuth by lazy{
        Firebase.auth
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val user = auth.currentUser
        binding.email.text = user!!.email
        return binding.root
    }

}