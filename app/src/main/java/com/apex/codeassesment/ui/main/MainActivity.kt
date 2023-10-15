package com.apex.codeassesment.ui.main

import android.content.Context
import android.os.Bundle
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.apex.codeassesment.R
import com.apex.codeassesment.data.model.User
import com.apex.codeassesment.databinding.ActivityMainBinding
import com.apex.codeassesment.di.MainComponent
import com.apex.codeassesment.extensions.hide
import com.apex.codeassesment.extensions.navigateDetails
import com.apex.codeassesment.extensions.show
import com.apex.codeassesment.extensions.showToast
import com.apex.codeassesment.ui.MainViewModelFactory
import com.apex.codeassesment.ui.main.adapter.UserListAdapter
import com.bumptech.glide.Glide
import kotlinx.coroutines.launch
import javax.inject.Inject

// TODO (5 points): Move calls to repository to Presenter or ViewModel.
// TODO (5 points): Use combination of sealed/Dataclasses for exposing the data required by the view from viewModel .
// TODO (3 points): Add tests for viewmodel or presenter.
// TODO (1 point): Add content description to images
// TODO (3 points): Add tests
// TODO (Optional Bonus 10 points): Make a copy of this activity with different name and convert the current layout it is using in
//  Jetpack Compose.
class MainActivity : AppCompatActivity() {

    // TODO (2 points): Convert to view binding
    //this is view biding also initialized in onCreate and used in everywhere in this activity
    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var mainViewModelFactory: MainViewModelFactory
    private lateinit var mainActivityViewModel: MainActivityViewModel
    private lateinit var userListAdapter: UserListAdapter


    private var randomUser: User = User()
        set(value) {
            // TODO (1 point): Use Glide to load images after getting the data from endpoints mentioned in RemoteDataSource

            //Setting image using glide
            setImageInView(binding.mainImage, "${value.picture?.medium}")

            //setting content description
            binding.mainImage.contentDescription = "${value.name?.first} image"
            binding.mainName.text = value.name?.first
            binding.mainEmail.text = value.email
            field = value
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedContext = this

        (applicationContext as MainComponent.Injector).mainComponent.inject(this)
        mainActivityViewModel =
            ViewModelProvider(this, mainViewModelFactory)[MainActivityViewModel::class.java]

        //Removed the array adapter added the recyclerview
        setupUserListAdapter()
        //Added observer to observer change from View Model
        observers()
        //Basically calling api
        mainActivityViewModel.getSavedUser()

        binding.mainSeeDetailsButton.setOnClickListener {


            navigateDetails(randomUser)
        }

        binding.mainRefreshButton.setOnClickListener { mainActivityViewModel.getUser(true) }

        binding.mainUserListButton.setOnClickListener {
            mainActivityViewModel.getUsers()
        }
    }


    private fun observers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainActivityViewModel.user.collect { state ->
                    when (state) {
                        is UiState.Error -> {
                            binding.progressBar.hide()
                            showToast(state.message)
                        }

                        UiState.Loading -> {
                            binding.progressBar.show()
                        }

                        is UiState.SuccessUserList -> {
                            binding.progressBar.hide()
                            userListAdapter.updateList(state.userList)
                        }

                        is UiState.SuccessUser -> {
                            binding.progressBar.hide()
                            randomUser = state.user
                        }

                        UiState.Empty -> {}
                    }
                }
            }
        }
    }

    private fun setupUserListAdapter() {
        userListAdapter =
            UserListAdapter(this, mutableListOf()) { user ->
                navigateDetails(user)
            }
        binding.mainUserList.layoutManager = LinearLayoutManager(this)
        binding.mainUserList.adapter = userListAdapter
    }

    private fun setImageInView(imageView: ImageView, url: String) {
        Glide
            .with(this)
            .load(url)
            .centerCrop()
            .placeholder(R.drawable.ic_launcher_background)
            .into(imageView)
    }

    // TODO (2 points): Convert to extenstion function.
    // Created an Extension function in Package = extensions/ActivityExtension.kt

    companion object {
        var sharedContext: Context? = null
    }
}
