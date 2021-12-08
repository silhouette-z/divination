package com.me.myapplication.activity.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.me.myapplication.R
import com.me.myapplication.activity.model.CardItem


class CardAdapter(val cardList: List<CardItem>):RecyclerView.Adapter<CardAdapter.CardViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_item, parent, false)
        return CardViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
       // holder.image. = cardList[position].imgId
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