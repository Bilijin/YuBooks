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
    private var books = ArrayList<Book>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val recyclerView = binding.recyclerView

        val recyclerViewAdapter = HomeRecyclerViewAdapter(books)

        DatabaseHelper().retrieveBooks(object : MyCallback{
            override fun onCallback(value: ArrayList<Book>) {
                recyclerViewAdapter.resetData(value)
            }
        })

        recyclerView.adapter = recyclerViewAdapter
        return binding.root
    }
}