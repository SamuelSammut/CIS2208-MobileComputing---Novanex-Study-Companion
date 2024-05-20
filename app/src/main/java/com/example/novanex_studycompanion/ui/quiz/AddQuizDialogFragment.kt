package com.example.novanex_studycompanion.ui.quiz

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RadioGroup
import android.widget.Spinner
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.novanex_studycompanion.R
import com.example.novanex_studycompanion.data.Quiz.Quiz
import com.example.novanex_studycompanion.ui.subjects.SubjectViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder

// DialogFragment to add a new quiz
class AddQuizDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = MaterialAlertDialogBuilder(requireContext())
        val inflater = requireActivity().layoutInflater
        val view = inflater.inflate(R.layout.dialog_add_quiz, null)

        val spinnerSubjects = view.findViewById<Spinner>(R.id.spinnerSubjects)
        val questionsContainer = view.findViewById<LinearLayout>(R.id.questionsContainer)
        val buttonAddQuestion = view.findViewById<View>(R.id.buttonAddQuestion)

        // Get the SubjectViewModel and QuizViewModel
        val subjectViewModel = ViewModelProvider(requireActivity()).get(SubjectViewModel::class.java)
        val quizViewModel = ViewModelProvider(requireActivity()).get(QuizViewModel::class.java)

        // Observe the subjects and populate the spinner
        subjectViewModel.allSubjects.observe(this, Observer { subjects ->
            val subjectTitles = subjects.map { it.title }
            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, subjectTitles)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerSubjects.adapter = adapter
        })

        // Function to add a new question and answer field
        fun addQuestionField() {
            val questionView = inflater.inflate(R.layout.item_question, null)
            questionsContainer.addView(questionView)
        }

        // Add the first question field initially
        addQuestionField()

        buttonAddQuestion.setOnClickListener {
            addQuestionField()
        }

        builder.setView(view)
            .setTitle("Add New Quiz")
            .setPositiveButton("Add") { dialog, id ->
                val selectedSubject = spinnerSubjects.selectedItem as String
                for (i in 0 until questionsContainer.childCount) {
                    val questionView = questionsContainer.getChildAt(i)
                    val question = questionView.findViewById<EditText>(R.id.editTextQuestion).text.toString()
                    val answer1 = questionView.findViewById<EditText>(R.id.editTextAnswer1).text.toString()
                    val answer2 = questionView.findViewById<EditText>(R.id.editTextAnswer2).text.toString()
                    val answer3 = questionView.findViewById<EditText>(R.id.editTextAnswer3).text.toString()
                    val answer4 = questionView.findViewById<EditText>(R.id.editTextAnswer4).text.toString()
                    val correctAnswerIndex = questionView.findViewById<RadioGroup>(R.id.radioGroupCorrectAnswer).checkedRadioButtonId

                    // Convert RadioButton ID to index
                    val correctIndex = when (correctAnswerIndex) {
                        R.id.radioButtonAnswer1 -> 0
                        R.id.radioButtonAnswer2 -> 1
                        R.id.radioButtonAnswer3 -> 2
                        R.id.radioButtonAnswer4 -> 3
                        else -> -1
                    }

                    // Insert the new quiz into the database
                    quizViewModel.insert(Quiz(subjectTitle = selectedSubject, question = question, answer1 = answer1, answer2 = answer2, answer3 = answer3, answer4 = answer4, correctAnswerIndex = correctIndex))
                }
            }
            .setNegativeButton("Cancel") { dialog, id ->
                dialog.cancel()
            }

        return builder.create()
    }
}
