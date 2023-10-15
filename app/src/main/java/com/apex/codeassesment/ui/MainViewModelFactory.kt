package com.apex.codeassesment.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.apex.codeassesment.data.IUserRepository
import com.apex.codeassesment.data.UserRepository
import com.apex.codeassesment.data.local.LocalDataSource
import com.apex.codeassesment.data.remote.RemoteDataSource
import com.apex.codeassesment.ui.main.MainActivityViewModel
import javax.inject.Inject

class MainViewModelFactory @Inject constructor(private val userRepository: IUserRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainActivityViewModel(userRepository) as T
    }
}