package com.example.novanex_studycompanion.ui.subjects

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.novanex_studycompanion.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.text.SimpleDateFormat
import java.util.*

class AddSubjectDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Create dialog builder
        val builder = MaterialAlertDialogBuilder(requireContext())
        val inflater = requireActivity().layoutInflater
        val view = inflater.inflate(R.layout.dialog_add_subject, null)

        // Find UI elements
        val editTextTitle = view.findViewById<EditText>(R.id.editTextTitle)
        val buttonSelectDate = view.findViewById<Button>(R.id.buttonSelectDate)
        val textViewSelectedDate = view.findViewById<TextView>(R.id.textViewSelectedDate)

        // Get ViewModel instance
        val subjectViewModel = ViewModelProvider(requireActivity()).get(SubjectViewModel::class.java)

        var selectedDate: String? = null

        // Date picker dialog for selecting the exam date
        buttonSelectDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            val datePickerDialog = DatePickerDialog(requireContext(),
                { _, year, month, dayOfMonth ->
                    val selectedCalendar = Calendar.getInstance().apply {
                        set(year, month, dayOfMonth)
                    }
                    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                    selectedDate = dateFormat.format(selectedCalendar.time)
                    textViewSelectedDate.text = selectedDate
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            datePickerDialog.datePicker.minDate = calendar.timeInMillis
            datePickerDialog.show()
        }

        // Build the dialog
        builder.setView(view)
            .setTitle("Add New Subject")
            .setPositiveButton("Add") { _, _ ->
                val title = editTextTitle.text.toString().trim()
                val date = selectedDate

                // Validate input before adding new subject
                if (validateInput(title, date)) {
                    subjectViewModel.addNewSubject(title, date!!)
                }
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.cancel()
            }

        return builder.create()
    }

    // Validate the input for title and date
    private fun validateInput(title: String, date: String?): Boolean {
        if (title.length < 3 || title.length > 50) {
            Toast.makeText(context, "Title must be between 3 and 50 characters", Toast.LENGTH_SHORT).show()
            return false
        }

        if (date == null) {
            Toast.makeText(context, "Please select a valid exam date", Toast.LENGTH_SHORT).show()
            return false
        }

        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val selectedDate = dateFormat.parse(date)
        val currentDate = Calendar.getInstance().time

        if (selectedDate.before(currentDate) || selectedDate.equals(currentDate)) {
            Toast.makeText(context, "Exam date must be a future date", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }
}
