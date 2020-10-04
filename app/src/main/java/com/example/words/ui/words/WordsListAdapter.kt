package com.example.words.ui.words

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.words.R
import com.example.words.Word

import kotlinx.android.synthetic.main.word_item_view.view.*

class WordItemViewHolder(val view: View): RecyclerView.ViewHolder(view)

class WordsListAdapter(): RecyclerView.Adapter<WordItemViewHolder>() {
    var words = listOf<Word>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = words.size

    override fun onBindViewHolder(holder: WordItemViewHolder, position: Int) {
        var word = words[position]
        holder.view.wordText.text = word.text
        holder.view.wordLang.text = word.lang
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.word_item_view, parent, false)
        return WordItemViewHolder(view)
    }
}