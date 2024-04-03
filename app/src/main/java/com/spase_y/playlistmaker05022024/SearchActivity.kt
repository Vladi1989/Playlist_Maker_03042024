package com.spase_y.playlistmaker05022024

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.PersistableBundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ScrollView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.spase_y.playlistmaker05022024.adapter.TracksAdapter
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
    val trackAdapter = TracksAdapter()
    val savedTracksAdapter = TracksAdapter()
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://itunes.apple.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val searchHistory by lazy {
        SearchHistory(getSharedPreferences("History shared preference", Context.MODE_PRIVATE))
    }
    val editText by lazy {
        findViewById<EditText>(R.id.editText)
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
        editText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                makeRequest()
                true
            }
            false
        }
        val clear = findViewById<ImageView>(R.id.clear)
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
                clear.visibility = if (p0.toString().isNullOrEmpty()){
                    View.INVISIBLE
                }
                else View.VISIBLE
                if(p0.toString().isNullOrEmpty() && searchHistory.getAllItems().isNotEmpty()){
                    clHistory.visibility = View.VISIBLE
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
        val itunesApiService = retrofit.create<ItunesApiService>()
        itunesApiService.search(editText.text.toString()).enqueue(object:Callback<TracksList>{
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(
                call: Call<TracksList>,
                response: Response<TracksList>
            ) {
                val listTracks = response.body()
                if (listTracks?.resultCount == 0){
                    clNotFound.visibility = View.VISIBLE
                }
                else clNotFound.visibility = View.GONE
                trackAdapter.listTracks = ArrayList(listTracks?.results)
                trackAdapter.notifyDataSetChanged()
            }
            @SuppressLint("NotifyDataSetChanged")
            override fun onFailure(call: Call<TracksList>, t: Throwable) {
                noInternet.visibility = View.VISIBLE
                trackAdapter.listTracks = ArrayList()
                trackAdapter.notifyDataSetChanged()
            }
        })
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(EDIT_TEXT_TAG, findViewById<EditText>(R.id.editText).text.toString())
    }
    companion object{
        const val EDIT_TEXT_TAG = "Edit text outstate"
    }
}

