package com.mobolajialabi.yubooks.cart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

        books = DatabaseHelper().retrieveBooks()

        val recyclerViewAdapter = CartRecyclerViewAdapter(books)
        recyclerView.adapter = recyclerViewAdapter
        return binding.root
    }

}