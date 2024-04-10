package com.spase_y.playlistmaker05022024.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.spase_y.playlistmaker05022024.*
import com.spase_y.playlistmaker05022024.PlayerActivity.Companion.currentTrackItem
import java.text.SimpleDateFormat
import java.util.*

class TracksAdapter:RecyclerView.Adapter<TracksAdapter.TracksViewHolder>() {
    class TracksViewHolder(itemView: View,val onItemClick:(Track)->Unit):RecyclerView.ViewHolder(itemView) {
        fun bind(track: Track){
            val tvName = itemView.findViewById<TextView>(R.id.tvName)
            val tvDuration = itemView.findViewById<TextView>(R.id.tvDuration)
            val tvNameArtists = itemView.findViewById<TextView>(R.id.tvNameArtist)
            val ivLogo = itemView.findViewById<ImageView>(R.id.ivTrack)
            tvName.text = track.trackName
            tvNameArtists.text = track.artistName
            itemView.setOnClickListener{
                onItemClick.invoke(track)
                currentTrackItem = track

            }
            tvDuration.text = SimpleDateFormat("mm:ss", Locale.getDefault()).format(track.trackTimeMillis)
            Glide.with(itemView.context)
                .load(track.artworkUrl100)
                .fitCenter()
                .error(R.drawable.placeholder)
                .placeholder(R.drawable.placeholder)
                .into(ivLogo)
        }
    }
    var onItemClick: (Track) -> Unit = {}
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TracksViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.track_item,parent,false)
        return TracksViewHolder(view,onItemClick)
    }
    override fun getItemCount(): Int {
        return listTracks.size
    }
    var listTracks = arrayListOf<Track>()
    override fun onBindViewHolder(holder: TracksViewHolder, position: Int) {
        holder.bind(listTracks[position])
    }

}
