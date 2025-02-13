package com.example.movie.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.movie.R
import com.example.movie.model.Person

class MyAdapter(private val onItemClick: (Person) -> Unit) : PagingDataAdapter<Person, MyAdapter.PersonViewHolder>(PersonComparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        // Inflate the item layout manually
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_person, parent, false)
        return PersonViewHolder(view)
    }

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        val person = getItem(position)
        if (person != null) {
            holder.bind(person)
        }
    }

    inner class PersonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        private val knownForTextView: TextView = itemView.findViewById(R.id.departmentTextView)

        fun bind(person: Person) {
            nameTextView.text = person.name
            knownForTextView.text = "Known for : ${person.knownForDepartment}"

            // Log the data when it's bound to the view
            Log.e("API_RESPONSE", "Binding item: Name - ${person.name}, Known For - ${person.knownForDepartment}")

            // Handle item click if needed
            itemView.setOnClickListener {
                onItemClick(person)

            }
        }
    }

    object PersonComparator : DiffUtil.ItemCallback<Person>() {
        override fun areItemsTheSame(oldItem: Person, newItem: Person): Boolean {
            return oldItem.id == newItem.id  // Assuming `id` is the unique identifier
        }

        override fun areContentsTheSame(oldItem: Person, newItem: Person): Boolean {
            return oldItem == newItem
        }
    }
}
