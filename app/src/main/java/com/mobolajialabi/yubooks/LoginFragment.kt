package com.mobolajialabi.yubooks

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.mobolajialabi.yubooks.databinding.ActivityMainBinding
import com.mobolajialabi.yubooks.databinding.FragmentLoginBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : Fragment() {

    private val binding : FragmentLoginBinding by lazy{
        FragmentLoginBinding.inflate(layoutInflater)
    }

    lateinit var auth : FirebaseAuth
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view : View = binding.root

        Navigation.findNavController(context as Activity, R.id.fragment)

        auth = FirebaseAuth.getInstance()
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
                        Toast.makeText(activity, "Sign in failed. Please try again", Toast.LENGTH_SHORT).show()
                    }
                }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LoginFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LoginFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

}