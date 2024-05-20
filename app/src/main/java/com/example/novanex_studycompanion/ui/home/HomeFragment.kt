package com.example.novanex_studycompanion.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.novanex_studycompanion.databinding.FragmentHomeBinding

// Fragment for the home screen
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val homeViewModel: HomeViewModel by viewModels()

    // Inflate the fragment layout and initialize the RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setupRecyclerView()

        return root
    }

    // Set up the RecyclerView to display exam items
    private fun setupRecyclerView() {
        homeViewModel.examItems.observe(viewLifecycleOwner) { examItems ->
            val adapter = ExamAdapter(examItems)
            binding.recyclerViewDashboard.adapter = adapter
            binding.recyclerViewDashboard.layoutManager = LinearLayoutManager(context)

            if (examItems.isEmpty()) {
                binding.textViewNoSubjects.visibility = View.VISIBLE
                binding.recyclerViewDashboard.visibility = View.GONE
            } else {
                binding.textViewNoSubjects.visibility = View.GONE
                binding.recyclerViewDashboard.visibility = View.VISIBLE
            }
        }
    }

    // Clean up the binding when the view is destroyed
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
