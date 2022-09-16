package com.example.proyectoagriaplicacion.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.proyectoagriaplicacion.R
import com.example.proyectoagriaplicacion.model.Commentary
import de.hdodenhof.circleimageview.CircleImageView
import java.util.ArrayList

class CommentaryAdapter(private val mContext: Context, private val mCommentary: ArrayList<Commentary>) :
    RecyclerView.Adapter<CommentaryAdapter.ViewHolder>(), View.OnClickListener {
    private var listener: View.OnClickListener? = null
    override fun onClick(v: View) {
        if (listener != null) {
            listener!!.onClick(v)
        }
    }

    fun setOnClickListener(listener: View.OnClickListener?) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.comentarylist, parent, false)
        view.setOnClickListener(this)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val commentary = mCommentary[position]

        if (commentary != null) {
            holder.tvDescription.text = commentary.description
        }
        if (commentary != null) {
            holder.tvNameComentator.text = commentary.nameCommentator
        }
        if (commentary != null) {
            holder.tvDate.text = commentary.date
        }
        var stars = ""
        if (commentary != null) {
            stars = when (commentary.stars!!.toInt()) {
                1 -> "⭐"
                2 -> "⭐⭐"
                3 -> "⭐⭐⭐"
                4 -> "⭐⭐⭐⭐"
                5 -> "⭐⭐⭐⭐⭐"
                else -> "⭐"
            }
        }
        holder.tvStars.text = stars
        if (commentary != null) {
            Glide.with(mContext).load(commentary.urlImagen).into(holder.imageComentator)
        }

    }

    override fun getItemCount(): Int {
        return mCommentary.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageComentator: CircleImageView
        val tvTitle: TextView
        val tvDescription: TextView
        val tvNameComentator: TextView
        val tvStars: TextView
        val tvDate: TextView

        //private ImageView imagen;
        init {
            tvTitle = itemView.findViewById(R.id.tvTitle)
            tvDescription = itemView.findViewById(R.id.tvDescription)
            tvNameComentator = itemView.findViewById(R.id.tvNameComentator)
            tvStars = itemView.findViewById(R.id.tvStars)
            tvDate = itemView.findViewById(R.id.tvDate)
            imageComentator = itemView.findViewById(R.id.imageComentator)
        }
    }
}