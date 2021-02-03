package com.example.f3test.ui.photo

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.example.f3test.Preferences
import com.example.f3test.R
import com.example.f3test.Screens
import com.example.f3test.databinding.AlbumFrgBinding
import com.example.f3test.databinding.PhotoFrgBinding
import com.example.f3test.ui.album.AlbumListAdapter
import com.example.f3test.ui.album.AlbumViewModel
import dagger.android.support.DaggerAppCompatActivity
import dagger.android.support.DaggerFragment
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class PhotoFrg constructor(private var albumsId: String): DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject
    lateinit var router: Router
    @Inject
    lateinit var preferences: Preferences

    private lateinit var viewModel: PhotoViewModel
    private val adapter =
            PhotoListAdapter { url: String? -> showDialog(url) }


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        (activity as DaggerAppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val binding = DataBindingUtil.inflate<PhotoFrgBinding>(
                inflater,
                R.layout.fragment_photo,
                container,
                false
        )
        val view = binding.root
        viewModel = ViewModelProvider(this, viewModelFactory)
                .get(PhotoViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        init(viewModel, binding)
        return view
    }

    private fun init(viewModel: PhotoViewModel, binding: PhotoFrgBinding) {
        viewModel.loading.postValue(false)
        setUpAdapter(binding)

        viewModel.searchPhotosLiveData(albumsId).observe(this, Observer {
            viewLifecycleOwner.lifecycleScope.launch {
                adapter.submitData(it)
            }
        })

        lifecycleScope.launch {
            adapter.loadStateFlow.collectLatest { loadStates ->
                viewModel.loading.postValue(loadStates.refresh is LoadState.Loading)
                if(loadStates.refresh is LoadState.Error) Toast.makeText(context, (loadStates.refresh as LoadState.Error).error.localizedMessage, Toast.LENGTH_LONG).show()
            }
        }

    }


    private fun setUpAdapter(binding: PhotoFrgBinding) {

        binding.list.apply {
            layoutManager = GridLayoutManager(context, 2)
            setHasFixedSize(true)
        }
        binding.list.adapter = adapter

    }

    private fun showDialog(url: String?) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Select this photo as your new photo?")
        builder.setMessage("Do you want to use this photo on main screen?")

        builder.setPositiveButton(android.R.string.yes) { _, _ ->
            preferences.setPhotoUrl(url)
            router.backTo(Screens.HomeScr())
        }

        builder.setNegativeButton(android.R.string.no) { _, _ ->

        }

        builder.show()
    }
}