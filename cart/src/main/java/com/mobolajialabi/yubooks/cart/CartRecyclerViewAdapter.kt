package com.mobolajialabi.yubooks.cart

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CartRecyclerViewAdapter(items : ArrayList<Book>) : RecyclerView.Adapter<CartRecyclerViewAdapter.MyViewHolder>() {

    private var itemsList = items
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = itemsList[position]

        holder.name.text = item.name
        holder.rating.rating = item.rating.toFloat()
        holder.price.text = item.price.toString()
//        holder.img.setImageResource(item.image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cart_item, parent, false)

        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }

    fun resetData(realBooks : ArrayList<Book>) {
        itemsList = realBooks
        notifyDataSetChanged()
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        private val view = itemView
        val name : TextView = view.findViewById(R.id.book_name)
        val price : TextView = view.findViewById(R.id.price)
        val rating : RatingBar = view.findViewById(R.id.rating)
        val img : ImageView = view.findViewById(R.id.image)
        init {
            itemView.setOnClickListener(this)
        }
        override fun onClick(v: View?) {

        }

    }
}