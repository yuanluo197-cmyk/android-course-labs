package com.example.lab04

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lab04.databinding.ItemCardBinding
import com.example.lab04.model.CardMatchingGame

class CardAdapter(
    private val game: CardMatchingGame,
    private val onChoose: (Int) -> Unit
) : RecyclerView.Adapter<CardAdapter.CardHolder>() {
    class CardHolder(val binding: ItemCardBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardHolder =
        CardHolder(ItemCardBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount(): Int = game.count

    override fun onBindViewHolder(holder: CardHolder, position: Int) {
        val card = game.cardAtIndex(position)
        val faceUp = card.isChosen || card.isMatched
        val button = holder.binding.card
        val context = button.context
        button.text = if (faceUp) card.toString() else context.getString(R.string.card_back)
        button.contentDescription = if (faceUp)
            context.getString(R.string.card_face, position + 1, card.toString())
        else context.getString(R.string.card_hidden, position + 1)
        button.isEnabled = !card.isMatched
        button.backgroundTintList = ColorStateList.valueOf(
            if (card.isMatched) 0xFFD0E8DD.toInt() else if (faceUp) Color.WHITE else 0xFF176B87.toInt()
        )
        button.setTextColor(if (faceUp) 0xFF143449.toInt() else Color.WHITE)
        button.setOnClickListener {
            val index = holder.bindingAdapterPosition
            if (index != RecyclerView.NO_POSITION) onChoose(index)
        }
    }
}
