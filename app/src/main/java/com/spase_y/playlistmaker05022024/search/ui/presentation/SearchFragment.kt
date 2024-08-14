package com.spase_y.playlistmaker05022024.search.ui.presentation


import org.koin.androidx.viewmodel.ext.android.viewModel


import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.ScrollView
import androidx.appcompat.widget.AppCompatButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.spase_y.playlistmaker05022024.R
import com.spase_y.playlistmaker05022024.databinding.FragmentSearchBinding
import com.spase_y.playlistmaker05022024.mediateka.favorites.ui.presentation.ARTIST_NAME_TAG
import com.spase_y.playlistmaker05022024.mediateka.favorites.ui.presentation.ARTWORK_URL_100_TAG
import com.spase_y.playlistmaker05022024.mediateka.favorites.ui.presentation.COLLECTION_NAME_TAG
import com.spase_y.playlistmaker05022024.mediateka.favorites.ui.presentation.COUNTRY_TAG
import com.spase_y.playlistmaker05022024.mediateka.favorites.ui.presentation.PREWIEW_URL_TAG
import com.spase_y.playlistmaker05022024.mediateka.favorites.ui.presentation.PRIMARY_GENRE_NAME_TAG
import com.spase_y.playlistmaker05022024.mediateka.favorites.ui.presentation.RELEASE_DATE_TAG
import com.spase_y.playlistmaker05022024.mediateka.favorites.ui.presentation.TRACK_NAME_TAG
import com.spase_y.playlistmaker05022024.mediateka.favorites.ui.presentation.TRACK_TIME_MILLIS_TAG
import com.spase_y.playlistmaker05022024.player.ui.presentation.PlayerFragment
import com.spase_y.playlistmaker05022024.search.domain.model.Track
import com.spase_y.playlistmaker05022024.search.ui.TrackScreenState
import com.spase_y.playlistmaker05022024.search.ui.adapter.TracksAdapter
import com.spase_y.playlistmaker05022024.search.ui.view_model.SearchViewModel


class SearchFragment : Fragment() {
    private val viewModel: SearchViewModel by viewModel()
    private lateinit var searchTrackAdapter: TracksAdapter
    private lateinit var historyTrackAdapter: TracksAdapter
    private lateinit var editText: EditText
    private lateinit var progressBar: ProgressBar
    private lateinit var clNotFound: ConstraintLayout
    private lateinit var noInternet: ConstraintLayout
    private lateinit var btnUpdate: AppCompatButton
    private lateinit var rvHistory: RecyclerView
    private lateinit var clHistory: ScrollView
    private lateinit var btnClearHistory: AppCompatButton
    private lateinit var rvTracks: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentSearchBinding.inflate(inflater, container, false)
        editText = binding.editText
        progressBar = binding.pb
        clNotFound = binding.clNotFound
        noInternet = binding.clFailure
        btnUpdate = binding.btnUpdate
        rvHistory = binding.rvHistory
        clHistory = binding.clHistory
        btnClearHistory = binding.btnClearHistory
        rvTracks = binding.rvTracks

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchTrackAdapter = TracksAdapter().apply {
            onItemClick = { track -> showPlayerFragment(track) }
        }
        historyTrackAdapter = TracksAdapter().apply {
            onItemClick = { track -> showPlayerFragment(track) }
        }

        rvTracks.layoutManager = LinearLayoutManager(requireContext())
        rvTracks.adapter = searchTrackAdapter

        rvHistory.layoutManager = LinearLayoutManager(requireContext())
        rvHistory.adapter = historyTrackAdapter

        viewModel.getScreenStateLD().observe(viewLifecycleOwner) { state ->
            when (state) {
                is TrackScreenState.Loading -> showLoading()
                is TrackScreenState.History -> showHistory(state.tracks)
                is TrackScreenState.Empty -> showEmpty()
                is TrackScreenState.Content -> showContent(state.tracks)
                is TrackScreenState.Error -> showError()
            }
        }

