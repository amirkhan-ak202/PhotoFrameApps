package com.example.framephoto_apps

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide

class FrameAdapter(val context:Context,val list: List<Framelist>,val onclick: Onclick
) : RecyclerView.Adapter<FrameAdapter.FrameViewHolder>()
{
  class  FrameViewHolder(view:View) : RecyclerView.ViewHolder(view)
  {
    //val imgaeview:ImageView =view.findViewById(R.id.frame_item)
    val imageview = view.findViewById<ImageView>(R.id.frame_item)


  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FrameViewHolder {

    return FrameViewHolder(LayoutInflater.from(context).inflate(R.layout.frame_layout_items,parent,false))
  }

  override fun onBindViewHolder(holder: FrameViewHolder, position: Int) {
   Glide.with(context).load(list.get(position).drawb).into(holder.imageview)
    holder.imageview.setOnClickListener {
      onclick.clickFrame(position)
    }
  }

  override fun getItemCount(): Int {
   return list.size
  }
}