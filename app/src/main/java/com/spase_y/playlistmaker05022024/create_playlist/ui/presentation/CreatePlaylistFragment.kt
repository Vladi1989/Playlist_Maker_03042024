package com.spase_y.playlistmaker05022024.create_playlist.ui.presentation

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.spase_y.playlistmaker05022024.databinding.FragmentCreatePlaylistBinding
import androidx.activity.result.PickVisualMediaRequest
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import com.spase_y.playlistmaker05022024.R
import com.spase_y.playlistmaker05022024.create_playlist.ui.view_model.CreatePlaylistViewModel
import com.spase_y.playlistmaker05022024.main.ui.MainActivity
import com.spase_y.playlistmaker05022024.mediateka.playlist.data.model.Playlist
import com.spase_y.playlistmaker05022024.mediateka.playlist.ui.presentation.MedialibraryPlaylistsFragment
import com.spase_y.playlistmaker05022024.mediateka.playlist.ui.view_model.MedialibraryPlaylistsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream


class CreatePlaylistFragment : Fragment() {
    private var _binding: FragmentCreatePlaylistBinding? = null
    private val vm: CreatePlaylistViewModel by viewModel()
    private val binding get() = _binding!!
    private var selectedImageUri = ""
    private var hasImage = false
    private val pickMedia =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                hasImage = true
                loadGlideImage(uri)
            }
        }

    private fun loadGlideImage(uri: Uri) {
        selectedImageUri = uri.toString()
        Glide.with(this)
            .load(uri)
            .transform(CenterCrop(), RoundedCorners(16))
            .into(binding.pickerImage)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreatePlaylistBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as MainActivity).hideBottomNavigation()
        binding.buttonBack.setOnClickListener{
            requireActivity().supportFragmentManager.beginTransaction().remove(this).commit()
        }
        binding.pickerImage.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }
        binding.etTextName.doOnTextChanged { text, start, before, count ->
            if (binding.etTextName.text.toString() == "") {
                setBtnDisable()

            } else {
                setBtnActive()

            }
        }
    }

    private fun setBtnDisable() {
        binding.btnCreate.setBackgroundResource(R.drawable.btn_create_bg)
        binding.btnCreate.setOnClickListener {

        }
    }

    private fun setBtnActive(){
        binding.btnCreate.setBackgroundResource(R.drawable.btn_create_bg_blue)
        binding.btnCreate.setOnClickListener {
            val imageName = "saved_image_${System.currentTimeMillis()}.jpg"
            var imageUrl = ""
            if(hasImage){
                val file = saveImageToInternalStorage(selectedImageUri, imageName)
                imageUrl = file.absolutePath
            }

            val name = binding.etTextName.text.toString()
            val description = binding.etTextDescription.text.toString()
            vm.addToPlaylists(Playlist(imageUrl, name, emptyList(), description))

            val parentFragment = requireActivity().supportFragmentManager
                .findFragmentById(R.id.fragmentContainerView) as? MedialibraryPlaylistsFragment

            parentFragment?.showPlaylistCreatedMessage()

            requireActivity().supportFragmentManager.beginTransaction().remove(this).commit()
        }
    }

    private fun saveImageToInternalStorage(uri: String, imageName: String): File {
        val inputStream: InputStream? =
            requireContext().contentResolver.openInputStream(Uri.parse(uri))
        val file = File(requireContext().filesDir, imageName)
        val outputStream = FileOutputStream(file)

        inputStream?.use { input ->
            outputStream.use { output ->
                input.copyTo(output)
            }
        }
        return file
    }


    override fun onDestroyView() {
        super.onDestroyView()
        (requireActivity() as MainActivity).showBottomNavigation()
        _binding = null
    }
}