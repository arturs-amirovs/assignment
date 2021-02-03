package com.example.f3test.ui.album

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.f3test.data.albums.Album
import com.example.f3test.databinding.AlbumListItemBinding
import com.squareup.picasso.Picasso

class AlbumListAdapter(private val clicked: (albumId: String) -> Unit) :
    PagingDataAdapter<Album, AlbumListAdapter.AlbumsViewHolder>(
        AlbumsDiffCallback()
    ) {

    override fun onBindViewHolder(holder: AlbumsViewHolder, position: Int) {

        val data = getItem(position)

        holder.bind(data, clicked)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumsViewHolder {

        return AlbumsViewHolder(
            AlbumListItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    }

    class AlbumsViewHolder(
        private val binding: AlbumListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Album?, clicked: (albumId: String) -> Unit) {

            binding.let {
                it.root.setOnClickListener {
                    clicked(data?.id ?: "")
                }
                it.name.text = data?.name
                it.count.text = data?.count ?: "0"

                Picasso.get()
                    .load(data?.photo?.data?.first()?.url)
                    .into(it.picture)
            }

        }
    }

    private class AlbumsDiffCallback : DiffUtil.ItemCallback<Album>() {
        override fun areItemsTheSame(oldItem: Album, newItem: Album): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Album, newItem: Album): Boolean {
            return oldItem == newItem
        }
    }

}