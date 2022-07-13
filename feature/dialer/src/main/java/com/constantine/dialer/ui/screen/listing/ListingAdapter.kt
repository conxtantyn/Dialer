package com.constantine.dialer.ui.screen.listing

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.constantine.dialer.R
import com.constantine.dialer.databinding.ListingItemContactBinding
import com.constantine.domain.parcelable.ContactLog
import javax.inject.Inject

class ListingAdapter @Inject constructor() :
    ListAdapter<ContactLog, ListingAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val layout = ListingItemContactBinding.inflate(inflater, parent, false)
        return ViewHolder(layout)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    fun submit(contacts: List<ContactLog>) = submitList(contacts)

    class ViewHolder(private val binding: ListingItemContactBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private var context: Context = binding.root.context

        fun bind(log: ContactLog) {
            binding.root.setOnClickListener { }
            binding.name.text = if (log.name.isNullOrEmpty()) log.number else log.name
            binding.duration.text = context.getString(R.string.duration, log.duration)
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<ContactLog>() {
        override fun areItemsTheSame(oldItem: ContactLog, newItem: ContactLog): Boolean {
            return oldItem.uId == newItem.uId
        }

        override fun areContentsTheSame(oldItem: ContactLog, newItem: ContactLog): Boolean {
            return oldItem == newItem
        }
    }
}
