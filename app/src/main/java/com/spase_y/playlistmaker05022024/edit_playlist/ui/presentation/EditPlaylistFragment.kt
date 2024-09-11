package com.spase_y.playlistmaker05022024.edit_playlist.ui.presentation

import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.google.gson.Gson
import com.spase_y.playlistmaker05022024.R
import com.spase_y.playlistmaker05022024.databinding.FragmentEditPlaylistBinding
import com.spase_y.playlistmaker05022024.edit_playlist.ui.view_model.EditPlaylistViewModel
import com.spase_y.playlistmaker05022024.main.ui.MainActivity
import com.spase_y.playlistmaker05022024.mediateka.playlist.data.model.Playlist
import com.spase_y.playlistmaker05022024.playlist_screen.ui.presentation.PlaylistScreenFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

class EditPlaylistFragment : Fragment() {
    val gson = Gson()

    private var selectedImageUri = "" // Переменная для нового изображения
    private var hasImage = false
    private val vm: EditPlaylistViewModel by viewModel()

    // Обработчик выбора изображения
    private val pickMedia =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                hasImage = true
                loadGlideImage(uri) // Загрузка нового изображения
            }
        }

    private lateinit var binding: FragmentEditPlaylistBinding

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
        // Здесь правильная инициализация binding
        binding = FragmentEditPlaylistBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as MainActivity).hideBottomNavigation()
        val _playlist: String = arguments?.getString("playlist") ?: ""
        val playlist = gson.fromJson(_playlist, Playlist::class.java)
        binding.buttonBack.setOnClickListener{
            onBack(playlist)
        }
        requireActivity().onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                onBack(playlist)

            }
        })
        // Изначально делаем кнопку серой и неактивной
        updateButtonState()

        // Добавляем слушатель для поля "Название"
        binding.etTextName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Не нужно ничего делать здесь
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Обновляем состояние кнопки, когда текст изменяется
                updateButtonState()
            }

            override fun afterTextChanged(s: Editable?) {
                // Не нужно ничего делать здесь
            }
        })



        // Устанавливаем текущие данные плейлиста
        binding.etTextDescription.setText(playlist.description)
        binding.etTextName.setText(playlist.playlistName)

        // Загружаем текущее изображение, если оно есть
        if (playlist.imageUrl.isNotEmpty()) {
            Glide.with(this)
                .load(playlist.imageUrl)
                .transform(CenterCrop(), RoundedCorners(16))
                .into(binding.pickerImage)
        }

        // Клик по картинке для выбора нового изображения
        binding.pickerImage.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        // Обработка сохранения изменений
        binding.btnCreate.setOnClickListener {
            saveChanges(playlist)
        }
    }

    private fun onBack(playlist: Playlist) {
        val fragment = PlaylistScreenFragment().apply {
            arguments = Bundle().apply {
                putString("playlist",gson.toJson(playlist))
            }
        }
        requireActivity()
            .supportFragmentManager
            .beginTransaction().replace(R.id.fragmentContainerView, fragment)
            .commit()
    }

    private fun updateButtonState() {
        val playlistName = binding.etTextName.text.toString().trim()
        val isNameNotEmpty = playlistName.isNotEmpty()

        if (isNameNotEmpty) {
            // Если поле не пустое, делаем кнопку синей и активной
            binding.btnCreate.isEnabled = true
            binding.btnCreate.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.blue
                )
            )
        } else {
            // Если поле пустое, делаем кнопку серой и неактивной
            binding.btnCreate.isEnabled = false
            binding.btnCreate.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.gray
                )
            )
        }
    }

    private fun saveChanges(playlist: Playlist) {
        // Если была выбрана новая картинка, сохраняем её
        var imageUrl = playlist.imageUrl
        if (hasImage) {
            val imageName = "updated_image_${System.currentTimeMillis()}.jpg"
            val file = saveImageToInternalStorage(selectedImageUri, imageName)
            imageUrl = file.absolutePath
        }

        // Обновляем плейлист с новыми данными
        val playlistName = binding.etTextName.text.toString()
        val description = binding.etTextDescription.text.toString()
        val newPlaylist = playlist.copy(
            playlistName = playlistName,
            description = description,
            imageUrl = imageUrl
        )
        vm.editPlaylist(playlist, newPlaylist)
        Toast.makeText(requireContext(), "Изменения сохранены", Toast.LENGTH_SHORT).show()
        openPlaylistScreen(newPlaylist)
    }

    fun openPlaylistScreen(playlist: Playlist) {
        val fragment = PlaylistScreenFragment().apply {
            arguments = Bundle().apply {
                putString("playlist",gson.toJson(playlist))
            }
        }
        requireActivity()
            .supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainerView, fragment)
            .commit()
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
}
