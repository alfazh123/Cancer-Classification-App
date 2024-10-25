package com.dicoding.asclepius.view.adapter

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.asclepius.R
import com.dicoding.asclepius.data.local.entity.ClasificationEntity
import com.dicoding.asclepius.databinding.ItemListBinding

class HistoryAdapter: ListAdapter<ClasificationEntity, HistoryAdapter.ClasificationHolder>(DIFF_CALLBACK) {

    class ClasificationHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemListBinding.bind(itemView)

        fun bind(clasification: ClasificationEntity) {

            val uri = Uri.parse(clasification.imageUriClassification)
            try {
                Glide.with(itemView.context)
                    .load(uri)
                    .into(binding.ivImage)
            } catch (e: Exception) {
                Log.e("HistoryAdapterError", "bind: ${e.message}")
            }

//            Glide.with(itemView.context)
//                .load(uri)
//                .into(binding.ivImage)
//            binding.ivImage.setImageURI(uri)
            binding.tvResult.text = clasification.clasificationResult
            Log.d("HistoryAdapter", "bind: ${clasification.imageUriClassification}")
        }

//            fun bind(clasification: ClasificationEntity) {
//                val uri = Uri.parse(clasification.imageUriClassification)
//                Glide.with(itemView.context)
//                    .load(uri)
//                    .into(binding.ivImage)
//                binding.tvResult.text = clasification.clasificationResult
//                Log.d("HistoryAdapter", "bind: ${clasification.imageUriClassification}")
//            }

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
        val binding = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return ClasificationHolder(binding)
    }

    override fun onBindViewHolder(holder: ClasificationHolder, position: Int) {
        holder.bind(getItem(position))
    }

}