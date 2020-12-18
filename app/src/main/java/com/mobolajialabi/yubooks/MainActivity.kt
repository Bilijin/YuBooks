package com.mobolajialabi.yubooks

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mobolajialabi.yubooks.cart.CartFragment
import com.mobolajialabi.yubooks.databinding.ActivityMainBinding
import com.mobolajialabi.yubooks.home.HomeFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val binding : ActivityMainBinding by lazy{
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        Navigation.findNavController(this, R.id.fragment)

        navHostFragment = (NavHostFragment)getSuppo

//        bottom_nav.setOnNavigationItemSelectedListener {
//            when(it.itemId) {
//                R.id.cart -> loadFragment(CartFragment())
//                R.id.account -> loadFragment(HomeFragment())
//
//            }
//
//            true
//        }
    }

    fun loadFragment(fragment : Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment,fragment)
            .addToBackStack(null)
            .commit()
    }
}