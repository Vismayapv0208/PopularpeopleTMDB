package com.example.movie

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movie.adapter.PersonListAdapter
import com.example.movie.fragment.DetailFragment
import com.example.movie.viewmodel.PersonListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var networkStatusTextView: TextView
    private lateinit var searchEditText: EditText
    private lateinit var recyclerView: RecyclerView
    private lateinit var personListAdapter: PersonListAdapter
    private lateinit var searchLayout: LinearLayout
    private val viewModel: PersonListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        networkStatusTextView = findViewById(R.id.networkStatusTextView)
        searchEditText = findViewById(R.id.searchEditText)
        recyclerView = findViewById(R.id.recyclerView)
        searchLayout= findViewById(R.id.searchLayout)
        setupRecyclerView()

        checkNetworkAndShowMessage()

        observeData()

        setupSearch()

        viewModel.showAllData()

        observeToastMessages()
    }

    private fun observeToastMessages() {
        viewModel.toastMessage.observe(this) { message ->
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()  // Show Toast message
        }
    }

    private fun setupRecyclerView() {
        personListAdapter = PersonListAdapter { person ->
            hideMainActivityUI()
            val bundle = Bundle().apply {
                putInt("person_id", person.id)
            }
            val detailFragment = DetailFragment()
            detailFragment.arguments = bundle

            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, detailFragment)
                .addToBackStack(null)
                .commit()
        }

        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = personListAdapter
        }

        lifecycleScope.launch {
            viewModel.pagingData.collectLatest { pagingData ->
                personListAdapter.submitData(pagingData)
            }
        }
    }
    private fun hideMainActivityUI() {
        val networkStatusTextView: TextView = findViewById(R.id.networkStatusTextView)
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)

        networkStatusTextView.visibility = View.GONE
        searchLayout.visibility = View.GONE
        recyclerView.visibility = View.GONE
    }

    private fun restoreMainActivityUI() {
        searchEditText.visibility = View.VISIBLE
        searchLayout.visibility = View.VISIBLE
        recyclerView.visibility = View.VISIBLE
    }

    private fun checkNetworkAndShowMessage() {
        if (isNetworkAvailable(this)) {
            networkStatusTextView.visibility = View.GONE
        } else {
            networkStatusTextView.visibility = View.VISIBLE
        }
    }

    private fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val networkCapabilities =
            connectivityManager.getNetworkCapabilities(network) ?: return false
        return networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    private fun setupSearch() {
        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val query = s.toString()
                viewModel.setSearchQuery(query)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun observeData() {
        lifecycleScope.launch {
            viewModel.filteredPeople.collectLatest { filteredPeople ->
                personListAdapter.submitData(filteredPeople)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        restoreMainActivityUI()

    }
    override fun onBackPressed() {
        super.onBackPressed()
        restoreMainActivityUI()
    }

}
