package com.example.novanex_studycompanion.ui.quiz

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.novanex_studycompanion.R
import com.example.novanex_studycompanion.data.Quiz.GroupedQuiz

// Adapter for displaying grouped quizzes in a RecyclerView
class QuizzesAdapter(
    private var quizzes: List<GroupedQuiz>,
    private val viewModel: QuizViewModel
) : RecyclerView.Adapter<QuizzesAdapter.QuizViewHolder>() {

    inner class QuizViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val subjectTitle: TextView = itemView.findViewById(R.id.subjectTitle)
        val questionCount: TextView = itemView.findViewById(R.id.questionCount)
        val bestScore: TextView = itemView.findViewById(R.id.bestScore)
        val deleteButton: ImageButton = itemView.findViewById(R.id.delete_button)
        val goButton: ImageButton = itemView.findViewById(R.id.go_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuizViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_quiz, parent, false)
        return QuizViewHolder(view)
    }

    override fun onBindViewHolder(holder: QuizViewHolder, position: Int) {
        val groupedQuiz = quizzes[position]
        holder.subjectTitle.text = groupedQuiz.subjectTitle
        holder.questionCount.text = "Questions: ${groupedQuiz.questions.size}"
        holder.bestScore.text = "Best Score: ${groupedQuiz.questions.maxOf { it.bestScore }}"

        holder.deleteButton.setOnClickListener {
            groupedQuiz.questions.forEach { quiz ->
                viewModel.delete(quiz)
            }
        }

        holder.goButton.setOnClickListener {
            val action = QuizzesFragmentDirections.actionQuizzesFragmentToTakeQuizFragment(groupedQuiz.subjectTitle)
            it.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int = quizzes.size

    fun submitList(newQuizzes: List<GroupedQuiz>) {
        quizzes = newQuizzes
        notifyDataSetChanged()
    }
}
