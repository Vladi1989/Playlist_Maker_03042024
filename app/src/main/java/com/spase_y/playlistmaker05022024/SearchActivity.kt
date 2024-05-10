package com.spase_y.playlistmaker05022024

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
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
import androidx.appcompat.widget.AppCompatButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.spase_y.playlistmaker05022024.App.Companion.PREFS_TAG
import com.spase_y.playlistmaker05022024.adapter.TracksAdapter
import com.spase_y.playlistmaker05022024.domain.models.Track
import com.spase_y.playlistmaker05022024.presentation.PlayerActivity
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory


class SearchActivity : AppCompatActivity() {
    override fun onRestoreInstanceState(
        savedInstanceState: Bundle?,
        persistentState: PersistableBundle?
    ) {
        super.onRestoreInstanceState(savedInstanceState, persistentState)
        if(savedInstanceState != null){
            val editText = findViewById<EditText>(R.id.editText)
            editText.setText(savedInstanceState.getString(EDIT_TEXT_TAG,""))
        }
    }

    private var isClickAllowed = true
    private val handler = Handler(Looper.getMainLooper())

    val trackAdapter = TracksAdapter()
    val savedTracksAdapter = TracksAdapter()
    private val searchRunnable = Runnable { makeRequest() }
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://itunes.apple.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val searchHistory by lazy {
        SearchHistory(getSharedPreferences(PREFS_TAG, Context.MODE_PRIVATE))
    }
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
        btnClearHistory.setOnClickListener {
            searchHistory.deleteAllItems()
            savedTracksAdapter.listTracks = arrayListOf<Track>()
            savedTracksAdapter.notifyDataSetChanged()
            clHistory.visibility = View.GONE
        }
        arrowBack.setOnClickListener {
            finish()
        }
        btnUpdate.setOnClickListener {
            noInternet.visibility = View.GONE
            makeRequest()
        }
        editText.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                searchDebounce()
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })
        val clear = findViewById<ImageView>(R.id.clear)
        trackAdapter.onItemClick = {
            if(clickDebounce()){
                val intent = Intent(this, PlayerActivity::class.java)
                intent.putExtra("trackName",it.trackName)
                intent.putExtra("previewUrl",it.previewUrl)
                intent.putExtra("artistName",it.artistName)
                intent.putExtra("trackTimeMillis",it.trackTimeMillis)
                intent.putExtra("artworkUrl100",it.artworkUrl100)
                intent.putExtra("collectionName",it.collectionName)
                intent.putExtra("releaseDate",it.releaseDate)
                intent.putExtra("primaryGenreName",it.primaryGenreName)
                intent.putExtra("country",it.country)
                startActivity(intent)
                if (searchHistory.getAllItems().contains(it)){
                    searchHistory.deleteItem(it)
                    searchHistory.addItem(it)
                }
                else if(searchHistory.getAllItems().size < 10){
                    searchHistory.addItem(it)
                }
            }

        }

        savedTracksAdapter.onItemClick = {
            if(clickDebounce()){
                val intent = Intent(this, PlayerActivity::class.java)
                intent.putExtra("trackName",it.trackName)
                intent.putExtra("previewUrl",it.previewUrl)
                intent.putExtra("artistName",it.artistName)
                intent.putExtra("trackTimeMillis",it.trackTimeMillis)
                intent.putExtra("artworkUrl100",it.artworkUrl100)
                intent.putExtra("collectionName",it.collectionName)
                intent.putExtra("releaseDate",it.releaseDate)
                intent.putExtra("primaryGenreName",it.primaryGenreName)
                intent.putExtra("country",it.country)

                startActivity(intent)
                if (searchHistory.getAllItems().contains(it)){
                    searchHistory.deleteItem(it)
                    searchHistory.addItem(it)
                }
                else if(searchHistory.getAllItems().size < 10){
                    searchHistory.addItem(it)
                }
            }
        }
        clear.setOnClickListener {
            trackAdapter.listTracks = arrayListOf()
            trackAdapter.notifyDataSetChanged()
            editText.setText("")
            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            inputMethodManager?.hideSoftInputFromWindow(editText.windowToken, 0)
        }
        editText.requestFocus()
        editText.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun afterTextChanged(p0: Editable?) {
                if (p0.toString().isNullOrEmpty()){
                    clear.visibility = View.INVISIBLE
                    trackAdapter.listTracks = ArrayList()
                    trackAdapter.notifyDataSetChanged()
                }
                else clear.visibility = View.VISIBLE

                if(p0.toString().isNullOrEmpty() && searchHistory.getAllItems().isNotEmpty()){
                    clHistory.visibility = View.VISIBLE
                    savedTracksAdapter.listTracks = ArrayList(searchHistory.getAllItems())
                    savedTracksAdapter.notifyDataSetChanged()
                }
                else{
                    clHistory.visibility = View.GONE
                }
            }
        })

        if (editText.text.isEmpty() && searchHistory.getAllItems().isNotEmpty()){
            clHistory.visibility = View.VISIBLE
            rvHistory.layoutManager = LinearLayoutManager(this)
            savedTracksAdapter.listTracks = searchHistory.getAllItems() as ArrayList<Track>
            rvHistory.adapter = savedTracksAdapter
        }
        else{
            clHistory.visibility = View.GONE
        }

        val rv = findViewById<RecyclerView>(R.id.rvTracks)
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = trackAdapter
    }
    fun makeRequest(){
        if (editText.text.toString().isNullOrEmpty()) {
            clNotFound.visibility = View.GONE
            noInternet.visibility = View.GONE
            progressBar.visibility = View.GONE
            return
        }

        trackAdapter.listTracks = ArrayList()
        trackAdapter.notifyDataSetChanged()
        progressBar.visibility = View.VISIBLE
        val itunesApiService = retrofit.create<ItunesApiService>()
        itunesApiService.search(editText.text.toString()).enqueue(object:Callback<TracksList>{
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(
                call: Call<TracksList>,
                response: Response<TracksList>
            ) {
                progressBar.visibility = View.GONE
                noInternet.visibility = View.GONE
                val listTracks = response.body()
                if (listTracks?.resultCount == 0 || listTracks == null){
                    clNotFound.visibility = View.VISIBLE
                }
                else {
                    clNotFound.visibility = View.GONE
                    trackAdapter.listTracks = ArrayList(listTracks?.results)
                    trackAdapter.notifyDataSetChanged()
                }
            }
            @SuppressLint("NotifyDataSetChanged")
            override fun onFailure(call: Call<TracksList>, t: Throwable) {
                progressBar.visibility = View.GONE
                noInternet.visibility = View.VISIBLE
                trackAdapter.listTracks = ArrayList()
                trackAdapter.notifyDataSetChanged()
            }
        })
    }

    private fun clickDebounce() : Boolean {
        val current = isClickAllowed
        if (isClickAllowed) {
            isClickAllowed = false
            handler.postDelayed({ isClickAllowed = true }, CLICK_DEBOUNCE_DELAY)
        }
        return current
    }
    private fun searchDebounce() {
        handler.removeCallbacks(searchRunnable)
        handler.postDelayed(searchRunnable, SEARCH_DEBOUNCE_DELAY)
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(EDIT_TEXT_TAG, findViewById<EditText>(R.id.editText).text.toString())
    }
    companion object{
        const val EDIT_TEXT_TAG = "Edit text outstate"
        private const val CLICK_DEBOUNCE_DELAY = 2000L
        private const val SEARCH_DEBOUNCE_DELAY = 2000L

    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null)
    }
}






