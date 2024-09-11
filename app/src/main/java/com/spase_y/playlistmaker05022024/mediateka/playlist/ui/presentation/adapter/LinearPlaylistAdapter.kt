package com.spase_y.playlistmaker05022024.mediateka.playlist.ui.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.spase_y.playlistmaker05022024.R
import com.spase_y.playlistmaker05022024.databinding.PlaylistItem2Binding
import com.spase_y.playlistmaker05022024.mediateka.playlist.data.model.Playlist

class LinearPlaylistAdapter : RecyclerView.Adapter<LinearPlaylistAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding: PlaylistItem2Binding):RecyclerView.ViewHolder(binding.root) {
        fun bind(playlist: Playlist){
            binding.root.setOnClickListener{
                onItemClick.invoke(playlist)
            }
            binding.tvName.text = playlist.playlistName
            if (playlist.imageUrl == ""){
                binding.ivTrack.setImageResource(R.drawable.placeholder)
            } else {
                Glide.with(itemView).load(playlist.imageUrl).centerCrop().placeholder(R.drawable.placeholder).into(binding.ivTrack)
            }
            binding.tvNameArtist.text = playlist.trackList.size.toString() + " треков"
        }
    }
    var onItemClick: (Playlist) -> Unit = {

    }
    var playlist = listOf<Playlist>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = PlaylistItem2Binding.inflate(layoutInflater,parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return playlist.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(playlist[position])

    }

}
