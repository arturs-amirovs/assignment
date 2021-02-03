package com.example.f3test.ui.photo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.f3test.data.photo.Photo
import com.example.f3test.databinding.PhotoListItemBinding
import com.squareup.picasso.Picasso

class PhotoListAdapter(private val clicked: (String?) -> Unit) :
        PagingDataAdapter<Photo, PhotoListAdapter.PhotoViewHolder>(
                PhotoDiffCallback()
        ) {

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {

        val data = getItem(position)

        holder.bind(data, clicked)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {

        return PhotoViewHolder(
                PhotoListItemBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                )
        )

    }

    class PhotoViewHolder(
            private val binding: PhotoListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Photo?, clicked: (String?) -> Unit) {

            binding.let {
                it.root.setOnClickListener {
                    clicked(data?.url)
                }

                Picasso.get()
                        .load(data?.url)
                        .into(it.picture)
            }

        }
    }

    private class PhotoDiffCallback : DiffUtil.ItemCallback<Photo>() {
        override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean {
            return oldItem == newItem
        }
    }
}

