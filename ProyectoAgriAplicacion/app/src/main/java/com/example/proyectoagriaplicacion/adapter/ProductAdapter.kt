package com.example.proyectoagriaplicacion.adapter

import android.content.Context
import android.content.Intent
import java.util.ArrayList
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import com.example.proyectoagriaplicacion.R
import android.view.View
import android.widget.TextView
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.proyectoagriaplicacion.DetailActivity
import com.example.proyectoagriaplicacion.EditActivity
import com.example.proyectoagriaplicacion.model.Product
import com.google.firebase.auth.FirebaseAuth



class ProductAdapter(private val mContext: Context, private val mProduct: ArrayList<Product>) :
    RecyclerView.Adapter<ProductAdapter.ViewHolder>(), View.OnClickListener {

    private val listener: View.OnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.marketlist,
            parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val firebaseUser = FirebaseAuth.getInstance().currentUser
        val product : Product = mProduct[position]
        holder.producto.text = product.nombre
        holder.descripcion.text = product.descripcion
        holder.cantidad.text = product.cantidad
        holder.precio.text = "S/ " + product.precio
        holder.fecha.text = product.fecha

        Glide.with(mContext).load(product.urlImagen).into(holder.imagen)
        holder.itemView.setOnClickListener {
            if (firebaseUser != null) {
                if (product.idUsuario == firebaseUser.uid) {
                    val intent = Intent(mContext, EditActivity::class.java)



                    intent.putExtra("id", product.idProducto)

                    intent.putExtra("idUser", product.idUsuario)
                    mContext.startActivity(intent)
                } else {
                    val intent = Intent(mContext, DetailActivity::class.java)

                    intent.putExtra("id", product.idProducto)
                    intent.putExtra("idUser", product.idUsuario)
                    mContext.startActivity(intent)
                }
            }
        }


    }
    override fun onClick(v: View) {
        listener?.onClick(v)
    }


    override fun getItemCount(): Int {
        return mProduct.size
    }

   class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val producto: TextView  = itemView.findViewById(R.id.nameProduct)
        val descripcion: TextView = itemView.findViewById(R.id.description)
        val cantidad: TextView = itemView.findViewById(R.id.cantProduct)
        val precio: TextView = itemView.findViewById(R.id.Precio)
        val fecha: TextView = itemView.findViewById(R.id.fecha)
        val imagen: ImageView = itemView.findViewById(R.id.image)


    }
}