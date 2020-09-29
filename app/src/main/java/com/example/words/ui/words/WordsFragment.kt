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
import com.example.words.databinding.FragmentWordsBinding

class WordsFragment : Fragment() {

    private lateinit var viewmodel: WordsViewModel
    private lateinit var viewDataBinding: FragmentWordsBinding
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        viewDataBinding = FragmentWordsBinding.inflate(inflater, container, false).apply {
            viewmodel = ViewModelProviders.of(this@WordsFragment).get(WordsViewModel::class.java)
            setLifecycleOwner(viewLifecycleOwner)
        }
        val root = inflater.inflate(R.layout.fragment_words, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        val adapter = WordAdapter()
        viewmodel.response.observe(viewLifecycleOwner, Observer {
            adapter.words = it
        })

        return root
    }
}