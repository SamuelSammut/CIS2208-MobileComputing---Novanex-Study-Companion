package com.example.novanex_studycompanion.ui.subjects

import android.os.Bundle
import android.util.Log
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

// Fragment for displaying and managing subjects
class SubjectsFragment : Fragment() {

    // Obtain the ViewModel instance
    private val subjectViewModel: SubjectViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the fragment layout
        val view = inflater.inflate(R.layout.fragment_subjects, container, false)

        // Set up the RecyclerView
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerViewSubjects)
        val adapter = SubjectsAdapter(emptyList(), subjectViewModel)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        // Set up the FloatingActionButton for adding a new subject
        val fabAddSubject: FloatingActionButton = view.findViewById(R.id.fabAddSubject)
        fabAddSubject.setOnClickListener {
            val dialog = AddSubjectDialogFragment()
            dialog.show(parentFragmentManager, "AddSubjectDialogFragment")
        }

        // Observe the list of subjects and update the RecyclerView
        subjectViewModel.allSubjects.observe(viewLifecycleOwner, Observer { subjects ->
            subjects?.let {
                adapter.submitList(it)
                Log.d("SubjectsFragment", "Subjects loaded: ${it.size}")
            } ?: run {
                Log.d("SubjectsFragment", "Subjects data is null")
            }
        })

        Log.d("SubjectsFragment", "SubjectsFragment created and ViewModel initialized")
        return view
    }
}
