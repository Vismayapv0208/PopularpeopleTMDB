package com.example.movie

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movie.adapter.MyAdapter
import com.example.movie.fragment.DetailFragment
import com.example.movie.viewmodel.MyViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import dagger.hilt.android.HiltAndroidApp
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var networkStatusTextView: TextView
    private lateinit var searchEditText: EditText
    private lateinit var recyclerView: RecyclerView
    private lateinit var myAdapter: MyAdapter
    private lateinit var searchLayout: LinearLayout
    private val viewModel: MyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize views
        networkStatusTextView = findViewById(R.id.networkStatusTextView)
        searchEditText = findViewById(R.id.searchEditText)
        recyclerView = findViewById(R.id.recyclerView)
        searchLayout= findViewById(R.id.searchLayout)
        // Set up RecyclerView with the adapter and layout manager
        setupRecyclerView()

        // Check network availability
        checkNetworkAndShowMessage()

        // Observe data changes (filtered list from ViewModel)
        observeData()

        // Set up search button click listener
        setupSearch()

        // Initially show all data
        viewModel.showAllData()
    }

//    private fun setupRecyclerView() {
//        myAdapter = MyAdapter { person -> // Handle item click
//            Toast.makeText(this, "Clicked on: ${person.name}", Toast.LENGTH_SHORT).show()
//        }
//        recyclerView.apply {
//            layoutManager = LinearLayoutManager(this@MainActivity)
//            adapter = myAdapter
//        }
//
//        // Set up infinite scrolling using Paging
//        lifecycleScope.launch {
//            viewModel.pagingData.collectLatest { pagingData ->
//                myAdapter.submitData(pagingData)
//            }
//        }
//    }

    private fun hideMainActivityUI() {
        val networkStatusTextView: TextView = findViewById(R.id.networkStatusTextView)
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)

        // Hide the UI elements
        networkStatusTextView.visibility = View.GONE
        searchLayout.visibility = View.GONE
        recyclerView.visibility = View.GONE
    }

    private fun setupRecyclerView() {
        myAdapter = MyAdapter { person -> // Handle item click
            // Log the person ID for debugging
            Log.d("MainActivity", "Clicked on: ${person.id}")
            hideMainActivityUI()
            // Navigate to DetailFragment and pass the person ID
            val bundle = Bundle().apply {
                putInt("person_id", person.id)  // Pass the person's ID to the DetailFragment
            }
            val detailFragment = DetailFragment()
            detailFragment.arguments = bundle

            // Begin the fragment transaction and add DetailFragment
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, detailFragment)  // R.id.fragment_container is your container where fragments are placed
                .addToBackStack(null)  // Add this transaction to the back stack
                .commit()
        }

        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = myAdapter
        }

        // Set up infinite scrolling using Paging
        lifecycleScope.launch {
            viewModel.pagingData.collectLatest { pagingData ->
                myAdapter.submitData(pagingData)
            }
        }
    }

    // Check network connection and show message accordingly
    private fun checkNetworkAndShowMessage() {
        if (isNetworkAvailable(this)) {
            networkStatusTextView.visibility = View.GONE
        } else {
            networkStatusTextView.visibility = View.VISIBLE
        }
    }

    // Check if network is available
    private fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val networkCapabilities =
            connectivityManager.getNetworkCapabilities(network) ?: return false
        return networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    // Set up search button to filter data
    private fun setupSearch() {
        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val query = s.toString()
                viewModel.setSearchQuery(query)  // Update ViewModel with the search query
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    // Observe the filtered data from ViewModel
    private fun observeData() {
        lifecycleScope.launch {
            viewModel.filteredPeople.collectLatest { filteredPeople ->
                myAdapter.submitData(filteredPeople) // Update RecyclerView with filtered list
            }
        }
    }
    private fun restoreMainActivityUI() {
//        networkStatusTextView.visibility = View.VISIBLE
        searchEditText.visibility = View.VISIBLE
        searchLayout.visibility = View.VISIBLE
        recyclerView.visibility = View.VISIBLE
    }
    override fun onResume() {
        super.onResume()
        // You can add code here to refresh the activity or check network status
        restoreMainActivityUI()

    // for example, to check the network again
    }
    override fun onBackPressed() {
        // Call the parent method to handle back press
        super.onBackPressed()

        // Restore the UI elements when navigating back
        restoreMainActivityUI()
    }

}
