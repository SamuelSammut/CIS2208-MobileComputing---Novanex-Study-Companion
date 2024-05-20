package com.example.novanex_studycompanion.ui.subjects

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.novanex_studycompanion.R
import com.example.novanex_studycompanion.data.Subject.Subject

// Adapter class for binding subject data to RecyclerView items
class SubjectsAdapter(
    private var subjects: List<Subject>,  // List of subjects to be displayed
    private val viewModel: SubjectViewModel  // ViewModel for handling subject operations
) : RecyclerView.Adapter<SubjectsAdapter.SubjectViewHolder>() {

    // ViewHolder class to hold references to the views in each item
    inner class SubjectViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val subjectName: TextView = itemView.findViewById(R.id.subject_name)
        val examDate: TextView = itemView.findViewById(R.id.exam_date)
        val deleteButton: ImageButton = itemView.findViewById(R.id.delete_button)
    }

    // Inflate the item layout and create the ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubjectViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_subject, parent, false)
        return SubjectViewHolder(view)
    }

    // Bind data to the views in each item
    override fun onBindViewHolder(holder: SubjectViewHolder, position: Int) {
        val subject = subjects[position]
        holder.subjectName.text = subject.title
        holder.examDate.text = "Exam day: ${subject.examDate}"

        // Set click listener for the delete button to delete the subject
        holder.deleteButton.setOnClickListener {
            viewModel.deleteSubject(subject)
        }
    }

    // Return the total number of items in the list
    override fun getItemCount(): Int = subjects.size

    // Update the list of subjects and notify the adapter to refresh the data
    fun submitList(newSubjects: List<Subject>) {
        subjects = newSubjects
        notifyDataSetChanged()
    }
}
