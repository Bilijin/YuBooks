package com.mobolajialabi.yubooks.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mobolajialabi.yubooks.home.databinding.HomeFragmentBinding

class HomeFragment : Fragment() {

    val binding : HomeFragmentBinding by lazy {
        HomeFragmentBinding.inflate(layoutInflater)
    }
    private val viewModel by viewModels<HomeViewModel>()
    lateinit var books : ArrayList<Book>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val recyclerView = binding.recyclerView


        DatabaseHelper().retrieveBooks(object : MyCallback{
            override fun onCallback(value: ArrayList<Book>) {
            }
        })

        val recyclerViewAdapter = HomeRecyclerViewAdapter(books)
        recyclerView.adapter = recyclerViewAdapter
        return binding.root
    }
}