        btnClearHistory.setOnClickListener {
            viewModel.deleteAllItems()
            historyTrackAdapter.listTracks = arrayListOf()
            historyTrackAdapter.notifyDataSetChanged()
            clHistory.visibility = View.GONE
        }

        btnUpdate.setOnClickListener {
            noInternet.visibility = View.GONE
            viewModel.makeRequest(editText.text.toString())
        }

        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.searchDebounce(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        val clear = requireView().findViewById<ImageView>(R.id.clear)
        clear.setOnClickListener {
            searchTrackAdapter.listTracks = arrayListOf()
            searchTrackAdapter.notifyDataSetChanged()
            editText.setText("")
            val inputMethodManager =
                requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(editText.windowToken, 0)
            clHistory.visibility = View.GONE
        }

        editText.requestFocus()

        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.isNullOrEmpty()) {
                    clear.visibility = View.INVISIBLE
                    searchTrackAdapter.listTracks = arrayListOf()
                    searchTrackAdapter.notifyDataSetChanged()
                } else {
                    clear.visibility = View.VISIBLE
                }

                if (s.isNullOrEmpty() && viewModel.getAllItems().isNotEmpty()) {
                    clHistory.visibility = View.VISIBLE
                    historyTrackAdapter.listTracks = ArrayList(viewModel.getAllItems())
                    historyTrackAdapter.notifyDataSetChanged()
                } else {
                    clHistory.visibility = View.GONE
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        if (editText.text.isEmpty() && viewModel.getAllItems().isNotEmpty()) {
            clHistory.visibility = View.VISIBLE
            rvHistory.layoutManager = LinearLayoutManager(requireContext())
            historyTrackAdapter.listTracks = viewModel.getAllItems() as ArrayList<Track>
            rvHistory.adapter = historyTrackAdapter
        } else {
            clHistory.visibility = View.GONE
        }

        val rvTracks = requireView().findViewById<RecyclerView>(R.id.rvTracks)
        rvTracks.layoutManager = LinearLayoutManager(requireContext())
        rvTracks.adapter = searchTrackAdapter
    }

    private fun showPlayerFragment(track: Track) {
        if (viewModel.clickDebounce()) {
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



            if (viewModel.getAllItems().contains(track)) {
                viewModel.deleteItem(track)
                viewModel.addItem(track)
            } else if (viewModel.getAllItems().size < 10) {
                viewModel.addItem(track)
            }

            requireActivity().supportFragmentManager.beginTransaction()
                .add(R.id.fragmentContainerView, playerFragment)
                .addToBackStack(null)
                .commit()
        }
    }

    private fun showHistory(tracksList: List<Track>) {
        clNotFound.visibility = View.GONE
        noInternet.visibility = View.GONE
        progressBar.visibility = View.GONE
        if (tracksList.isEmpty()) {
            clHistory.visibility = View.GONE
        } else {
            historyTrackAdapter.listTracks = ArrayList(tracksList)
            historyTrackAdapter.notifyDataSetChanged()
            clHistory.visibility = View.VISIBLE
        }
    }

    private fun showLoading() {
        clNotFound.visibility = View.GONE
        noInternet.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
        searchTrackAdapter.listTracks = arrayListOf()
        searchTrackAdapter.notifyDataSetChanged()
        clHistory.visibility = View.GONE
    }

    private fun showError() {
        progressBar.visibility = View.GONE
        noInternet.visibility = View.VISIBLE
        searchTrackAdapter.listTracks = arrayListOf()
        searchTrackAdapter.notifyDataSetChanged()
    }

    private fun showEmpty() {
        progressBar.visibility = View.GONE
        noInternet.visibility = View.GONE
        clNotFound.visibility = View.VISIBLE

        searchTrackAdapter.listTracks = arrayListOf()
        searchTrackAdapter.notifyDataSetChanged()
    }

    private fun showContent(tracksList: List<Track>) {
        clNotFound.visibility = View.GONE
        clNotFound.visibility = View.GONE
        searchTrackAdapter.listTracks = ArrayList(tracksList)
        searchTrackAdapter.notifyDataSetChanged()
        progressBar.visibility = View.GONE
    }
}