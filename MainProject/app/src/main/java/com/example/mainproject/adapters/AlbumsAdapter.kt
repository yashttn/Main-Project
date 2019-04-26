package com.example.mainproject.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.mainproject.R
import com.example.mainproject.models.pojo.Album

class AlbumsAdapter : RecyclerView.Adapter<AlbumsAdapter.AlbumsViewHolder>() {

    var albumsList = ArrayList<Album>()

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): AlbumsViewHolder {
        return AlbumsViewHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.item_album_layout, viewGroup, false))
    }

    override fun onBindViewHolder(holder: AlbumsViewHolder, position: Int) {
        val album = albumsList[position]
        holder.albumNameTV?.text = album.albumName
        holder.albumSizeTV?.text = album.albumSize
//        Set background image album
//        holder.albumNameTV.text = album.albumBackground
    }

    override fun getItemCount(): Int = albumsList.size

    class AlbumsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val albumBackgroundIV = itemView.findViewById<ImageView>(R.id.iv_album_background)
        val albumNameTV: TextView? = itemView.findViewById(R.id.tv_album_name)
        val albumSizeTV: TextView? = itemView.findViewById(R.id.tv_album_size)

    }
}