package com.example.githubapi

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.githubapi.model.RecyclerData

class RecyclerViewAdapter: RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>(){

    private val listData = mutableListOf<RecyclerData>()

    /*@SuppressLint("NotifyDataSetChanged")
    fun setUpdatedData(newList: List<RecyclerData>){
        listData.clear()
        listData.addAll(newList)
        notifyDataSetChanged()
    }*/

    fun addUpdatedData(newList: List<RecyclerData>){
        val curSize = listData.size
        listData.addAll(newList)
        notifyItemRangeInserted(curSize, newList.size)
    }

    class MyViewHolder(view: View): RecyclerView.ViewHolder(view){

        private val imageView: ImageView = view.findViewById(R.id.imageView)
        private val name: TextView = view.findViewById(R.id.txt_name)
        private val description: TextView = view.findViewById(R.id.txt_description)
        private val author: TextView = view.findViewById(R.id.txt_author)
        private val starCount: TextView = view.findViewById(R.id.txt_stars)
        private val forkCount: TextView = view.findViewById(R.id.txt_fork)

        fun bind(data: RecyclerData){

            name.text = data.name
            description.text = data.description
            author.text = itemView.context.getString(R.string.author_mask, data.owner?.login)
            starCount.text = data.stargazersCount
            forkCount.text = data.forksCount
            Glide.with(imageView).load(data.owner?.avatarUrl).apply(RequestOptions
                .centerCropTransform()).into(imageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_list_row,
            parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.bind(listData[position])
    }

    override fun getItemCount(): Int {
        return listData.size
    }
}