package com.example.restexamen

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.restexamen.databinding.ItemProductBinding

class ProductAdapter (private val productList: MutableList<Product>,
         private val listener:OnProductListener):
RecyclerView.Adapter<ProductAdapter.ViewHolder>(){

    private lateinit var context: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.item_product,parent,false)

        return  ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = productList[position]

        holder.setListener(product)

        holder.binding.tvname.text = product.name
        holder.binding.tvprice.text = product.price.toString()
        holder.binding.tvquantity.text = product.quantity.toString()
    }

    override fun getItemCount(): Int = productList.size

    fun add(product: Product){
        if (!productList.contains(product)){
            productList.add(product)
            notifyItemInserted(productList.size -1)
        }
    }

    fun update(product: Product) {
        val index = productList.indexOf(product)
        if (index != -1){
            productList.set(index, product)
            notifyItemChanged(index)
        }
    }

    fun delete(product: Product) {
        val index = productList.indexOf(product)
        if (index != -1){
            productList.removeAt(index)
            notifyItemRemoved(index)
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val binding = ItemProductBinding.bind(view)

        fun setListener(product:Product){
            binding.root.setOnClickListener{
                listener.onClick(product)
            }
            binding.root.setOnLongClickListener {
                listener.onLongClick(product)
                true
            }
    }
}
}