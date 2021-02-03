package com.example.f3test.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.f3test.Preferences
import com.example.f3test.R
import com.example.f3test.Screens
import com.example.f3test.databinding.HomeFrgBinding
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import dagger.android.support.DaggerAppCompatActivity
import dagger.android.support.DaggerFragment
import jp.wasabeef.picasso.transformations.CropCircleTransformation
import ru.terrakok.cicerone.Router
import javax.inject.Inject


class HomeFrg : DaggerFragment() {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject lateinit var router: Router
    @Inject lateinit var preferences: Preferences

    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as DaggerAppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        val binding = DataBindingUtil.inflate<HomeFrgBinding>(
            inflater,
            R.layout.fragment_home,
            container,
            false
        )
        val view = binding.root
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(HomeViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        init(viewModel, binding)
        return view
    }

    private fun init(viewModel: HomeViewModel, binding: HomeFrgBinding) {
        binding.changeAlbumBtn.setOnClickListener { router.navigateTo(Screens.AlbumScr()) }
        viewModel.loading.postValue(true)
        val userID = preferences.getUserID()
        val token = preferences.getToken()

        Picasso.get()
            .load(preferences.getPhotoUrl() ?: "https://graph.facebook.com/$userID/picture?type=large&access_token=$token")
            .transform(CropCircleTransformation())
            .into(binding.imgProfile, object : Callback {
                override fun onSuccess() {
                    viewModel.loading.postValue(false)
                    kotlin.run {
                        pulseAnimation(binding)
                    }

                }

                override fun onError(e: Exception?) {
                    viewModel.loading.postValue(false)
                    Toast.makeText(context, e?.message, Toast.LENGTH_LONG).show()
                }

            })

    }

    private fun pulseAnimation(binding: HomeFrgBinding) {
        binding.imgAnim1.visibility = View.VISIBLE
        binding.imgAnim2.visibility = View.VISIBLE

        binding.imgAnim1.scaleX = 0.5f
        binding.imgAnim1.scaleY = 0.5f
        binding.imgAnim1.alpha = 1f


        binding.imgAnim2.scaleX = 0.5f
        binding.imgAnim2.scaleY = 0.5f
        binding.imgAnim2.alpha = 1f

        binding.imgAnim1.animate().scaleX(4f).scaleY(4f).alpha(0f).setDuration(2000).withEndAction {
            pulseAnimation(binding)
        }

        binding.imgAnim2.animate().scaleX(4f).scaleY(4f).alpha(0f).duration = 1000

    }

}