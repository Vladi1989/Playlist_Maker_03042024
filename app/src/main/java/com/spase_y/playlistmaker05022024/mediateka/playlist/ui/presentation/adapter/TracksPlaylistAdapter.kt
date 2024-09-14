package com.spase_y.playlistmaker05022024.mediateka.playlist.ui.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.spase_y.playlistmaker05022024.R
import com.spase_y.playlistmaker05022024.mediateka.playlist.data.model.Playlist


class TracksPlaylistAdapter(
) : RecyclerView.Adapter<TracksPlaylistAdapter.TracksPlaylistViewHolder>() {

    var playlistItems: List<Playlist> = emptyList()

    inner class TracksPlaylistViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val playlistImageView: ImageView = itemView.findViewById(R.id.ivPlaylist)
        private val albumNameTextView: TextView = itemView.findViewById(R.id.tvFavotiteAlbomName)
        private val trackCountTextView: TextView = itemView.findViewById(R.id.countTracksInPlaylist)


        fun bind(item: Playlist) {
            if (item.imageUrl.isEmpty()) {
                playlistImageView.setImageResource(R.drawable.placeholder)
            } else {
                Glide.with(itemView)
                    .load(item.imageUrl)
                    .centerCrop()
                    .placeholder(R.drawable.placeholder)
                    .into(playlistImageView)
            }
            albumNameTextView.text = item.playlistName
            trackCountTextView.text = "${item.trackList.size} ${getTrackWord(item.trackList.size)}"
            itemView.setOnClickListener{
                onPlaylistClick(item)
            }
        }

        private fun getTrackWord(count: Int): String {
            return when {
                count % 10 == 1 && count % 100 != 11 -> "трек"
                count % 10 in 2..4 && (count % 100 !in 12..14) -> "трека"
                else -> "треков"
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TracksPlaylistViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.playlist_item, parent, false)
        return TracksPlaylistViewHolder(view)
    }


    override fun onBindViewHolder(holder: TracksPlaylistViewHolder, position: Int) {
        val item = playlistItems[position]
        holder.bind(item)
    }


    override fun getItemCount(): Int {
        return playlistItems.size
    }
    var onPlaylistClick:(Playlist) -> Unit = {

    }

}


