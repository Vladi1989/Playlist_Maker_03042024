package com.spase_y.playlistmaker05022024.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.spase_y.playlistmaker05022024.R
import com.spase_y.playlistmaker05022024.Track
import java.text.SimpleDateFormat
import java.util.*

class TracksAdapter:RecyclerView.Adapter<TracksAdapter.TracksViewHolder>() {
    class TracksViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        fun bind(track: Track){
            Log.d("TAG",track.toString())
            val tvName = itemView.findViewById<TextView>(R.id.tvName)
            val tvDuration = itemView.findViewById<TextView>(R.id.tvDuration)
            val tvNameArtists = itemView.findViewById<TextView>(R.id.tvNameArtist)
            val ivLogo = itemView.findViewById<ImageView>(R.id.ivTrack)
            tvName.text = track.trackName
            tvNameArtists.text = track.artistName


            tvDuration.text = SimpleDateFormat("mm:ss", Locale.getDefault()).format(track.trackTimeMillis)
            Glide.with(itemView.context).load(track.artworkUrl100).fitCenter().error(R.drawable.placeholder).into(ivLogo)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TracksViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.track_item,parent,false)
        return TracksViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listTracks.size
    }
    var listTracks = arrayListOf<Track>()
    override fun onBindViewHolder(holder: TracksViewHolder, position: Int) {
        holder.bind(listTracks[position])
    }

}
