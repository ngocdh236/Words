package com.example.words.ui.quiz

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.words.AppExecutors
import com.example.words.R
import com.example.words.databinding.FragmentPlayQuizBinding
import com.example.words.db.WordsDB
import com.example.words.models.QuizResult
import com.example.words.other.QuizResultFeedbackHelper
import kotlinx.android.synthetic.main.fragment_play_quiz.*
import java.util.*
import kotlin.concurrent.schedule


class PlayQuizFragment : Fragment() {
    private lateinit var viewDataBinding: FragmentPlayQuizBinding

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
        context?.let {
            viewDataBinding.viewmodel?.quizResultDao = WordsDB.getInstance(it).quizResultDao()
        }

        next_button.setOnClickListener {
            goNext()
        }

        quiz_answer_input.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                viewDataBinding.viewmodel?.setUserAnswer(p0?.toString())
            }
        })

        done_button.setOnClickListener {
            viewDataBinding.viewmodel?.finishQuizzes { result ->
                showResultAndDismiss(result)
            }

        }
    }

    private fun setupObservers() {
        viewDataBinding.viewmodel?.currentQuizIndexTitle?.observe(viewLifecycleOwner, Observer {
            index_text.text = it
        })

        viewDataBinding.viewmodel?.currentQuizTitle?.observe(viewLifecycleOwner, Observer {
            quiz_title_text_view.text = it
        })

        viewDataBinding.viewmodel?.currentEditDistanceText?.observe(viewLifecycleOwner, Observer {
            edit_distanace_text.text = it
        })

        viewDataBinding.viewmodel?.currentCountDownTimerText?.observe(viewLifecycleOwner, Observer {
            count_down_text.text = it
        })

        viewDataBinding.viewmodel?.currentCountDownTimer?.observe(viewLifecycleOwner, Observer {
            if (it == 0) {
                // Delay a bit
                Timer("", false).schedule(1000) {
                    AppExecutors.instance.mainThread().execute {
                        goNext()
                    }
                }
            }
        })

        viewDataBinding.viewmodel?.isQuizzesDone?.observe(viewLifecycleOwner, Observer {
            next_button.isEnabled = !it
            quiz_answer_input.isEnabled = !it
            quiz_answer_input.clearFocus()
            if (it) {
                done_button.visibility = View.VISIBLE
            } else {
                done_button.visibility = View.INVISIBLE
            }
        })
    }

    private fun goNext() {
        quiz_answer_input.text = null
        viewDataBinding.viewmodel?.goNext()
    }

    private fun showResultAndDismiss(result: QuizResult) {
        val dialogBuilder = AlertDialog.Builder(activity!!)
        dialogBuilder.setMessage(QuizResultFeedbackHelper.feedback(result))
            // if the dialog is cancelable
            .setCancelable(false)
            .setPositiveButton("Ok", DialogInterface.OnClickListener {
                    dialog, id ->
                dialog.dismiss()
                activity?.finish()
            })

        val alert = dialogBuilder.create()
        alert.setTitle("Quiz Result")
        alert.show()
    }
}