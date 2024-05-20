package com.example.novanex_studycompanion.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.novanex_studycompanion.R

// Adapter for displaying exam items in a RecyclerView
class ExamAdapter(private val examList: List<HomeViewModel.ExamItem>) : RecyclerView.Adapter<ExamAdapter.ExamViewHolder>() {

    // ViewHolder class that holds the views for each exam item
    class ExamViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleTextView: TextView = view.findViewById(R.id.textViewExamTitle)
        val dateTextView: TextView = view.findViewById(R.id.textViewExamDate)
        val countdownTextView: TextView = view.findViewById(R.id.textViewCountdown)
        val preparationProgressBar: ProgressBar = view.findViewById(R.id.progressBarPreparation)
    }

    // Inflate the layout for each exam item
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExamViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_exam_item, parent, false)
        return ExamViewHolder(view)
    }

    // Bind data to each view in the ViewHolder
    override fun onBindViewHolder(holder: ExamViewHolder, position: Int) {
        val examItem = examList[position]
        holder.titleTextView.text = examItem.title
        holder.dateTextView.text = examItem.date
        holder.countdownTextView.text = "${examItem.daysLeft} days left"
        holder.preparationProgressBar.progress = examItem.preparationProgress
    }

    // Return the total number of items
    override fun getItemCount() = examList.size
}
