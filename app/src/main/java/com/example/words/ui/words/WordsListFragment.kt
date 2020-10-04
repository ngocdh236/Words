package com.example.words.ui.words

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.words.R
import com.example.words.Word
import com.example.words.databinding.FragmentWordsBinding
import kotlinx.android.synthetic.main.fragment_words.*

class WordsListFragment : Fragment() {

    private lateinit var viewDataBinding: FragmentWordsBinding
    private lateinit var adapter: WordsListAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewDataBinding = FragmentWordsBinding.inflate(inflater, container, false).apply {
            viewmodel =
                ViewModelProviders.of(this@WordsListFragment).get(WordsListViewModel::class.java)
            setLifecycleOwner(viewLifecycleOwner)
        }
        val root = inflater.inflate(R.layout.fragment_words, container, false)
//        val adapter = WordsListAdapter()

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        setupObservers()
    }

    fun setupObservers() {
        viewDataBinding.viewmodel?.words?.observe(viewLifecycleOwner, Observer { resource ->
            val words = resource.data ?: listOf<Word>()
            adapter.words = words
        })
    }

    fun setupAdapter() {
        val viewModel = viewDataBinding.viewmodel
        adapter = WordsListAdapter()
        val layoutManager = LinearLayoutManager(activity)
        words_list_rc.layoutManager = layoutManager
        words_list_rc.addItemDecoration(
            DividerItemDecoration(
                activity,
                layoutManager.orientation
            )
        )
        words_list_rc.adapter = adapter
    }
}