package com.example.movie.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.movie.R
import com.example.movie.model.Person

class PersonListAdapter(private val onItemClick: (Person) -> Unit) : PagingDataAdapter<Person, PersonListAdapter.PersonViewHolder>(PersonComparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
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
            itemView.setOnClickListener {
                onItemClick(person)

            }
        }
    }

    object PersonComparator : DiffUtil.ItemCallback<Person>() {
        override fun areItemsTheSame(oldItem: Person, newItem: Person): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Person, newItem: Person): Boolean {
            return oldItem == newItem
        }
    }
}
