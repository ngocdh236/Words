package com.example.words.ui.quiz

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.words.R
import com.example.words.databinding.FragmentPlayQuizBinding
import com.example.words.databinding.FragmentWordsBinding
import com.example.words.ui.words.WordsListViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_play_quiz.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PlayQuizFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PlayQuizFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var viewDataBinding: FragmentPlayQuizBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = FragmentPlayQuizBinding.inflate(inflater, container, false).apply {
            viewmodel = ViewModelProviders.of(this@PlayQuizFragment).get(QuizViewViewModel::class.java)
            setLifecycleOwner(viewLifecycleOwner)
        }
        return inflater.inflate(R.layout.fragment_play_quiz, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setup()
        setupObservers()
    }

    private fun setup() {
        next_button.setOnClickListener {
            viewDataBinding.viewmodel?.goNext()
        }
    }

    private fun setupObservers() {
        viewDataBinding.viewmodel?.currentQuizTitle?.observe(viewLifecycleOwner, Observer {
            index_text.text = it
        })
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PlayQuizFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PlayQuizFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}