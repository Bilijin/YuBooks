package com.mobolajialabi.yubooks.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mobolajialabi.yubooks.core.data.Book
import com.mobolajialabi.yubooks.core.data.DatabaseHelper
import com.mobolajialabi.yubooks.home.databinding.HomeFragmentBinding

class HomeFragment : Fragment(), BooksClickListener {

    private val binding: HomeFragmentBinding by lazy {
        HomeFragmentBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val recyclerView = binding.recyclerView

        val recyclerViewAdapter = HomeRecyclerViewAdapter(this)
        DatabaseHelper.booksList.observe(viewLifecycleOwner){
            if (it.isNotEmpty()) {
                recyclerViewAdapter.submitList(it)
                recyclerView.adapter = recyclerViewAdapter
            }
        }

        return binding.root
    }

    override fun onBookClicked(book: Book) {

    }

}