package com.example.novanex_studycompanion.ui.quiz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.novanex_studycompanion.R
import com.example.novanex_studycompanion.data.Quiz.Quiz
import androidx.navigation.fragment.findNavController

// Fragment for taking a quiz
class TakeQuizFragment : Fragment() {

    private val quizViewModel: QuizViewModel by activityViewModels()
    private lateinit var subjectTitle: String
    private lateinit var questions: List<Quiz>
    private lateinit var answers: MutableList<Int>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_take_quiz, container, false)
        subjectTitle = arguments?.getString("subjectTitle") ?: ""

        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerViewTakeQuiz)
        val submitButton: Button = view.findViewById(R.id.buttonSubmitQuiz)
        val cancelButton: Button = view.findViewById(R.id.buttonCancelQuiz)
        val adapter = TakeQuizAdapter(emptyList())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        // Observe the quizzes for the subject and update the UI
        quizViewModel.getQuizzesForSubject(subjectTitle).observe(viewLifecycleOwner, Observer { quizzes ->
            quizzes?.let {
                questions = it
                answers = MutableList(questions.size) { -1 } // -1 indicates no answer selected
                adapter.submitList(questions)
            }
        })

        submitButton.setOnClickListener {
            val score = calculateScore()
            saveBestScore(score)
            showResultPopup(score)
        }

        cancelButton.setOnClickListener {
            findNavController().navigate(R.id.action_takeQuizFragment_to_navigation_quizzes)
        }

        return view
    }

    // Calculate the score based on the selected answers
    private fun calculateScore(): Int {
        var score = 0
        for (i in questions.indices) {
            if (answers[i] == questions[i].correctAnswerIndex) {
                score++
            }
        }
        return score
    }

    // Save the best score if the current score is higher
    private fun saveBestScore(score: Int) {
        for (quiz in questions) {
            if (score > quiz.bestScore) {
                quiz.bestScore = score
                quizViewModel.update(quiz)
            }
        }
    }

    // Show a popup with the quiz result
    private fun showResultPopup(score: Int) {
        Toast.makeText(context, "Your score: $score", Toast.LENGTH_LONG).show()
        view?.postDelayed({
            findNavController().navigate(R.id.action_takeQuizFragment_to_navigation_quizzes)
        }, 3000) // Delay for 3 seconds before navigating back
    }

    // Adapter for displaying the quiz questions
    inner class TakeQuizAdapter(private var quizzes: List<Quiz>) : RecyclerView.Adapter<TakeQuizAdapter.QuizViewHolder>() {

        inner class QuizViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val question: TextView = itemView.findViewById(R.id.textViewQuestion)
            val radioGroup: RadioGroup = itemView.findViewById(R.id.radioGroupAnswers)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuizViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_take_quiz, parent, false)
            return QuizViewHolder(view)
        }

        override fun onBindViewHolder(holder: QuizViewHolder, position: Int) {
            val quiz = quizzes[position]
            holder.question.text = quiz.question
            holder.radioGroup.setOnCheckedChangeListener(null)
            holder.radioGroup.clearCheck()

            val radioButtons = listOf(
                holder.radioGroup.findViewById<RadioButton>(R.id.radioButtonAnswer1),
                holder.radioGroup.findViewById<RadioButton>(R.id.radioButtonAnswer2),
                holder.radioGroup.findViewById<RadioButton>(R.id.radioButtonAnswer3),
                holder.radioGroup.findViewById<RadioButton>(R.id.radioButtonAnswer4)
            )

            radioButtons[0].text = quiz.answer1
            radioButtons[1].text = quiz.answer2
            radioButtons[2].text = quiz.answer3
            radioButtons[3].text = quiz.answer4

            if (answers[position] != -1) {
                radioButtons[answers[position]].isChecked = true
            }

            holder.radioGroup.setOnCheckedChangeListener { _, checkedId ->
                answers[position] = when (checkedId) {
                    R.id.radioButtonAnswer1 -> 0
                    R.id.radioButtonAnswer2 -> 1
                    R.id.radioButtonAnswer3 -> 2
                    R.id.radioButtonAnswer4 -> 3
                    else -> -1
                }
            }
        }

        override fun getItemCount(): Int = quizzes.size

        fun submitList(newQuizzes: List<Quiz>) {
            quizzes = newQuizzes
            notifyDataSetChanged()
        }
    }
}
