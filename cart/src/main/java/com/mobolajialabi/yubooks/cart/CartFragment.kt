package com.mobolajialabi.yubooks.cart

import android.os.Bundle
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

    private lateinit var books : ArrayList<Book>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val recyclerView = binding.recyclerView

        DatabaseHelper().retrieveBooks(object : MyCallback{
            override fun onCallback(value: List<Book>) {
                books = value as ArrayList<Book>
            }
        })

        val recyclerViewAdapter = CartRecyclerViewAdapter(books)
        recyclerView.layoutManager = GridLayoutManager(activity,2, LinearLayoutManager.VERTICAL,false)
        recyclerView.adapter = recyclerViewAdapter
        return binding.root
    }

}