package com.example.proyectoagriaplicacion.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectoagriaplicacion.R
import com.example.proyectoagriaplicacion.model.ListProduct

class AdapterProduct(private val mList:List<ListProduct>): RecyclerView.Adapter<AdapterProduct.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.marketlist, parent, false)

        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ItemsViewModel = mList[position]

        // sets the image to the imageview from our itemHolder class


        // sets the text to the textview from our itemHolder class
        holder.textView.text = ItemsViewModel.name
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {

        val textView: TextView = itemView.findViewById(R.id.nameProduct)
    }

}