package com.spase_y.playlistmaker05022024.playlist_screen.ui.presentation

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.Gson
import com.spase_y.playlistmaker05022024.R
import com.spase_y.playlistmaker05022024.databinding.BottomSheetPlaylistOptionsBinding
import com.spase_y.playlistmaker05022024.edit_playlist.ui.presentation.EditPlaylistFragment
import com.spase_y.playlistmaker05022024.main.ui.MainActivity
import com.spase_y.playlistmaker05022024.mediateka.playlist.data.model.Playlist
import com.spase_y.playlistmaker05022024.mediateka.root.ui.fragment.MediatekaFragment
import com.spase_y.playlistmaker05022024.playlist_screen.ui.view_model.PlaylistsViewModel
import com.spase_y.playlistmaker05022024.search.domain.model.Track

class PlaylistOptionsBottomSheet(val fragmentContext: Context, style: Int, val vm: PlaylistsViewModel) : BottomSheetDialog(fragmentContext, style) {

    private lateinit var binding: BottomSheetPlaylistOptionsBinding
    var currentPlaylist: Playlist? = null
    val gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Инфлейт layout в onCreate
        binding = BottomSheetPlaylistOptionsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Логика для инициализации
        currentPlaylist?.let { playlist ->
            Glide.with(context)
                .load(playlist.imageUrl)
                .placeholder(R.drawable.placeholder)
                .into(binding.ivTrack)
        }

        // Обрабатываем клики на кнопках
        binding.shareButton.setOnClickListener {
            currentPlaylist?.let { playlist ->
                if(playlist.trackList.isEmpty()){
                    Toast.makeText(context, "В этом плейлисте нет списка треков, которым можно поделиться", Toast.LENGTH_SHORT).show()
                } else {
                    val shareText = createShareText(playlist)
                    sharePlaylist(shareText)
                }
            }
        }

        binding.editButton.setOnClickListener {
            val fragment = EditPlaylistFragment()
            fragment.arguments = Bundle().apply {
                putString("playlist",gson.toJson(currentPlaylist))

            }
            (fragmentContext as MainActivity).supportFragmentManager
                .beginTransaction()
                .add(R.id.fragmentContainerView,fragment)
                .commit()
            dismiss()
        }

        binding.deleteButton.setOnClickListener {
            dismiss()
            showConfirmDeleteDialog()
        }

        // Проверка на null
        currentPlaylist?.let { playlist ->
            binding.tvName.text = playlist.playlistName

            val trackCount = playlist.trackList.size
            val trackWord = getTrackWordForm(trackCount)

            binding.tvCountTracksOnDialod.text = "$trackCount $trackWord"
        }
    }

    private fun showConfirmDeleteDialog() {
        val dialog = AlertDialog.Builder(context)
            dialog.setMessage("Хотите удалить плейлист «${currentPlaylist!!.playlistName}»?")
        dialog.setPositiveButton("Да", object : DialogInterface.OnClickListener{
            override fun onClick(dialog: DialogInterface?, which: Int) {
                vm.deletePlaylist(currentPlaylist!!)
                (fragmentContext as MainActivity).showBottomNavigation()
                (fragmentContext as MainActivity).supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragmentContainerView,MediatekaFragment())
                    .commit()
                dialog!!.dismiss()
            }

        })
        dialog.setNegativeButton("Нет", object : DialogInterface.OnClickListener{
            override fun onClick(dialog: DialogInterface?, which: Int) {
                dialog?.dismiss()
            }

        })
        dialog.show()
    }

    fun getTrackWordForm(count: Int): String {
        return when {
            count % 10 == 1 && count % 100 != 11 -> "трек"
            count % 10 in 2..4 && (count % 100 !in 12..14) -> "трека"
            else -> "треков"
        }
    }

    // Функция для создания текста для шаринга
    private fun createShareText(playlist: Playlist): String {
        val playlistName = playlist.playlistName
        val description = playlist.description
        val trackCount = playlist.trackList.size
        val trackWord = getTrackWordForm(trackCount)

        // Начинаем с заголовка и описания плейлиста
        val shareTextBuilder = StringBuilder()
        shareTextBuilder.append("Плейлист: $playlistName\n")
        shareTextBuilder.append("Описание: $description\n")
        shareTextBuilder.append("Количество треков: $trackCount $trackWord\n")

        // Добавляем нумерованный список треков с продолжительностью
        playlist.trackList.forEachIndexed { index, track ->
            val trackNumber = index + 1
            val artistName = track.artistName
            val trackName = track.trackName
            val trackDuration = formatTrackDuration(track.trackTimeMillis)

            shareTextBuilder.append("$trackNumber. $artistName - $trackName ($trackDuration)\n")
        }

        return shareTextBuilder.toString().trim() // Убираем лишние пробелы в конце текста
    }

    // Функция для форматирования продолжительности трека в формате "мин:сек"
    private fun formatTrackDuration(trackTimeMillis: Long): String {
        val totalSeconds = trackTimeMillis / 1000
        val minutes = totalSeconds / 60
        val seconds = totalSeconds % 60
        return String.format("%d:%02d", minutes, seconds) // Формат "мин:сек", с добавлением нуля к секундам
    }

    // Функция для отправки Intent на шаринг
    private fun sharePlaylist(shareText: String) {
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, shareText)
        }
        context.startActivity(Intent.createChooser(shareIntent, "Поделиться плейлистом"))
    }

    // Функция для расчета продолжительности плейлиста
    private fun calculateTotalDurationInMinutes(trackList: List<Track>): Int {
        var totalDurationMillis = 0L
        for (track in trackList) {
            totalDurationMillis += track.trackTimeMillis
        }
        return (totalDurationMillis / 1000 / 60).toInt()
    }
}