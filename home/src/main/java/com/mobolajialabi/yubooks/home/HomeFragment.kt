package com.mobolajialabi.yubooks.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager

class HomeFragment : Fragment() {

    val binding : FragmentHomeBinding by lazy {
        FragmentHomeBinding.inflate(layoutInflater)
    }
    private val viewModel by viewModels<HomeViewModel>()
    lateinit var books : ArrayList<Book>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_fragment, container, false)
        val recyclerView = binding.recyclerView


        books = DatabaseHelper().retrieveBooks()

        val recyclerViewAdapter = HomeRecyclerViewAdapter(books)
        recyclerView.adapter = recyclerViewAdapter
        return binding.root
    }
}