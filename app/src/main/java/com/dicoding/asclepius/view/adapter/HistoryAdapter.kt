package com.dicoding.asclepius.view.adapter

import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.asclepius.R
import com.dicoding.asclepius.data.local.entity.ClasificationEntity
import com.dicoding.asclepius.databinding.HistoryListBinding

class HistoryAdapter: ListAdapter<ClasificationEntity, HistoryAdapter.ClasificationHolder>(DIFF_CALLBACK) {

    class ClasificationHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = HistoryListBinding.bind(itemView)

        fun bind(clasification: ClasificationEntity) {

            val uri = Uri.parse(clasification.imageUriClassification)
            try {
                Glide.with(itemView.context)
                    .load(uri)
                    .into(binding.ivImage)
            } catch (e: Exception) {
                Log.e("HistoryAdapterError", "bind: ${e.message}")
            }

            val isCancer = clasification.clasificationResult.split(" ")[0]
            if (isCancer == "Cancer") {
                binding.listItem.background = itemView.context.getDrawable(R.color.red)
            } else {
                binding.listItem.background = itemView.context.getDrawable(R.color.green)
            }
            binding.tvResult.text = clasification.clasificationResult
            Log.d("HistoryAdapter", "bind: ${clasification.imageUriClassification}")
        }

    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ClasificationEntity>() {
            override fun areItemsTheSame(oldItem: ClasificationEntity, newItem: ClasificationEntity): Boolean {
                return oldItem.imageUriClassification == newItem.imageUriClassification
            }

            override fun areContentsTheSame(oldItem: ClasificationEntity, newItem: ClasificationEntity): Boolean {
                return oldItem.clasificationResult == newItem.clasificationResult
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClasificationHolder {
        val binding = LayoutInflater.from(parent.context).inflate(R.layout.history_list, parent, false)
        return ClasificationHolder(binding)
    }

    override fun onBindViewHolder(holder: ClasificationHolder, position: Int) {
        holder.bind(getItem(position))
    }

}