package com.spase_y.playlistmaker05022024.playlist_screen.ui.presentation

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.spase_y.playlistmaker05022024.R
import com.spase_y.playlistmaker05022024.databinding.BottomSheetPlaylistOptionsBinding
import com.spase_y.playlistmaker05022024.databinding.FragmentPlaylistScreenBinding
import com.spase_y.playlistmaker05022024.main.ui.MainActivity
import com.spase_y.playlistmaker05022024.mediateka.favorites.ui.presentation.ARTIST_NAME_TAG
import com.spase_y.playlistmaker05022024.mediateka.favorites.ui.presentation.ARTWORK_URL_100_TAG
import com.spase_y.playlistmaker05022024.mediateka.favorites.ui.presentation.COLLECTION_NAME_TAG
import com.spase_y.playlistmaker05022024.mediateka.favorites.ui.presentation.COUNTRY_TAG
import com.spase_y.playlistmaker05022024.mediateka.favorites.ui.presentation.PREWIEW_URL_TAG
import com.spase_y.playlistmaker05022024.mediateka.favorites.ui.presentation.PRIMARY_GENRE_NAME_TAG
import com.spase_y.playlistmaker05022024.mediateka.favorites.ui.presentation.RELEASE_DATE_TAG
import com.spase_y.playlistmaker05022024.mediateka.favorites.ui.presentation.TRACK_NAME_TAG
import com.spase_y.playlistmaker05022024.mediateka.favorites.ui.presentation.TRACK_TIME_MILLIS_TAG
import com.spase_y.playlistmaker05022024.mediateka.playlist.data.model.Playlist
import com.spase_y.playlistmaker05022024.mediateka.root.ui.fragment.MediatekaFragment
import com.spase_y.playlistmaker05022024.player.ui.presentation.PlayerFragment
import com.spase_y.playlistmaker05022024.playlist_screen.ui.adapter.PlaylistTrackAdapter
import com.spase_y.playlistmaker05022024.playlist_screen.ui.model.PlaylistScreenState
import com.spase_y.playlistmaker05022024.playlist_screen.ui.view_model.PlaylistsViewModel
import com.spase_y.playlistmaker05022024.search.domain.model.Track
import org.koin.androidx.viewmodel.ext.android.viewModel


