package com.spase_y.playlistmaker05022024.search.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.ScrollView
import androidx.appcompat.app.AppCompatActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import androidx.appcompat.widget.AppCompatButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.spase_y.playlistmaker05022024.R
import com.spase_y.playlistmaker05022024.search.domain.model.Track
import com.spase_y.playlistmaker05022024.player.ui.activity.PlayerActivity
import com.spase_y.playlistmaker05022024.search.ui.TrackScreenState
import com.spase_y.playlistmaker05022024.search.ui.adapter.TracksAdapter
import com.spase_y.playlistmaker05022024.search.ui.view_model.SearchViewModel


class SearchActivity : AppCompatActivity() {
    private val viewModel: SearchViewModel by viewModel()
    override fun onRestoreInstanceState(
        savedInstanceState: Bundle?,
        persistentState: PersistableBundle?
    ) {
        super.onRestoreInstanceState(savedInstanceState, persistentState)
        if (savedInstanceState != null) {
            val editText = findViewById<EditText>(R.id.editText)
            editText.setText(savedInstanceState.getString(EDIT_TEXT_TAG, ""))
        }
    }
    val searchTrackAdapter = TracksAdapter()
    val historyTrackAdapter = TracksAdapter()

    val editText by lazy {
        findViewById<EditText>(R.id.editText)
    }
    val progressBar by lazy {
        findViewById<ProgressBar>(R.id.pb)
    }
    val arrowBack by lazy {
        findViewById<ImageButton>(R.id.buttonBack)
    }
    val clNotFound by lazy {
        findViewById<ConstraintLayout>(R.id.clNotFound)
    }
    val noInternet by lazy {
        findViewById<ConstraintLayout>(R.id.clFailure)
    }
    val btnUpdate by lazy {
        findViewById<AppCompatButton>(R.id.btnUpdate)
    }
    val rvHistory by lazy {
        findViewById<RecyclerView>(R.id.rvHistory)
    }
    val clHistory by lazy {
        findViewById<ScrollView>(R.id.clHistory)
    }
    val btnClearHistory by lazy {
        findViewById<AppCompatButton>(R.id.btnClearHistory)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        viewModel.getScreenStateLD().observe(this){
            when (it){
                is TrackScreenState.Loading -> {
                    showLoading()
                }
                is TrackScreenState.History -> {
                    showHistory(it.tracks)
                }
                is TrackScreenState.Empty -> {
                    showEmpty()
                }
                is TrackScreenState.Content -> {
                    showContent(it.tracks)
                }
                is TrackScreenState.Error -> {
                    showError()
                }
            }
        }
        btnClearHistory.setOnClickListener {
            viewModel.deleteAllItems()
            historyTrackAdapter.listTracks = arrayListOf<Track>()
            historyTrackAdapter.notifyDataSetChanged()
            clHistory.visibility = View.GONE
        }
        arrowBack.setOnClickListener {
            finish()
        }
        btnUpdate.setOnClickListener {
            noInternet.visibility = View.GONE
            viewModel.makeRequest(editText.text.toString())
        }
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.searchDebounce(p0.toString())
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })
        val clear = findViewById<ImageView>(R.id.clear)
        searchTrackAdapter.onItemClick = {
            showPlayerActivity(it)
        }
        historyTrackAdapter.onItemClick = {
            showPlayerActivity(it)
        }
        clear.setOnClickListener {
            searchTrackAdapter.listTracks = arrayListOf()
            searchTrackAdapter.notifyDataSetChanged()
            editText.setText("")
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            inputMethodManager?.hideSoftInputFromWindow(editText.windowToken, 0)
        }
        editText.requestFocus()
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                if (p0.toString().isNullOrEmpty()) {
                    clear.visibility = View.INVISIBLE
                    searchTrackAdapter.listTracks = ArrayList()
                    searchTrackAdapter.notifyDataSetChanged()
                } else clear.visibility = View.VISIBLE

                if (p0.toString().isNullOrEmpty() && viewModel.getAllItems().isNotEmpty()) {
                    clHistory.visibility = View.VISIBLE
                    historyTrackAdapter.listTracks = ArrayList(viewModel.getAllItems())
                    historyTrackAdapter.notifyDataSetChanged()
                } else {
                    clHistory.visibility = View.GONE
                }
            }
        })

        if (editText.text.isEmpty() && viewModel.getAllItems().isNotEmpty()) {
            clHistory.visibility = View.VISIBLE
            rvHistory.layoutManager = LinearLayoutManager(this)
            historyTrackAdapter.listTracks = viewModel.getAllItems() as ArrayList<Track>
            rvHistory.adapter = historyTrackAdapter
        } else {
            clHistory.visibility = View.GONE
        }

        val rv = findViewById<RecyclerView>(R.id.rvTracks)
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = searchTrackAdapter
    }

    private fun showPlayerActivity(it: Track) {
        if (viewModel.clickDebounce()) {
            val intent = Intent(this, PlayerActivity::class.java)
            intent.putExtra("trackName", it.trackName)
            intent.putExtra("previewUrl", it.previewUrl)
            intent.putExtra("artistName", it.artistName)
            intent.putExtra("trackTimeMillis", it.trackTimeMillis)
            intent.putExtra("artworkUrl100", it.artworkUrl100)
            intent.putExtra("collectionName", it.collectionName)
            intent.putExtra("releaseDate", it.releaseDate)
            intent.putExtra("primaryGenreName", it.primaryGenreName)
            intent.putExtra("country", it.country)
            startActivity(intent)
            if (viewModel.getAllItems().contains(it)) {
                viewModel.deleteItem(it)
                viewModel.addItem(it)
            } else if (viewModel.getAllItems().size < 10) {
                viewModel.addItem(it)
            }
        }
    }



    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(EDIT_TEXT_TAG, findViewById<EditText>(R.id.editText).text.toString())
    }

    companion object {
        const val EDIT_TEXT_TAG = "Edit text outstate"
        const val CLICK_DEBOUNCE_DELAY = 2000L
        const val SEARCH_DEBOUNCE_DELAY = 2000L

    }
    fun showHistory(tracksList: List<Track>){
        clNotFound.visibility = View.GONE
        noInternet.visibility = View.GONE
        progressBar.visibility = View.GONE
        if (tracksList.isEmpty()){
            clHistory.visibility = View.GONE
        }
        else {
            historyTrackAdapter.listTracks = ArrayList(tracksList)
            historyTrackAdapter.notifyDataSetChanged()
            clHistory.visibility = View.VISIBLE
        }

    }
    private fun showLoading() {
        clNotFound.visibility = View.GONE
        noInternet.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
        searchTrackAdapter.listTracks = ArrayList()
        searchTrackAdapter.notifyDataSetChanged()
        clHistory.visibility = View.GONE
    }
    private fun showError(){
        progressBar.visibility = View.GONE
        noInternet.visibility = View.VISIBLE
        searchTrackAdapter.listTracks = ArrayList()
        searchTrackAdapter.notifyDataSetChanged()
    }
    private fun showEmpty() {
        progressBar.visibility = View.GONE
        noInternet.visibility = View.GONE
        clNotFound.visibility = View.VISIBLE
        searchTrackAdapter.listTracks = ArrayList()
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






