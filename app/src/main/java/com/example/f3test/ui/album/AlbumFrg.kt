package com.example.f3test.ui.album

import android.os.Bundle
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
import dagger.android.support.DaggerAppCompatActivity
import dagger.android.support.DaggerFragment
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class AlbumFrg : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject
    lateinit var router: Router
    @Inject
    lateinit var preferences: Preferences

    private lateinit var viewModel: AlbumViewModel
    private val adapter =
        AlbumListAdapter { item: String -> router.navigateTo(Screens.PhotoScr(item)) }


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        (activity as DaggerAppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val binding = DataBindingUtil.inflate<AlbumFrgBinding>(
                inflater,
                R.layout.fragment_album,
                container,
                false
        )
        val view = binding.root
        viewModel = ViewModelProvider(this, viewModelFactory)
                .get(AlbumViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        init(viewModel, binding)
        return view
    }

    private fun init(viewModel: AlbumViewModel, binding: AlbumFrgBinding) {
        viewModel.loading.postValue(false)

        setUpAdapter(binding)
        viewModel.searchAlbumsLiveData().observe(this, Observer {
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


    private fun setUpAdapter(binding: AlbumFrgBinding) {
        binding.list.apply {
            layoutManager = GridLayoutManager(context, 2)
            setHasFixedSize(true)
        }
        binding.list.adapter = adapter
    }
}