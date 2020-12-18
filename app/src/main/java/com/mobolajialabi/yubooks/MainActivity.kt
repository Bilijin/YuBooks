package com.mobolajialabi.yubooks

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.mobolajialabi.yubooks.databinding.ActivityMainBinding
import com.mobolajialabi.yubooks.util.Helpers.hide
import com.mobolajialabi.yubooks.util.Helpers.reveal

class MainActivity : AppCompatActivity(), View.OnClickListener{
    private val binding : ActivityMainBinding by lazy{
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.bottomNav.background = null
        binding.bottomNav.menu.getItem(2).isEnabled = false
        Navigation.findNavController(this, R.id.fragment)

        val navController = findNavController(R.id.fragment)
        binding.bottomNav.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when(destination.id){
                R.id.registerFragment,
                R.id.loginFragment,
                R.id.forgotPasswordFragment -> {
                    binding.bottomAppBar.hide()
                    binding.homeFragment.hide()
                }

                else -> {
                    binding.bottomAppBar.reveal()
                    binding.homeFragment.reveal()
                }
            }
        }

        binding.homeFragment

    }

    override fun onClick(v: View?) {
        when(v){
            binding.homeFragment ->
                Navigation.findNavController(binding.bottomNav).navigate(R.id.homeFragment)
        }
    }

}