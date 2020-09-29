package com.example.words.ui.words

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.words.R

class WordsFragment : Fragment() {

    private lateinit var wordsViewModel: WordsViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        wordsViewModel =
                ViewModelProviders.of(this).get(WordsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_words, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        val adapter = WordAdapter()
        binding.wordList.adapter = adapter
        wordsViewModel.response.observe(viewLifecycleOwner, Observer {
            adapter.words = it
        })

        return root
    }
}