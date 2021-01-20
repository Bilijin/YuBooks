package com.mobolajialabi.yubooks.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mobolajialabi.yubooks.core.data.Book
import com.mobolajialabi.yubooks.home.databinding.CartItemBinding

class HomeRecyclerViewAdapter(private val listener: BooksClickListener) : ListAdapter<Book, HomeRecyclerViewAdapter.MyViewHolder>(DataUtilClass()) {

    class DataUtilClass(): DiffUtil.ItemCallback<Book>() {
        override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean {
           return oldItem.equals( newItem)
        }
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       holder.bind(getItem(position), listener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
               return MyViewHolder.from(parent)
    }


    class MyViewHolder(private val binding: CartItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(book: Book, listener: BooksClickListener)  {
            binding.bookName.text = book.name
            binding.price.text = book.price.toString()

            binding.root.setOnClickListener {
                listener.onBookClicked(book)
            }

        }

        companion object {
            fun from(parent: ViewGroup): MyViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = CartItemBinding.inflate(layoutInflater,parent,false)
                return MyViewHolder(binding)
            }
        }
    }


}

interface BooksClickListener {
    fun onBookClicked(book: Book)
}