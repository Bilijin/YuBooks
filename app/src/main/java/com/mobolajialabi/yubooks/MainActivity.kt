package com.mobolajialabi.yubooks

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.mobolajialabi.yubooks.databinding.ActivityMainBinding

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

        binding.homeFragment

    }

    override fun onClick(v: View?) {
        when(v){
            binding.homeFragment ->
                Navigation.findNavController(binding.bottomNav).navigate(R.id.homeFragment)
        }
    }

}