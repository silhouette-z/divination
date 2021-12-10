package com.me.myapplication.activity.adapter

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.me.myapplication.R
import com.me.myapplication.activity.bean.Pic

class PictureAdapter(val picList: List<Pic>, val activity: AppCompatActivity) :RecyclerView.Adapter<PictureAdapter.PictureViewHolder>(){
    inner class PictureViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val image = itemView.findViewById<ImageView>(R.id.picImageView)
    }

    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PictureViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.pic_item, parent, false)
        val viewHolder =  PictureViewHolder(view)

        context = parent.context

        return viewHolder
    }

    override fun onBindViewHolder(holder: PictureViewHolder, position: Int) {
        val pic = picList[position]
        Glide.with(context).load(pic.uri).error(R.drawable.header).thumbnail().into(holder.image)
        holder.image.setOnClickListener{
            val intent = Intent()
            intent.putExtra("data",pic.uri.toString())
            activity.setResult(RESULT_OK, intent)
            activity.finish()
        }
    }

    override fun getItemCount(): Int {
        return picList.size
    }
}