class PlaylistScreenFragment : Fragment() {
    private val binding by lazy {
        FragmentPlaylistScreenBinding.inflate(layoutInflater)
    }
    private val gson = Gson()
    private var totalDuration = 0
    private val vm by viewModel<PlaylistsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    val adapter = PlaylistTrackAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val _playlist: String = arguments?.getString("playlist")!!
        val playlist = gson.fromJson(_playlist, Playlist::class.java)
        playlist?.let { displayPlaylist(it) }
        requireActivity().onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                onBack()

            }
        })

        (requireActivity() as MainActivity).hideBottomNavigation()

        binding.buttonBack.setOnClickListener {
            onBack()
        }

        binding.dot3.setOnClickListener {
            val bottomSheet = PlaylistOptionsBottomSheet(requireContext(),R.style.BottomSheetDialogTheme,vm)
            val binding = BottomSheetPlaylistOptionsBinding.inflate(LayoutInflater.from(requireContext()))
            bottomSheet.setContentView(binding.root)
            bottomSheet.currentPlaylist = playlist
            bottomSheet.show()
        }

        binding.sharePlaylist.setOnClickListener {
            playlist?.let { sharePlaylist(it) }
        }

        binding.rvTracks.adapter = adapter
        if(playlist.trackList.isEmpty()){
            binding.rvTracks.visibility = View.GONE
            binding.tvEmpty.visibility = View.VISIBLE
        }
        else {
            binding.rvTracks.visibility = View.VISIBLE
            binding.tvEmpty.visibility = View.GONE
        }
        adapter.listTracks = ArrayList(playlist.trackList.reversed())
        adapter.onItemClick = {
            showPlayerFragment(it)

        }
        adapter.onLongItemClick = {
            showDeleteDialog(playlist,it)
        }
        adapter.notifyDataSetChanged()
        binding.rvTracks.layoutManager = LinearLayoutManager(requireContext())


        vm.getScreenStateLD().observe(viewLifecycleOwner){
            when(it){
                is PlaylistScreenState.Update -> {
                    displayPlaylist(it.playlist)
                    adapter.listTracks = ArrayList(it.playlist.trackList.reversed())
                    adapter.notifyDataSetChanged()
                }
            }
        }
    }

    private fun sharePlaylist(playlist: Playlist) {

        playlist.let { playlist ->
            if(playlist.trackList.isEmpty()){
                Toast.makeText(context, "В этом плейлисте нет списка треков, которым можно поделиться", Toast.LENGTH_SHORT).show()
            } else {
                val shareText = createShareText(playlist)
                val shareIntent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, shareText)
                    type = "text/plain"
                }
                startActivity(Intent.createChooser(shareIntent, "Поделится плейлистом через"))
            }
        }
    }

    override fun onResume() {
        super.onResume()
        (requireActivity() as MainActivity).hideBottomNavigation()
    }

    private fun createShareText(playlist: Playlist): String {
        val trackWord = getTrackWord(playlist.trackList.size)

        // Формируем текст для шаринга
        val shareTextBuilder = StringBuilder()
        shareTextBuilder.append("Плейлист: ${playlist.playlistName}\n")
        shareTextBuilder.append("Описание: ${playlist.description}\n")
        shareTextBuilder.append("Треков: ${playlist.trackList.size} $trackWord\n\n")

        // Нумерованный список треков с их длительностью
        playlist.trackList.forEachIndexed { index, track ->
            val trackNumber = index + 1
            val trackDuration = formatTrackDuration(track.trackTimeMillis)
            shareTextBuilder.append("$trackNumber. ${track.artistName} - ${track.trackName} ($trackDuration)\n")
        }
        return shareTextBuilder.toString()
    }

    // Форматирование времени трека в формате "мин:сек"
    private fun formatTrackDuration(trackTimeMillis: Long): String {
        val totalSeconds = trackTimeMillis / 1000
        val minutes = totalSeconds / 60
        val seconds = totalSeconds % 60
        return String.format("%d:%02d", minutes, seconds) // Формат "мин:сек"
    }

    private fun showPlayerFragment(track: Track) {
        if (vm.clickDebounce()) {
            val playerFragment = PlayerFragment()
            val args = Bundle()
            args.putString(TRACK_NAME_TAG, track.trackName)
            args.putString(PREWIEW_URL_TAG, track.previewUrl)
            args.putString(ARTIST_NAME_TAG, track.artistName)
            args.putLong(TRACK_TIME_MILLIS_TAG, track.trackTimeMillis)
            args.putString(ARTWORK_URL_100_TAG, track.artworkUrl100)
            args.putString(COLLECTION_NAME_TAG, track.collectionName)
            args.putString(RELEASE_DATE_TAG, track.releaseDate)
            args.putString(PRIMARY_GENRE_NAME_TAG, track.primaryGenreName)
            args.putString(COUNTRY_TAG, track.country)

            playerFragment.arguments = args

            requireActivity().supportFragmentManager.beginTransaction()
                .add(R.id.fragmentContainerView, playerFragment)
                .addToBackStack(null)
                .commit()
        }
    }


    private fun displayPlaylist(playlist: Playlist) {
        // Пример отображения данных плейлиста на экране
        binding.tvNamePlaylist.text = playlist.playlistName
        binding.tvDescription.text = playlist.description
        binding.tvCountOfTracks.text =
            "${playlist.trackList.size} ${getTrackWord(playlist.trackList.size)}"

        // Другие данные и изображения плейлиста
        // Например, использование Glide для загрузки изображения
        if (playlist.imageUrl.isNotEmpty()) {
            Glide.with(requireContext())
                .load(playlist.imageUrl)
                .placeholder(R.drawable.placeholder)
                .into(binding.placeholder)
        }
        totalDuration = calculateTotalDurationInMinutes(playlist.trackList)
        binding.tvAllDurationTracks.text = "$totalDuration минут"
    }


    private fun calculateTotalDurationInMinutes(trackList: List<Track>): Int {
        var totalDurationMillis = 0L
        for (track in trackList) {
            totalDurationMillis += track.trackTimeMillis
            Log.d("TAG", totalDurationMillis.toString())
        }
        return (totalDurationMillis / 1000 / 60).toInt()
    }

    private fun getTrackWord(count: Int): String {
        // Логика для склонения слова "трек" в зависимости от количества
        // Можете использовать вашу логику, как в вашем адаптере
        return when {
            count % 10 == 1 && count % 100 != 11 -> "трек"
            count % 10 in 2..4 && (count % 100 !in 12..14) -> "трека"
            else -> "треков"
        }
    }

    private fun onBack() {
        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainerView,MediatekaFragment())
            .commit()
    }

    private fun showDeleteDialog(playlist:Playlist, track: Track) {
        val dialog = AlertDialog.Builder(requireContext())
        dialog.setMessage("Хотите удалить трек?")
        dialog.setNegativeButton("Отмена", object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface?, which: Int) {
                dialog?.dismiss()
            }

        })
        dialog.setPositiveButton("Удалить", object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface?, which: Int) {
                dialog?.dismiss()
                vm.deleteTrack(playlist, track)
            }

        })
        dialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (requireActivity() as MainActivity).showBottomNavigation()
    }
}

