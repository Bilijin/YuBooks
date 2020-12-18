package com.mobolajialabi.yubooks.cart

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobolajialabi.yubooks.cart.databinding.FragmentCartBinding


class CartFragment : Fragment() {

    val binding : FragmentCartBinding by lazy {
        FragmentCartBinding.inflate(layoutInflater)
    }

    private var books = ArrayList<Book>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val recyclerView = binding.recyclerView
        val recyclerViewAdapter = CartRecyclerViewAdapter(books)

        DatabaseHelper().retrieveBooks(object : MyCallback{
            override fun onCallback(value: List<Book>) {
                books = value as ArrayList<Book>

                recyclerViewAdapter.resetData(value)
            }
        })


        recyclerView.layoutManager = GridLayoutManager(activity,2, LinearLayoutManager.VERTICAL,false)
        recyclerView.adapter = recyclerViewAdapter
        return binding.root
    }

}