package com.example.novanex_studycompanion.ui.quiz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.novanex_studycompanion.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

// Fragment to display quizzes
class QuizzesFragment : Fragment() {

    private val quizViewModel: QuizViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_quizzes, container, false)

        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerViewQuizzes)
        val adapter = QuizzesAdapter(emptyList(), quizViewModel)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        val fabAddQuiz: FloatingActionButton = view.findViewById(R.id.fabAddQuiz)
        fabAddQuiz.setOnClickListener {
            val dialog = AddQuizDialogFragment()
            dialog.show(parentFragmentManager, "AddQuizDialogFragment")
        }

        // Observe the grouped quizzes and update the UI
        quizViewModel.getAllQuizzes().observe(viewLifecycleOwner, Observer { quizzes ->
            quizzes?.let {
                adapter.submitList(it)
            }
        })

        return view
    }
}
