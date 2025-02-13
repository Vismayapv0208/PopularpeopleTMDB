package com.example.movie.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.activity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movie.R
import com.example.movie.adapter.ImageAdapter
import com.example.movie.model.PersonDetailsResponse
import com.example.movie.viewmodel.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.fragment_detail) {

    private lateinit var backButton: ImageButton  // Back button
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var detailsLayout: LinearLayout  // Layout to hold dynamically created TextViews
    private lateinit var myAdapter: ImageAdapter
    private val viewModel: DetailViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize views
        progressBar = view.findViewById(R.id.progressBar)
        recyclerView = view.findViewById(R.id.recyclerView)
        detailsLayout = view.findViewById(R.id.detailsLayout)  // LinearLayout to hold TextViews
        backButton = view.findViewById(R.id.backButton)

        // Set up back button click listener to navigate to MainActivity
        backButton.setOnClickListener {
            activity?.onBackPressed()  // Navigate back to the previous activity (MainActivity)
        }

        val personId = arguments?.getInt("person_id") ?: return
        Log.d("DetailFragment", "Received personId: $personId")

        // Show progress bar while loading data
        progressBar.visibility = View.VISIBLE

        // Make API calls
        viewModel.getPersonImages(personId)
        viewModel.getPersonDetails(personId)

        // Observe the data for images and details
        observeData(personId)
    }

    private fun observeData(personId: Int) {
        // Observe person images
        viewModel.personImages.observe(viewLifecycleOwner) { images ->
            myAdapter = ImageAdapter(requireContext(), images)
            recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            recyclerView.adapter = myAdapter
        }

        // Observe person details
        viewModel.personDetails.observe(viewLifecycleOwner) { details ->
            // Dynamically add TextViews for the details
            populatePersonDetails(details)

            // Hide progress bar after data is loaded
            progressBar.visibility = View.GONE
        }
    }

    private fun populatePersonDetails(details: PersonDetailsResponse) {
        // Clear any previous views
        detailsLayout.removeAllViews()

        // Create a TextView for each field in PersonDetailsResponse
        val fields = listOf(
            "Name" to details.name,
            "Biography" to details.biography,
            "Birthday" to details.birthday,
            "Deathday" to (details.deathday ?: "N/A"),
            "Gender" to formatGender(details.gender),
            "Place of Birth" to details.place_of_birth,
            "Known For" to details.known_for_department,
            "Popularity" to details.popularity.toString()
        )

        // Iterate over fields and add TextViews to the layout
        for (field in fields) {
            val labelText = "${field.first}:"
            val valueText = field.second

            // Create label TextView
            val labelTextView = TextView(requireContext()).apply {
                text = labelText
                textSize = 16f
                setPadding(0, 16, 0, 4)
                setTextColor(ContextCompat.getColor(context, R.color.purple_700)) // Set label color to teal700
            }
            detailsLayout.addView(labelTextView)

            // Create value TextView
            val valueTextView = TextView(requireContext()).apply {
                text = valueText
                textSize = 14f
                setPadding(0, 0, 0, 16)
                setTextColor(ContextCompat.getColor(context, R.color.purple_200)) // Set value color to teal200
            }
            detailsLayout.addView(valueTextView)
        }
    }

    private fun formatGender(gender: Int): String {
        return when (gender) {
            1 -> "Female"
            2 -> "Male"
            else -> "Not specified"
        }
    }
}