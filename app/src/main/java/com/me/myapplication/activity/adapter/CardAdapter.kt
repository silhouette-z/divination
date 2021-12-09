package com.me.myapplication.activity.adapter

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity

import androidx.recyclerview.widget.RecyclerView
import com.me.myapplication.R
import com.me.myapplication.activity.SavePicActivity
import com.me.myapplication.activity.ShakeActivity
import com.me.myapplication.activity.StartDailyTestActivity

import com.me.myapplication.activity.VersionActivity
import com.me.myapplication.activity.model.CardItem


class CardAdapter(val cardList: List<CardItem>):RecyclerView.Adapter<CardAdapter.CardViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_item, parent, false)
        val viewHolder =  CardViewHolder(view)
        viewHolder.itemView.setOnClickListener{
            when(viewHolder.name.text) {
                "每日运势" -> {
                    val intent = Intent(parent.context, StartDailyTestActivity::class.java)
                    startActivity(parent.context,intent, Bundle())
                }
                "摇一摇" -> {
                    val intent = Intent(parent.context, ShakeActivity::class.java)
                    startActivity(parent.context,intent, Bundle())
                }
                "每日心情"-> {
                    val intent = Intent(parent.context, VersionActivity::class.java)
                    startActivity(parent.context,intent, Bundle())
                }
                "不知道"-> {
                    val intent = Intent(parent.context, SavePicActivity::class.java)
                    startActivity(parent.context,intent, Bundle())
                }

            }
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.image.setImageResource(cardList[position].imgId)
        holder.name.text = cardList[position].name

    }

    override fun getItemCount(): Int {
        return cardList.size
    }

    inner class CardViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView) {
        val name = itemView.findViewById<TextView>(R.id.cardTextView)
        val image = itemView.findViewById<ImageView>(R.id.cardImageView)
    }

}